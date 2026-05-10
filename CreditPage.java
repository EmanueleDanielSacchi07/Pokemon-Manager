import java.awt.*;
import javax.swing.*;


public class CreditPage extends JPanel{
    JButton btnIndietro;
    static final Color BG_DARK       = new Color(30, 30, 40);
    static final Color BG_PANEL      = new Color(45, 45, 60);
    static final Color ACCENT_RED    = new Color(220, 50, 50);
    static final Color ACCENT_YELLOW = new Color(255, 220, 50);
    static final Color ACCENT_GREEN  = new Color(50, 200, 50);
    static final Color TEXT_WHITE    = Color.WHITE;

    CreditPage(MainController controller) {
        this.setBackground(BG_DARK);
        this.setLayout(new BorderLayout());

        JPanel pnlCrediti = new JPanel(new GridLayout(3, 1));
        pnlCrediti.setBackground(BG_DARK);

        JLabel lblTitolo = new JLabel("Crediti", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitolo.setForeground(ACCENT_YELLOW);
        lblTitolo.setBackground(BG_DARK);
        lblTitolo.setOpaque(true);

        JLabel lblNomi = new JLabel("Sacchi Emanuele  &  Angelomaria Gurraj", SwingConstants.CENTER);
        lblNomi.setFont(new Font("Arial", Font.BOLD, 16));
        lblNomi.setForeground(TEXT_WHITE);
        lblNomi.setBackground(BG_DARK);
        lblNomi.setOpaque(true);

        JLabel lblClasse = new JLabel("Classe 4^G  ISIS Bernocchi  -  A.S. 2025-2026", SwingConstants.CENTER);
        lblClasse.setFont(new Font("Arial", Font.PLAIN, 12));
        lblClasse.setForeground(new Color(130, 130, 155));
        lblClasse.setBackground(BG_DARK);
        lblClasse.setOpaque(true);

        pnlCrediti.add(lblTitolo);
        pnlCrediti.add(lblNomi);
        pnlCrediti.add(lblClasse);

        btnIndietro = creaBottone("Indietro", ACCENT_RED);
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));

        this.add(pnlCrediti, BorderLayout.CENTER);
        this.add(btnIndietro, BorderLayout.SOUTH);
    
    }

    JButton creaBottone(String titolo, Color colore) {
        JButton btn = new JButton(titolo);

        btn.setBackground(colore);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        return btn;
    }
}