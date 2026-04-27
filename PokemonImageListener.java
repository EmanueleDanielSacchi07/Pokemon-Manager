import java.awt.event.*;
import java.io.File;
import java.awt.Image;
import javax.swing.*;

public class PokemonImageListener implements ActionListener {
    
    private Pokedex pokedex;
    private JLabel lblPokemon;

    PokemonImageListener(Pokedex pokedex, JLabel lblPokemon) {
        this.pokedex = pokedex;
        this.lblPokemon = lblPokemon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> cbx = (JComboBox<String>) e.getSource();
        String selezionato = (String) cbx.getSelectedItem();

        for (Pokemon p : pokedex.kanto) {
            if (p != null && p.nome.equals(selezionato)) {
                System.out.println("Carico immagine: " + p.immagine); // debug
                ImageIcon icon = new ImageIcon(p.immagine);
                Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblPokemon.setIcon(new ImageIcon(scaled));
                lblPokemon.revalidate();
                lblPokemon.repaint();
                File f = new File(p.immagine);
                System.out.println("File esiste: " + f.exists());
                System.out.println("Path assoluto: " + f.getAbsolutePath());
                break;
            }
        }
    }
}