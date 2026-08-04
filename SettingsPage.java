/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
*/
import java.awt.*;
import javax.swing.*;

public class SettingsPage extends JPanel {

    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color BG_PANEL      = new Color(45, 45, 60, 200);
    static Color TEXT_WHITE    = Color.WHITE;

    Image sfondo;
    JSlider sliderVolume;
    JLabel lblVolumeValore;

    SettingsPage(MainController controller) {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // --- Titolo ---
        JLabel lblTitolo = new JLabel("Impostazioni");
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitolo.setForeground(ACCENT_YELLOW);
        gbc.gridy = 0;
        this.add(lblTitolo, gbc);

        // --- Pannello volume ---
        JPanel pnlVolume = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        pnlVolume.setOpaque(false);
        pnlVolume.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        pnlVolume.setPreferredSize(new Dimension(400, 100));

        JLabel lblVolumeTitolo = new JLabel("Volume musica");
        lblVolumeTitolo.setForeground(TEXT_WHITE);
        lblVolumeTitolo.setFont(new Font("Arial", Font.BOLD, 16));
        pnlVolume.add(lblVolumeTitolo, BorderLayout.NORTH);

        sliderVolume = new JSlider(0, 100, 50);
        sliderVolume.setOpaque(false);
        sliderVolume.setForeground(TEXT_WHITE);
        sliderVolume.addChangeListener(e -> {
            int valore = sliderVolume.getValue();
            lblVolumeValore.setText(valore + "%");
            controller.getMusicPlayer().setVolume(valore / 100f);
        });
        pnlVolume.add(sliderVolume, BorderLayout.CENTER);

        lblVolumeValore = new JLabel("50%");
        lblVolumeValore.setForeground(ACCENT_YELLOW);
        lblVolumeValore.setFont(new Font("Arial", Font.BOLD, 14));
        lblVolumeValore.setHorizontalAlignment(SwingConstants.CENTER);
        pnlVolume.add(lblVolumeValore, BorderLayout.SOUTH);

        gbc.gridy = 1;
        this.add(pnlVolume, gbc);

        // --- Bottone indietro ---
        JButton btnIndietro = creaBottone("← Indietro");
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
        gbc.gridy = 2;
        this.add(btnIndietro, gbc);
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