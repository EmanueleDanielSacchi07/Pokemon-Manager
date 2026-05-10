/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.event.*;
import java.awt.Image;
import javax.swing.*;

// Listener della cbx di TeamBuilderPage, quando cambia pokemon 
// della cbx cambia anche la lbl con l'immagine del pokemon esatto
public class PokemonImageListener implements ActionListener {
    
    Pokedex pokedex;
    JLabel lblPokemon;

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
    
                ImageIcon icon = new ImageIcon(p.immagine);
                // Rimpicciolisce 
                Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblPokemon.setIcon(new ImageIcon(scaled));
                lblPokemon.revalidate();
                lblPokemon.repaint();
                break;
            }
        }
    }
}