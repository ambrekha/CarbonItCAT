import java.text.NumberFormat;
import java.util.Arrays;

public class CAT {

    static String [][] map = { {"", "T2", ".", "T1"},
            {".", ".", "M", "T5"},
            {".", "M", ".", "."}};
    static Adventurer adventurer = new Adventurer("N", 2, 0, null, "Tester");
    static CATImpl cat = new CATImpl();

    public static void main(String[] args) throws Exception{

        System.out.println("starting");
        adventurer.setPosition(map.length-1, map[0].length-1);
        adventurer.setMoves("AADAGAGGA");
        String[] moves = adventurer.getMoves();
        cat.adventurer = adventurer;
        cat.map = map;
        cat.map[adventurer.getpX()][adventurer.getpY()] = adventurer.getName();
        int i = 0;

        String orientation = adventurer.getOrientation();
        int x = cat.adventurer.getpX(), y = cat.adventurer.getpY();

        while(i < moves.length) {
            switch (orientation) {
                case "N":
                    switch (moves[i]) {
                        case "A":
                            cat.Move(x, y, x, y+1);
                            break;
                        case "D":
                            cat.Move(x, y, x+1, y);
                            break;
                        case "G":
                            cat.Move(x, y, x-1, y);
                            break;
                    }
                    break;
                case "S":
                    switch (moves[i]) {
                        case "A":
                            cat.Move(x, y, x, y-1);
                            break;
                        case "D":
                            cat.Move(x, y, x-1, y);
                            break;
                        case "G":
                            cat.Move(x, y, x+1, y);
                            break;
                    }
                case "E":
                    switch (moves[i]) {
                        case "A":
                            cat.Move(x, y, x+1, y);
                            break;
                        case "D":
                            cat.Move(x, y, x, y-1);
                            break;
                        case "G":
                            cat.Move(x, y, x, y+1);
                            break;
                    }
                case "O":
                    switch (moves[i]) {
                        case "A":
                            cat.Move(x, y, x-1, y);
                            break;
                        case "D":
                            cat.Move(x, y, x, y+1);
                            break;
                        case "G":
                            cat.Move(x, y, x, y-1);
                            break;
                    }
            }

            System.out.println(Arrays.deepToString(map));
            i++;
        }
        System.out.println("done");
    }
}
