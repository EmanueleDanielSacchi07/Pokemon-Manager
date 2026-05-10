/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelo Gurraj
                  Classe: 4G
                  Mese e Anno: Maggio 2026

*/
public class Team { // Classe che definisce un singolo team
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
