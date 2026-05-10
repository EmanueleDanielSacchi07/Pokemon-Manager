/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
// La natura di un pokemon è basicamente un un piccolo boost (+1.1) 
// ad una statistica e contemporaneamente un piccolo debuff 
// ad un altra statistica (+0.9)
public class Natura {

    String nome;
    double atkMod;  //attacco
    double defMod;  //difesa
    double spAtkMod;    //attacco speciale
    double spDefMod;    //difesa speciale
    double speedMod;    //velocità

    public Natura(String nome, double atkMod, double defMod, double spAtkMod,
        double spDefMod, double speedMod) {
        this.nome = nome;
        this.atkMod = atkMod;
        this.defMod = defMod;
        this.spAtkMod = spAtkMod;
        this.spDefMod = spDefMod;
        this.speedMod = speedMod;
    }

    @Override
    public String toString() {
        return nome;
    }
}
