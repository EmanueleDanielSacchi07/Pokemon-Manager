/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
public class Mossa {

    public int id;
    public String nome;
    public int pp;
    public int potenza;
    public int precisione;
    public String tipo;
    public String categoria; // "physical", "special", "status"
    public String descrizione;

    // Costruttore da MossaData della PokeAPI
    public Mossa(PokeApiClient.MossaData data) {
        this.id          = data.id;
        this.nome        = data.nome;
        this.pp          = data.pp;
        this.potenza     = data.potenza;
        this.precisione  = data.precisione;
        this.tipo        = data.tipo;
        this.categoria   = data.categoria;
        this.descrizione = data.descrizione;
    }

    // Costruttore manuale — usato quando si carica dal database
    public Mossa(int id, String nome, int pp, int potenza, int precisione,
                 String tipo, String categoria, String descrizione) {
        this.id          = id;
        this.nome        = nome;
        this.pp          = pp;
        this.potenza     = potenza;
        this.precisione  = precisione;
        this.tipo        = tipo;
        this.categoria   = categoria;
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return nome + " [" + tipo + ", " + categoria + ", Pow: " + potenza + ", PP: " + pp + "]";
    }
}