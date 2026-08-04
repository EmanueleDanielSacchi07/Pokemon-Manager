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
import java.util.List;

import javax.swing.border.*;
import javax.swing.*;

public class TeamBuilderPage extends JPanel {

    MainController controller;

    JComboBox<String> cbxSelezPokemon;
    JTextField txtNome, txtIVhp, txtIVatk, txtIVspatk, txtIVdef, txtIVspdef, txtIVspeed;
    JTextField txtEVhp, txtEVatk, txtEVspatk, txtEVdef, txtEVspdef, txtEVspeed;

    JComboBox<String> cbxMossa1, cbxMossa2, cbxMossa3, cbxMossa4;
    JComboBox<String> cbxNatura;

    JComboBox<String> cbxTeamSelezionato;
    JButton btnNuovoTeam;

    JButton btnAggiungi, btnIndietro;

    JPanel pnl1, pnl2, pnl3, pnl4, pnl5;

    JLabel lblPokemon;
    Image sfondo;

    // Pokemon attualmente selezionato dalla combobox
    PokeApiClient.PokemonData pokemonSelezionato;

    // Lista team dal database
    ArrayList<Team> teams = new ArrayList<>();

    static Color BG_PANEL      = new Color(45, 45, 60, 200);
    static Color BG_FIELD      = new Color(60, 60, 80);
    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color ACCENT_GREEN  = new Color(50, 180, 50);
    static Color TEXT_WHITE    = Color.WHITE;
    static Color TEXT_DIM      = new Color(180, 180, 200);

    TeamBuilderPage(MainController controller) {
        this.controller = controller;
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

        // ---PANEL 1---
        lblPokemon = new JLabel();
        lblPokemon.setPreferredSize(new Dimension(120, 120));
        lblPokemon.setHorizontalAlignment(SwingConstants.CENTER);
        lblPokemon.setBorder(BorderFactory.createLineBorder(ACCENT_YELLOW, 1));
        pnl1.add(lblPokemon, BorderLayout.WEST);

        JPanel pnlCentro = creaPanel(new GridLayout(1, 2, 8, 8));

        // Popola la combobox con i pokemon dalla cache PokeAPI
        cbxSelezPokemon = new JComboBox<>();
        for (PokeApiClient.PokemonData pk : PokeApiClient.tuttiIPokemon) {
            cbxSelezPokemon.addItem(pk.nome);
        }
        stilizzaCbx(cbxSelezPokemon, "Seleziona Pokemon:");

        // Quando cambia il pokemon aggiorna immagine e mosse disponibili
        cbxSelezPokemon.addActionListener(e -> aggiornaPokemonSelezionato());
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

        // Seleziona il primo pokemon e aggiorna l'immagine
        if (!PokeApiClient.tuttiIPokemon.isEmpty()) {
            cbxSelezPokemon.setSelectedIndex(0);
            aggiornaPokemonSelezionato();
        }

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

        cbxMossa2 = new JComboBox<>();
        cbxMossa2.addItem("Nessuna");
        stilizzaCbx(cbxMossa2, "Mossa 2:");

        cbxMossa3 = new JComboBox<>();
        cbxMossa3.addItem("Nessuna");
        stilizzaCbx(cbxMossa3, "Mossa 3:");

        cbxMossa4 = new JComboBox<>();
        cbxMossa4.addItem("Nessuna");
        stilizzaCbx(cbxMossa4, "Mossa 4:");

        pnl4.add(cbxMossa1);
        pnl4.add(cbxMossa2);
        pnl4.add(cbxMossa3);
        pnl4.add(cbxMossa4);
        this.add(pnl4);

        // ---PANEL 5--- (Team + Natura + Aggiungi)

        // Combobox per selezionare il team a cui aggiungere il pokemon
        cbxTeamSelezionato = new JComboBox<>();
        stilizzaCbx(cbxTeamSelezionato, "Team:");
        pnl5.add(cbxTeamSelezionato);

        // Pulsante fisso per creare un nuovo team (sempre visibile, non appare/scompare)
        btnNuovoTeam = creaBottone("+ Nuovo Team", ACCENT_RED);
        btnNuovoTeam.addActionListener(e -> creaNuovoTeam());
        pnl5.add(btnNuovoTeam);

        // Carica i team esistenti dal database nella combobox
        caricaTeams();

        cbxNatura = new JComboBox<>();
        stilizzaCbx(cbxNatura, "Natura:");
        // Carica le nature dalla PokeAPI
        new Thread(() -> {
            List<PokeApiClient.NaturaData> nature = PokeApiClient.getListaNature();
            SwingUtilities.invokeLater(() -> {
                for (PokeApiClient.NaturaData n : nature) {
                    cbxNatura.addItem(n.nome);
                }
            });
        }).start();
        pnl5.add(cbxNatura);

        btnAggiungi = creaBottone("+ Aggiungi", ACCENT_GREEN);
        btnAggiungi.addActionListener(e -> aggiungiPokemon());
        pnl5.add(btnAggiungi);

        this.add(pnl5);
    }

    // Ricarica la combobox dei pokemon (da chiamare dopo che PokeApiClient.tuttiIPokemon è stato popolato)
    public void aggiornaListaPokemon() {
        cbxSelezPokemon.removeAllItems();
        for (PokeApiClient.PokemonData pk : PokeApiClient.tuttiIPokemon) {
            cbxSelezPokemon.addItem(pk.nome);
        }
        if (!PokeApiClient.tuttiIPokemon.isEmpty()) {
            cbxSelezPokemon.setSelectedIndex(0);
            aggiornaPokemonSelezionato();
        }
    }

    // Aggiorna immagine e mosse disponibili quando si cambia pokemon nella combobox
    private void aggiornaPokemonSelezionato() {
        int idx = cbxSelezPokemon.getSelectedIndex();
        if (idx < 0 || idx >= PokeApiClient.tuttiIPokemon.size()) return;

        pokemonSelezionato = PokeApiClient.tuttiIPokemon.get(idx);

        // Aggiorna immagine
        new Thread(() -> {
            try {
                java.net.URL url = java.net.URI.create(pokemonSelezionato.spriteUrl).toURL();
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
                SwingUtilities.invokeLater(() -> lblPokemon.setIcon(new ImageIcon(scaled)));
            } catch (Exception e) {
                System.out.println("Errore caricamento sprite: " + e.getMessage());
            }
        }).start();

        // Aggiorna mosse disponibili per questo pokemon
        cbxMossa1.removeAllItems();
        cbxMossa2.removeAllItems();
        cbxMossa3.removeAllItems();
        cbxMossa4.removeAllItems();
        cbxMossa2.addItem("Nessuna");
        cbxMossa3.addItem("Nessuna");
        cbxMossa4.addItem("Nessuna");

        for (String nomeMossa : pokemonSelezionato.nomeMosseDisponibili) {
            cbxMossa1.addItem(nomeMossa);
            cbxMossa2.addItem(nomeMossa);
            cbxMossa3.addItem(nomeMossa);
            cbxMossa4.addItem(nomeMossa);
        }
    }

    // Carica (o ricarica) i team dal database nella combobox di selezione
    private void caricaTeams() {
        teams.clear();
        cbxTeamSelezionato.removeAllItems();

        try {
            Database db = controller.getDatabase();
            ResultSet rs = db.getAllTeams();
            while (rs != null && rs.next()) {
                int id      = rs.getInt("id");
                String nome = rs.getString("nome");
                teams.add(new Team(id, nome));
                cbxTeamSelezionato.addItem(nome);
            }
        } catch (SQLException e) {
            System.out.println("Errore caricaTeams: " + e.getMessage());
        }

        if (teams.isEmpty()) {
            cbxTeamSelezionato.addItem("Nessun team — creane uno");
        }
    }

    // Crea un nuovo team nel database chiedendo il nome all'utente, poi lo seleziona
    private void creaNuovoTeam() {
        String nome = JOptionPane.showInputDialog(this, "Nome del nuovo team:");
        if (nome == null || nome.isBlank()) return;
        try {
            Database db = controller.getDatabase();
            db.inserisciTeam(nome);
            caricaTeams();
            // Seleziona automaticamente il team appena creato (ultimo della lista)
            if (!teams.isEmpty()) {
                cbxTeamSelezionato.setSelectedIndex(teams.size() - 1);
            }
        } catch (Exception e) {
            System.out.println("Errore creaNuovoTeam: " + e.getMessage());
        }
    }

    // Legge i campi e aggiunge il pokemon al team selezionato nel database
    private void aggiungiPokemon() {
        if (pokemonSelezionato == null) {
            JOptionPane.showMessageDialog(this, "Seleziona un pokemon!");
            return;
        }

        int teamIdx = cbxTeamSelezionato.getSelectedIndex();
        if (teamIdx < 0 || teamIdx >= teams.size()) {
            JOptionPane.showMessageDialog(this, "Seleziona o crea un team!");
            return;
        }

        Team teamSelezionato = teams.get(teamIdx);
        String soprannome    = txtNome.getText().trim();
        String natura        = (String) cbxNatura.getSelectedItem();
        int livello          = 50;

        try {
            Database db = controller.getDatabase();

            // Calcola il prossimo slot disponibile
            ResultSet rs = db.getPokemonDelTeam(teamSelezionato.id);
            int slot = 0;
            while (rs != null && rs.next()) slot++;

            if (slot >= 6) {
                JOptionPane.showMessageDialog(this, "Il team è pieno!");
                return;
            }

            // Inserisce il pokemon
            int pokemonSlotId = db.inserisciPokemon(
                teamSelezionato.id,
                pokemonSelezionato.id,
                soprannome.isBlank() ? null : soprannome,
                livello, natura, slot
            );

            // Inserisce IV
            db.inserisciStats(pokemonSlotId,
                Integer.parseInt(txtIVhp.getText()),
                Integer.parseInt(txtIVatk.getText()),
                Integer.parseInt(txtIVdef.getText()),
                Integer.parseInt(txtIVspatk.getText()),
                Integer.parseInt(txtIVspdef.getText()),
                Integer.parseInt(txtIVspeed.getText()),
                Integer.parseInt(txtEVhp.getText()),
                Integer.parseInt(txtEVatk.getText()),
                Integer.parseInt(txtEVdef.getText()),
                Integer.parseInt(txtEVspatk.getText()),
                Integer.parseInt(txtEVspdef.getText()),
                Integer.parseInt(txtEVspeed.getText())
            );

            // Inserisce mosse
            String[] mosseScelte = {
                (String) cbxMossa1.getSelectedItem(),
                (String) cbxMossa2.getSelectedItem(),
                (String) cbxMossa3.getSelectedItem(),
                (String) cbxMossa4.getSelectedItem()
            };

            for (int i = 0; i < mosseScelte.length; i++) {
                if (mosseScelte[i] != null && !mosseScelte[i].equals("Nessuna")) {
                    // Cerca l'id della mossa dalla lista mosse disponibili del pokemon
                    int mossaIdx = pokemonSelezionato.nomeMosseDisponibili.indexOf(mosseScelte[i]);
                    if (mossaIdx >= 0) {
                        int mossaId = pokemonSelezionato.mosseDisponibili.get(mossaIdx)[0];
                        db.inserisciMossa(pokemonSlotId, mossaId, i);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, pokemonSelezionato.nome + " aggiunto al team!");
            reset();

        } catch (SQLException e) {
            System.out.println("Errore aggiungiPokemon: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio!");
        }
    }

    // Disegna l'immagine di sfondo e un overlay scuro sopra
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Aggiunge un bordo con titolo colorato al pannello passato
    private void aggiungiTitolo(JPanel pnl, String testo) {
        pnl.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT_YELLOW, 1),
            testo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 11), ACCENT_YELLOW));
    }

    // Crea un pannello semitrasparente con angoli arrotondati
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

    // Crea un bottone stilizzato con colore e testo personalizzati
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

    // Crea una JTextField stilizzata con titolo e colori del tema
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

    // Crea una JTextField per gli IV con documento validatore e valore iniziale 31
    private JTextField creaIVField(String titolo) {
        JTextField txt = creaTxtField(titolo);
        txt.setDocument(new IVDocument());
        txt.setText("31");
        return txt;
    }

    // Applica stile grafico a una JComboBox
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

    // Riporta tutti i campi della pagina ai valori predefiniti
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
        // Il team selezionato resta invariato dopo l'aggiunta, cosi puoi aggiungere
        // subito un altro pokemon allo stesso team senza doverlo riselezionare
    }
}