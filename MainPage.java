import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {

    JButton btnPlay, btnTeambuilder, btnShowteam, btnCredit;
    JLabel lblTitolo, lblLogo;

    public MainPage(MainController controller) {

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(30, 30, 40)); // sfondo scuro
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
        this.add(lblLogo, gbc);

        // --- TITOLO ---
        lblTitolo = new JLabel("Pokemon Manager");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 50));
        lblTitolo.setForeground(new Color(255, 220, 50)); // giallo pokemon

        gbc.gridy = 1;
        this.add(lblTitolo, gbc);

        // --- DIMENSIONE PULSANTI ---
        Dimension buttonSize = new Dimension(200, 50);

        // --- STILE PULSANTI ---
        btnPlay        = creaBottone("Play", buttonSize);
        btnTeambuilder = creaBottone("Team Builder", buttonSize);
        btnShowteam    = creaBottone("Show Team", buttonSize);
        btnCredit      = creaBottone("Credit", buttonSize);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2;
        this.add(btnPlay, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        this.add(btnTeambuilder, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        this.add(btnShowteam, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        this.add(btnCredit, gbc);

        btnPlay.addActionListener(new PageSwitchListener(controller, "select"));
        btnTeambuilder.addActionListener(new PageSwitchListener(controller, "team"));
        btnShowteam.addActionListener(new PageSwitchListener(controller, "showteam"));
        btnCredit.addActionListener(new PageSwitchListener(controller, "credit"));
    }

    private JButton creaBottone(String testo, Dimension size) {
        JButton btn = new JButton(testo);
        btn.setPreferredSize(size);
        btn.setBackground(new Color(220, 50, 50)); // rosso pokeball
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}