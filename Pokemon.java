/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import java.util.*;

public class Pokemon {

    // Dati base
    public int id;              // id PokeAPI
    public String nome;
    public String nomePersonale;
    public List<String> tipi;
    public String spriteUrl;    // URL dell'immagine dalla PokeAPI

    // Statistiche base (dalla PokeAPI)
    public Map<String, Integer> baseStats;

    // Statistiche personalizzate
    public int livello;
    public Map<String, Integer> iv; // hp, attack, defense, special-attack, special-defense, speed
    public Map<String, Integer> ev;

    // Mosse e natura
    public List<Mossa> mosse;
    public String natura;

    // Abilità
    public List<String> abilita;

    // Costruttore completo — usato quando si carica dal database
    public Pokemon(int id, String nome, String nomePersonale, List<String> tipi,
                   String spriteUrl, Map<String, Integer> baseStats, int livello,
                   Map<String, Integer> iv, Map<String, Integer> ev,
                   List<Mossa> mosse, String natura, List<String> abilita) {
        this.id           = id;
        this.nome         = nome;
        this.nomePersonale = nomePersonale;
        this.tipi         = tipi;
        this.spriteUrl    = spriteUrl;
        this.baseStats    = baseStats;
        this.livello      = livello;
        this.iv           = iv;
        this.ev           = ev;
        this.mosse        = mosse;
        this.natura       = natura;
        this.abilita      = abilita;
    }

    // Costruttore da PokemonData — usato quando si seleziona un pokemon dalla PokeAPI
    public Pokemon(PokeApiClient.PokemonData data) {
        this.id        = data.id;
        this.nome      = data.nome;
        this.tipi      = data.tipi;
        this.spriteUrl = data.spriteUrl;
        this.baseStats = data.stats;
        this.abilita   = data.abilita;
        this.livello   = 50;
        this.mosse     = new ArrayList<>();
        this.natura    = "hardy";

        // IV e EV di default
        this.iv = new HashMap<>();
        this.ev = new HashMap<>();
        for (String stat : new String[]{"hp", "attack", "defense", "special-attack", "special-defense", "speed"}) {
            iv.put(stat, 31);
            ev.put(stat, 0);
        }
    }

    // Restituisce una stat base per nome (es. "attack", "speed")
    public int getBaseStat(String nome) {
        return baseStats.getOrDefault(nome, 0);
    }

    // Restituisce un IV per nome
    public int getIv(String nome) {
        return iv.getOrDefault(nome, 31);
    }

    // Restituisce un EV per nome
    public int getEv(String nome) {
        return ev.getOrDefault(nome, 0);
    }

    @Override
    public String toString() {
        return nome + (nomePersonale != null ? " (" + nomePersonale + ")" : "");
    }
}