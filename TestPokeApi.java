public class TestPokeApi {
    public static void main(String[] args) {

        // Test getPokemon
        System.out.println("--- Test getPokemon ---");
        PokeApiClient.PokemonData pk = PokeApiClient.getPokemon("dragonite");
        if (pk != null) {
            System.out.println("Nome: " + pk.nome);
            System.out.println("ID: " + pk.id);
            System.out.println("Tipi: " + pk.tipi);
            System.out.println("HP: " + pk.getStat("hp"));
            System.out.println("Attack: " + pk.getStat("attack"));
            System.out.println("Sprite: " + pk.spriteUrl);
            System.out.println("Mosse disponibili: " + pk.nomeMosseDisponibili.size());
            System.out.println("Prima mossa: " + pk.nomeMosseDisponibili.get(0));
        }

        // Test getMossa
        System.out.println("\n--- Test getMossa ---");
        PokeApiClient.MossaData mossa = PokeApiClient.getMossa("flamethrower");
        if (mossa != null) {
            System.out.println("Nome: " + mossa.nome);
            System.out.println("Potenza: " + mossa.potenza);
            System.out.println("PP: " + mossa.pp);
            System.out.println("Tipo: " + mossa.tipo);
            System.out.println("Categoria: " + mossa.categoria);
        }

        // Test lista pokemon
        System.out.println("\n--- Test getListaPokemon ---");
        var lista = PokeApiClient.getListaPokemon(1025);
        for (var p : lista) {
            System.out.println(p.id + " - " + p.nome);
        }
    }
}
