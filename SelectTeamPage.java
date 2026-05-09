import java.awt.*;
import javax.swing.*;

public class SelectTeamPage extends JPanel {
    JComboBox<String> cbxTeam1, cbxTeam2;
    JButton btnPlay, btnIndietro;
    JPanel pnlMid, pnlCenter;
    Teams teams;
    MainController controller;
    Image sfondo;

    static final Color ACCENT_RED    = new Color(220, 50, 50);
    static final Color ACCENT_YELLOW = new Color(255, 220, 50);
    static final Color BG_PANEL      = new Color(45, 45, 60, 180);
    static final Color TEXT_WHITE    = Color.WHITE;

    SelectTeamPage(MainController controller) {
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        // --- Titolo ---
        JLabel lblTitolo = new JLabel("Seleziona i Team");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitolo.setForeground(ACCENT_YELLOW);
        lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Caricamento team ---
        teams = new Teams();
        Mosse mosseList = new Mosse();
        mosseList.readFromMosseFile();
        for (int i = 0; i < 6; i++) {
            teams.readPokemonFromFile(i, mosseList);
        }

        // --- ComboBox ---
        cbxTeam1 = creaCbx(teams);
        cbxTeam2 = creaCbx(teams);

        // --- Bottoni ---
        btnIndietro = creaBottone("Indietro");
        btnPlay     = creaBottone("Gioca");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        btnPlay.addActionListener(new PlayListener(controller, cbxTeam1, cbxTeam2, teams));

        pnlMid = new JPanel(new GridLayout(2, 1, 5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        pnlMid.setOpaque(false);
        pnlMid.add(btnIndietro);
        pnlMid.add(btnPlay);

        pnlCenter = new JPanel(new GridLayout(1, 3, 20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                // trasparente
            }
        };
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        pnlCenter.add(cbxTeam1);
        pnlCenter.add(pnlMid);
        pnlCenter.add(cbxTeam2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        gbc.gridy = 0;
        this.add(lblTitolo, gbc);

        gbc.gridy = 1;
        this.add(pnlCenter, gbc);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                ricaricaTeams();
            }
        });
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

    private void ricaricaTeams() {
        Mosse mosseList = new Mosse();
        mosseList.readFromMosseFile();
        teams = new Teams();
        for (int i = 0; i < 6; i++) {
            teams.readPokemonFromFile(i, mosseList);
        }
        cbxTeam1.removeAllItems();
        cbxTeam2.removeAllItems();
        for (int i = 0; i < 6; i++) {
            cbxTeam1.addItem(teams.teams[i].nome);
            cbxTeam2.addItem(teams.teams[i].nome);
        }
        for (var l : btnPlay.getActionListeners()) {
            btnPlay.removeActionListener(l);
        }
        btnPlay.addActionListener(new PlayListener(controller, cbxTeam1, cbxTeam2, teams));
    }

    private JComboBox<String> creaCbx(Teams teams) {
        JComboBox<String> cbx = new JComboBox<>();
        cbx.setBackground(new Color(60, 60, 80));
        cbx.setForeground(TEXT_WHITE);
        cbx.setFont(new Font("Arial", Font.PLAIN, 14));
        for (int i = 0; i < teams.teams.length; i++) {
            cbx.addItem(teams.teams[i].nome);
        }
        return cbx;
    }

    private JButton creaBottone(String testo) {
        JButton btn = new JButton(testo);
        btn.setBackground(ACCENT_RED);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}