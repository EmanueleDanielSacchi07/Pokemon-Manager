import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageSwitchListener implements ActionListener {

    private MainController controller;
    private String pageName;

    public PageSwitchListener(MainController controller, String pageName) {
        this.controller = controller;
        this.pageName = pageName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.showPage(pageName);
    }
}

