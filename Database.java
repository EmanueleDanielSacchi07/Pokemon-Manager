import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
   private static final String DB_URL = "jdbc:sqlite:pokemon_manager.db";
   private Connection conn;

   public Database() {
      try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:pokemon_manager.db");
            this.creaTabelle();
            System.out.println("Database connesso.");
            this.conn = DriverManager.getConnection("jdbc:sqlite:pokemon_manager.db");
            Statement pragma = this.conn.createStatement();
            pragma.execute("PRAGMA journal_mode=WAL;");
            pragma.close();
            this.creaTabelle();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite non trovato: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Errore connessione database: " + e.getMessage());
        }

   }

   private void creaTabelle() throws SQLException {
      Statement st = this.conn.createStatement();

      st.execute("    CREATE TABLE IF NOT EXISTS teams (\n        id   INTEGER PRIMARY KEY AUTOINCREMENT,\n        nome TEXT NOT NULL\n    )\n");

      st.execute("    CREATE TABLE IF NOT EXISTS team_pokemon (\n        id          INTEGER PRIMARY KEY AUTOINCREMENT,\n        team_id     INTEGER NOT NULL REFERENCES teams(id),\n        pokemon_id  INTEGER NOT NULL,\n        soprannome  TEXT,\n        livello     INTEGER DEFAULT 50,\n        natura      TEXT,\n        slot        INTEGER NOT NULL\n    )\n");

      st.execute("    CREATE TABLE IF NOT EXISTS pokemon_stats (\n        pokemon_slot_id INTEGER PRIMARY KEY REFERENCES team_pokemon(id),\n        iv_hp    INTEGER DEFAULT 31,\n        iv_atk   INTEGER DEFAULT 31,\n        iv_def   INTEGER DEFAULT 31,\n        iv_spatk INTEGER DEFAULT 31,\n        iv_spdef INTEGER DEFAULT 31,\n        iv_speed INTEGER DEFAULT 31,\n        ev_hp    INTEGER DEFAULT 0,\n        ev_atk   INTEGER DEFAULT 0,\n        ev_def   INTEGER DEFAULT 0,\n        ev_spatk INTEGER DEFAULT 0,\n        ev_spdef INTEGER DEFAULT 0,\n        ev_speed INTEGER DEFAULT 0\n    )\n");

      st.execute("    CREATE TABLE IF NOT EXISTS pokemon_mosse (\n        id              INTEGER PRIMARY KEY AUTOINCREMENT,\n        pokemon_slot_id INTEGER NOT NULL REFERENCES team_pokemon(id),\n        mossa_id        INTEGER NOT NULL,\n        slot            INTEGER NOT NULL\n    )\n");

      // Tabella di cache: contiene tutti i dati dei pokemon scaricati dalla PokeAPI,
      // cosi non serve riscaricarli ad ogni avvio del programma
      st.execute("    CREATE TABLE IF NOT EXISTS pokemon_cache (\n        id           INTEGER PRIMARY KEY,\n        nome         TEXT NOT NULL,\n        sprite_url   TEXT,\n        tipi_json    TEXT,\n        stats_json   TEXT,\n        mosse_json   TEXT,\n        abilita_json TEXT\n    )\n");

      st.execute("    CREATE TABLE IF NOT EXISTS mosse_cache (\n        id          INTEGER PRIMARY KEY,\n        nome        TEXT NOT NULL,\n        pp          INTEGER,\n        potenza     INTEGER,\n        precisione  INTEGER,\n        tipo        TEXT,\n        categoria   TEXT,\n        descrizione TEXT\n    )\n");
      
      st.close();
   }

   public int inserisciTeam(String nome) {
      String sql = "INSERT INTO teams (nome) VALUES (?)";
      try {
         PreparedStatement ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         try {
            ps.setString(1, nome);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
               return rs.getInt(1);
            }
            return -1;
         } finally {
            ps.close();
         }
      } catch (SQLException e) {
         System.out.println("Errore inserisciTeam: " + e.getMessage());
         return -1;
      }
   }

   public ResultSet getAllTeams() {
      try {
         Statement st = this.conn.createStatement();
         return st.executeQuery("SELECT * FROM teams ORDER BY id");
      } catch (SQLException e) {
         System.out.println("Errore getAllTeams: " + e.getMessage());
         return null;
      }
   }

   public void eliminaTeam(int teamId) {
      try {
         Statement st = this.conn.createStatement();
         st.execute("DELETE FROM pokemon_mosse WHERE pokemon_slot_id IN (SELECT id FROM team_pokemon WHERE team_id = " + teamId + ")");
         st.execute("DELETE FROM pokemon_stats WHERE pokemon_slot_id IN (SELECT id FROM team_pokemon WHERE team_id = " + teamId + ")");
         st.execute("DELETE FROM team_pokemon WHERE team_id = " + teamId);
         st.execute("DELETE FROM teams WHERE id = " + teamId);
      } catch (SQLException e) {
         System.out.println("Errore eliminaTeam: " + e.getMessage());
      }
   }

   public int inserisciPokemon(int teamId, int pokemonId, String soprannome, int livello, String natura, int slot) {
      String sql = "    INSERT INTO team_pokemon (team_id, pokemon_id, soprannome, livello, natura, slot)\n    VALUES (?, ?, ?, ?, ?, ?)\n";
      try {
         PreparedStatement ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         try {
            ps.setInt(1, teamId);
            ps.setInt(2, pokemonId);
            ps.setString(3, soprannome);
            ps.setInt(4, livello);
            ps.setString(5, natura);
            ps.setInt(6, slot);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
               return rs.getInt(1);
            }
            return -1;
         } finally {
            ps.close();
         }
      } catch (SQLException e) {
         System.out.println("Errore inserisciPokemon: " + e.getMessage());
         return -1;
      }
   }

   public void inserisciStats(int slotId, int ivHp, int ivAtk, int ivDef, int ivSpAtk, int ivSpDef, int ivSpeed,
                               int evHp, int evAtk, int evDef, int evSpAtk, int evSpDef, int evSpeed) {
      String sql = "    INSERT INTO pokemon_stats VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n";
      try {
         PreparedStatement ps = this.conn.prepareStatement(sql);
         try {
            ps.setInt(1, slotId);
            ps.setInt(2, ivHp);
            ps.setInt(3, ivAtk);
            ps.setInt(4, ivDef);
            ps.setInt(5, ivSpAtk);
            ps.setInt(6, ivSpDef);
            ps.setInt(7, ivSpeed);
            ps.setInt(8, evHp);
            ps.setInt(9, evAtk);
            ps.setInt(10, evDef);
            ps.setInt(11, evSpAtk);
            ps.setInt(12, evSpDef);
            ps.setInt(13, evSpeed);
            ps.executeUpdate();
         } finally {
            ps.close();
         }
      } catch (SQLException e) {
         System.out.println("Errore inserisciStats: " + e.getMessage());
      }
   }

   public void inserisciMossa(int slotId, int mossaId, int slot) {
      String sql = "INSERT INTO pokemon_mosse (pokemon_slot_id, mossa_id, slot) VALUES (?, ?, ?)";
      try {
         PreparedStatement ps = this.conn.prepareStatement(sql);
         try {
            ps.setInt(1, slotId);
            ps.setInt(2, mossaId);
            ps.setInt(3, slot);
            ps.executeUpdate();
         } finally {
            ps.close();
         }
      } catch (SQLException e) {
         System.out.println("Errore inserisciMossa: " + e.getMessage());
      }
   }

   public ResultSet getPokemonDelTeam(int teamId) {
      try {
         PreparedStatement ps = this.conn.prepareStatement("    SELECT tp.*, ps.*\n    FROM team_pokemon tp\n    JOIN pokemon_stats ps ON ps.pokemon_slot_id = tp.id\n    WHERE tp.team_id = ?\n    ORDER BY tp.slot\n");
         ps.setInt(1, teamId);
         return ps.executeQuery();
      } catch (SQLException e) {
         System.out.println("Errore getPokemonDelTeam: " + e.getMessage());
         return null;
      }
   }

   public ResultSet getMosseDelPokemon(int slotId) {
      try {
         PreparedStatement ps = this.conn.prepareStatement("    SELECT * FROM pokemon_mosse\n    WHERE pokemon_slot_id = ?\n    ORDER BY slot\n");
         ps.setInt(1, slotId);
         return ps.executeQuery();
      } catch (SQLException e) {
         System.out.println("Errore getMosseDelPokemon: " + e.getMessage());
         return null;
      }
   }

   public void eliminaPokemon(int slotId) {
      try {
         Statement st = this.conn.createStatement();
         st.execute("DELETE FROM pokemon_mosse WHERE pokemon_slot_id = " + slotId);
         st.execute("DELETE FROM pokemon_stats WHERE pokemon_slot_id = " + slotId);
         st.execute("DELETE FROM team_pokemon WHERE id = " + slotId);
      } catch (SQLException e) {
         System.out.println("Errore eliminaPokemon: " + e.getMessage());
      }
   }

   // --- CACHE POKEMON (dati scaricati dalla PokeAPI) ---

   // Inserisce o aggiorna (se gia' presente) i dati di un pokemon nella cache
   public void salvaPokemonCache(int id, String nome, String spriteUrl, String tipiJson,
                                  String statsJson, String mosseJson, String abilitaJson) {
      String sql = "INSERT OR REPLACE INTO pokemon_cache " +
                   "(id, nome, sprite_url, tipi_json, stats_json, mosse_json, abilita_json) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)";
      try {
         PreparedStatement ps = this.conn.prepareStatement(sql);
         try {
            ps.setInt(1, id);
            ps.setString(2, nome);
            ps.setString(3, spriteUrl);
            ps.setString(4, tipiJson);
            ps.setString(5, statsJson);
            ps.setString(6, mosseJson);
            ps.setString(7, abilitaJson);
            ps.executeUpdate();
         } finally {
            ps.close();
         }
      } catch (SQLException e) {
         System.out.println("Errore salvaPokemonCache: " + e.getMessage());
      }
   }

   // Restituisce tutti i pokemon salvati in cache, ordinati per id
   public ResultSet getAllPokemonCache() {
      try {
         Statement st = this.conn.createStatement();
         return st.executeQuery("SELECT * FROM pokemon_cache ORDER BY id");
      } catch (SQLException e) {
         System.out.println("Errore getAllPokemonCache: " + e.getMessage());
         return null;
      }
   }

   // Conta quanti pokemon sono attualmente salvati in cache
   public int contaPokemonCache() {
      try {
         Statement st = this.conn.createStatement();
         ResultSet rs = st.executeQuery("SELECT COUNT(*) AS totale FROM pokemon_cache");
         if (rs.next()) {
            return rs.getInt("totale");
         }
      } catch (SQLException e) {
         System.out.println("Errore contaPokemonCache: " + e.getMessage());
      }
      return 0;
   }

   // Svuota completamente la cache (utile per forzare un ri-scaricamento futuro)
   public void svuotaPokemonCache() {
      try {
         Statement st = this.conn.createStatement();
         st.execute("DELETE FROM pokemon_cache");
      } catch (SQLException e) {
         System.out.println("Errore svuotaPokemonCache: " + e.getMessage());
      }
   }

   // --- CACHE MOSSE (dati scaricati dalla PokeAPI) ---

    // Inserisce o aggiorna (se gia' presente) i dati di una mossa nella cache
    public void salvaMossaCache(int id, String nome, int pp, int potenza, int precisione,
                                String tipo, String categoria, String descrizione) {
    String sql = "INSERT OR REPLACE INTO mosse_cache " +
                    "(id, nome, pp, potenza, precisione, tipo, categoria, descrizione) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement ps = this.conn.prepareStatement(sql);
        try {
            ps.setInt(1, id);
            ps.setString(2, nome);
            ps.setInt(3, pp);
            ps.setInt(4, potenza);
            ps.setInt(5, precisione);
            ps.setString(6, tipo);
            ps.setString(7, categoria);
            ps.setString(8, descrizione);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    } catch (SQLException e) {
        System.out.println("Errore salvaMossaCache: " + e.getMessage());
    }
    }

    // Restituisce la riga della cache per una mossa dato il suo id, o null se non presente
    public ResultSet getMossaCacheById(int id) {
    try {
        PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM mosse_cache WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs : null;
    } catch (SQLException e) {
        System.out.println("Errore getMossaCacheById: " + e.getMessage());
        return null;
    }
    }

   public void chiudi() {
      try {
         if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
            System.out.println("Database chiuso.");
         }
      } catch (SQLException e) {
         System.out.println("Errore chiusura database: " + e.getMessage());
      }
   }
}