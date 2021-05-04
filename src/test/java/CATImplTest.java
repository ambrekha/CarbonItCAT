import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class CATImplTest {

    String[][] map;
    Adventurer adventurer;
    CATImpl cat = new CATImpl();

    public void ReadInFile() throws Exception {

        FileReader input = new FileReader("src/main/resources/in.txt");
        BufferedReader bufferedReader = new BufferedReader(input);
        String myLine;

        while((myLine = bufferedReader.readLine()) != null) {

            String[] line = myLine.split(" - ");

            switch (line[0]) {
                case "#": // the read line is a comment and must be ignored
                    break;
                case "C": // the read line contains info about the map
                    map = new String[Integer.valueOf(line[1])][Integer.valueOf(line[2])];
                    break;
                case "M": // the read line contains info about one mountain
                    map[Integer.valueOf(line[1])][Integer.valueOf(line[2])] = "M";
                    break;
                case "T": // the read line contains info about one treasure cell
                    map[Integer.valueOf(line[1])][Integer.valueOf(line[2])] = "T" + Integer.valueOf(line[3]);
                    break;
                case "A": // the read line contains info about the adventurer
                    String[] moves = line[5].split("");
                    int pX = Integer.valueOf(line[2]);
                    int pY = Integer.valueOf(line[3]);

                    adventurer = new Adventurer(line[4], pX, pY, moves , line[1]);
                    map[pX][pY] = line[1];
                    break;
                default:
                    break;
            }
        }
    }

    /*
    * Each tested unit comes with 1 passing scenario and 1 failing scenario
     */

    @Before
    public void SetUp() throws Exception{

        ReadInFile();

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] == null)
                    map[i][j] = ".";
            }
        }

        cat.map = map;
        cat.adventurer = adventurer;

    }

    @Test
    public void TestAdventurer() {
        String[] expectedMoves = {"A","A","D","A","D","A","G","G","A"};
        Adventurer expectedAdventurer = new Adventurer("S", 1, 1, expectedMoves, "Lara");

        Assert.assertEquals(expectedAdventurer.getOrientation(), adventurer.getOrientation());
        Assert.assertEquals(expectedAdventurer.getMoves(), adventurer.getMoves());
        Assert.assertEquals(expectedAdventurer.getpX(), adventurer.getpX());
        Assert.assertEquals(expectedAdventurer.getpY(), adventurer.getpY());

    }

    @Test
    public void TestMap() {
        String[][] expectedMap = { {".", ".", ".", "T2"},
                                    {"M", "Lara", ".", "T3"},
                                    {".", "M", ".", "."}};

        Assert.assertEquals(map, expectedMap);

    }

    @Test
    public void TestMapFail() {

        String[][] expectedMap = { {".", ".", ".", "T"},
                {"M", ".", ".", "T"},
                {".", "M", "Lara", "."}};

        Assert.assertNotEquals(map, expectedMap);

    }

    @Test
    public void TestMove() {

        cat.Move(adventurer.getpX(), adventurer.getpY(), adventurer.getpX()-1, adventurer.getpY());
        int expectedX = 0;
        int expectedY = 1;

        int actualX = adventurer.getpX();
        int actualY = adventurer.getpY();

        Assert.assertEquals(actualX, expectedX);
        Assert.assertEquals(actualY, expectedY);

    }

    @Test
    public void TestMoveFail() {

        // adventurer tries to move to a mountain
        cat.Move(adventurer.getpX(), adventurer.getpY(), adventurer.getpX()-1, adventurer.getpY());
        int expectedX = 1;
        int expectedY = 1;

        Assert.assertNotEquals(adventurer.getpX(), expectedX);
        Assert.assertEquals(adventurer.getpY(), expectedY);
    }
}
