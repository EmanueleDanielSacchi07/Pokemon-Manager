/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelo Gurraj
                  Classe: 4G
                  Mese e Anno: Maggio 2026

*/

// Rappresenta un pokemon durante la battaglia
// con le statistiche calcolate e lo stato attuale (hp, status, ecc.)
public class PokemonInBattaglia {
    Pokemon pokemon;
    int hpAttuali;
    int hpMax;
    int atk;
    int def;
    int spaAtk;
    int spaDef;
    int speed;
    boolean haCambiato;

    PokemonInBattaglia(Pokemon p) {
        this.pokemon = p;
        this.haCambiato = false;

        this.hpMax     = calcolaHp(p);
        this.hpAttuali = hpMax;
        this.atk       = calcolaStat(p.bst.bstAtk,    p.iv.ivAtk,    p.ev.evAtk,    p.natura.atkMod,    p.livello);
        this.def       = calcolaStat(p.bst.bstDef,    p.iv.ivDef,    p.ev.evDef,    p.natura.defMod,    p.livello);
        this.spaAtk    = calcolaStat(p.bst.bstSpaAtk, p.iv.ivSpaAtk, p.ev.evSpaAtk, p.natura.spAtkMod,  p.livello);
        this.spaDef    = calcolaStat(p.bst.bstSpaDef, p.iv.ivSpaDef, p.ev.evSpaDef, p.natura.spDefMod,  p.livello);
        this.speed     = calcolaStat(p.bst.bstSpeed,  p.iv.ivSpeed,  p.ev.evSpeed,  p.natura.speedMod,  p.livello);
    }

    // Calcola gli HP massimi con la formula ufficiale: ((2*base + iv + ev/4) * livello / 100) + livello + 10
    private int calcolaHp(Pokemon p) {
        return (int)((2 * p.bst.bstHp + p.iv.ivHp + p.ev.evHp / 4.0)
               * p.livello / 100.0) + p.livello + 10;
    }

    // Calcola una statistica con la formula ufficiale: ((2*base + iv + ev/4) * livello / 100 + 5) * natura
    private int calcolaStat(int base, double iv, double ev, double natura, int livello) {
        return (int)(((2 * base + iv + ev / 4.0) * livello / 100.0 + 5) * natura);
    }

    // Restituisce true se il pokemon è esaurito (hp a 0)
    boolean isKo() {
        return hpAttuali <= 0;
    }

    // Sottrae il danno ricevuto dagli hp attuali, senza scendere sotto 0
    void subisciDanno(int danno) {
        hpAttuali = Math.max(0, hpAttuali - danno);
    }

    // Restituisce una stringa con gli hp attuali e massimi (es. "245/310 HP")
    String getHpString() {
        return hpAttuali + "/" + hpMax + " HP";
    }

    @Override
    public String toString() {
        return pokemon.nome + " [" + getHpString() + "]";
    }
}