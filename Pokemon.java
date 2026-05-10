/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.util.*;

public class Pokemon { // Classe che identifica un pokemon

    // Pokemon
    String nome;
    String nomePersonale;
    ArrayList<Tipo> tipi; // max 2 tipi
    String immagine;

    // Statistiche
    int livello;
    Ev ev; // Effort Values
    Iv iv; // Individual Values
    ArrayList<Mossa> mosse; // max 4 mosse
    BaseStats bst; // Statistiche base 
    Natura natura;

    public Pokemon(String nome, String nomePersonale, ArrayList<Tipo> tipi, int livello,
                   Ev ev, Iv iv, ArrayList<Mossa> mosse, BaseStats bst, Natura natura, String immagine) {

        this.nome = nome;
        this.nomePersonale = nomePersonale;
        this.tipi = tipi;
        this.livello = livello;
        this.ev = ev;
        this.iv = iv;
        this.mosse = mosse;
        this.bst = bst;
        this.natura = natura;
        this.immagine = immagine;
    }

    public Pokemon(String nome, BaseStats bst, ArrayList<Tipo> tipi, String immagine) {
        this.nome = nome;
        this.bst = bst;
        this.tipi = tipi;
        this.immagine = immagine;
    }

    // Aggiunge un tipo se non sono gia 2
    public void addTipo(Tipo t) {
        if (tipi.size() < 2) tipi.add(t);
    }

    // Aggiunge una mossa se non sono gia 4
    public void addMossa(Mossa m) {
        if (mosse.size() < 4) mosse.add(m);
    }

    // To string per il csv di un oggetto pokemon
    public String toStringCsv() {
        String strNomePersonale = (nomePersonale != null && !nomePersonale.isBlank()) ? nomePersonale : "null";

        String tipo1 = tipi.size() > 0 ? tipi.get(0).getNome() : "null";
        String tipo2 = tipi.size() > 1 ? tipi.get(1).getNome() : "null";

        String mossa1 = mosse.size() > 0 && mosse.get(0) != null ? mosse.get(0).nome : "null";
        String mossa2 = mosse.size() > 1 && mosse.get(1) != null ? mosse.get(1).nome : "null";
        String mossa3 = mosse.size() > 2 && mosse.get(2) != null ? mosse.get(2).nome : "null";
        String mossa4 = mosse.size() > 3 && mosse.get(3) != null ? mosse.get(3).nome : "null";

        return nome + ";" +
            strNomePersonale + ";" +
            tipo1 + ";" +
            tipo2 + ";" +
            livello + ";" +
            iv.ivHp + ";" + iv.ivAtk + ";" + iv.ivSpaAtk + ";" + iv.ivDef + ";" + iv.ivSpaDef + ";" + iv.ivSpeed + ";" +
            ev.evHp + ";" + ev.evAtk + ";" + ev.evSpaAtk + ";" + ev.evDef + ";" + ev.evSpaDef + ";" + ev.evSpeed + ";" +
            mossa1 + ";" +
            mossa2 + ";" +
            mossa3 + ";" +
            mossa4 + ";" +
            bst.bstHp + ";" + bst.bstAtk + ";" + bst.bstSpaAtk + ";" + bst.bstDef + ";" + bst.bstSpaDef + ";" + bst.bstSpeed + ";" +
            natura.nome + ";" +
            immagine;
    }
    
}
