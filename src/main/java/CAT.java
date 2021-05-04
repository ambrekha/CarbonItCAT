import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class CAT {

    static CATImpl cat = new CATImpl();
    static ArrayList<Adventurer> adventurers;

    public static void ReadInFile() throws Exception {

        System.out.println("reading");

        FileReader input = new FileReader("src/main/resources/in.txt");
        BufferedReader bufferedReader = new BufferedReader(input);
        String myLine;

        while((myLine = bufferedReader.readLine()) != null) {

            String[] line = myLine.split(" - ");

            switch (line[0]) {
                case "#": // the read line is a comment and must be ignored
                    break;
                case "C": // the read line contains info about the map
                    cat.map = new String[Integer.valueOf(line[1])][Integer.valueOf(line[2])];
                    break;
                case "M": // the read line contains info about one mountain
                    cat.map[Integer.valueOf(line[1])][Integer.valueOf(line[2])] = "M";
                    break;
                case "T": // the read line contains info about one treasure cell
                    cat.map[Integer.valueOf(line[1])][Integer.valueOf(line[2])] = "T" + Integer.valueOf(line[3]);
                    break;
                case "A": // the read line contains info about the adventurer
                    String[] moves = line[5].split("");
                    int pX = Integer.valueOf(line[2]);
                    int pY = Integer.valueOf(line[3]);

                    Adventurer adventurer = new Adventurer(line[4], pX, pY, moves , line[1]);
                    adventurers.add(adventurer);
                    cat.map[pX][pY] = line[1];
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) throws Exception{

        System.out.println("starting");

        adventurers = new ArrayList<>();

        ReadInFile();

        // Since we don't know how many Adventurers have enrolled, we use ArrayList as they are resizable.
        ArrayList<String[]> moves = new ArrayList<>();
        ArrayList<String> orientations = new ArrayList<>();

        int i = 0;

        for(Adventurer ad : adventurers) {
            moves.add(ad.getMoves());
            orientations.add(ad.getOrientation());
            i++;
        }
        cat.adventurers = adventurers;
        i = 0;

        for(Adventurer ad : adventurers) {
            String[] move = moves.get(i);
            String orientation = orientations.get(i);

            int x = ad.getpX(), y = ad.getpY();
            int j = 0;

            while(j < moves.get(i).length) {
                switch (orientation) {
                    case "N":
                        switch (move[j]) {
                            case "A":
                                cat.Move(i, x, y, x, y + 1);
                                break;
                            case "D":
                                cat.Move(i, x, y, x + 1, y);
                                break;
                            case "G":
                                cat.Move(i, x, y, x - 1, y);
                                break;
                        }
                        break;
                    case "S":
                        switch (move[j]) {
                            case "A":
                                cat.Move(i, x, y, x, y - 1);
                                break;
                            case "D":
                                cat.Move(i, x, y, x - 1, y);
                                break;
                            case "G":
                                cat.Move(i, x, y, x + 1, y);
                                break;
                        }
                    case "E":
                        switch (move[j]) {
                            case "A":
                                cat.Move(i, x, y, x + 1, y);
                                break;
                            case "D":
                                cat.Move(i, x, y, x, y - 1);
                                break;
                            case "G":
                                cat.Move(i, x, y, x, y + 1);
                                break;
                        }
                    case "O":
                        switch (move[j]) {
                            case "A":
                                cat.Move(i, x, y, x - 1, y);
                                break;
                            case "D":
                                cat.Move(i, x, y, x, y + 1);
                                break;
                            case "G":
                                cat.Move(i, x, y, x, y - 1);
                                break;
                        }
                    default:
                        break;
                }
                j++;
            }
            i++;
        }

        for(Adventurer adventurer : adventurers)
            System.out.println(adventurer.getName() + " has finished.");
    }
}
