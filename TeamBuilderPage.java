import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

public class TeamBuilderPage extends JPanel {
    JComboBox<String> cbxSelezPokemon;
    JTextField txtNome, txtIVhp, txtIVatk, txtIVspatk, txtIVdef, txtIVspdef, txtIVspeed;
    JTextField txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed;

    JComboBox<String> cbxMossa1, cbxMossa2, cbxMossa3, cbxMossa4;
    JComboBox<String> cbxNatura;

    JButton btnAggiungi, btnIndietro;

    JPanel pnl1, pnl2, pnl3, pnl4, pnl5;

    JRadioButton[] slotsTeam;
    ButtonGroup grpTeam;

    JLabel lblPokemon;

    Pokedex pkDex;
    Mosse mosseList;
    NatureList natureList;
    Teams teams;

    TeamBuilderPage(MainController controller) {
        this.setLayout(new GridLayout(5, 1));

        pnl1 = new JPanel(new BorderLayout(10, 10));
        pnl1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl2 = new JPanel();
        pnl2.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl3 = new JPanel();
        pnl3.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl4 = new JPanel();
        pnl4.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl5 = new JPanel();
        pnl5.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Caricamento dati ---
        pkDex = new Pokedex();
        pkDex.readFromPokedexFile();

        mosseList = new Mosse();
        mosseList.readFromMosseFile();

        natureList = new NatureList();
        teams = new Teams();

        // ---PANEL 1---

        // WEST: immagine pokemon
        lblPokemon = new JLabel();
        lblPokemon.setPreferredSize(new Dimension(120, 120));
        lblPokemon.setHorizontalAlignment(SwingConstants.CENTER);
        pnl1.add(lblPokemon, BorderLayout.WEST);

        // CENTER: combobox + soprannome
        JPanel pnlCentro = new JPanel(new GridLayout(1, 2, 5, 5));

        cbxSelezPokemon = new JComboBox<>();
        for (int i = 0; i < pkDex.kanto.length; i++) {
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

        // EAST: bottone indietro
        btnIndietro = new JButton("Indietro");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        pnl1.add(btnIndietro, BorderLayout.EAST);

        this.add(pnl1);
        cbxSelezPokemon.setSelectedIndex(0);

        // ---PANEL 2--- (EV)
        pnl2 = new JPanel(new GridLayout(1, 6, 5, 5));

        txtEVhp    = new JTextField();
        txtEVatk   = new JTextField();
        txtEVspatk = new JTextField();
        txtEVdef   = new JTextField();
        txtEVspdef = new JTextField();
        txtEVspeed = new JTextField();

        JTextField[] evFields = {txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed};
        for (JTextField field : evFields) {
            field.setDocument(new EVDocument(evFields));
            field.setText("0");
        }

        txtEVhp.setBorder(new TitledBorder("Ev Hp:"));
        pnl2.add(txtEVhp);
        txtEVatk.setBorder(new TitledBorder("Ev Atk:"));
        pnl2.add(txtEVatk);
        txtEVspatk.setBorder(new TitledBorder("Ev Special Atk:"));
        pnl2.add(txtEVspatk);
        txtEVdef.setBorder(new TitledBorder("Ev Def:"));
        pnl2.add(txtEVdef);
        txtEVspdef.setBorder(new TitledBorder("Ev Special Def:"));
        pnl2.add(txtEVspdef);
        txtEVspeed.setBorder(new TitledBorder("Ev Speed:"));
        pnl2.add(txtEVspeed);

        this.add(pnl2);

        // ---PANEL 3--- (IV)
        pnl3 = new JPanel(new GridLayout(1, 6, 5, 5));

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

        // ---PANEL 4--- (Mosse)
        pnl4 = new JPanel(new GridLayout(1, 4, 5, 5));

        cbxMossa1 = new JComboBox<>();
        cbxMossa1.setBorder(new TitledBorder("Seleziona Mossa 1:"));
        cbxMossa1.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Mossa mossa : mosseList.mosse) {
            cbxMossa1.addItem(mossa.nome);
        }
        pnl4.add(cbxMossa1);

        cbxMossa2 = new JComboBox<>();
        cbxMossa2.addItem("Nessuna");
        cbxMossa2.setBorder(new TitledBorder("Seleziona Mossa 2:"));
        cbxMossa2.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Mossa mossa : mosseList.mosse) {
            cbxMossa2.addItem(mossa.nome);
        }
        pnl4.add(cbxMossa2);

        cbxMossa3 = new JComboBox<>();
        cbxMossa3.addItem("Nessuna");
        cbxMossa3.setBorder(new TitledBorder("Seleziona Mossa 3:"));
        cbxMossa3.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Mossa mossa : mosseList.mosse) {
            cbxMossa3.addItem(mossa.nome);
        }
        pnl4.add(cbxMossa3);

        cbxMossa4 = new JComboBox<>();
        cbxMossa4.addItem("Nessuna");
        cbxMossa4.setBorder(new TitledBorder("Seleziona Mossa 4:"));
        cbxMossa4.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Mossa mossa : mosseList.mosse) {
            cbxMossa4.addItem(mossa.nome);
        }
        pnl4.add(cbxMossa4);

        this.add(pnl4);

        // ---PANEL 5--- (Team + Natura + Aggiungi)
        pnl5 = new JPanel(new GridLayout(1, 8, 5, 5));

        slotsTeam = new JRadioButton[6];
        grpTeam = new ButtonGroup();
        for (int i = 0; i < 6; i++) {
            slotsTeam[i] = new JRadioButton("Team " + (i + 1));
            grpTeam.add(slotsTeam[i]);
            pnl5.add(slotsTeam[i]);
        }
        slotsTeam[0].setSelected(true);

        cbxNatura = new JComboBox<>();
        cbxNatura.setBorder(new TitledBorder("Seleziona Natura"));
        cbxNatura.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Natura n : natureList.getAll()) {
            cbxNatura.addItem(n.nome);
        }
        pnl5.add(cbxNatura);

        btnAggiungi = new JButton("Aggiungi");
        btnAggiungi.setBorder(new TitledBorder("New Pokemon"));
        btnAggiungi.setFont(new Font("Arial", Font.PLAIN, 14));
        btnAggiungi.addActionListener(new AddPokemonListener(this, teams));
        pnl5.add(btnAggiungi);

        this.add(pnl5);
    }

    public void reset() {
        cbxSelezPokemon.setSelectedIndex(0);
        txtNome.setText("");
        txtIVhp.setText("31");
        txtIVatk.setText("31");
        txtIVspatk.setText("31");
        txtIVdef.setText("31");
        txtIVspdef.setText("31");
        txtIVspeed.setText("31");
        txtEVhp.setText("0");
        txtEVatk.setText("0");
        txtEVspatk.setText("0");
        txtEVdef.setText("0");
        txtEVspdef.setText("0");
        txtEVspeed.setText("0");
        cbxMossa1.setSelectedIndex(0);
        cbxMossa2.setSelectedIndex(0);
        cbxMossa3.setSelectedIndex(0);
        cbxMossa4.setSelectedIndex(0);
        cbxNatura.setSelectedIndex(0);
        slotsTeam[0].setSelected(true);
    }
}