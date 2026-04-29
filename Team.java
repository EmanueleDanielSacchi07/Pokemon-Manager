public class Team {
    Pokemon []pokemons;
    String nome;
    int countPokemon = 0;

    Team() {
        pokemons = new Pokemon[6];
    }

    Team(String nome) {
        pokemons = new Pokemon[6];
        this.nome = nome;
    }

    boolean newPokemon(Pokemon p) {
        if(countPokemon >= 6) {
            return false;
        }
        pokemons[countPokemon] = p;
        return true;
    }

}
