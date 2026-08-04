/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
// Motore di battaglia: contiene tutta la logica di calcolo del danno e della gestione dei turni
import java.util.*;

public class BattleEngine {

    // Calcola il moltiplicatore di efficacia usando la tabella dei tipi della PokeAPI
    // Per semplicità usiamo una mappa hardcodata dei moltiplicatori
    private static final Map<String, Map<String, Double>> tabellaTipi = costruisciTabella();

    private static Map<String, Map<String, Double>> costruisciTabella() {
        Map<String, Map<String, Double>> t = new HashMap<>();

        // fuoco
        t.put("fire", new HashMap<>(Map.of(
            "grass", 2.0, "ice", 2.0, "bug", 2.0, "steel", 2.0,
            "fire", 0.5, "water", 0.5, "rock", 0.5, "dragon", 0.5
        )));
        // acqua
        t.put("water", new HashMap<>(Map.of(
            "fire", 2.0, "ground", 2.0, "rock", 2.0,
            "water", 0.5, "grass", 0.5, "dragon", 0.5
        )));
        // erba
        t.put("grass", new HashMap<>(Map.of(
            "water", 2.0, "ground", 2.0, "rock", 2.0,
            "fire", 0.5, "grass", 0.5, "poison", 0.5,
            "flying", 0.5, "bug", 0.5, "dragon", 0.5, "steel", 0.5
        )));
        // elettro
        t.put("electric", new HashMap<>(Map.of(
            "water", 2.0, "flying", 2.0,
            "electric", 0.5, "grass", 0.5, "dragon", 0.5,
            "ground", 0.0
        )));
        // ghiaccio
        t.put("ice", new HashMap<>(Map.of(
            "grass", 2.0, "ground", 2.0, "flying", 2.0, "dragon", 2.0,
            "fire", 0.5, "water", 0.5, "ice", 0.5, "steel", 0.5
        )));
        // lotta
        Map<String, Double> fighting = new HashMap<>();
        fighting.put("normal", 2.0);
        fighting.put("ice", 2.0);
        fighting.put("rock", 2.0);
        fighting.put("dark", 2.0);
        fighting.put("steel", 2.0);
        fighting.put("poison", 0.5);
        fighting.put("bug", 0.5);
        fighting.put("flying", 0.5);
        fighting.put("psychic", 0.5);
        fighting.put("fairy", 0.5);
        fighting.put("ghost", 0.0);
        t.put("fighting", fighting);
        // veleno
        t.put("poison", new HashMap<>(Map.of(
            "grass", 2.0, "fairy", 2.0,
            "poison", 0.5, "ground", 0.5, "rock", 0.5, "ghost", 0.5,
            "steel", 0.0
        )));
        // terra
        t.put("ground", new HashMap<>(Map.of(
            "fire", 2.0, "electric", 2.0, "poison", 2.0, "rock", 2.0, "steel", 2.0,
            "grass", 0.5, "bug", 0.5,
            "flying", 0.0
        )));
        // volante
        t.put("flying", new HashMap<>(Map.of(
            "grass", 2.0, "fighting", 2.0, "bug", 2.0,
            "electric", 0.5, "rock", 0.5, "steel", 0.5
        )));
        // psico
        t.put("psychic", new HashMap<>(Map.of(
            "fighting", 2.0, "poison", 2.0,
            "psychic", 0.5, "steel", 0.5,
            "dark", 0.0
        )));
        // coleottero
        t.put("bug", new HashMap<>(Map.of(
            "grass", 2.0, "psychic", 2.0, "dark", 2.0,
            "fire", 0.5, "fighting", 0.5, "poison", 0.5,
            "flying", 0.5, "ghost", 0.5, "steel", 0.5, "fairy", 0.5
        )));
        // roccia
        t.put("rock", new HashMap<>(Map.of(
            "fire", 2.0, "ice", 2.0, "flying", 2.0, "bug", 2.0,
            "fighting", 0.5, "ground", 0.5, "steel", 0.5
        )));
        // spettro
        t.put("ghost", new HashMap<>(Map.of(
            "psychic", 2.0, "ghost", 2.0,
            "dark", 0.5,
            "normal", 0.0
        )));
        // drago
        t.put("dragon", new HashMap<>(Map.of(
            "dragon", 2.0,
            "steel", 0.5,
            "fairy", 0.0
        )));
        // buio
        t.put("dark", new HashMap<>(Map.of(
            "psychic", 2.0, "ghost", 2.0,
            "fighting", 0.5, "dark", 0.5, "fairy", 0.5
        )));
        // acciaio
        t.put("steel", new HashMap<>(Map.of(
            "ice", 2.0, "rock", 2.0, "fairy", 2.0,
            "fire", 0.5, "water", 0.5, "electric", 0.5, "steel", 0.5
        )));
        // folletto
        t.put("fairy", new HashMap<>(Map.of(
            "fighting", 2.0, "dragon", 2.0, "dark", 2.0,
            "fire", 0.5, "poison", 0.5, "steel", 0.5
        )));
        // normale
        t.put("normal", new HashMap<>(Map.of(
            "rock", 0.5, "steel", 0.5,
            "ghost", 0.0
        )));

        return t;
    }

    // Calcola il moltiplicatore di efficacia
    static double calcolaEfficacia(String tipoMossa, List<String> tipiDifensore) {
        double moltiplicatore = 1.0;
        Map<String, Double> effetti = tabellaTipi.getOrDefault(tipoMossa, new HashMap<>());
        for (String tipoDif : tipiDifensore) {
            moltiplicatore *= effetti.getOrDefault(tipoDif, 1.0);
        }
        return moltiplicatore;
    }

    // Calcola il danno
    static int calcolaDanno(PokemonInBattaglia attaccante, PokemonInBattaglia difensore, Mossa mossa) {
        if (mossa.potenza == 0) return 0;

        int statAtk = mossa.categoria.equals("physical")
            ? attaccante.getAtkEffettivo()
            : attaccante.getSpaAtkEffettivo();
        int statDef = mossa.categoria.equals("physical")
            ? difensore.getDefEffettivo()
            : difensore.getSpaDefEffettivo();

        double dannoBase = ((2.0 * attaccante.pokemon.livello / 5.0 + 2)
                           * mossa.potenza * statAtk / statDef) / 50.0 + 2;

        // STAB
        double stab = 1.0;
        for (String tipo : attaccante.pokemon.tipi) {
            if (tipo.equals(mossa.tipo)) {
                stab = 1.5;
                break;
            }
        }

        double efficacia = calcolaEfficacia(mossa.tipo, difensore.pokemon.tipi);
        return (int)(dannoBase * stab * efficacia);
    }

    // Gestisce un turno completo
    static String giocaTurno(PokemonInBattaglia pkT1, PokemonInBattaglia pkT2,
                              Mossa mossaT1, Mossa mossaT2) {

        StringBuilder log = new StringBuilder();
        log.append("--- Nuovo Turno ---\n");

        if (pkT1.haCambiato && pkT2.haCambiato) {
            log.append("Entrambi i team hanno cambiato pokemon — turno saltato.\n");
            pkT1.haCambiato = false;
            pkT2.haCambiato = false;
            return log.toString();
        }

        if (pkT1.haCambiato) {
            log.append(pkT1.pokemon.nome + " è entrato in campo (turno perso).\n");
            if (!pkT1.isKo()) eseguiMossa(pkT2, pkT1, mossaT2, log);
            pkT1.haCambiato = false;
            return log.toString();
        }

        if (pkT2.haCambiato) {
            log.append(pkT2.pokemon.nome + " è entrato in campo (turno perso).\n");
            if (!pkT2.isKo()) eseguiMossa(pkT1, pkT2, mossaT1, log);
            pkT2.haCambiato = false;
            return log.toString();
        }

        if (pkT1.getSpeedEffettivo() >= pkT2.getSpeedEffettivo()) {
            eseguiMossa(pkT1, pkT2, mossaT1, log);
            if (!pkT2.isKo()) eseguiMossa(pkT2, pkT1, mossaT2, log);
        } else {
            eseguiMossa(pkT2, pkT1, mossaT2, log);
            if (!pkT1.isKo()) eseguiMossa(pkT1, pkT2, mossaT1, log);
        }

        return log.toString();
    }

    private static void eseguiMossa(PokemonInBattaglia attaccante, PokemonInBattaglia difensore,
                                     Mossa mossa, StringBuilder log) {
        if (mossa == null) {
            log.append(attaccante.pokemon.nome + " non ha selezionato una mossa.\n");
            return;
        }

        log.append(attaccante.pokemon.nome + " usa " + mossa.nome + "!\n");

        if (mossa.potenza == 0) {
            log.append("  → Mossa di stato, nessun danno.\n");
            return;
        }

        // Controlla precisione
        if (mossa.precisione > 0 && Math.random() * 100 > mossa.precisione) {
            log.append("  → " + attaccante.pokemon.nome + " manca il bersaglio!\n");
            return;
        }

        int danno = calcolaDanno(attaccante, difensore, mossa);
        difensore.subisciDanno(danno);
        log.append("  → Danno inflitto: " + danno + "\n");
        log.append("  → " + difensore.pokemon.nome + ": " + difensore.getHpString() + "\n");

        if (difensore.isKo()) {
            log.append("  → " + difensore.pokemon.nome + " è stato sconfitto!\n");
        }
    }
}