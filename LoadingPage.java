import java.awt.*;
import javax.swing.*;

public class LoadingPage extends JPanel {

    static Color BG_DARK       = new Color(30, 30, 40);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color TEXT_WHITE    = Color.WHITE;

    JProgressBar progressBar;
    JLabel lblStato;
    JLabel lblPercentuale;
    int totale = 1025;
    Image sfondo;

    LoadingPage(MainController controller) {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titolo
        JLabel lblTitolo = new JLabel("Caricamento Pokemon...");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitolo.setForeground(ACCENT_YELLOW);
        gbc.gridy = 0;
        this.add(lblTitolo, gbc);

        // Stato (es. "Caricamento dragonite...")
        lblStato = new JLabel("Inizializzazione...");
        lblStato.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStato.setForeground(TEXT_WHITE);
        gbc.gridy = 1;
        this.add(lblStato, gbc);

        // Barra di progresso
        progressBar = new JProgressBar(0, totale);
        progressBar.setPreferredSize(new Dimension(500, 30));
        progressBar.setStringPainted(false);
        progressBar.setForeground(ACCENT_YELLOW);
        progressBar.setBackground(new Color(45, 45, 60));
        progressBar.setBorderPainted(false);
        gbc.gridy = 2;
        this.add(progressBar, gbc);

        // Percentuale
        lblPercentuale = new JLabel("0 / " + totale);
        lblPercentuale.setFont(new Font("Arial", Font.BOLD, 14));
        lblPercentuale.setForeground(ACCENT_YELLOW);
        gbc.gridy = 3;
        this.add(lblPercentuale, gbc);
    }

    // Aggiorna la barra — chiamato dal thread di caricamento
    public void aggiorna(int fatto) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(fatto);
            lblPercentuale.setText(fatto + " / " + totale);
            lblStato.setText("Caricati " + fatto + " pokemon...");
        });
    }

    // Chiamato quando il caricamento è completo
    // Chiamato quando il caricamento è completo
    public void completato(MainController controller, String pageDestinazione) {
        SwingUtilities.invokeLater(() -> {
            lblStato.setText("Caricamento completato!");
            Timer timer = new Timer(500, e -> controller.showPage(pageDestinazione));
            timer.setRepeats(false);
            timer.start();
        });
    }

    // Mostra un messaggio diverso quando i dati vengono letti dalla cache locale (istantaneo)
    public void mostraCaricamentoDaCache() {
        SwingUtilities.invokeLater(() -> {
        lblStato.setText("Lettura dati salvati...");
        progressBar.setIndeterminate(true);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}