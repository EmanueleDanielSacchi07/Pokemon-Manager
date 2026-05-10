/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

// Listener che so occupa di creare l'oggetto pokemon quando l'utente tramite 
// la pagina TeamBuiler ne crea uno e preme su aggiungi
public class AddPokemonListener implements ActionListener {

    private TeamBuilderPage page;
    private Teams teams;

    AddPokemonListener(TeamBuilderPage page, Teams teams) {
        this.page = page;
        this.teams = teams;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // --- Trova quale slot team è selezionato ---
        int teamIndex = -1;
        for (int i = 0; i < page.slotsTeam.length; i++) {
            if (page.slotsTeam[i].isSelected()) {
                teamIndex = i;
                break;
            }
        }
        if (teamIndex == -1) {
            JOptionPane.showMessageDialog(page, "Seleziona uno slot del team!");
            return;
        }

        // --- Pokemon base dal pokedex ---
        String nomePokemon = (String) page.cbxSelezPokemon.getSelectedItem();
        Pokemon base = null;
        for (Pokemon p : page.pkDex.kanto) {
            if (p != null && p.nome.equals(nomePokemon)) {
                base = p;
                break;
            }
        }
        if (base == null) {
            JOptionPane.showMessageDialog(page, "Pokemon non trovato!");
            return;
        }

        // --- Soprannome ---
        String nomePersonale = page.txtNome.getText().trim();

        // --- IV ---
        Iv iv = new Iv(
            Double.parseDouble(page.txtIVhp.getText()),
            Double.parseDouble(page.txtIVatk.getText()),
            Double.parseDouble(page.txtIVspatk.getText()),
            Double.parseDouble(page.txtIVdef.getText()),
            Double.parseDouble(page.txtIVspdef.getText()),
            Double.parseDouble(page.txtIVspeed.getText())
        );

        // --- EV ---
        Ev ev = new Ev(
            Double.parseDouble(page.txtEVhp.getText()),
            Double.parseDouble(page.txtEVatk.getText()),
            Double.parseDouble(page.txtEVspatk.getText()),
            Double.parseDouble(page.txtEVdef.getText()),
            Double.parseDouble(page.txtEVspdef.getText()),
            Double.parseDouble(page.txtEVspeed.getText())
        );

        // --- Mosse ---
        ArrayList<Mossa> mosseScelte = new ArrayList<>();
        JComboBox<?>[] cbxMosse = {page.cbxMossa1, page.cbxMossa2, page.cbxMossa3, page.cbxMossa4};
        for (JComboBox<?> cbx : cbxMosse) {
            String nomeMossa = (String) cbx.getSelectedItem();
            if (nomeMossa == null || nomeMossa.equals("Nessuna")) continue;
            for (Mossa mossa : page.mosseList.mosse) {
                if (mossa != null && mossa.nome.equals(nomeMossa)) {
                    mosseScelte.add(mossa);
                    break;
                }
            }
        }

        // --- Natura ---
        String nomNatura = (String) page.cbxNatura.getSelectedItem();
        Natura natura = null;
        for (Natura n : page.natureList.getAll()) {
            if (n.nome.equals(nomNatura)) {
                natura = n;
                break;
            }
        }

        // --- Crea il pokemon ---
        Pokemon nuovo = new Pokemon(
            base.nome,
            nomePersonale,
            base.tipi,
            100,
            ev,
            iv,
            mosseScelte,
            base.bst,
            natura,
            base.immagine
        );

        // --- Scrivi sul file ---
        boolean successo = teams.writePokemonOnFile(nuovo, teamIndex);
        if (successo) {
            JOptionPane.showMessageDialog(page, nuovo.nome + " aggiunto al Team " + (teamIndex + 1) + "!");
            page.reset();
        } else {
            JOptionPane.showMessageDialog(page, "Team " + (teamIndex + 1) + " è già pieno!");
        }
    }
}