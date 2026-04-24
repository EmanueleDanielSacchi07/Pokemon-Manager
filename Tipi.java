public class Tipi {

    Tipo[] tipi = new Tipo[18];
    double[][] efficacia = new double[18][18];

    public Tipi() {

        // --- CREAZIONE TIPI ---
        tipi[0] = new Tipo("normale");
        tipi[1] = new Tipo("fuoco");
        tipi[2] = new Tipo("acqua");
        tipi[3] = new Tipo("erba");
        tipi[4] = new Tipo("elettro");
        tipi[5] = new Tipo("ghiaccio");
        tipi[6] = new Tipo("lotta");
        tipi[7] = new Tipo("veleno");
        tipi[8] = new Tipo("terra");
        tipi[9] = new Tipo("volante");
        tipi[10] = new Tipo("psico");
        tipi[11] = new Tipo("coleottero");
        tipi[12] = new Tipo("roccia");
        tipi[13] = new Tipo("spettro");
        tipi[14] = new Tipo("drago");
        tipi[15] = new Tipo("buio");
        tipi[16] = new Tipo("acciaio");
        tipi[17] = new Tipo("folletto");

        // --- INIZIALIZZA TUTTO A 1 (neutro) ---
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                efficacia[i][j] = 1.0;
            }
        }

        // NORMALE (0)
        setEfficacia(0, 12, 0.5);   //Normale -> Roccia
        setEfficacia(0, 16, 0.5);   //Normale -> Acciaio
        setEfficacia(0, 13, 0);   //Normale -> Spettro

        // FUOCO (1) 
        setEfficacia(1, 3, 2.0);   // Fuoco -> Erba
        setEfficacia(1, 5, 2.0);   // Fuoco -> Ghiaccio
        setEfficacia(1, 11, 2.0);  // Fuoco -> Coloettero
        setEfficacia(1, 16, 2.0);  // Fuoco -> Acciaio
        setEfficacia(1, 2, 0.5);   // Fuoco -> Acqua
        setEfficacia(1, 1, 0.5);   // Fuoco -> Fuoco
        setEfficacia(1, 14, 0.5);  // Fuoco -> Drago 
        setEfficacia(1, 12, 0.5);  // Fuoco -> Roccia 

        // ACQUA (2) 
        setEfficacia(2, 1, 2.0);   // Acqua -> Fuoco
        setEfficacia(2, 8, 2.0);   // Acqua -> Terra
        setEfficacia(2, 12, 2.0);  // Acqua -> Roccia
        setEfficacia(2, 3, 0.5);   // Acqua -> Erba
        setEfficacia(2, 2, 0.5);   // Acqua -> Acqua
        setEfficacia(2, 14, 0.5);  // Acqua -> Drago

        // ERBA (3) ok
        setEfficacia(3, 2, 2);      //Erba -> Acqua
        setEfficacia(3, 8, 2);      //Erba -> Terra
        setEfficacia(3, 12, 2);     //Erba -> Roccia
        setEfficacia(3, 1, 0.5);    //Erba -> Fuoco
        setEfficacia(3, 3, 0.5);    //Erba -> Erba
        setEfficacia(3, 14, 0.5);   //Erba -> Drago
        setEfficacia(3, 7, 0.5);    //Erba -> Veleno
        setEfficacia(3, 9, 0.5);    //Erba -> Volante
        setEfficacia(3, 11, 0.5);   //Erba -> Coleottero
        setEfficacia(3, 16, 0.5);   //Erba -> Acciaio

        // ELETTRO (4) ok
        setEfficacia(4, 2, 2);      //Elettro -> Acqua
        setEfficacia(4, 9, 2);      //Elettro -> Volante
        setEfficacia(4, 3, 0.5);    //Elettro -> Erba
        setEfficacia(4, 4, 0.5);    //Elettro -> Elettro
        setEfficacia(4, 14, 0.5);   //Elettro -> Drago
        setEfficacia(4, 8, 0);      //Elettro -> Terra
        
        // GHIACCIO (5)
        setEfficacia(5, 3, 2);      //Ghiaccio -> Erba
        setEfficacia(5, 8, 2);      //Ghiaccio -> Terra
        setEfficacia(5, 9, 2);      //Ghiaccio -> Volante
        setEfficacia(5, 14, 2);     //Ghiaccio -> Drago
        setEfficacia(5, 1, 0.5);    //Ghiaccio -> Fuoco
        setEfficacia(5, 2, 0.5);    //Ghiaccio -> Acqua
        setEfficacia(5, 5, 0.5);    //Ghiaccio -> Ghiaccio
        setEfficacia(5, 16, 0.5);   //Ghiaccio -> Acciaio

        // LOTTA (6)
        setEfficacia(6, 0, 2);      //Lotta -> Normale
        setEfficacia(6, 12, 2);     //Lotta -> Roccia
        setEfficacia(6, 16, 2);     //Lotta -> Acciaio
        setEfficacia(6, 15, 2);     //Lotta -> Buio
        setEfficacia(6, 5, 2);      //Lotta -> Ghiaccio
        setEfficacia(6, 9, 0.5);    //Lotta -> Volante
        setEfficacia(6, 11, 0.5);   //Lotta -> Coleottero
        setEfficacia(6, 7, 0.5);    //Lotta -> Veleno
        setEfficacia(6, 17, 0.5);   //Lotta -> Folletto
        setEfficacia(6, 10, 0.5);   //Lotta -> Psico
        setEfficacia(6, 13, 0);     //Lotta -> Spettro

        // VELENO (7)
        setEfficacia(7, 3, 2);      //Veleno -> Erba
        setEfficacia(7, 17, 2);     //Veleno -> Folletto
        setEfficacia(7, 7, 0.5);    //Veleno -> Veleno
        setEfficacia(7, 12, 0.5);   //Veleno -> Roccia
        setEfficacia(7, 13, 0.5);   //Veleno -> Spettro
        setEfficacia(7, 8, 0.5);    //Veleno -> Terra
        setEfficacia(7, 16, 0.5);   //Veleno -> Terra

        // TERRA (8)
        setEfficacia(8, 1, 2);      //Terra -> Fuoco
        setEfficacia(8, 4, 2);      //Terra -> Elettro
        setEfficacia(8, 12, 2);     //Terra -> Roccia
        setEfficacia(8, 16, 2);     //Terra -> Acciaio
        setEfficacia(8, 7, 2);      //Terra -> Veleno
        setEfficacia(8, 3, 0.5);    //Terra -> Erba
        setEfficacia(8, 11, 0.5);   //Terra -> Coleottero
        setEfficacia(8, 9, 0);      //Terra -> Volante

        // VOLANTE (9)
        setEfficacia(9, 3, 2);      //Volante -> Erba
        setEfficacia(9, 11, 2);     //Volante -> Coleottero
        setEfficacia(9, 6, 2);      //Volante -> Lotta
        setEfficacia(9, 4, 0.5);    //Volante -> Elettro
        setEfficacia(9, 12, 0.5);   //Volante -> Roccia
        setEfficacia(9, 16, 0.5);   //Volante -> Acciaio

        // PSICO (10)
        setEfficacia(10, 6, 2);     //Psico -> Lotta
        setEfficacia(10, 7, 2);     //Psico -> Veleno
        setEfficacia(10, 10, 0.5);  //Psico -> Psico
        setEfficacia(10, 16, 0.5);  //Psico -> Acciaio
        setEfficacia(10, 13, 0);    //Psico -> Spettro

        // COLEOTTERO (11)
        setEfficacia(11, 3, 2);     //Coleottero -> Erba
        setEfficacia(11, 10, 2);    //Coleottero -> Psico
        setEfficacia(11, 15, 2);    //Coleottero -> Buio
        setEfficacia(11, 1, 0.5);   //Coleottero -> Fuoco
        setEfficacia(11, 6, 0.5);   //Coleottero -> Lotta
        setEfficacia(11, 7, 0.5);   //Coleottero -> Veleno
        setEfficacia(11, 9, 0.5);   //Coleottero -> Volante
        setEfficacia(11, 12, 0.5);  //Coleottero -> Roccia
        setEfficacia(11, 16, 0.5);  //Coleottero -> Acciaio
        setEfficacia(11, 17, 0.5);  //Coleottero -> Folletto

        // ROCCIA (12)
        setEfficacia(12, 1, 2);     //Roccia -> Fuoco 
        setEfficacia(12, 5, 2);     //Roccia -> Ghiaccio
        setEfficacia(12, 9, 2);     //Roccia -> Volante
        setEfficacia(12, 11, 2);    //Roccia -> Coleottero
        setEfficacia(12, 6, 0.5);   //Roccia -> Lotta
        setEfficacia(12, 8, 0.5);   //Roccia -> Terra    
        setEfficacia(12, 16, 0.5);  //Roccia -> Acciaio

        // SPETTRO (13)
        setEfficacia(13, 10, 2);    //Spettro -> Psico
        setEfficacia(13, 13, 2);    //Spettro -> Spettro
        setEfficacia(13, 15, 0.5);  //Spettro -> Buio
        setEfficacia(13, 0, 0);     //Spettro -> Normale

        // DRAGO (14)
        setEfficacia(14, 14, 2);    //Drago -> Drago
        setEfficacia(14, 16, 0.5);  //Drago -> Acciaio
        setEfficacia(14, 17, 0);    //Drago -> Folletto

        // BUIO (15)
        setEfficacia(15, 10, 2);    //Buio -> Psico
        setEfficacia(15, 13, 2);    //Buio -> Spettro
        setEfficacia(15, 6, 0.5);   //Buio -> Lotta
        setEfficacia(15, 11, 0.5);  //Buio -> Coleottero
        setEfficacia(15, 17, 0.5);  //Buio -> Folletto

        // ACCIAIO (16)
        setEfficacia(16, 5, 2);     //Acciaio -> Ghiaccio
        setEfficacia(16, 12, 2);    //Acciaio -> Roccia
        setEfficacia(16, 17, 2);    //Acciaio -> Folletto
        setEfficacia(16, 1, 0.5);   //Acciaio -> Fuoco
        setEfficacia(16, 2, 0.5);   //Acciaio -> Acqua
        setEfficacia(16, 3, 0.5);   //Acciaio -> Erba
        setEfficacia(16, 16, 0.5);  //Acciaio -> Acciaio

        // FOLLETTO (17)
        setEfficacia(17, 6, 2);     //Folletto -> Lotta
        setEfficacia(17, 15, 2);    //Folletto -> Buio
        setEfficacia(17, 14, 2);    //Folletto -> Drago     
        setEfficacia(17, 1, 0.5);   //Folletto -> Fuoco
        setEfficacia(17, 7, 0.5);   //Folletto -> Veleno
        setEfficacia(17, 16, 0.5);  //Folletto -> Acciaio

    }

    private void setEfficacia(int att, int dif, double valore) {
        efficacia[att][dif] = valore;
    }

    public Tipo getByIndex(int index) {
        return tipi[index];
    }

    public Tipo getByName(String name) {
        for (Tipo t : tipi) {
            if (t.getNome().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public double getEfficacia(Tipo attaccante, Tipo difensore) {
        int i = indexOf(attaccante);
        int j = indexOf(difensore);
        return efficacia[i][j];
    }

    private int indexOf(Tipo t) {
        for (int i = 0; i < tipi.length; i++) {
            if (tipi[i].getNome().equals(t.getNome())) {
                return i;
            }
        }
        return -1;
    }
}

