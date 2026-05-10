/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Listener che se aggiunto ad un pulsante tramite il nome della 
// pagina cambia la pagina visualizzata
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

