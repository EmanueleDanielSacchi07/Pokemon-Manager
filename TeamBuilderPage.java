import java.awt.GridLayout;

import javax.swing.*;

public class TeamBuilderPage extends JPanel {
    JComboBox cbxSelezPokemon;
    JTextField txtNome, txtIVhp, txtIVatk, txtIVspatk, txtIVdef, txtIVspdef, txtIVspeed;
    JTextField txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed;

    JComboBox[] cbxMosse;
    JComboBox cbxNatura;

    JButton btnAggiungi;
    JRadioButton btnSelezTeam;

    TeamBuilderPage(){
        this.setLayout(new GridLayout(6,1));
    }
}
