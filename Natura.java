public class Natura {

    String nome;
    double atkMod;
    double defMod;
    double spAtkMod;
    double spDefMod;
    double speedMod;

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
