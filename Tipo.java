/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelo Gurraj
                  Classe: 4G
                  Mese e Anno: Maggio 2026

*/
public class Tipo {     // Ogni Pokemon ha uno o due tipi (erba, fuoco, acciaio, ecc..)
    private String nome; // Nome del singolo tipo

    public Tipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

