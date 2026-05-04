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

    boolean readPokemonFromFile(int teamIndex, Mosse mosseList) {
        String fileName = "teams/Team" + (teamIndex + 1) + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String riga;

            while ((riga = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(riga, ";");

                String nome         = st.nextToken();
                String nomePersonale = st.nextToken();
                if (nomePersonale.equals("null")) nomePersonale = null;

                String strTipo1 = st.nextToken();
                String strTipo2 = st.nextToken();
                ArrayList<Tipo> tipi = new ArrayList<>();
                if (!strTipo1.equals("null")) tipi.add(new Tipo(strTipo1));
                if (!strTipo2.equals("null")) tipi.add(new Tipo(strTipo2));

                int livello = Integer.parseInt(st.nextToken());

                Iv iv = new Iv(
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken())
                );

                Ev ev = new Ev(
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken())
                );

                ArrayList<Mossa> mosse = new ArrayList<>();
                String[] nomeMosse = {st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken()};
                for (String nomeMossa : nomeMosse) {
                    if (!nomeMossa.equals("null")) {
                        Mossa trovata = null;
                        for (Mossa m : mosseList.mosse) {
                            if (m != null && m.nome.equals(nomeMossa)) {
                                trovata = m;
                                break;
                            }
                        }
                        if (trovata != null) {
                            mosse.add(trovata);
                        } else {
                            System.out.println("Mossa non trovata: " + nomeMossa);
                        }
                    }
                }

                BaseStats bst = new BaseStats();
                bst.bstHp     = Integer.parseInt(st.nextToken());
                bst.bstAtk    = Integer.parseInt(st.nextToken());
                bst.bstSpaAtk = Integer.parseInt(st.nextToken());
                bst.bstDef    = Integer.parseInt(st.nextToken());
                bst.bstSpaDef = Integer.parseInt(st.nextToken());
                bst.bstSpeed  = Integer.parseInt(st.nextToken());

                String nomeNatura = st.nextToken();
                Natura natura = null;
                if (!nomeNatura.equals("null")) {
                    natura = new Natura(nomeNatura, 1, 1, 1, 1, 1);
                }

                String immagine = st.nextToken();

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