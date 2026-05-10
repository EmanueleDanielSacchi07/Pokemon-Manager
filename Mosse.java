/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.io.*;
import java.util.*;

// Carica da file l'array con tutte le mosse presenti nel file mosse.csv
public class Mosse {    
    Mossa []mosse;

    public Mosse() {
        mosse = new Mossa[165];
    }

    //metodo che legge dal file
    void readFromMosseFile() {
        FileReader fr;
        BufferedReader br;
        String s = "";
        StringTokenizer st;
        int i = 0;

        String nome;
        int pow;    
        int pp;
        int precisione;
        Tipo tipo;
        String strTipo;
        String tipologia;

        try {
            fr = new FileReader("mosse.csv");
            br = new BufferedReader(fr);

            while((s = br.readLine()) != null) {
                st = new StringTokenizer(s, ";");

                nome = st.nextToken();
                pow = Integer.parseInt(st.nextToken());
                pp = Integer.parseInt(st.nextToken());
                precisione = Integer.parseInt(st.nextToken());
                strTipo = st.nextToken();
                tipo = new Tipo(strTipo);
                tipologia = st.nextToken();

                mosse[i] = new Mossa(nome, pow, pp, precisione, tipo, tipologia);
                i++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
