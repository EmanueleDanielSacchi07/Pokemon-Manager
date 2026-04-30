import java.io.*;
import java.util.*;

public class Teams {
    Team[] teams;

    Teams() {
        teams = new Team[6];
        for (int i = 0; i < 6; i++)
            teams[i] = new Team("Team " + (i + 1));
    }

    boolean writePokemonOnFile(Pokemon p, int teamIndex) {

        boolean thereIsSpace = teams[teamIndex].newPokemon(p);
        if (!thereIsSpace) return false;

        teams[teamIndex].countPokemon++;
        String fileName = "teams/Team" + (teamIndex + 1) + ".csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(p.toStringCsv());
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    boolean readPokemonFromFile(int teamIndex) {
        String fileName = "teams/Team" + (teamIndex + 1) + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String riga;

            while ((riga = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(riga, ";");

                // --- Nome ---
                String nome = st.nextToken();

                // --- Soprannome ---
                String nomePersonale = st.nextToken();
                if (nomePersonale.equals("null")) nomePersonale = null;

                // --- Tipi ---
                ArrayList<Tipo> tipi = new ArrayList<>();
                StringTokenizer stTipi = new StringTokenizer(st.nextToken(), ",");
                String strTipo1 = stTipi.nextToken();
                if (!strTipo1.equals("null")) tipi.add(new Tipo(strTipo1));
                String strTipo2 = stTipi.nextToken();
                if (!strTipo2.equals("null")) tipi.add(new Tipo(strTipo2));

                // --- Livello ---
                int livello = Integer.parseInt(st.nextToken());

                // --- IV ---
                StringTokenizer stIv = new StringTokenizer(st.nextToken(), ",");
                Iv iv = new Iv(
                    Double.parseDouble(stIv.nextToken()),
                    Double.parseDouble(stIv.nextToken()),
                    Double.parseDouble(stIv.nextToken()),
                    Double.parseDouble(stIv.nextToken()),
                    Double.parseDouble(stIv.nextToken()),
                    Double.parseDouble(stIv.nextToken())
                );

                // --- EV ---
                StringTokenizer stEv = new StringTokenizer(st.nextToken(), ",");
                Ev ev = new Ev(
                    Double.parseDouble(stEv.nextToken()),
                    Double.parseDouble(stEv.nextToken()),
                    Double.parseDouble(stEv.nextToken()),
                    Double.parseDouble(stEv.nextToken()),
                    Double.parseDouble(stEv.nextToken()),
                    Double.parseDouble(stEv.nextToken())
                );

                // --- Mosse ---
                ArrayList<Mossa> mosse = new ArrayList<>();
                StringTokenizer stMosse = new StringTokenizer(st.nextToken(), ",");
                while (stMosse.hasMoreTokens()) {
                    String nomeMossa = stMosse.nextToken().trim();
                    if (!nomeMossa.equals("null")) {
                        mosse.add(new Mossa(nomeMossa, 0, 0, 0, null, ""));
                    }
                }

                // --- BST ---
                StringTokenizer stBst = new StringTokenizer(st.nextToken(), ",");
                BaseStats bst = new BaseStats();
                bst.bstHp     = Integer.parseInt(stBst.nextToken());
                bst.bstAtk    = Integer.parseInt(stBst.nextToken());
                bst.bstSpaAtk = Integer.parseInt(stBst.nextToken());
                bst.bstDef    = Integer.parseInt(stBst.nextToken());
                bst.bstSpaDef = Integer.parseInt(stBst.nextToken());
                bst.bstSpeed  = Integer.parseInt(stBst.nextToken());

                // --- Natura ---
                String nomeNatura = st.nextToken();
                Natura natura = null;
                if (!nomeNatura.equals("null")) {
                    natura = new Natura(nomeNatura, 1, 1, 1, 1, 1); // moltiplicatori neutri di default
                }

                // --- Immagine ---
                String immagine = st.nextToken();

                // --- Crea e aggiungi il pokemon al team ---
                Pokemon p = new Pokemon(nome, nomePersonale, tipi, livello, ev, iv, mosse, bst, natura, immagine);
                teams[teamIndex].newPokemon(p);
                teams[teamIndex].countPokemon++;
            }

            return true;

        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}