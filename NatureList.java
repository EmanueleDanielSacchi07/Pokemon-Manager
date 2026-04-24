import java.util.HashMap;

public class NatureList {

    private final HashMap<String, Natura> nature = new HashMap<>();

    public NatureList() {

        // Nature neutre
        add(new Natura("hardy",   1, 1, 1, 1, 1));
        add(new Natura("docile",  1, 1, 1, 1, 1));
        add(new Natura("serious", 1, 1, 1, 1, 1));
        add(new Natura("bashful", 1, 1, 1, 1, 1));
        add(new Natura("quirky",  1, 1, 1, 1, 1));

        // +Atk
        add(new Natura("lonely",  1.1, 0.9, 1,   1,   1));
        add(new Natura("brave",   1.1, 1,   1,   1,   0.9));
        add(new Natura("adamant", 1.1, 1,   0.9, 1,   1));
        add(new Natura("naughty", 1.1, 1,   1,   0.9, 1));

        // +Def
        add(new Natura("bold",    0.9, 1.1, 1,   1,   1));
        add(new Natura("relaxed", 1,   1.1, 1,   1,   0.9));
        add(new Natura("impish",  1,   1.1, 0.9, 1,   1));
        add(new Natura("lax",     1,   1.1, 1,   0.9, 1));

        // +SpAtk
        add(new Natura("modest",  0.9, 1,   1.1, 1,   1));
        add(new Natura("mild",    1,   0.9, 1.1, 1,   1));
        add(new Natura("quiet",   1,   1,   1.1, 1,   0.9));
        add(new Natura("rash",    1,   1,   1.1, 0.9, 1));

        // +SpDef
        add(new Natura("calm",    0.9, 1,   1,   1.1, 1));
        add(new Natura("gentle",  1,   0.9, 1,   1.1, 1));
        add(new Natura("sassy",   1,   1,   1,   1.1, 0.9));
        add(new Natura("careful", 1,   1,   0.9, 1.1, 1));

        // +Speed
        add(new Natura("timid",   0.9, 1,   1,   1,   1.1));
        add(new Natura("hasty",   1,   0.9, 1,   1,   1.1));
        add(new Natura("jolly",   1,   1,   0.9, 1,   1.1));
        add(new Natura("naive",   1,   1,   1,   0.9, 1.1));
    }

    private void add(Natura n) {
        nature.put(n.getNome(), n);
    }

    public Natura get(String nome) {
        return nature.get(nome.toLowerCase());
    }

    public HashMap<String, Natura> getAll() {
        return nature;
    }
}

