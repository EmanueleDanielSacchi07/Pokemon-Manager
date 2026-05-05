/* Gruppo 10 */

import javax.swing.*;
import java.awt.*;

public class MainController {

    private JFrame frame;
    private JPanel container;
    private CardLayout layout;

    public MainController() {

        frame = new JFrame("Pokémon Manager");
        frame.setIconImage(new ImageIcon("resouces/logo.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);

        layout = new CardLayout();
        container = new JPanel(layout);

        // Pagine
        MainPage mainPage = new MainPage(this);
        CreditPage creditPage = new CreditPage(this);
        TeamBuilderPage teamBuilder = new TeamBuilderPage(this);
        SelectTeamPage selectTeamPage = new SelectTeamPage(this);
        //ShowTeamPage showTeam = new ShowTeamPage(this);

        container.add(mainPage, "main");
        container.add(creditPage, "credit");
        container.add(teamBuilder, "team");
        container.add(selectTeamPage, "select");
        //container.add(showTeam, "showteam");

        frame.add(container);
        frame.setVisible(true);

        layout.show(container, "main");
    }

    public void showPlayPage(Team t1, Team t2) {
        PlayPage playPage = new PlayPage(t1, t2, this);
        container.add(playPage, "play");
        layout.show(container, "play");
    }

    public void showPage(String name) {
        layout.show(container, name);
    }

    public static void main(String agrs[]) {
        new MainController();
    }
}
