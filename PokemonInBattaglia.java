/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/

// Rappresenta un pokemon durante la battaglia
// con le statistiche calcolate e lo stato attuale (hp, status, ecc.)
public class PokemonInBattaglia {

    public Pokemon pokemon;
    public int hpAttuali;
    public int hpMax;
    public int atk;
    public int def;
    public int spaAtk;
    public int spaDef;
    public int speed;
    public boolean haCambiato;

    // Modificatori stat -6/+6
    public int modAtk    = 0;
    public int modDef    = 0;
    public int modSpaAtk = 0;
    public int modSpaDef = 0;
    public int modSpeed  = 0;

    PokemonInBattaglia(Pokemon p) {
        this.pokemon    = p;
        this.haCambiato = false;

        this.hpMax   = calcolaHp(p);
        this.hpAttuali = hpMax;
        this.atk     = calcolaStat(p.getBaseStat("attack"),          p.getIv("attack"),          p.getEv("attack"),          p.livello);
        this.def     = calcolaStat(p.getBaseStat("defense"),         p.getIv("defense"),         p.getEv("defense"),         p.livello);
        this.spaAtk  = calcolaStat(p.getBaseStat("special-attack"),  p.getIv("special-attack"),  p.getEv("special-attack"),  p.livello);
        this.spaDef  = calcolaStat(p.getBaseStat("special-defense"), p.getIv("special-defense"), p.getEv("special-defense"), p.livello);
        this.speed   = calcolaStat(p.getBaseStat("speed"),           p.getIv("speed"),           p.getEv("speed"),           p.livello);
    }

    // Formula HP ufficiale
    private int calcolaHp(Pokemon p) {
        return (int)((2 * p.getBaseStat("hp") + p.getIv("hp") + p.getEv("hp") / 4.0)
               * p.livello / 100.0) + p.livello + 10;
    }

    // Formula stat ufficiale (senza modificatore natura per ora)
    private int calcolaStat(int base, int iv, int ev, int livello) {
        return (int)(((2 * base + iv + ev / 4.0) * livello / 100.0 + 5));
    }

    // Stat effettive con modificatori applicati
    private int getStatConMod(int statBase, int mod) {
        if (mod >= 0) return (int)(statBase * (2 + mod) / 2.0);
        else          return (int)(statBase * 2.0 / (2 - mod));
    }

    public int getAtkEffettivo()    { return getStatConMod(atk,    modAtk);    }
    public int getDefEffettivo()    { return getStatConMod(def,    modDef);    }
    public int getSpaAtkEffettivo() { return getStatConMod(spaAtk, modSpaAtk); }
    public int getSpaDefEffettivo() { return getStatConMod(spaDef, modSpaDef); }
    public int getSpeedEffettivo()  { return getStatConMod(speed,  modSpeed);  }

    public void applicaModificatore(String stat, int grado, StringBuilder log) {
        int vecchio, nuovo;
        switch (stat) {
            case "attack":
                vecchio = modAtk;
                modAtk = Math.max(-6, Math.min(6, modAtk + grado));
                nuovo = modAtk;
                break;
            case "defense":
                vecchio = modDef;
                modDef = Math.max(-6, Math.min(6, modDef + grado));
                nuovo = modDef;
                break;
            case "special-attack":
                vecchio = modSpaAtk;
                modSpaAtk = Math.max(-6, Math.min(6, modSpaAtk + grado));
                nuovo = modSpaAtk;
                break;
            case "special-defense":
                vecchio = modSpaDef;
                modSpaDef = Math.max(-6, Math.min(6, modSpaDef + grado));
                nuovo = modSpaDef;
                break;
            case "speed":
                vecchio = modSpeed;
                modSpeed = Math.max(-6, Math.min(6, modSpeed + grado));
                nuovo = modSpeed;
                break;
            default: return;
        }
        if (nuovo == vecchio) {
            log.append("  → " + pokemon.nome + ": " + stat + " non può più cambiare!\n");
        } else {
            String verso = grado > 0 ? "aumenta" : "diminuisce";
            log.append("  → " + stat + " di " + pokemon.nome + " " + verso + "!\n");
        }
    }

    public boolean isKo() { return hpAttuali <= 0; }

    public void subisciDanno(int danno) {
        hpAttuali = Math.max(0, hpAttuali - danno);
    }

    public String getHpString() {
        return hpAttuali + "/" + hpMax + " HP";
    }

    @Override
    public String toString() {
        return pokemon.nome + " [" + getHpString() + "]";
    }
}