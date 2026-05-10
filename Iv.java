/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
public class Iv { // Classe che identifica l'oggetto individual Values
    double ivHp; //statiscica iv punti salute
    double ivAtk; //statiscica iv attacco
    double ivSpaAtk; //statiscica iv attacco speciale
    double ivDef; //statiscica iv difesa
    double ivSpaDef; //statiscica iv difesa speciale
    double ivSpeed; //statiscica iv velocita

    public Iv(double ivHp, double ivAtk, double ivSpaAtk, double ivDef, double ivSpaDef,
            double ivSpeed) {
                this.ivHp = ivHp;
                this.ivAtk = ivAtk;
                this.ivSpaAtk = ivSpaAtk;
                this.ivDef = ivDef;
                this.ivSpaDef = ivSpaDef;
                this.ivSpeed = ivSpeed;
            }
}
