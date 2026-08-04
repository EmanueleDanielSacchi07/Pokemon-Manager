import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {

    private Clip clip;

    // Avvia la riproduzione in loop continuo del file audio passato
    public void avviaLoop(String percorsoFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(percorsoFile));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Loop infinito, dall'inizio alla fine del file
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            System.out.println("Errore riproduzione musica: " + e.getMessage());
        }
    }

    // Ferma la musica
    public void ferma() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Imposta il volume (0.0 = silenzio, 1.0 = originale). Alcuni sistemi supportano anche valori >1.0
    public void setVolume(float volume) {
        if (clip == null) return;
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = min + (max - min) * volume; // conversione lineare semplificata
            gainControl.setValue(gain);
        } catch (Exception e) {
            System.out.println("Volume non supportato su questo sistema: " + e.getMessage());
        }
    }
}