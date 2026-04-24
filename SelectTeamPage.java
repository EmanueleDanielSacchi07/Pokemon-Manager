import java.awt.*;
import javax.swing.*;

public class SelectTeamPage extends JPanel{
    JComboBox cbxTeam1, cbxTeam2;
    JButton btnPlay, btnIndietro;
    JPanel pnlMid;

    SelectTeamPage() {
        this.setLayout(new BorderLayout());
        pnlMid = new JPanel(new GridLayout(2, 2));

        // Creare su teambuilder un array con il nome dei team che verrà aggiunto come array dei combobox
        btnIndietro = new JButton("Indietro");
        btnPlay = new JButton("Gioca");
        pnlMid.add(btnIndietro);
        pnlMid.add(btnPlay);

        this.add(pnlMid);
    }

}
