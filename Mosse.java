import java.io.*;
import java.util.*;

public class Mosse {
    Mossa []mosse;

    public Mosse() {
        mosse = new Mossa[165];
    }

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

            while(s != null) {
                s = br.readLine();
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
