/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class SelectTeamPage extends JPanel {
    JComboBox<String> cbxTeam1, cbxTeam2;
    JButton btnPlay, btnIndietro;
    JPanel pnlMid, pnlCenter;
    MainController controller;
    Image sfondo;

    // Lista team caricati dal database
    ArrayList<Team> teams = new ArrayList<>();

    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color BG_PANEL      = new Color(45, 45, 60, 180);
    static Color TEXT_WHITE    = Color.WHITE;

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

        // --- ComboBox ---
        cbxTeam1 = creaCbx();
        cbxTeam2 = creaCbx();

        // --- Bottoni ---
        btnIndietro = creaBottone("Indietro");
        btnPlay     = creaBottone("Gioca");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        btnPlay.addActionListener(e -> avviaBattaglia());

        // Pannello centrale con i due bottoni, sfondo semitrasparente arrotondato
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

        // Pannello contenitore delle combobox e del pannello bottoni
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

        // Posiziona titolo e pannello centrale con GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        gbc.gridy = 0;
        this.add(lblTitolo, gbc);

        gbc.gridy = 1;
        this.add(pnlCenter, gbc);

        // Ricarica i team dal database ogni volta che la pagina viene mostrata
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                ricaricaTeams();
            }
        });
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

    // Rilegge i team dal database (con i relativi pokemon) e aggiorna le combobox
    private void ricaricaTeams() {
        teams.clear();
        cbxTeam1.removeAllItems();
        cbxTeam2.removeAllItems();

        try {
            Database db = controller.getDatabase();
            ResultSet rs = db.getAllTeams();
            while (rs != null && rs.next()) {
                int id      = rs.getInt("id");
                String nome = rs.getString("nome");
                Team team   = new Team(id, nome);

                // Carica i pokemon del team dal database
                ResultSet rsPokemon = db.getPokemonDelTeam(id);
                while (rsPokemon != null && rsPokemon.next()) {
                    int pokemonSlotId = rsPokemon.getInt("id");
                    int pokemonId     = rsPokemon.getInt("pokemon_id");
                    String soprannome = rsPokemon.getString("soprannome");
                    int livello       = rsPokemon.getInt("livello");
                    String natura     = rsPokemon.getString("natura");

                    // Carica i dati del pokemon dalla cache PokeAPI
                    Pokemon p = trovaPokemonDaCache(pokemonId);
                    if (p != null) {
                        p.nomePersonale = soprannome;
                        p.livello       = livello;
                        p.natura        = natura;

                        // Carica IV e EV
                        p.iv = new HashMap<>();
                        p.ev = new HashMap<>();
                        p.iv.put("hp",               rsPokemon.getInt("iv_hp"));
                        p.iv.put("attack",           rsPokemon.getInt("iv_atk"));
                        p.iv.put("defense",          rsPokemon.getInt("iv_def"));
                        p.iv.put("special-attack",   rsPokemon.getInt("iv_spatk"));
                        p.iv.put("special-defense",  rsPokemon.getInt("iv_spdef"));
                        p.iv.put("speed",            rsPokemon.getInt("iv_speed"));
                        p.ev.put("hp",               rsPokemon.getInt("ev_hp"));
                        p.ev.put("attack",           rsPokemon.getInt("ev_atk"));
                        p.ev.put("defense",          rsPokemon.getInt("ev_def"));
                        p.ev.put("special-attack",   rsPokemon.getInt("ev_spatk"));
                        p.ev.put("special-defense",  rsPokemon.getInt("ev_spdef"));
                        p.ev.put("speed",            rsPokemon.getInt("ev_speed"));

                        // Carica le mosse
                        p.mosse = new ArrayList<>();
                        ResultSet rsMosse = db.getMosseDelPokemon(pokemonSlotId);
                        while (rsMosse != null && rsMosse.next()) {
                            int mossaId = rsMosse.getInt("mossa_id");
                            PokeApiClient.MossaData md = PokeApiClient.getMossa(String.valueOf(mossaId), db);
                            if (md != null) p.mosse.add(new Mossa(md));
                        }

                        team.aggiungiPokemon(p);
                    }
                }

                teams.add(team);
                cbxTeam1.addItem(nome);
                cbxTeam2.addItem(nome);
            }
        } catch (SQLException e) {
            System.out.println("Errore ricaricaTeams: " + e.getMessage());
        }

        // Se non ci sono team mostra messaggio
        if (teams.isEmpty()) {
            cbxTeam1.addItem("Nessun team");
            cbxTeam2.addItem("Nessun team");
        }
    }

    // Cerca un pokemon nella cache di PokeApiClient per id
    private Pokemon trovaPokemonDaCache(int id) {
        for (PokeApiClient.PokemonData data : PokeApiClient.tuttiIPokemon) {
            if (data.id == id) return new Pokemon(data);
        }
        return null;
    }

    // Avvia la battaglia con i due team selezionati
    private void avviaBattaglia() {
        if (teams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non ci sono team disponibili!");
            return;
        }

        int idx1 = cbxTeam1.getSelectedIndex();
        int idx2 = cbxTeam2.getSelectedIndex();

        if (idx1 < 0 || idx2 < 0 || idx1 >= teams.size() || idx2 >= teams.size()) {
            JOptionPane.showMessageDialog(this, "Seleziona due team validi!");
            return;
        }

        Team t1 = teams.get(idx1);
        Team t2 = teams.get(idx2);

        if (t1.pokemons.isEmpty() || t2.pokemons.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Entrambi i team devono avere almeno un pokemon!");
            return;
        }

        controller.showPlayPage(t1, t2);
    }

    // Crea una JComboBox stilizzata
    private JComboBox<String> creaCbx() {
        JComboBox<String> cbx = new JComboBox<>();
        cbx.setBackground(new Color(60, 60, 80));
        cbx.setForeground(TEXT_WHITE);
        cbx.setFont(new Font("Arial", Font.PLAIN, 14));
        return cbx;
    }

    // Crea un bottone stilizzato con colori e font del tema
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