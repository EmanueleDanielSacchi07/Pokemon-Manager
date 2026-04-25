import java.util.*;

public class NatureList {

    ArrayList<Natura> nature;

    public NatureList() {

        nature = new ArrayList<>();
        // Nature neutre
        nature.add(new Natura("hardy",   1, 1, 1, 1, 1));
        nature.add(new Natura("docile",  1, 1, 1, 1, 1));
        nature.add(new Natura("serious", 1, 1, 1, 1, 1));
        nature.add(new Natura("bashful", 1, 1, 1, 1, 1));
        nature.add(new Natura("quirky",  1, 1, 1, 1, 1));

        // +Atk
        nature.add(new Natura("lonely",  1.1, 0.9, 1,   1,   1));
        nature.add(new Natura("brave",   1.1, 1,   1,   1,   0.9));
        nature.add(new Natura("adamant", 1.1, 1,   0.9, 1,   1));
        nature.add(new Natura("naughty", 1.1, 1,   1,   0.9, 1));

        // +Def
        nature.add(new Natura("bold",    0.9, 1.1, 1,   1,   1));
        nature.add(new Natura("relaxed", 1,   1.1, 1,   1,   0.9));
        nature.add(new Natura("impish",  1,   1.1, 0.9, 1,   1));
        nature.add(new Natura("lax",     1,   1.1, 1,   0.9, 1));

        // +SpAtk
        nature.add(new Natura("modest",  0.9, 1,   1.1, 1,   1));
        nature.add(new Natura("mild",    1,   0.9, 1.1, 1,   1));
        nature.add(new Natura("quiet",   1,   1,   1.1, 1,   0.9));
        nature.add(new Natura("rash",    1,   1,   1.1, 0.9, 1));

        // +SpDef
        nature.add(new Natura("calm",    0.9, 1,   1,   1.1, 1));
        nature.add(new Natura("gentle",  1,   0.9, 1,   1.1, 1));
        nature.add(new Natura("sassy",   1,   1,   1,   1.1, 0.9));
        nature.add(new Natura("careful", 1,   1,   0.9, 1.1, 1));

        // +Speed
        nature.add(new Natura("timid",   0.9, 1,   1,   1,   1.1));
        nature.add(new Natura("hasty",   1,   0.9, 1,   1,   1.1));
        nature.add(new Natura("jolly",   1,   1,   0.9, 1,   1.1));
        nature.add(new Natura("naive",   1,   1,   1,   0.9, 1.1));
    }

    public ArrayList<Natura> getAll() {
        return nature;
    }
}

