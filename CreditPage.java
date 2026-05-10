/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.*;
import javax.swing.*;

public class CreditPage extends JPanel { // Pagina crediti
    JButton btnIndietro;
    static Color ACCENT_RED    = new Color(220, 50, 50);
    static Color ACCENT_YELLOW = new Color(255, 220, 50);
    static Color TEXT_WHITE    = Color.WHITE;
    Image sfondo;

    CreditPage(MainController controller) {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        sfondo = new ImageIcon("resouces/sfondo.png").getImage();

        JPanel pnlCrediti = new JPanel(new GridLayout(3, 1));
        pnlCrediti.setOpaque(false);

        JLabel lblTitolo = new JLabel("Crediti", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitolo.setForeground(ACCENT_YELLOW);
        lblTitolo.setOpaque(false);

        JLabel lblNomi = new JLabel("Sacchi Emanuele  &  Angelomaria Gurraj", SwingConstants.CENTER);
        lblNomi.setFont(new Font("Arial", Font.BOLD, 16));
        lblNomi.setForeground(TEXT_WHITE);
        lblNomi.setOpaque(false);

        JLabel lblClasse = new JLabel("Classe 4^G  ISIS Bernocchi  -  A.S. 2025-2026", SwingConstants.CENTER);
        lblClasse.setFont(new Font("Arial", Font.PLAIN, 12));
        lblClasse.setForeground(new Color(180, 180, 200));
        lblClasse.setOpaque(false);

        pnlCrediti.add(lblTitolo);
        pnlCrediti.add(lblNomi);
        pnlCrediti.add(lblClasse);

        btnIndietro = creaBottone("Indietro", ACCENT_RED);
        btnIndietro.addActionListener(new PageSwitchListener(controller, "main"));

        this.add(pnlCrediti, BorderLayout.CENTER);
        this.add(btnIndietro, BorderLayout.SOUTH);
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

    // Crea un bottone stilizzato con colori e font del tema
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