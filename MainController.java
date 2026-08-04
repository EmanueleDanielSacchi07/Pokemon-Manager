/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import javax.swing.*;
import java.awt.*;
// Main controller è la pagina che gestisce il frame e il panel 
// container (Pannello che contiene tutti gli altri pannelli ovvero le altre pagine) 
// Oltre che a contenere l'effettivo main per far partire il programma
public class MainController {

    private JFrame frame;
    private JPanel container;
    private CardLayout layout;
    LoadingPage loadingPage;
    TeamBuilderPage teamBuilder;
    MusicPlayer musicPlayer;


    // Connessione unica al database, condivisa da tutto il programma
    private Database db;

    public MainController() { 

        // Database (una sola connessione per tutta l'applicazione)
        db = new Database();

        // Frame
        frame = new JFrame("Pokémon Manager");
        frame.setIconImage(new ImageIcon("resouces/logo.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Sound
        musicPlayer = new MusicPlayer();
        musicPlayer.avviaLoop("resouces/sottofondo.wav");
        musicPlayer.setVolume(0.5f);

        // Card Layout 
        layout = new CardLayout();
        container = new JPanel(layout);

        // Pagine
        MainPage mainPage = new MainPage(this);
        CreditPage creditPage = new CreditPage(this);
        teamBuilder = new TeamBuilderPage(this);
        SelectTeamPage selectTeamPage = new SelectTeamPage(this);
        ShowTeamPage showTeam = new ShowTeamPage(this);
        loadingPage = new LoadingPage(this);
        SettingsPage settingsPage = new SettingsPage(this);

        // Aggiunta al panel container 
        container.add(mainPage, "main");
        container.add(creditPage, "credit");
        container.add(teamBuilder, "team");
        container.add(selectTeamPage, "select");
        container.add(showTeam, "showteam");
        container.add(loadingPage, "loading");
        container.add(settingsPage, "settings");

        frame.add(container);
        frame.setVisible(true);
        layout.show(container, "main"); // Mostra subito la prima pagina
        
    }

    // Restituisce la connessione al database condivisa
    public Database getDatabase() {
        return db;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    // Metodo per far partire la pagina PlayPage con i team selezionati
    public void showPlayPage(Team t1, Team t2) {
        PlayPage playPage = new PlayPage(t1, t2, this);
        container.add(playPage, "play");
        layout.show(container, "play");
    }

    public void avviaCaricamentoPokemon(String pageDestinazione) {
        showPage("loading");
        PokeApiClient.caricaTuttiIPokemon(
            db,
            fatto -> loadingPage.aggiorna(fatto),
            ()    -> {
                teamBuilder.aggiornaListaPokemon();
                loadingPage.completato(this, pageDestinazione);
            }
        );
    }

    // Metodo per mostrare una pagina contenuta nel cotainer tramite il nome
    public void showPage(String name) {
        layout.show(container, name);
    }
    
    // Main
    public static void main(String args[]) {
        new MainController();
    }
}