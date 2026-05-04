import java.util.*;

public class Pokemon {

    // Pokemon
    String nome;
    String nomePersonale;
    ArrayList<Tipo> tipi; // max 2 tipi
    String immagine;

    // Statistiche
    int livello;
    Ev ev;
    Iv iv;
    ArrayList<Mossa> mosse; // max 4 mosse
    BaseStats bst;
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

    // Getter
    public String getNome() { return nome; }
    public String getNomePersonale() { return nomePersonale; }
    public ArrayList<Tipo> getTipi() { return tipi; }
    public int getLivello() { return livello; }
    public Ev getEv() { return ev; }
    public Iv getIv() { return iv; }
    public ArrayList<Mossa> getMosse() { return mosse; }
    public BaseStats getBaseStats() { return bst; }
    public Natura getNatura() { return natura; }
    public String getImmagine() { return immagine; }

    // Aggiunta sicura
    public void addTipo(Tipo t) {
        if (tipi.size() < 2) tipi.add(t);
    }

    public void addMossa(Mossa m) {
        if (mosse.size() < 4) mosse.add(m);
    }

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
    
    // Statistiche finali
    /* 
    public Stats getStats() {
        return StatCalculator.calculateAll(bst, iv, ev, natura, livello);
    }
    */    
}
