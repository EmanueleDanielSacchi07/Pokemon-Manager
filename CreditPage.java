import java.awt.*;
import javax.swing.*;


public class CreditPage extends JPanel{
    JButton btnIndietro;

    CreditPage(MainController controller) {
        this.setLayout(new BorderLayout());
        btnIndietro = new JButton("Indietro");
        this.add(btnIndietro, BorderLayout.SOUTH);

        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));
    }
}
