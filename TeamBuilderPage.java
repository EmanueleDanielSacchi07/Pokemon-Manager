import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

public class TeamBuilderPage extends JPanel { // Pagina teamBuilder
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

    Image sfondo;

    static Color BG_PANEL      = new Color(45, 45, 60, 200);
    static Color BG_FIELD      = new Color(60, 60, 80);
    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color ACCENT_GREEN  = new Color(50, 180, 50);
    static Color TEXT_WHITE    = Color.WHITE;
    static Color TEXT_DIM      = new Color(180, 180, 200);

    TeamBuilderPage(MainController controller) {
        this.setLayout(new GridLayout(5, 1, 0, 4));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        pnl1 = creaPanel(new BorderLayout(10, 10));
        pnl2 = creaPanel(new GridLayout(1, 6, 6, 6));
        pnl3 = creaPanel(new GridLayout(1, 6, 6, 6));
        pnl4 = creaPanel(new GridLayout(1, 4, 6, 6));
        pnl5 = creaPanel(new GridLayout(1, 8, 6, 6));

        aggiungiTitolo(pnl2, "EV");
        aggiungiTitolo(pnl3, "IV");
        aggiungiTitolo(pnl4, "MOSSE");
        aggiungiTitolo(pnl5, "TEAM / NATURA");

        // --- Caricamento dati ---
        pkDex = new Pokedex();
        pkDex.readFromPokedexFile();
        mosseList = new Mosse();
        mosseList.readFromMosseFile();
        natureList = new NatureList();
        teams = new Teams();

        // ---PANEL 1---
        lblPokemon = new JLabel();
        lblPokemon.setPreferredSize(new Dimension(120, 120));
        lblPokemon.setHorizontalAlignment(SwingConstants.CENTER);
        lblPokemon.setBorder(BorderFactory.createLineBorder(ACCENT_YELLOW, 1));
        pnl1.add(lblPokemon, BorderLayout.WEST);

        JPanel pnlCentro = creaPanel(new GridLayout(1, 2, 8, 8));

        cbxSelezPokemon = new JComboBox<>();
        for (int i = 0; i < pkDex.kanto.length; i++) {
            cbxSelezPokemon.addItem(pkDex.kanto[i].nome);
        }
        stilizzaCbx(cbxSelezPokemon, "Seleziona Pokemon:");
        cbxSelezPokemon.addActionListener(new PokemonImageListener(pkDex, lblPokemon));
        pnlCentro.add(cbxSelezPokemon);

        txtNome = creaTxtField("Soprannome:");
        pnlCentro.add(txtNome);
        pnl1.add(pnlCentro, BorderLayout.CENTER);

        JPanel pnlEast = creaPanel(new BorderLayout());
        btnIndietro = creaBottone("← Indietro", ACCENT_RED);
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        pnlEast.add(btnIndietro, BorderLayout.CENTER);
        pnl1.add(pnlEast, BorderLayout.EAST);

        this.add(pnl1);
        cbxSelezPokemon.setSelectedIndex(0);

        // ---PANEL 2--- (EV)
        txtEVhp    = creaTxtField("HP");
        txtEVatk   = creaTxtField("Atk");
        txtEVspatk = creaTxtField("Sp.Atk");
        txtEVdef   = creaTxtField("Def");
        txtEVspdef = creaTxtField("Sp.Def");
        txtEVspeed = creaTxtField("Speed");

        JTextField[] evFields = {txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed};
        for (JTextField field : evFields) {
            field.setDocument(new EVDocument(evFields));
            field.setText("0");
        }

        pnl2.add(txtEVhp);
        pnl2.add(txtEVatk);
        pnl2.add(txtEVspatk);
        pnl2.add(txtEVdef);
        pnl2.add(txtEVspdef);
        pnl2.add(txtEVspeed);
        this.add(pnl2);

        // ---PANEL 3--- (IV)
        txtIVhp    = creaIVField("HP");
        txtIVatk   = creaIVField("Atk");
        txtIVspatk = creaIVField("Sp.Atk");
        txtIVdef   = creaIVField("Def");
        txtIVspdef = creaIVField("Sp.Def");
        txtIVspeed = creaIVField("Speed");

        pnl3.add(txtIVhp);
        pnl3.add(txtIVatk);
        pnl3.add(txtIVspatk);
        pnl3.add(txtIVdef);
        pnl3.add(txtIVspdef);
        pnl3.add(txtIVspeed);
        this.add(pnl3);

        // ---PANEL 4--- (Mosse)
        cbxMossa1 = new JComboBox<>();
        stilizzaCbx(cbxMossa1, "Mossa 1:");
        for (Mossa mossa : mosseList.mosse) cbxMossa1.addItem(mossa.nome);

        cbxMossa2 = new JComboBox<>();
        cbxMossa2.addItem("Nessuna");
        stilizzaCbx(cbxMossa2, "Mossa 2:");
        for (Mossa mossa : mosseList.mosse) cbxMossa2.addItem(mossa.nome);

        cbxMossa3 = new JComboBox<>();
        cbxMossa3.addItem("Nessuna");
        stilizzaCbx(cbxMossa3, "Mossa 3:");
        for (Mossa mossa : mosseList.mosse) cbxMossa3.addItem(mossa.nome);

        cbxMossa4 = new JComboBox<>();
        cbxMossa4.addItem("Nessuna");
        stilizzaCbx(cbxMossa4, "Mossa 4:");
        for (Mossa mossa : mosseList.mosse) cbxMossa4.addItem(mossa.nome);

        pnl4.add(cbxMossa1);
        pnl4.add(cbxMossa2);
        pnl4.add(cbxMossa3);
        pnl4.add(cbxMossa4);
        this.add(pnl4);

        // ---PANEL 5--- (Team + Natura + Aggiungi)
        slotsTeam = new JRadioButton[6];
        grpTeam = new ButtonGroup();
        for (int i = 0; i < 6; i++) {
            slotsTeam[i] = new JRadioButton("Team " + (i + 1));
            slotsTeam[i].setOpaque(false);
            slotsTeam[i].setForeground(ACCENT_YELLOW);
            slotsTeam[i].setFont(new Font("Arial", Font.BOLD, 12));
            grpTeam.add(slotsTeam[i]);
            pnl5.add(slotsTeam[i]);
        }
        slotsTeam[0].setSelected(true);

        cbxNatura = new JComboBox<>();
        stilizzaCbx(cbxNatura, "Natura:");
        for (Natura n : natureList.getAll()) cbxNatura.addItem(n.nome);
        pnl5.add(cbxNatura);

        btnAggiungi = creaBottone("+ Aggiungi", ACCENT_GREEN);
        btnAggiungi.addActionListener(new AddPokemonListener(this, teams));
        pnl5.add(btnAggiungi);

        this.add(pnl5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void aggiungiTitolo(JPanel pnl, String testo) {
        pnl.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT_YELLOW, 1),
            testo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 11), ACCENT_YELLOW));
    }

    private JPanel creaPanel(LayoutManager layout) {
        JPanel p = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return p;
    }

    private JButton creaBottone(String testo, Color colore) {
        JButton btn = new JButton(testo);
        btn.setBackground(colore);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField creaTxtField(String titolo) {
        JTextField txt = new JTextField();
        txt.setBackground(BG_FIELD);
        txt.setForeground(TEXT_WHITE);
        txt.setCaretColor(TEXT_WHITE);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 120), 1),
            titolo,
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 10),
            TEXT_DIM
        ));
        return txt;
    }

    private JTextField creaIVField(String titolo) {
        JTextField txt = creaTxtField(titolo);
        txt.setDocument(new IVDocument());
        txt.setText("31");
        return txt;
    }

    private void stilizzaCbx(JComboBox<String> cbx, String titolo) {
        cbx.setBackground(BG_FIELD);
        cbx.setForeground(TEXT_WHITE);
        cbx.setFont(new Font("Arial", Font.PLAIN, 13));
        cbx.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT_YELLOW, 1),
            titolo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 10),
            ACCENT_YELLOW
        ));
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