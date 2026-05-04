import java.awt.*;
import javax.swing.*;

public class PlayPage extends JPanel {

    static final Color BG_DARK       = new Color(30, 30, 40);
    static final Color BG_PANEL      = new Color(45, 45, 60);
    static final Color ACCENT_RED    = new Color(220, 50, 50);
    static final Color ACCENT_YELLOW = new Color(255, 220, 50);
    static final Color ACCENT_GREEN  = new Color(50, 200, 50);
    static final Color TEXT_WHITE    = Color.WHITE;

    // Team 1
    JButton[] btnMosseT1 = new JButton[4];
    JButton[] btnCambioT1 = new JButton[6];
    JLabel lblHpT1, lblPokemonT1;

    // Team 2
    JButton[] btnMosseT2 = new JButton[4];
    JButton[] btnCambioT2 = new JButton[6];
    JLabel lblHpT2, lblPokemonT2;

    // Centro
    JButton btnGiocaTurno;
    JTextArea txtLog;

    // Stato battaglia
    PokemonInBattaglia[] squadraT1;
    PokemonInBattaglia[] squadraT2;
    int activePokemonT1 = 0;
    int activePokemonT2 = 0;
    Mossa mossaSelezionataT1 = null;
    Mossa mossaSelezionataT2 = null;
    boolean mossaSceltaT1 = false;
    boolean mossaSceltaT2 = false;
    boolean battersiFinita = false;

    PlayPage(Team t1, Team t2, MainController controller) {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(BG_DARK);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Inizializza squadre ---
        squadraT1 = new PokemonInBattaglia[6];
        squadraT2 = new PokemonInBattaglia[6];
        for (int i = 0; i < 6; i++) {
            if (t1.pokemons[i] != null) squadraT1[i] = new PokemonInBattaglia(t1.pokemons[i]);
            if (t2.pokemons[i] != null) squadraT2[i] = new PokemonInBattaglia(t2.pokemons[i]);
        }

        // ===== PANEL SINISTRO (Team 1) =====
        JPanel pnlSinistra = new JPanel(new BorderLayout(5, 5));
        pnlSinistra.setBackground(BG_PANEL);
        pnlSinistra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblHpT1 = new JLabel(squadraT1[activePokemonT1].getHpString());
        stilizzaLabelHp(lblHpT1);
        pnlSinistra.add(lblHpT1, BorderLayout.NORTH);

        lblPokemonT1 = new JLabel();
        lblPokemonT1.setHorizontalAlignment(SwingConstants.CENTER);
        aggiornaImmagine(lblPokemonT1, squadraT1[activePokemonT1]);
        pnlSinistra.add(lblPokemonT1, BorderLayout.CENTER);

        JPanel pnlControlliT1 = new JPanel(new BorderLayout(5, 5));
        pnlControlliT1.setBackground(BG_PANEL);

        JPanel pnlMosseT1 = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlMosseT1.setBackground(BG_PANEL);
        for (int i = 0; i < 4; i++) {
            btnMosseT1[i] = creaBottone("", ACCENT_RED);
            final int idx = i;
            btnMosseT1[i].addActionListener(e -> selezionaMossa(1, idx));
            pnlMosseT1.add(btnMosseT1[i]);
        }
        aggiornaMosse(btnMosseT1, squadraT1[activePokemonT1]);

        JPanel pnlCambioT1 = new JPanel(new GridLayout(1, 6, 3, 3));
        pnlCambioT1.setBackground(BG_PANEL);
        for (int i = 0; i < 6; i++) {
            btnCambioT1[i] = creaBottone("", new Color(80, 80, 120));
            btnCambioT1[i].setFont(new Font("Arial", Font.BOLD, 10));
            String nome = (squadraT1[i] != null) ? squadraT1[i].pokemon.nome : "-";
            btnCambioT1[i].setText(nome);
            if (squadraT1[i] == null) btnCambioT1[i].setEnabled(false);
            final int idx = i;
            btnCambioT1[i].addActionListener(e -> cambiaPokemon(1, idx));
            pnlCambioT1.add(btnCambioT1[i]);
        }

        pnlControlliT1.add(pnlMosseT1, BorderLayout.CENTER);
        pnlControlliT1.add(pnlCambioT1, BorderLayout.SOUTH);
        pnlSinistra.add(pnlControlliT1, BorderLayout.SOUTH);

        // ===== PANEL DESTRO (Team 2) =====
        JPanel pnlDestra = new JPanel(new BorderLayout(5, 5));
        pnlDestra.setBackground(BG_PANEL);
        pnlDestra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblHpT2 = new JLabel(squadraT2[activePokemonT2].getHpString());
        stilizzaLabelHp(lblHpT2);
        pnlDestra.add(lblHpT2, BorderLayout.NORTH);

        lblPokemonT2 = new JLabel();
        lblPokemonT2.setHorizontalAlignment(SwingConstants.CENTER);
        aggiornaImmagine(lblPokemonT2, squadraT2[activePokemonT2]);
        pnlDestra.add(lblPokemonT2, BorderLayout.CENTER);

        JPanel pnlControlliT2 = new JPanel(new BorderLayout(5, 5));
        pnlControlliT2.setBackground(BG_PANEL);

        JPanel pnlMosseT2 = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlMosseT2.setBackground(BG_PANEL);
        for (int i = 0; i < 4; i++) {
            btnMosseT2[i] = creaBottone("", ACCENT_RED);
            final int idx = i;
            btnMosseT2[i].addActionListener(e -> selezionaMossa(2, idx));
            pnlMosseT2.add(btnMosseT2[i]);
        }
        aggiornaMosse(btnMosseT2, squadraT2[activePokemonT2]);

        JPanel pnlCambioT2 = new JPanel(new GridLayout(1, 6, 3, 3));
        pnlCambioT2.setBackground(BG_PANEL);
        for (int i = 0; i < 6; i++) {
            btnCambioT2[i] = creaBottone("", new Color(80, 80, 120));
            btnCambioT2[i].setFont(new Font("Arial", Font.BOLD, 10));
            String nome = (squadraT2[i] != null) ? squadraT2[i].pokemon.nome : "-";
            btnCambioT2[i].setText(nome);
            if (squadraT2[i] == null) btnCambioT2[i].setEnabled(false);
            final int idx = i;
            btnCambioT2[i].addActionListener(e -> cambiaPokemon(2, idx));
            pnlCambioT2.add(btnCambioT2[i]);
        }

        pnlControlliT2.add(pnlMosseT2, BorderLayout.CENTER);
        pnlControlliT2.add(pnlCambioT2, BorderLayout.SOUTH);
        pnlDestra.add(pnlControlliT2, BorderLayout.SOUTH);

        // ===== PANEL CENTRALE =====
        JPanel pnlCentro = new JPanel(new BorderLayout(5, 5));
        pnlCentro.setBackground(BG_DARK);
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnGiocaTurno = creaBottone("Gioca Turno", ACCENT_YELLOW);
        btnGiocaTurno.setForeground(BG_DARK);
        btnGiocaTurno.setFont(new Font("Arial", Font.BOLD, 16));
        btnGiocaTurno.setPreferredSize(new Dimension(150, 50));
        btnGiocaTurno.addActionListener(e -> giocaTurno());
        pnlCentro.add(btnGiocaTurno, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setBackground(new Color(20, 20, 30));
        txtLog.setForeground(TEXT_WHITE);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createLineBorder(ACCENT_YELLOW));
        pnlCentro.add(scrollLog, BorderLayout.CENTER);

        this.add(pnlSinistra, BorderLayout.WEST);
        this.add(pnlCentro, BorderLayout.CENTER);
        this.add(pnlDestra, BorderLayout.EAST);
    }

    // --- Logica battaglia ---

    private void selezionaMossa(int team, int idx) {
        if (team == 1) {
            if (mossaSceltaT1) return;
            PokemonInBattaglia pk = squadraT1[activePokemonT1];
            if (idx < pk.pokemon.mosse.size()) {
                mossaSelezionataT1 = pk.pokemon.mosse.get(idx);
                mossaSceltaT1 = true;
                for (JButton b : btnMosseT1) b.setEnabled(false);
                btnMosseT1[idx].setBackground(ACCENT_GREEN);
                appendLog("Team 1 seleziona: " + mossaSelezionataT1.nome);
            }
        } else {
            if (mossaSceltaT2) return;
            PokemonInBattaglia pk = squadraT2[activePokemonT2];
            if (idx < pk.pokemon.mosse.size()) {
                mossaSelezionataT2 = pk.pokemon.mosse.get(idx);
                mossaSceltaT2 = true;
                for (JButton b : btnMosseT2) b.setEnabled(false);
                btnMosseT2[idx].setBackground(ACCENT_GREEN);
                appendLog("Team 2 seleziona: " + mossaSelezionataT2.nome);
            }
        }
    }

    private void giocaTurno() {
        if (battersiFinita) return;

        PokemonInBattaglia pkT1 = squadraT1[activePokemonT1];
        PokemonInBattaglia pkT2 = squadraT2[activePokemonT2];

        if (!pkT1.haCambiato && !mossaSceltaT1) {
            appendLog("⚠ Team 1 deve selezionare una mossa!");
            return;
        }
        if (!pkT2.haCambiato && !mossaSceltaT2) {
            appendLog("⚠ Team 2 deve selezionare una mossa!");
            return;
        }

        String log = BattleEngine.giocaTurno(pkT1, pkT2, mossaSelezionataT1, mossaSelezionataT2);
        appendLog(log);

        // Reset selezione e flag
        mossaSelezionataT1 = null;
        mossaSelezionataT2 = null;
        mossaSceltaT1 = false;
        mossaSceltaT2 = false;

        // Riabilita bottoni mosse
        aggiornaMosse(btnMosseT1, squadraT1[activePokemonT1]);
        aggiornaMosse(btnMosseT2, squadraT2[activePokemonT2]);

        aggiornaUI();

        if (pkT1.isKo()) gestisciKo(1);
        if (pkT2.isKo()) gestisciKo(2);
    }

    private void gestisciKo(int team) {
        if (team == 1) {
            for (int i = 0; i < 6; i++) {
                if (squadraT1[i] != null && !squadraT1[i].isKo()) {
                    activePokemonT1 = i;
                    aggiornaImmagine(lblPokemonT1, squadraT1[i]);
                    aggiornaMosse(btnMosseT1, squadraT1[i]);
                    lblHpT1.setText(squadraT1[i].getHpString());
                    appendLog("Team 1 manda in campo " + squadraT1[i].pokemon.nome + "!");
                    return;
                }
            }
            appendLog("🏆 Team 2 ha vinto la battaglia!");
            btnGiocaTurno.setEnabled(false);
            battersiFinita = true;
        } else {
            for (int i = 0; i < 6; i++) {
                if (squadraT2[i] != null && !squadraT2[i].isKo()) {
                    activePokemonT2 = i;
                    aggiornaImmagine(lblPokemonT2, squadraT2[i]);
                    aggiornaMosse(btnMosseT2, squadraT2[i]);
                    lblHpT2.setText(squadraT2[i].getHpString());
                    appendLog("Team 2 manda in campo " + squadraT2[i].pokemon.nome + "!");
                    return;
                }
            }
            appendLog("🏆 Team 1 ha vinto la battaglia!");
            btnGiocaTurno.setEnabled(false);
            battersiFinita = true;
        }
    }

    private void cambiaPokemon(int team, int idx) {
        if (team == 1) {
            if (mossaSceltaT1) return;
            if (squadraT1[idx] == null || squadraT1[idx].isKo()) return;
            if (idx == activePokemonT1) return;
            squadraT1[activePokemonT1].haCambiato = true;
            mossaSceltaT1 = true;
            activePokemonT1 = idx;
            aggiornaImmagine(lblPokemonT1, squadraT1[idx]);
            aggiornaMosse(btnMosseT1, squadraT1[idx]);
            for (JButton b : btnMosseT1) b.setEnabled(false);
            lblHpT1.setText(squadraT1[idx].getHpString());
            mossaSelezionataT1 = null;
            appendLog("Team 1 cambia in " + squadraT1[idx].pokemon.nome + " (turno perso)");
        } else {
            if (mossaSceltaT2) return;
            if (squadraT2[idx] == null || squadraT2[idx].isKo()) return;
            if (idx == activePokemonT2) return;
            squadraT2[activePokemonT2].haCambiato = true;
            mossaSceltaT2 = true;
            activePokemonT2 = idx;
            aggiornaImmagine(lblPokemonT2, squadraT2[idx]);
            aggiornaMosse(btnMosseT2, squadraT2[idx]);
            for (JButton b : btnMosseT2) b.setEnabled(false);
            lblHpT2.setText(squadraT2[idx].getHpString());
            mossaSelezionataT2 = null;
            appendLog("Team 2 cambia in " + squadraT2[idx].pokemon.nome + " (turno perso)");
        }
    }

    private void aggiornaUI() {
        lblHpT1.setText(squadraT1[activePokemonT1].getHpString());
        lblHpT2.setText(squadraT2[activePokemonT2].getHpString());
    }

    // --- Metodi helper UI ---

    private void aggiornaImmagine(JLabel lbl, PokemonInBattaglia pk) {
        if (pk != null) {
            ImageIcon icon = new ImageIcon(pk.pokemon.immagine);
            Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(scaled));
        }
    }

    private void aggiornaMosse(JButton[] btns, PokemonInBattaglia pk) {
        if (pk == null) return;
        for (int i = 0; i < 4; i++) {
            if (i < pk.pokemon.mosse.size()) {
                btns[i].setText(pk.pokemon.mosse.get(i).nome);
                btns[i].setEnabled(true);
                btns[i].setBackground(ACCENT_RED);
            } else {
                btns[i].setText("-");
                btns[i].setEnabled(false);
            }
        }
    }

    private void stilizzaLabelHp(JLabel lbl) {
        lbl.setForeground(ACCENT_YELLOW);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void appendLog(String testo) {
        txtLog.append(testo + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    private JButton creaBottone(String testo, Color colore) {
        JButton btn = new JButton(testo);
        btn.setBackground(colore);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}