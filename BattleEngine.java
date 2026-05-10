/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.util.ArrayList;

// Motore di battaglia: contiene tutta la logica di calcolo del danno e della gestione dei turni
public class BattleEngine {

    // Istanza della tabella dei tipi usata per calcolare l'efficacia delle mosse
    static Tipi tipi = new Tipi();

    // Calcola il moltiplicatore di efficacia della mossa sui tipi del difensore
    // (es. fuoco vs erba/ghiaccio = 4.0, acqua vs fuoco = 2.0, normale vs spettro = 0.0)
    static double calcolaEfficacia(Tipo tipoMossa, ArrayList<Tipo> tipiDifensore) {
        double moltiplicatore = 1.0;
        for (Tipo tipoDif : tipiDifensore) {
            moltiplicatore *= tipi.getEfficacia(tipoMossa, tipoDif);
        }
        return moltiplicatore;
    }

    // Calcola il danno inflitto dalla mossa usando la formula ufficiale dei giochi:
    // danno = ((2*livello/5 + 2) * potenza * atk/def) / 50 + 2) * STAB * efficacia
    // STAB (Same Type Attack Bonus) = 1.5 se il tipo della mossa coincide con un tipo dell'attaccante
    static int calcolaDanno(PokemonInBattaglia attaccante, PokemonInBattaglia difensore, Mossa mossa) {
        // Le mosse di stato non fanno danno
        if (mossa.basePow == 0) return 0;

        // Sceglie attacco/difesa fisici o speciali in base alla categoria della mossa
        int statAtk = mossa.categoria.equals("fisica") ? attaccante.atk : attaccante.spaAtk;
        int statDef = mossa.categoria.equals("fisica") ? difensore.def : difensore.spaDef;

        // Calcola il danno base con la formula ufficiale
        double dannoBase = ((2.0 * attaccante.pokemon.livello / 5.0 + 2)
                           * mossa.basePow * statAtk / statDef) / 50.0 + 2;

        // Applica STAB se il tipo della mossa coincide con uno dei tipi dell'attaccante
        double stab = 1.0;
        for (Tipo t : attaccante.pokemon.tipi) {
            if (t.getNome().equals(mossa.tipo.getNome())) {
                stab = 1.5;
                break;
            }
        }

        // Applica il moltiplicatore di efficacia del tipo
        double efficacia = calcolaEfficacia(mossa.tipo, difensore.pokemon.tipi);

        return (int)(dannoBase * stab * efficacia);
    }

    // Gestisce un turno completo di battaglia: determina l'ordine
    // di attacco in base alla speed,
    // gestisce i cambi pokemon e restituisce il log testuale di
    // tutto ciò che è accaduto nel turno
    static String giocaTurno(PokemonInBattaglia pkT1, PokemonInBattaglia pkT2,
                              Mossa mossaT1, Mossa mossaT2) {

        StringBuilder log = new StringBuilder();
        log.append("--- Nuovo Turno ---\n");

        // Se entrambi hanno cambiato il turno viene saltato
        if (pkT1.haCambiato && pkT2.haCambiato) {
            log.append("Entrambi i team hanno cambiato pokemon — turno saltato.\n");
            pkT1.haCambiato = false;
            pkT2.haCambiato = false;
            return log.toString();
        }

        // Se solo T1 ha cambiato, T2 attacca indisturbato
        if (pkT1.haCambiato) {
            log.append(pkT1.pokemon.nome + " è entrato in campo (turno perso).\n");
            eseguiMossa(pkT2, pkT1, mossaT2, log);
            pkT1.haCambiato = false;
            return log.toString();
        }

        // Se solo T2 ha cambiato, T1 attacca indisturbato
        if (pkT2.haCambiato) {
            log.append(pkT2.pokemon.nome + " è entrato in campo (turno perso).\n");
            eseguiMossa(pkT1, pkT2, mossaT1, log);
            pkT2.haCambiato = false;
            return log.toString();
        }

        // Chi ha speed maggiore attacca per primo; in caso di parità va prima T1
        if (pkT1.speed >= pkT2.speed) {
            eseguiMossa(pkT1, pkT2, mossaT1, log);
            if (!pkT2.isKo()) eseguiMossa(pkT2, pkT1, mossaT2, log);
        } else {
            eseguiMossa(pkT2, pkT1, mossaT2, log);
            if (!pkT1.isKo()) eseguiMossa(pkT1, pkT2, mossaT1, log);
        }

        return log.toString();
    }

    // Esegue la singola azione di un pokemon: calcola il danno,
    // lo applica al difensore
    // e aggiunge al log tutte le informazioni sull'azione
    // (mossa usata, danno inflitto, HP rimanenti)
    private static void eseguiMossa(PokemonInBattaglia attaccante, PokemonInBattaglia difensore,
                                     Mossa mossa, StringBuilder log) {
        if (mossa == null) {
            log.append(attaccante.pokemon.nome + " non ha selezionato una mossa.\n");
            return;
        }

        int danno = calcolaDanno(attaccante, difensore, mossa);
        difensore.subisciDanno(danno);

        log.append(attaccante.pokemon.nome + " usa " + mossa.nome + "!\n");

        if (danno == 0) {
            log.append("  → Mossa di stato, nessun danno.\n");
        } else {
            log.append("  → Danno inflitto: " + danno + "\n");
        }

        log.append("  → " + difensore.pokemon.nome + ": " + difensore.getHpString() + "\n");

        if (difensore.isKo()) {
            log.append("  → " + difensore.pokemon.nome + " è stato sconfitto!\n");
        }
    }
}