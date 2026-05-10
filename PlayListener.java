/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.event.*;
import javax.swing.*;

// Tramite la page selectTeam questo action listener seleziona
//  i team da mandare alla play page per fare partire la partita
public class PlayListener implements ActionListener {

    private MainController controller;
    private JComboBox<String> cbxTeam1;
    private JComboBox<String> cbxTeam2;
    private Teams teams;

    PlayListener(MainController controller, JComboBox<String> cbxTeam1, JComboBox<String> cbxTeam2, Teams teams) {
        this.controller = controller;
        this.cbxTeam1 = cbxTeam1;
        this.cbxTeam2 = cbxTeam2;
        this.teams = teams;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int indexTeam1 = cbxTeam1.getSelectedIndex();
        int indexTeam2 = cbxTeam2.getSelectedIndex();

        Team t1 = teams.teams[indexTeam1];
        Team t2 = teams.teams[indexTeam2];

        System.out.println("Team1: " + t1.nome + " - pokemon: " + t1.countPokemon);
        System.out.println("Team2: " + t2.nome + " - pokemon: " + t2.countPokemon);
        for (int i = 0; i < 6; i++) {
            System.out.println("T1[" + i + "]: " + t1.pokemons[i]);
            System.out.println("T2[" + i + "]: " + t2.pokemons[i]);
        }

        controller.showPlayPage(t1, t2);
    }
}