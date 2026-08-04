import java.net.*;
import java.net.http.*;
import java.sql.ResultSet;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PokeApiClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    // Fa una chiamata GET all'URL passato e restituisce la risposta come JsonObject
    // Ritenta automaticamente in caso di errori temporanei (502/503/429) o problemi di rete
    private static JsonObject get(String url) {
        int maxTentativi = 4;

        for (int tentativo = 1; tentativo <= maxTentativi; tentativo++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return gson.fromJson(response.body(), JsonObject.class);
                }

                boolean erroreTemporaneo = response.statusCode() == 502
                        || response.statusCode() == 503
                        || response.statusCode() == 429;

                if (erroreTemporaneo && tentativo < maxTentativi) {
                    Thread.sleep(300L * tentativo);
                    continue;
                }

                System.out.println("Errore HTTP: " + response.statusCode() + " per " + url);
                return null;

            } catch (Exception e) {
                if (tentativo == maxTentativi) {
                    System.out.println("Errore chiamata API: " + e.getMessage());
                    return null;
                }
                try {
                    Thread.sleep(300L * tentativo);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }
        return null;
    }

    // Estrae l'id numerico dalla URL della PokeAPI (es. "https://pokeapi.co/api/v2/move/1/")
    private static int estraiId(String url) {
        String urlPulita = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        return Integer.parseInt(urlPulita.substring(urlPulita.lastIndexOf("/") + 1));
    }

    // --- POKEMON ---

    // Restituisce i dati base di un pokemon dato il nome o l'id
    public static PokemonData getPokemon(String nomeOId) {
        JsonObject json = get(BASE_URL + "pokemon/" + nomeOId.toLowerCase());
        if (json == null) return null;

        PokemonData data = new PokemonData();

        // Id e nome
        data.id   = json.get("id").getAsInt();
        data.nome = json.get("name").getAsString();

        // Sprite
        data.spriteUrl = json.getAsJsonObject("sprites")
                             .getAsJsonObject("other")
                             .getAsJsonObject("official-artwork")
                             .get("front_default").getAsString();

        // Tipi
        data.tipi = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray("types")) {
            String tipo = el.getAsJsonObject()
                            .getAsJsonObject("type")
                            .get("name").getAsString();
            data.tipi.add(tipo);
        }

        // Base stats
        data.stats = new HashMap<>();
        for (JsonElement el : json.getAsJsonArray("stats")) {
            JsonObject statObj = el.getAsJsonObject();
            String statNome = statObj.getAsJsonObject("stat").get("name").getAsString();
            int baseStat    = statObj.get("base_stat").getAsInt();
            data.stats.put(statNome, baseStat);
        }

        // Mosse disponibili
        data.mosseDisponibili    = new ArrayList<>();
        data.nomeMosseDisponibili = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray("moves")) {
            JsonObject mossaObj = el.getAsJsonObject().getAsJsonObject("move");
            String nomeMossa = mossaObj.get("name").getAsString();
            String urlMossa  = mossaObj.get("url").getAsString();
            int idMossa = estraiId(urlMossa);
            data.mosseDisponibili.add(new int[]{idMossa, 0});
            data.nomeMosseDisponibili.add(nomeMossa);
        }

        // Abilità
        data.abilita = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray("abilities")) {
            String nomeAbilita = el.getAsJsonObject()
                                   .getAsJsonObject("ability")
                                   .get("name").getAsString();
            data.abilita.add(nomeAbilita);
        }

        return data;
    }

    // --- MOSSA ---

    // Restituisce i dati di una mossa dato il nome o l'id.
    // Controlla prima la cache nel database: se presente, non fa nessuna chiamata HTTP.
    public static MossaData getMossa(String nomeOId, Database db) {
        // Se l'argomento e' un id numerico, prova prima la cache
        Integer idNumerico = null;
        try {
            idNumerico = Integer.parseInt(nomeOId.trim());
        } catch (NumberFormatException ignored) {
            // non era un numero, va bene: si procede con il nome
        }

        if (idNumerico != null) {
            MossaData daCache = leggiMossaDaCache(db, idNumerico);
            if (daCache != null) {
                return daCache;
            }
        }

        // Non in cache: chiama l'API come prima
        JsonObject json = get(BASE_URL + "move/" + nomeOId.toLowerCase());
        if (json == null) return null;

        MossaData data = new MossaData();

        data.id         = json.get("id").getAsInt();
        data.nome       = json.get("name").getAsString();
        data.pp         = json.get("pp").getAsInt();
        data.potenza    = json.get("power").isJsonNull()    ? 0 : json.get("power").getAsInt();
        data.precisione = json.get("accuracy").isJsonNull() ? 0 : json.get("accuracy").getAsInt();
        data.tipo       = json.getAsJsonObject("type").get("name").getAsString();
        data.categoria  = json.getAsJsonObject("damage_class").get("name").getAsString();

        // Descrizione in italiano se disponibile, altrimenti inglese
        data.descrizione = "";
        for (JsonElement el : json.getAsJsonArray("flavor_text_entries")) {
            JsonObject entry = el.getAsJsonObject();
            String lingua = entry.getAsJsonObject("language").get("name").getAsString();
            if (lingua.equals("it")) {
                data.descrizione = entry.get("flavor_text").getAsString();
                break;
            } else if (lingua.equals("en") && data.descrizione.isEmpty()) {
                data.descrizione = entry.get("flavor_text").getAsString();
            }
        }

        // Salva nella cache per le prossime volte
        db.salvaMossaCache(data.id, data.nome, data.pp, data.potenza, data.precisione,
                            data.tipo, data.categoria, data.descrizione);

        return data;
    }

    // Legge una mossa dalla cache del database, restituisce null se non presente
    private static MossaData leggiMossaDaCache(Database db, int id) {
        ResultSet rs = db.getMossaCacheById(id);
        if (rs == null) return null;

        try {
            MossaData data = new MossaData();
            data.id         = rs.getInt("id");
            data.nome       = rs.getString("nome");
            data.pp         = rs.getInt("pp");
            data.potenza    = rs.getInt("potenza");
            data.precisione = rs.getInt("precisione");
            data.tipo       = rs.getString("tipo");
            data.categoria  = rs.getString("categoria");
            data.descrizione = rs.getString("descrizione");
            return data;
        } catch (Exception e) {
            System.out.println("Errore lettura cache mossa: " + e.getMessage());
            return null;
        }
    }

    // --- LISTA POKEMON ---

    // Restituisce la lista di tutti i pokemon fino al limite passato
    public static List<PokemonListItem> getListaPokemon(int limite) {
        JsonObject json = get(BASE_URL + "pokemon?limit=" + limite + "&offset=0");
        if (json == null) return new ArrayList<>();

        List<PokemonListItem> lista = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray("results")) {
            JsonObject obj  = el.getAsJsonObject();
            String nome     = obj.get("name").getAsString();
            String url      = obj.get("url").getAsString();
            int id          = estraiId(url);
            lista.add(new PokemonListItem(id, nome));
        }
        return lista;
    }

    // --- NATURE ---

    // Restituisce la lista di tutte le nature con i loro modificatori
    public static List<NaturaData> getListaNature() {
        JsonObject json = get(BASE_URL + "nature?limit=25");
        if (json == null) return new ArrayList<>();

        List<NaturaData> lista = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray("results")) {
            String urlNatura = el.getAsJsonObject().get("url").getAsString();

            JsonObject dettagli = get(urlNatura);
            if (dettagli == null) continue;

            NaturaData natura = new NaturaData();
            natura.nome = dettagli.get("name").getAsString();

            JsonElement statAum = dettagli.get("increased_stat");
            JsonElement statDim = dettagli.get("decreased_stat");

            natura.statAumentata = statAum.isJsonNull() ? "none" : statAum.getAsJsonObject().get("name").getAsString();
            natura.statDiminuita = statDim.isJsonNull() ? "none" : statDim.getAsJsonObject().get("name").getAsString();

            lista.add(natura);
        }
        return lista;
    }

    // --- CLASSI DATI INTERNI ---

    // Contiene i dati di un pokemon restituiti dalla PokeAPI
    public static class PokemonData {
        public int id;
        public String nome;
        public String spriteUrl;
        public List<String> tipi;
        public Map<String, Integer> stats;
        public List<int[]> mosseDisponibili;
        public List<String> nomeMosseDisponibili;
        public List<String> abilita;

        public PokemonData() {
            nomeMosseDisponibili = new ArrayList<>();
            mosseDisponibili     = new ArrayList<>();
        }

        // Restituisce una stat per nome (es. "attack", "speed")
        public int getStat(String nome) {
            return stats.getOrDefault(nome, 0);
        }
    }

    // Piccola classe di supporto usata solo per serializzare le mosse disponibili nella cache
    private static class MossaRef {
        int id;
        String nome;
        MossaRef(int id, String nome) { this.id = id; this.nome = nome; }
    }

    // Cache in memoria — tutti i pokemon caricati rimangono qui durante l'esecuzione
    public static List<PokemonData> tuttiIPokemon = new ArrayList<>();
    public static boolean caricamentoCompletato = false;

    // Numero totale di pokemon esistenti (usato per capire se la cache su database è completa)
    private static final int TOTALE_POKEMON = 1025;

    // Carica tutti i pokemon, usando prima la cache nel database se disponibile.
    // Se il database contiene già tutti i pokemon, li carica da lì (istantaneo, nessuna chiamata HTTP).
    // Altrimenti li scarica dalla PokeAPI e li salva nel database mano a mano, cosi la prossima
    // volta non serve riscaricarli.
    // onProgress viene chiamato ad ogni pokemon caricato (per aggiornare la barra)
    // onComplete viene chiamato quando ha finito tutto
    public static void caricaTuttiIPokemon(Database db,
                                            java.util.function.Consumer<Integer> onProgress,
                                            Runnable onComplete) {
        new Thread(() -> {
            try {
                // 1. Controlla se il database ha già tutti i pokemon in cache
                int inCache = db.contaPokemonCache();

                if (inCache >= TOTALE_POKEMON) {
                    List<PokemonData> daCache = caricaPokemonDaDatabase(db);
                    tuttiIPokemon = Collections.synchronizedList(new ArrayList<>(daCache));
                    caricamentoCompletato = true;
                    onProgress.accept(tuttiIPokemon.size());
                    onComplete.run();
                    return;
                }

                // 2. Cache assente o incompleta: scarica dall'API
                JsonObject json = get(BASE_URL + "pokemon?limit=" + TOTALE_POKEMON + "&offset=0");
                if (json == null) return;

                List<String> urls = new ArrayList<>();
                for (JsonElement el : json.getAsJsonArray("results")) {
                    urls.add(el.getAsJsonObject().get("url").getAsString());
                }

                int totale = urls.size();
                tuttiIPokemon = Collections.synchronizedList(new ArrayList<>());

                // Divide in gruppi e carica in parallelo
                int gruppoSize = 20;
                java.util.concurrent.atomic.AtomicInteger contatore =
                    new java.util.concurrent.atomic.AtomicInteger(0);

                for (int i = 0; i < totale; i += gruppoSize) {
                    int fine = Math.min(i + gruppoSize, totale);
                    List<String> gruppo = urls.subList(i, fine);

                    List<Thread> threads = new ArrayList<>();
                    for (String url : gruppo) {
                        Thread t = new Thread(() -> {
                            // Estrae il nome dalla url e carica il pokemon
                            String urlPulita = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
                            String nome = urlPulita.substring(urlPulita.lastIndexOf("/") + 1);
                            PokemonData pk = getPokemon(nome);
                            if (pk != null) {
                                tuttiIPokemon.add(pk);
                                salvaPokemonInCache(db, pk);
                            }
                            // Aggiorna il contatore e notifica il progresso
                            int fatto = contatore.incrementAndGet();
                            onProgress.accept(fatto);
                        });
                        threads.add(t);
                        t.start();
                    }

                    // Aspetta che tutti i thread del gruppo finiscano prima di passare al prossimo
                    for (Thread t : threads) {
                        t.join();
                    }
                }

                // 3. Ordina per id
                tuttiIPokemon.sort((a, b) -> Integer.compare(a.id, b.id));
                caricamentoCompletato = true;

                // 4. Notifica che ha finito
                onComplete.run();

            } catch (Exception e) {
                System.out.println("Errore caricamento pokemon: " + e.getMessage());
            }
        }).start();
    }

    // Salva un singolo PokemonData nella tabella pokemon_cache del database
    private static void salvaPokemonInCache(Database db, PokemonData pk) {
        String tipiJson = gson.toJson(pk.tipi);
        String statsJson = gson.toJson(pk.stats);

        List<MossaRef> mosseRef = new ArrayList<>();
        for (int i = 0; i < pk.mosseDisponibili.size(); i++) {
            mosseRef.add(new MossaRef(pk.mosseDisponibili.get(i)[0], pk.nomeMosseDisponibili.get(i)));
        }
        String mosseJson = gson.toJson(mosseRef);
        String abilitaJson = gson.toJson(pk.abilita);

        db.salvaPokemonCache(pk.id, pk.nome, pk.spriteUrl, tipiJson, statsJson, mosseJson, abilitaJson);
    }

    // Carica tutti i pokemon salvati nel database (istantaneo, nessuna chiamata HTTP)
    public static List<PokemonData> caricaPokemonDaDatabase(Database db) {
        List<PokemonData> lista = new ArrayList<>();
        ResultSet rs = db.getAllPokemonCache();
        if (rs == null) return lista;

        try {
            while (rs.next()) {
                PokemonData pk = new PokemonData();
                pk.id = rs.getInt("id");
                pk.nome = rs.getString("nome");
                pk.spriteUrl = rs.getString("sprite_url");

                pk.tipi = gson.fromJson(rs.getString("tipi_json"),
                        new TypeToken<List<String>>(){}.getType());

                pk.stats = gson.fromJson(rs.getString("stats_json"),
                        new TypeToken<Map<String, Integer>>(){}.getType());

                List<MossaRef> mosseRef = gson.fromJson(rs.getString("mosse_json"),
                        new TypeToken<List<MossaRef>>(){}.getType());
                pk.mosseDisponibili = new ArrayList<>();
                pk.nomeMosseDisponibili = new ArrayList<>();
                if (mosseRef != null) {
                    for (MossaRef m : mosseRef) {
                        pk.mosseDisponibili.add(new int[]{m.id, 0});
                        pk.nomeMosseDisponibili.add(m.nome);
                    }
                }

                pk.abilita = gson.fromJson(rs.getString("abilita_json"),
                        new TypeToken<List<String>>(){}.getType());

                lista.add(pk);
            }
        } catch (Exception e) {
            System.out.println("Errore lettura cache pokemon: " + e.getMessage());
        }
        return lista;
    }

    // Contiene i dati di una mossa restituiti dalla PokeAPI
    public static class MossaData {
        public int id;
        public String nome;
        public int pp;
        public int potenza;
        public int precisione;
        public String tipo;
        public String categoria;
        public String descrizione;
    }

    // Elemento della lista pokemon (id + nome)
    public static class PokemonListItem {
        public int id;
        public String nome;

        public PokemonListItem(int id, String nome) {
            this.id   = id;
            this.nome = nome;
        }
    }

    // Contiene i dati di una natura
    public static class NaturaData {
        public String nome;
        public String statAumentata;
        public String statDiminuita;
    }
}