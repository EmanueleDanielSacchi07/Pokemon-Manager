import java.io.*;

public class Teams {
    Team[] teams;

    Teams() {
        teams = new Team[6];
        for (int i = 0; i < 6; i++)
            teams[i] = new Team("Team " + (i + 1));
    }

    boolean writePokemonOnFile(Pokemon p, int teamIndex) {

        boolean thereIsSpace = teams[teamIndex].newPokemon(p);
        if (!thereIsSpace) return false;

        teams[teamIndex].countPokemon++;
        String fileName = "teams/Team" + (teamIndex + 1) + ".csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(p.toStringCsv());
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}