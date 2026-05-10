/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
public class Ev { // Classe che identifica l'oggetto individual Values
    double evHp; //statiscica ev punti salute
    double evAtk; //statiscica ev attacco
    double evSpaAtk; //statiscica ev attacco speciale
    double evDef; //statiscica ev difesa
    double evSpaDef; //statiscica ev difesa speciale
    double evSpeed; //statiscica ev velocita

    public Ev(double evHp, double evAtk, double evSpaAtk, double evDef, double evSpaDef,
            double evSpeed) {
                this.evHp = evHp;
                this.evAtk = evAtk;
                this.evSpaAtk = evSpaAtk;
                this.evDef = evDef;
                this.evSpaDef = evSpaDef;
                this.evSpeed = evSpeed;
            }
}
