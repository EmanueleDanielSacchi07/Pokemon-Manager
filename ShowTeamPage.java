import java.awt.*;
import javax.swing.*;
import java.io.*;

public class ShowTeamPage extends JPanel {

    static final Color ACCENT_RED    = new Color(220, 50, 50);
    static final Color ACCENT_YELLOW = new Color(255, 220, 50);
    static final Color BG_PANEL      = new Color(45, 45, 60, 180); // semitrasparente
    static final Color TEXT_WHITE    = Color.WHITE;

    JComboBox<String> cbxTeam;
    JButton btnIndietro;
    JPanel pnlTeam;
    Teams teams;
    Mosse mosseList;
    Image sfondo;

    ShowTeamPage(MainController controller) {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        // --- Caricamento dati ---
        mosseList = new Mosse();
        mosseList.readFromMosseFile();
        teams = new Teams();
        for (int i = 0; i < 6; i++) {
            teams.readPokemonFromFile(i, mosseList);
        }

        // --- NORTH ---
        JPanel pnlNorth = new JPanel(new BorderLayout(10, 10));
        pnlNorth.setOpaque(false);
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblTitolo = new JLabel("Visualizza Team");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitolo.setForeground(ACCENT_YELLOW);
        lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
        pnlNorth.add(lblTitolo, BorderLayout.NORTH);

        JPanel pnlControlli = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        pnlControlli.setOpaque(false);

        cbxTeam = new JComboBox<>();
        cbxTeam.setBackground(new Color(60, 60, 80));
        cbxTeam.setForeground(TEXT_WHITE);
        cbxTeam.setFont(new Font("Arial", Font.PLAIN, 14));
        for (int i = 0; i < 6; i++) {
            cbxTeam.addItem(teams.teams[i].nome);
        }
        cbxTeam.addActionListener(e -> aggiornaTeam());
        pnlControlli.add(cbxTeam);

        btnIndietro = creaBottone("Indietro");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        pnlControlli.add(btnIndietro);

        pnlNorth.add(pnlControlli, BorderLayout.CENTER);
        this.add(pnlNorth, BorderLayout.NORTH);

        // --- CENTER ---
        pnlTeam = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlTeam.setOpaque(false);
        pnlTeam.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(pnlTeam, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                ricaricaTeams();
                aggiornaTeam();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            // overlay scuro per leggibilità
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void aggiornaTeam() {
        pnlTeam.removeAll();
        int teamIndex = cbxTeam.getSelectedIndex();
        Team team = teams.teams[teamIndex];

        for (int i = 0; i < 6; i++) {
            Pokemon p = team.pokemons[i];
            pnlTeam.add(creaPokemonCard(p, i, teamIndex));
        }

        pnlTeam.revalidate();
        pnlTeam.repaint();
    }

    private void ricaricaTeams() {
        mosseList = new Mosse();
        mosseList.readFromMosseFile();
        teams = new Teams();
        for (int i = 0; i < 6; i++) {
            teams.readPokemonFromFile(i, mosseList);
        }

        var listeners = cbxTeam.getActionListeners();
        for (var l : listeners) cbxTeam.removeActionListener(l);

        cbxTeam.removeAllItems();
        for (int i = 0; i < 6; i++) {
            cbxTeam.addItem(teams.teams[i].nome);
        }

        cbxTeam.addActionListener(e -> aggiornaTeam());
    }

    private JPanel creaPokemonCard(Pokemon p, int slotIndex, int teamIndex) {
        JPanel card = new JPanel(new BorderLayout(5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_YELLOW, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        if (p == null) {
            JLabel lblVuoto = new JLabel("Slot vuoto");
            lblVuoto.setForeground(new Color(180, 180, 200));
            lblVuoto.setFont(new Font("Arial", Font.ITALIC, 14));
            lblVuoto.setHorizontalAlignment(SwingConstants.CENTER);
            card.add(lblVuoto, BorderLayout.CENTER);
            return card;
        }

        // --- Immagine ---
        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon(p.immagine);
        Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(scaled));
        card.add(lblImg, BorderLayout.WEST);

        // --- Info ---
        JPanel pnlInfo = new JPanel(new GridLayout(6, 1, 2, 2));
        pnlInfo.setOpaque(false);

        String soprannome = (p.nomePersonale != null && !p.nomePersonale.isBlank())
            ? p.nomePersonale + " (" + p.nome + ")" : p.nome;
        pnlInfo.add(creaLabel(soprannome, Font.BOLD, 13));

        String tipi = p.tipi.stream().map(t -> t.getNome()).reduce((a, b) -> a + " / " + b).orElse("");
        pnlInfo.add(creaLabel("Tipo: " + tipi, Font.PLAIN, 12));

        pnlInfo.add(creaLabel("Natura: " + (p.natura != null ? p.natura.nome : "N/A"), Font.PLAIN, 12));

        String mosse = p.mosse.stream().map(m -> m.nome).reduce((a, b) -> a + ", " + b).orElse("Nessuna");
        pnlInfo.add(creaLabel("Mosse: " + mosse, Font.PLAIN, 11));

        pnlInfo.add(creaLabel("Lv: " + p.livello, Font.PLAIN, 12));

        JButton btnElimina = creaBottone("Elimina");
        btnElimina.setBackground(ACCENT_RED);
        btnElimina.setFont(new Font("Arial", Font.BOLD, 11));
        btnElimina.addActionListener(e -> eliminaPokemon(slotIndex, teamIndex));
        pnlInfo.add(btnElimina);

        card.add(pnlInfo, BorderLayout.CENTER);
        return card;
    }

    private void eliminaPokemon(int slotIndex, int teamIndex) {
        Team team = teams.teams[teamIndex];
        String nomePokemon = team.pokemons[slotIndex].nome;

        int conferma = JOptionPane.showConfirmDialog(
            this,
            "Vuoi eliminare " + nomePokemon + " dal team?",
            "Conferma eliminazione",
            JOptionPane.YES_NO_OPTION
        );

        if (conferma != JOptionPane.YES_OPTION) return;

        team.pokemons[slotIndex] = null;
        team.countPokemon--;

        String fileName = "teams/Team" + (teamIndex + 1) + ".csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < 6; i++) {
                if (team.pokemons[i] != null) {
                    pw.println(team.pokemons[i].toStringCsv());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione!");
            return;
        }

        JOptionPane.showMessageDialog(this, nomePokemon + " eliminato dal team!");
        aggiornaTeam();
    }

    private JLabel creaLabel(String testo, int stile, int size) {
        JLabel lbl = new JLabel(testo);
        lbl.setForeground(TEXT_WHITE);
        lbl.setFont(new Font("Arial", stile, size));
        return lbl;
    }

    private JButton creaBottone(String testo) {
        JButton btn = new JButton(testo);
        btn.setBackground(new Color(80, 80, 120));
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}