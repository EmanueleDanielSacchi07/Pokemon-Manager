import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {

    JButton btnPlay, btnTeambuilder, btnShowteam, btnCredit;
    JLabel lblTitolo;

    public MainPage(MainController controller) {

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Spaziatura generale
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // --- TITOLO ---
        lblTitolo = new JLabel("Pokemon Manager");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 50)); // più grande

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(lblTitolo, gbc);

        // --- DIMENSIONE UNICA PER TUTTI I PULSANTI ---
        Dimension buttonSize = new Dimension(200, 50);

        // --- PULSANTI PRIMA RIGA ---
        btnPlay = new JButton("Play");
        btnPlay.setPreferredSize(buttonSize);

        btnTeambuilder = new JButton("Team Builder");
        btnTeambuilder.setPreferredSize(buttonSize);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(btnPlay, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(btnTeambuilder, gbc);

        // --- PULSANTI SECONDA RIGA ---
        btnShowteam = new JButton("Show Team");
        btnShowteam.setPreferredSize(buttonSize);

        btnCredit = new JButton("Credit");
        btnCredit.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(btnShowteam, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(btnCredit, gbc);

        btnPlay.addActionListener(new PageSwitchListener(controller, "play"));
        btnTeambuilder.addActionListener(new PageSwitchListener(controller, "team"));
        btnShowteam.addActionListener(new PageSwitchListener(controller, "showteam"));
        btnCredit.addActionListener(new PageSwitchListener(controller, "credit"));

    }
}
