public class Natura {

    private final String nome;
    private final double atkMod;
    private final double defMod;
    private final double spAtkMod;
    private final double spDefMod;
    private final double speedMod;

    public Natura(String nome, double atkMod, double defMod, double spAtkMod,
        double spDefMod, double speedMod) {
        this.nome = nome;
        this.atkMod = atkMod;
        this.defMod = defMod;
        this.spAtkMod = spAtkMod;
        this.spDefMod = spDefMod;
        this.speedMod = speedMod;
    }

    public String getNome() { return nome; }
    public double getAtkMod() { return atkMod; }
    public double getDefMod() { return defMod; }
    public double getSpAtkMod() { return spAtkMod; }
    public double getSpDefMod() { return spDefMod; }
    public double getSpeedMod() { return speedMod; }

    @Override
    public String toString() {
        return nome;
    }
}
