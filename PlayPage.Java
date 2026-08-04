/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.*;
import javax.swing.*;

public class PlayPage extends JPanel { // Pagina per il game

    static Color BG_PANEL      = new Color(45, 45, 60, 200);
    static Color BG_LOG        = new Color(20, 20, 30, 220);
    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color ACCENT_GREEN  = new Color(50, 200, 50);
    static Color TEXT_WHITE    = Color.WHITE;

    JButton[] btnMosseT1 = new JButton[4];
    JButton[] btnCambioT1 = new JButton[6];
    JLabel lblHpT1, lblPokemonT1;

    JButton[] btnMosseT2 = new JButton[4];
    JButton[] btnCambioT2 = new JButton[6];
    JLabel lblHpT2, lblPokemonT2;

    JButton btnGiocaTurno, btnFinisciBattaglia;
    JTextArea txtLog;

    PokemonInBattaglia[] squadraT1;
    PokemonInBattaglia[] squadraT2;
    int activePokemonT1 = 0;
    int activePokemonT2 = 0;
    Mossa mossaSelezionataT1 = null;
    Mossa mossaSelezionataT2 = null;
    boolean mossaSceltaT1 = false;
    boolean mossaSceltaT2 = false;
    boolean battersiFinita = false;

    Image sfondo;

    // Costruttore: inizializza la pagina di battaglia con i due team, crea tutti i pannelli e i componenti
    PlayPage(Team t1, Team t2, MainController controller) {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        // Converte i pokemon dei team in PokemonInBattaglia con le statistiche calcolate
        squadraT1 = new PokemonInBattaglia[6];
        squadraT2 = new PokemonInBattaglia[6];
        for (int i = 0; i < 6; i++) {
            if (i < t1.pokemons.size()) squadraT1[i] = new PokemonInBattaglia(t1.pokemons.get(i));
            if (i < t2.pokemons.size()) squadraT2[i] = new PokemonInBattaglia(t2.pokemons.get(i));
        }

        // ===== PANEL SINISTRO =====
        JPanel pnlSinistra = creaPanelSemitrasparente(new BorderLayout(5, 5));
        pnlSinistra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblHpT1 = new JLabel(squadraT1[activePokemonT1].getHpString());
        stilizzaLabelHp(lblHpT1);
        pnlSinistra.add(lblHpT1, BorderLayout.NORTH);

        lblPokemonT1 = new JLabel();
        lblPokemonT1.setHorizontalAlignment(SwingConstants.CENTER);
        aggiornaImmagine(lblPokemonT1, squadraT1[activePokemonT1]);
        pnlSinistra.add(lblPokemonT1, BorderLayout.CENTER);

        JPanel pnlControlliT1 = creaPanelSemitrasparente(new BorderLayout(5, 5));

        JPanel pnlMosseT1 = creaPanelSemitrasparente(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++) {
            btnMosseT1[i] = creaBottone("", ACCENT_RED);
            final int idx = i;
            btnMosseT1[i].addActionListener(e -> selezionaMossa(1, idx));
            pnlMosseT1.add(btnMosseT1[i]);
        }
        aggiornaMosse(btnMosseT1, squadraT1[activePokemonT1]);

        JPanel pnlCambioT1 = creaPanelSemitrasparente(new GridLayout(1, 6, 3, 3));
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

        // ===== PANEL DESTRO =====
        JPanel pnlDestra = creaPanelSemitrasparente(new BorderLayout(5, 5));
        pnlDestra.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblHpT2 = new JLabel(squadraT2[activePokemonT2].getHpString());
        stilizzaLabelHp(lblHpT2);
        pnlDestra.add(lblHpT2, BorderLayout.NORTH);

        lblPokemonT2 = new JLabel();
        lblPokemonT2.setHorizontalAlignment(SwingConstants.CENTER);
        aggiornaImmagine(lblPokemonT2, squadraT2[activePokemonT2]);
        pnlDestra.add(lblPokemonT2, BorderLayout.CENTER);

        JPanel pnlControlliT2 = creaPanelSemitrasparente(new BorderLayout(5, 5));

        JPanel pnlMosseT2 = creaPanelSemitrasparente(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++) {
            btnMosseT2[i] = creaBottone("", ACCENT_RED);
            int idx = i;
            btnMosseT2[i].addActionListener(e -> selezionaMossa(2, idx));
            pnlMosseT2.add(btnMosseT2[i]);
        }
        aggiornaMosse(btnMosseT2, squadraT2[activePokemonT2]);

        JPanel pnlCambioT2 = creaPanelSemitrasparente(new GridLayout(1, 6, 3, 3));
        for (int i = 0; i < 6; i++) {
            btnCambioT2[i] = creaBottone("", new Color(80, 80, 120));
            btnCambioT2[i].setFont(new Font("Arial", Font.BOLD, 10));
            String nome = (squadraT2[i] != null) ? squadraT2[i].pokemon.nome : "-";
            btnCambioT2[i].setText(nome);
            if (squadraT2[i] == null) btnCambioT2[i].setEnabled(false);
            int idx = i;
            btnCambioT2[i].addActionListener(e -> cambiaPokemon(2, idx));
            pnlCambioT2.add(btnCambioT2[i]);
        }

        pnlControlliT2.add(pnlMosseT2, BorderLayout.CENTER);
        pnlControlliT2.add(pnlCambioT2, BorderLayout.SOUTH);
        pnlDestra.add(pnlControlliT2, BorderLayout.SOUTH);

        // ===== PANEL CENTRALE =====
        JPanel pnlCentro = creaPanelSemitrasparente(new BorderLayout(5, 5));
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlPulsanti = creaPanelSemitrasparente(new GridLayout(2, 1, 5, 5));
        pnlPulsanti.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnGiocaTurno = creaBottone("Gioca Turno", ACCENT_YELLOW);
        btnGiocaTurno.setForeground(new Color(30, 30, 40));
        btnGiocaTurno.setFont(new Font("Arial", Font.BOLD, 16));
        btnGiocaTurno.setPreferredSize(new Dimension(150, 50));
        btnGiocaTurno.addActionListener(e -> giocaTurno());

        btnFinisciBattaglia = creaBottone("Fine", ACCENT_YELLOW);
        btnFinisciBattaglia.setForeground(new Color(30, 30, 40));
        btnFinisciBattaglia.setFont(new Font("Arial", Font.BOLD, 16));
        btnFinisciBattaglia.setEnabled(false);
        btnFinisciBattaglia.setPreferredSize(new Dimension(150, 50));
        btnFinisciBattaglia.addActionListener(new PageSwitchListener(controller, "main"));

        pnlPulsanti.add(btnFinisciBattaglia);
        pnlPulsanti.add(btnGiocaTurno);
        pnlCentro.add(pnlPulsanti, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setOpaque(true);
        txtLog.setBackground(new Color(20, 20, 30));
        txtLog.setForeground(TEXT_WHITE);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createLineBorder(ACCENT_YELLOW));
        scrollLog.setOpaque(false);
        scrollLog.getViewport().setOpaque(false);
        pnlCentro.add(scrollLog, BorderLayout.CENTER);

        this.add(pnlSinistra, BorderLayout.WEST);
        this.add(pnlCentro, BorderLayout.CENTER);
        this.add(pnlDestra, BorderLayout.EAST);
    }

    // Disegna lo sfondo e un overlay scuro per migliorare la leggibilità
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Crea un pannello semitrasparente con angoli arrotondati
    private JPanel creaPanelSemitrasparente(LayoutManager layout) {
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
        return p;
    }

    // Salva la mossa scelta dal team e blocca i bottoni mosse per evitare cambi successivi
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

    // Esegue un turno di battaglia, verifica che entrambi abbiano scelto, aggiorna UI e gestisce i KO
    private void giocaTurno() {
        if (battersiFinita) return;

        PokemonInBattaglia pkT1 = squadraT1[activePokemonT1];
        PokemonInBattaglia pkT2 = squadraT2[activePokemonT2];

        if (!pkT1.haCambiato && !mossaSceltaT1) {
            appendLog("Team 1 deve selezionare una mossa!");
            return;
        }
        if (!pkT2.haCambiato && !mossaSceltaT2) {
            appendLog("Team 2 deve selezionare una mossa!");
            return;
        }

        String log = BattleEngine.giocaTurno(pkT1, pkT2, mossaSelezionataT1, mossaSelezionataT2);
        appendLog(log);

        // Reset selezione mosse per il turno successivo
        mossaSelezionataT1 = null;
        mossaSelezionataT2 = null;
        mossaSceltaT1 = false;
        mossaSceltaT2 = false;

        aggiornaMosse(btnMosseT1, squadraT1[activePokemonT1]);
        aggiornaMosse(btnMosseT2, squadraT2[activePokemonT2]);

        aggiornaUI();

        if (pkT1.isKo()) gestisciKo(1);
        if (pkT2.isKo()) gestisciKo(2);
    }

    // Trova il prossimo pokemon vivo del team e lo manda in campo; se non ce ne sono dichiara la vittoria
    private void gestisciKo(int team) {
        if (team == 1) {
            for (int i = 0; i < 6; i++) {
                if (i == activePokemonT1) continue;
                if (squadraT1[i] != null && !squadraT1[i].isKo()) {
                    activePokemonT1 = i;
                    aggiornaImmagine(lblPokemonT1, squadraT1[i]);
                    aggiornaMosse(btnMosseT1, squadraT1[i]);
                    lblHpT1.setText(squadraT1[i].getHpString());
                    appendLog("Team 1 manda in campo " + squadraT1[i].pokemon.nome + "!");
                    return;
                }
            }
            appendLog("Team 2 ha vinto la battaglia!");
            btnGiocaTurno.setEnabled(false);
            btnFinisciBattaglia.setEnabled(true);
            battersiFinita = true;
        } else {
            for (int i = 0; i < 6; i++) {
                if (i == activePokemonT2) continue;
                if (squadraT2[i] != null && !squadraT2[i].isKo()) {
                    activePokemonT2 = i;
                    aggiornaImmagine(lblPokemonT2, squadraT2[i]);
                    aggiornaMosse(btnMosseT2, squadraT2[i]);
                    lblHpT2.setText(squadraT2[i].getHpString());
                    appendLog("Team 2 manda in campo " + squadraT2[i].pokemon.nome + "!");
                    return;
                }
            }
            appendLog("Team 1 ha vinto la battaglia!");
            btnGiocaTurno.setEnabled(false);
            btnFinisciBattaglia.setEnabled(true);
            battersiFinita = true;
        }
    }

    // Cambia il pokemon attivo del team, blocca i bottoni mosse e consuma il turno
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

    // Aggiorna le label HP di entrambi i pokemon attivi
    private void aggiornaUI() {
        lblHpT1.setText(squadraT1[activePokemonT1].getHpString());
        lblHpT2.setText(squadraT2[activePokemonT2].getHpString());
    }

    // Carica e scala l'immagine del pokemon dalla URL della PokeAPI
    private void aggiornaImmagine(JLabel lbl, PokemonInBattaglia pk) {
        if (pk != null) {
            new Thread(() -> {
                try {
                    java.net.URL url = java.net.URI.create(pk.pokemon.spriteUrl).toURL();
                    ImageIcon icon = new ImageIcon(url);
                    Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    SwingUtilities.invokeLater(() -> lbl.setIcon(new ImageIcon(scaled)));
                } catch (Exception e) {
                    System.out.println("Errore immagine: " + e.getMessage());
                }
            }).start();
        }
    }

    // Aggiorna i bottoni mosse con i nomi delle mosse del pokemon attivo, disabilita gli slot vuoti
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

    // Applica stile giallo e font bold alla label degli HP
    private void stilizzaLabelHp(JLabel lbl) {
        lbl.setForeground(ACCENT_YELLOW);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Aggiunge una riga al log di battaglia e scrolla automaticamente in fondo
    public void appendLog(String testo) {
        txtLog.append(testo + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    // Crea un bottone stilizzato con colore e font del tema
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