import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

public class TeamBuilderPage extends JPanel {
    JComboBox<String> cbxSelezPokemon;
    JTextField txtNome, txtIVhp, txtIVatk, txtIVspatk, txtIVdef, txtIVspdef, txtIVspeed;
    JTextField txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed;

    JComboBox<String> cbxMosse;
    JComboBox<String> cbxNatura;

    JButton btnAggiungi, btnIndietro;
    JRadioButton btnSelezTeam;

    JPanel pnl1, pnl2, pnl3, pnl4, pnl5, pnl6;

    JLabel lblPokemon;

    TeamBuilderPage(MainController controller){
        this.setLayout(new GridLayout(6,1));

        pnl1 = new JPanel(new BorderLayout(10, 10));
        pnl1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnl2 = new JPanel();
        pnl2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnl3 = new JPanel();
        pnl3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnl4 = new JPanel();
        pnl5 = new JPanel();
        pnl6 = new JPanel();

        //  ---PANEL 1---

        // --- WEST: immagine pokemon ---
        lblPokemon = new JLabel();
        lblPokemon.setPreferredSize(new Dimension(120, 120));
        lblPokemon.setHorizontalAlignment(SwingConstants.CENTER);
        pnl1.add(lblPokemon, BorderLayout.WEST);

        // --- CENTER: combobox + soprannome ---
        JPanel pnlCentro = new JPanel(new GridLayout(1, 2, 5, 5));

        cbxSelezPokemon = new JComboBox<>();
        Pokedex pkDex = new Pokedex();
        pkDex.readFromPokedexFile();
        for(int i = 0; i < pkDex.kanto.length; i++) {
            cbxSelezPokemon.addItem(pkDex.kanto[i].nome);
        }
        cbxSelezPokemon.setBorder(new TitledBorder("Seleziona Pokemon:"));
        cbxSelezPokemon.setFont(new Font("Arial", Font.PLAIN, 14));
        cbxSelezPokemon.addActionListener(new PokemonImageListener(pkDex, lblPokemon));
        pnlCentro.add(cbxSelezPokemon);

        txtNome = new JTextField();
        txtNome.setBorder(new TitledBorder("Soprannome:"));
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlCentro.add(txtNome);

        pnl1.add(pnlCentro, BorderLayout.CENTER);

        // --- EAST: bottone indietro ---
        btnIndietro = new JButton("Indietro");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        pnl1.add(btnIndietro, BorderLayout.EAST);

        this.add(pnl1);

        // Carica immagine del primo pokemon subito
        cbxSelezPokemon.setSelectedIndex(0);

        //  ---PANEL 2--- (Ev textField)

        pnl2 = new JPanel(new GridLayout(1, 6, 5 ,5));

        txtEVhp = new JTextField();
        txtEVhp.setBorder(new TitledBorder("Ev Hp:"));
        pnl2.add(txtEVhp);

        txtEVatk = new JTextField();
        txtEVatk.setBorder(new TitledBorder("Ev Atk:"));
        pnl2.add(txtEVatk);

        txtEVspatk = new JTextField();
        txtEVspatk.setBorder(new TitledBorder("Ev Special Atk:"));
        pnl2.add(txtEVspatk);

        txtEVdef = new JTextField();
        txtEVdef.setBorder(new TitledBorder("Ev Def:"));
        pnl2.add(txtEVdef);

        txtEVspdef = new JTextField();
        txtEVspdef.setBorder(new TitledBorder("Ev Special Def:"));
        pnl2.add(txtEVspdef);
        
        txtEVspeed = new JTextField();
        txtEVspeed.setBorder(new TitledBorder("Ev Speed:"));
        pnl2.add(txtEVspeed);
        
        this.add(pnl2);

        //  ---PANEL 3--- (Iv textField)
        
        pnl3 = new JPanel(new GridLayout(1, 6, 5 ,5));

        txtIVhp = new JTextField();
        txtIVhp.setBorder(new TitledBorder("Iv Hp:"));
        txtIVhp.setDocument(new IVDocument());
        txtIVhp.setText("31");
        pnl3.add(txtIVhp);

        txtIVatk = new JTextField();
        txtIVatk.setBorder(new TitledBorder("Iv Atk:"));
        txtIVatk.setDocument(new IVDocument());
        txtIVatk.setText("31");
        pnl3.add(txtIVatk);

        txtIVspatk = new JTextField();
        txtIVspatk.setBorder(new TitledBorder("Iv Special Atk:"));
        txtIVspatk.setDocument(new IVDocument());
        txtIVspatk.setText("31");
        pnl3.add(txtIVspatk);

        txtIVdef = new JTextField();
        txtIVdef.setBorder(new TitledBorder("Iv Def:"));
        txtIVdef.setDocument(new IVDocument());
        txtIVdef.setText("31");
        pnl3.add(txtIVdef);

        txtIVspdef = new JTextField();
        txtIVspdef.setBorder(new TitledBorder("Iv Special Def:"));
        txtIVspdef.setDocument(new IVDocument());
        txtIVspdef.setText("31");
        pnl3.add(txtIVspdef);
        
        txtIVspeed = new JTextField();
        txtIVspeed.setBorder(new TitledBorder("Iv Speed:"));
        txtIVspeed.setDocument(new IVDocument());
        txtIVspeed.setText("31");
        pnl3.add(txtIVspeed);
        
        this.add(pnl3);
    }
}