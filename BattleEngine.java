import java.util.ArrayList;

public class BattleEngine {

    private static Tipi tipi = new Tipi();

    static double calcolaEfficacia(Tipo tipoMossa, ArrayList<Tipo> tipiDifensore) {
        double moltiplicatore = 1.0;
        for (Tipo tipoDif : tipiDifensore) {
            moltiplicatore *= tipi.getEfficacia(tipoMossa, tipoDif);
        }
        return moltiplicatore;
    }

    static int calcolaDanno(PokemonInBattaglia attaccante, PokemonInBattaglia difensore, Mossa mossa) {
        if (mossa.basePow == 0) return 0;

        int statAtk = mossa.categoria.equals("fisica") ? attaccante.atk : attaccante.spaAtk;
        int statDef = mossa.categoria.equals("fisica") ? difensore.def : difensore.spaDef;

        double dannoBase = ((2.0 * attaccante.pokemon.livello / 5.0 + 2)
                           * mossa.basePow * statAtk / statDef) / 50.0 + 2;

        double stab = 1.0;
        for (Tipo t : attaccante.pokemon.tipi) {
            if (t.getNome().equals(mossa.tipo.getNome())) {
                stab = 1.5;
                break;
            }
        }

        double efficacia = calcolaEfficacia(mossa.tipo, difensore.pokemon.tipi);

        return (int)(dannoBase * stab * efficacia);
    }

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
            eseguiMossa(pkT2, pkT1, mossaT2, log);
            pkT1.haCambiato = false;
            return log.toString();
        }

        if (pkT2.haCambiato) {
            log.append(pkT2.pokemon.nome + " è entrato in campo (turno perso).\n");
            eseguiMossa(pkT1, pkT2, mossaT1, log);
            pkT2.haCambiato = false;
            return log.toString();
        }

        if (pkT1.speed >= pkT2.speed) {
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