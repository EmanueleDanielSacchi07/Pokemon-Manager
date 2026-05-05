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
        
        btnIndietro = creaBottone("Indietro", ACCENT_RED);
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));

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
