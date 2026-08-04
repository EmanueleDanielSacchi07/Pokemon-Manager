import java.util.ArrayList;

public class Team {
    public int id;          // id nel database
    public String nome;
    public ArrayList<Pokemon> pokemons = new ArrayList<>();

    public Team(int id, String nome) {
        this.id   = id;
        this.nome = nome;
    }

    public boolean aggiungiPokemon(Pokemon p) {
        if (pokemons.size() >= 6) return false;
        pokemons.add(p);
        return true;
    }
}