import java.io.*;
import java.util.*;

public class Pokedex {
    
    Pokemon []kanto;

    Pokedex (){
        kanto = new Pokemon[151];
    }

    void readFromPokedexFile() {
        FileReader fr;
        BufferedReader br;

        try{
            fr = new FileReader("kanto.csv");
            br = new BufferedReader(fr);
            StringTokenizer st;
            String riga;
            int i = 0;
            String nome;
            BaseStats bst;
            ArrayList<Tipo> tipi = new ArrayList<>();
            Tipo tipo1;
            Tipo tipo2;
            String strTipo1;
            String strTipo2;

            while(br != null) {
                riga = br.readLine();
                st = new StringTokenizer(riga, ";");
                // Nome pokemon
                nome = st.nextToken();
                // Base Stats
                bst = new BaseStats();
                bst.bstHp = Integer.parseInt(st.nextToken());
                bst.bstAtk = Integer.parseInt(st.nextToken());
                bst.bstSpaAtk = Integer.parseInt(st.nextToken());
                bst.bstDef = Integer.parseInt(st.nextToken());
                bst.bstSpaDef = Integer.parseInt(st.nextToken());
                bst.bstSpeed = Integer.parseInt(st.nextToken());
                // Tipi
                strTipo1 = st.nextToken();
                strTipo2 = st.nextToken();
                if(strTipo2.equals(null)) {
                    tipo1 = new Tipo(strTipo1);
                    tipi.add(tipo1);
                } else {
                    tipo1 = new Tipo(strTipo1);
                    tipo2 = new Tipo(strTipo2);
                    tipi.add(tipo1);
                    tipi.add(tipo2);
                }
                // Nuovo pokemon letto
                String imagePath = "resources/pokemon/" + nome + ".png";
                kanto[i] = new Pokemon(nome, bst, tipi, imagePath);
                // Incremento l'array
                i++;
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
}   

