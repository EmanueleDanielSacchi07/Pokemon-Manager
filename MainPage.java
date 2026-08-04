/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel { // Pagina main (La prima mostrata)

    JButton btnPlay, btnTeambuilder, btnShowteam, btnCredit, btnImpostazioni;
    JLabel lblTitolo, lblLogo;
    Image sfondo;

    public MainPage(MainController controller) {

        // Carica sfondo
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        this.setLayout(new BorderLayout());

        // --- PANNELLO SUPERIORE (ingranaggio impostazioni) ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlTop.setOpaque(false);

        ImageIcon iconaIngranaggio = new ImageIcon("resouces/ingranaggio.png");
        Image ingranaggioScaled = iconaIngranaggio.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        btnImpostazioni = new JButton(new ImageIcon(ingranaggioScaled));
        btnImpostazioni.setBorderPainted(false);
        btnImpostazioni.setContentAreaFilled(false); // sfondo del bottone trasparente
        btnImpostazioni.setFocusPainted(false);
        btnImpostazioni.setOpaque(false);
        btnImpostazioni.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnImpostazioni.setToolTipText("Impostazioni");
        btnImpostazioni.addActionListener(new PageSwitchListener(controller, "settings"));
        pnlTop.add(btnImpostazioni);

        this.add(pnlTop, BorderLayout.NORTH);

        // --- PANNELLO CENTRALE (logo, titolo, pulsanti) ---
        JPanel pnlContenuto = new JPanel(new GridBagLayout());
        pnlContenuto.setOpaque(false);
        this.add(pnlContenuto, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // --- LOGO ---
        lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("resouces/logo.png");
        Image logoScaled = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoScaled));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlContenuto.add(lblLogo, gbc);

        // --- TITOLO ---
        lblTitolo = new JLabel("Pokemon Manager");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 50));
        lblTitolo.setForeground(new Color(255, 220, 50));

        gbc.gridy = 1;
        pnlContenuto.add(lblTitolo, gbc);

        // --- PULSANTI ---
        Dimension buttonSize = new Dimension(200, 50);
        btnPlay        = creaBottone("Play",        buttonSize);
        btnTeambuilder = creaBottone("Team Builder", buttonSize);
        btnShowteam    = creaBottone("Show Team",   buttonSize);
        btnCredit      = creaBottone("Credit",      buttonSize);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2;
        pnlContenuto.add(btnPlay, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        pnlContenuto.add(btnTeambuilder, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        pnlContenuto.add(btnShowteam, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        pnlContenuto.add(btnCredit, gbc);

        // Aggiunta dell'action listener per lo switch della pagina qunado premuto il pulsante
        btnPlay.addActionListener(e -> {
            if (PokeApiClient.caricamentoCompletato) {
                controller.showPage("select");
            } else {
                controller.avviaCaricamentoPokemon("select");
            }
        });

        btnTeambuilder.addActionListener(e -> {
            if (PokeApiClient.caricamentoCompletato) {
                controller.showPage("team");
            } else {
                controller.avviaCaricamentoPokemon("team");
            }
        });

        btnShowteam.addActionListener(e -> {
            if (PokeApiClient.caricamentoCompletato) {
                controller.showPage("showteam");
            } else {
                controller.avviaCaricamentoPokemon("showteam");
            }
        });

        btnCredit.addActionListener(new PageSwitchListener(controller, "credit"));
    }

    // Override per disegnare lo sfondo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Crea un bottone stilizzato con colore e font del tema
    private JButton creaBottone(String testo, Dimension size) {
        JButton btn = new JButton(testo);
        btn.setPreferredSize(size);
        btn.setBackground(new Color(220, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }
}