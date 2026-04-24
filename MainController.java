/* Gruppo 10 */

import javax.swing.*;
import java.awt.*;

public class MainController {

    private JFrame frame;
    private JPanel container;
    private CardLayout layout;

    public MainController() {

        frame = new JFrame("Pokémon Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        layout = new CardLayout();
        container = new JPanel(layout);

        // Pagine
        MainPage mainPage = new MainPage(this);
        CreditPage creditPage = new CreditPage(this);
        TeamBuilderPage teamBuilder = new TeamBuilderPage(this);
        PlayPage playPage = new PlayPage(this);
        //ShowTeamPage showTeam = new ShowTeamPage(this);

        container.add(mainPage, "main");
        container.add(creditPage, "credit");
        container.add(teamBuilder, "team");
        container.add(playPage, "play");
        //container.add(showTeam, "showteam");

        frame.add(container);
        frame.setVisible(true);

        layout.show(container, "main");
    }

    public void showPage(String name) {
        layout.show(container, name);
    }

    public static void main(String agrs[]) {
        new MainController();
    }
}
