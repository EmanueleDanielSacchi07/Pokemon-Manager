import java.io.*;
import java.util.*;

public class Pokedex {
    
    Pokemon []kanto;

    Pokedex (){
        kanto = new Pokemon[151];
    }

    void readFromPokedexFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("kanto.csv"))) {
            String riga;
            int i = 0;

            while ((riga = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(riga, ";");

                // Nome pokemon
                String nome = st.nextToken();

                // Base Stats
                BaseStats bst = new BaseStats();
                bst.bstHp    = Integer.parseInt(st.nextToken());
                bst.bstAtk   = Integer.parseInt(st.nextToken());
                bst.bstSpaAtk = Integer.parseInt(st.nextToken());
                bst.bstDef   = Integer.parseInt(st.nextToken());
                bst.bstSpaDef = Integer.parseInt(st.nextToken());
                bst.bstSpeed = Integer.parseInt(st.nextToken());

                // Tipi — lista resettata ad ogni pokemon
                ArrayList<Tipo> tipi = new ArrayList<>();
                String strTipo1 = st.nextToken();
                tipi.add(new Tipo(strTipo1));

                // Il secondo tipo è opzionale
                if (st.hasMoreTokens()) {
                    String strTipo2 = st.nextToken();
                    if (strTipo2 != null && !strTipo2.isBlank()) {
                        tipi.add(new Tipo(strTipo2));
                    }
                }

                // Nuovo pokemon letto
                String pokemonNum = String.format("%03d", i + 1);
                String imagePath = "resouces/pokemon/Artwork0" + pokemonNum + "_RB.png";
                kanto[i] = new Pokemon(nome, bst, tipi, imagePath);
                i++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}   

