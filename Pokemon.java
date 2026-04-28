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
        // Soprannome
        String strNomePersonale = (nomePersonale != null && !nomePersonale.isBlank()) ? nomePersonale : "null";

        // Tipi — sempre 2 slot
        String tipo1 = tipi.size() > 0 ? tipi.get(0).getNome() : "null";
        String tipo2 = tipi.size() > 1 ? tipi.get(1).getNome() : "null";
        String strTipi = tipo1 + "," + tipo2;

        // Mosse — sempre 4 slot
        StringBuilder strMosse = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i < mosse.size() && mosse.get(i) != null) {
                strMosse.append(mosse.get(i).nome);
            } else {
                strMosse.append("null");
            }
            if (i < 3) strMosse.append(",");
        }

        return nome + ";" +
            strNomePersonale + ";" +
            strTipi + ";" +
            livello + ";" +
            iv.ivHp + "," + iv.ivAtk + "," + iv.ivSpaAtk + "," + iv.ivDef + "," + iv.ivSpaDef + "," + iv.ivSpeed + ";" +
            ev.evHp + "," + ev.evAtk + "," + ev.evSpaAtk + "," + ev.evDef + "," + ev.evSpaDef + "," + ev.evSpeed + ";" +
            strMosse + ";" +
            bst.bstHp + "," + bst.bstAtk + "," + bst.bstSpaAtk + "," + bst.bstDef + "," + bst.bstSpaDef + "," + bst.bstSpeed + ";" +
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
