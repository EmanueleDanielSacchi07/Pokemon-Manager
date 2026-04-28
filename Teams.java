import java.io.*;

public class Teams {
   Team []teams;
   
   Teams() {
        teams = new Team[6];
        for(int i = 0; i < 5; i++)
            teams[i] = new Team("Team " + (i + 1));
   }

    boolean writePokemonOnFile(Pokemon p, int teamIndex) {
        
        Boolean thereIsSpace = teams[teamIndex].newPokemon(p);
        if(thereIsSpace == false){
            return false;
        }

        teams[teamIndex].countPokemon++; 
        FileWriter fw;
        PrintWriter pw;
        String fileName = "teams/Team" + teamIndex + ".csv";

        try {   
            fw = new FileWriter(fileName, true);
            pw = new PrintWriter(fw);
            pw.println(p.toStringCsv());
            pw.close();       
            return true;
        } catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }


}
