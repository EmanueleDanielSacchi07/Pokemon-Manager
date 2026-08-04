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

public class ShowTeamPage extends JPanel {

    MainController controller;

    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color BG_PANEL      = new Color(45, 45, 60, 180);
    static Color TEXT_WHITE    = Color.WHITE;

    JComboBox<String> cbxTeam;
    JButton btnIndietro;
    JPanel pnlTeam;
    Image sfondo;

    // Lista team caricati dal database
    ArrayList<Team> teams = new ArrayList<>();

    // Costruttore: inizializza layout, carica dati e dispone i componenti della pagina
    ShowTeamPage(MainController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        // --- PANNELLO NORTH ---
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
        cbxTeam.addActionListener(e -> aggiornaTeam());
        pnlControlli.add(cbxTeam);

        btnIndietro = creaBottone("Indietro");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        pnlControlli.add(btnIndietro);

        pnlNorth.add(pnlControlli, BorderLayout.CENTER);
        this.add(pnlNorth, BorderLayout.NORTH);

        // --- PANNELLO CENTER ---
        pnlTeam = new JPanel(new GridLayout(2, 3, 10, 10));
        pnlTeam.setOpaque(false);
        pnlTeam.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(pnlTeam, BorderLayout.CENTER);

        // Ricarica i dati ogni volta che la pagina viene mostrata
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                ricaricaTeams();
                aggiornaTeam();
            }
        });
    }

    // Disegna lo sfondo e un overlay scuro per migliorare la leggibilità
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Rilegge i team dal database e aggiorna la combobox senza triggerare il listener
    private void ricaricaTeams() {
        teams.clear();

        var listeners = cbxTeam.getActionListeners();
        for (var l : listeners) cbxTeam.removeActionListener(l);
        cbxTeam.removeAllItems();

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
                            // Carica la mossa dalla PokeAPI
                            PokeApiClient.MossaData md = PokeApiClient.getMossa(String.valueOf(mossaId), db);
                            if (md != null) p.mosse.add(new Mossa(md));
                        }

                        team.aggiungiPokemon(p);
                    }
                }
                teams.add(team);
                cbxTeam.addItem(nome);
            }
        } catch (SQLException e) {
            System.out.println("Errore ricaricaTeams: " + e.getMessage());
        }

        cbxTeam.addActionListener(e -> aggiornaTeam());

        if (teams.isEmpty()) {
            cbxTeam.addItem("Nessun team");
        }
    }

    // Cerca un pokemon nella cache di PokeApiClient per id
    private Pokemon trovaPokemonDaCache(int id) {
        for (PokeApiClient.PokemonData data : PokeApiClient.tuttiIPokemon) {
            if (data.id == id) return new Pokemon(data);
        }
        return null;
    }

    // Aggiorna la griglia dei pokemon in base al team selezionato nella combobox
    private void aggiornaTeam() {
        pnlTeam.removeAll();
        int idx = cbxTeam.getSelectedIndex();
        if (idx < 0 || idx >= teams.size()) {
            pnlTeam.revalidate();
            pnlTeam.repaint();
            return;
        }

        Team team = teams.get(idx);

        // Mostra i pokemon del team + slot vuoti fino a 6
        for (int i = 0; i < 6; i++) {
            Pokemon p = i < team.pokemons.size() ? team.pokemons.get(i) : null;
            int teamId = team.id;
            pnlTeam.add(creaPokemonCard(p, i, teamId));
        }

        pnlTeam.revalidate();
        pnlTeam.repaint();
    }

    // Crea e restituisce la card grafica di un singolo pokemon
    private JPanel creaPokemonCard(Pokemon p, int slotIndex, int teamId) {
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

        // --- Immagine dalla PokeAPI ---
        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            // NUOVO — usa URI.create().toURL()
            java.net.URL url = java.net.URI.create(p.spriteUrl).toURL();
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImg.setText("?");
        }
        card.add(lblImg, BorderLayout.WEST);

        // --- Info ---
        JPanel pnlInfo = new JPanel(new GridLayout(6, 1, 2, 2));
        pnlInfo.setOpaque(false);

        String soprannome = (p.nomePersonale != null && !p.nomePersonale.isBlank())
            ? p.nomePersonale + " (" + p.nome + ")" : p.nome;
        pnlInfo.add(creaLabel(soprannome, Font.BOLD, 13));

        String tipi = "";
        for (int i = 0; i < p.tipi.size(); i++) {
            tipi += p.tipi.get(i);
            if (i < p.tipi.size() - 1) tipi += " / ";
        }
        pnlInfo.add(creaLabel("Tipo: " + tipi, Font.PLAIN, 12));
        pnlInfo.add(creaLabel("Natura: " + (p.natura != null ? p.natura : "N/A"), Font.PLAIN, 12));

        String mosse = "Nessuna";
        if (!p.mosse.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < p.mosse.size(); i++) {
                sb.append(p.mosse.get(i).nome);
                if (i < p.mosse.size() - 1) sb.append(", ");
            }
            mosse = sb.toString();
        }
        pnlInfo.add(creaLabel("Mosse: " + mosse, Font.PLAIN, 11));
        pnlInfo.add(creaLabel("Lv: " + p.livello, Font.PLAIN, 12));

        // --- Bottone elimina ---
        JButton btnElimina = creaBottone("Elimina");
        btnElimina.setBackground(ACCENT_RED);
        btnElimina.setFont(new Font("Arial", Font.BOLD, 11));
        btnElimina.addActionListener(e -> eliminaPokemon(p, slotIndex, teamId));
        pnlInfo.add(btnElimina);

        card.add(pnlInfo, BorderLayout.CENTER);
        return card;
    }

    // Chiede conferma ed elimina il pokemon dal database
    private void eliminaPokemon(Pokemon p, int slotIndex, int teamId) {
        int conferma = JOptionPane.showConfirmDialog(
            this,
            "Vuoi eliminare " + p.nome + " dal team?",
            "Conferma eliminazione",
            JOptionPane.YES_NO_OPTION
        );
        if (conferma != JOptionPane.YES_OPTION) return;

        try {
            Database db = controller.getDatabase();
            // Trova il pokemonSlotId dal database
            ResultSet rs = db.getPokemonDelTeam(teamId);
            int slot = 0;
            while (rs != null && rs.next()) {
                if (slot == slotIndex) {
                    db.eliminaPokemon(rs.getInt("id"));
                    break;
                }
                slot++;
            }
        } catch (SQLException e) {
            System.out.println("Errore eliminaPokemon: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione!");
            return;
        }

        JOptionPane.showMessageDialog(this, p.nome + " eliminato dal team!");
        ricaricaTeams();
        aggiornaTeam();
    }

    // Crea una JLabel stilizzata
    private JLabel creaLabel(String testo, int stile, int size) {
        JLabel lbl = new JLabel(testo);
        lbl.setForeground(TEXT_WHITE);
        lbl.setFont(new Font("Arial", stile, size));
        return lbl;
    }

    // Crea un bottone stilizzato
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