/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
public class Mossa {

    String nome;
    int basePow;   // Potenza base
    int pp;        // Numero di utilizzi 
    int precisione; // 0–100
    Tipo tipo;     // Tipo della mossa
    String categoria; // "fisica", "speciale", "status"
    // + efffetti + priority (cose che si possono aggiungere in una successiva versione)

    public Mossa(String nome, int basePow, int pp, int precisione, Tipo tipo, String categoria) {
        this.nome = nome;
        this.basePow = basePow;
        this.pp = pp;
        this.precisione = precisione;
        this.tipo = tipo;
        this.categoria = categoria.toLowerCase();
    }

    // Riduzione PP (numero utilizzi della mossa)
    public void usaMossa() {
        if (pp > 0) pp--;
    }

    @Override
    public String toString() {
        return nome + " [" + tipo.getNome() + ", " + categoria + ", Pow: " + basePow + ", PP: " + pp + "]";
    }
}
