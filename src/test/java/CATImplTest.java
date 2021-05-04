import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CATImplTest {

    String[][] map;
    ArrayList<Adventurer> adventurers = new ArrayList<>();
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


                    Adventurer adventurer = new Adventurer(line[4], pX, pY, moves , line[1]);
                    adventurers.add(adventurer);
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
        cat.adventurers = adventurers;

    }

    @Test
    public void TestAdventurer() {
        String[] expectedMoves = {"A","A","D","A","D","A","G","G","A"};
        Adventurer expectedAdventurer = new Adventurer("S", 1, 1, expectedMoves, "Lara");

        Assert.assertEquals(expectedAdventurer.getOrientation(), adventurers.get(0).getOrientation());
        Assert.assertEquals(expectedAdventurer.getMoves(), adventurers.get(0).getMoves());
        Assert.assertEquals(expectedAdventurer.getpX(), adventurers.get(0).getpX());
        Assert.assertEquals(expectedAdventurer.getpY(), adventurers.get(0).getpY());

    }

    @Test
    public void TestMap() {
        String[][] expectedMap = { {".", "Jonas", ".", "T2"},
                                    {"M", "Lara", ".", "T3"},
                                    {".", "M", ".", "."}};

        Assert.assertEquals(map, expectedMap);

    }

    @Test
    public void TestMapFail() {

        String[][] expectedMap = { {".", ".", ".", "T2"},
                {"M", ".", ".", "T3"},
                {".", "M", "Lara", "."}};

        Assert.assertNotEquals(map, expectedMap);

    }

    @Test
    public void TestMove() {

        cat.Move(0, adventurers.get(0).getpX(), adventurers.get(0).getpY(), adventurers.get(0).getpX(), adventurers.get(0).getpY()+1);
        int expectedX = 1;
        int expectedY = 2;

        int actualX = adventurers.get(0).getpX();
        int actualY = adventurers.get(0).getpY();

        Assert.assertEquals(actualX, expectedX);
        Assert.assertEquals(actualY, expectedY);
        Assert.assertEquals(map[actualX][actualY], adventurers.get(0).getName());

    }

    @Test
    public void TestMoveFail() {

        // adventurer tries to move to the position of another adventurer
        cat.Move(0, adventurers.get(0).getpX(), adventurers.get(0).getpY(), adventurers.get(0).getpX()-1, adventurers.get(0).getpY());

        int actualX = adventurers.get(0).getpX();
        int actualY = adventurers.get(0).getpY();
        int expectedX = 0;
        int expectedY = 1;

        Assert.assertNotEquals(expectedX, actualX);
        Assert.assertEquals(expectedY, actualY);
        Assert.assertEquals(map[actualX-1][actualY], adventurers.get(1).getName());
    }

    @Test
    public void TestMoveTreasure() {

        // adventurer moves to a cell containing at least one treasure
        cat.Move(0, adventurers.get(0).getpX(), adventurers.get(0).getpY(), adventurers.get(0).getpX(), adventurers.get(0).getpY()+1);
        cat.Move(0, adventurers.get(0).getpX(), adventurers.get(0).getpY(), adventurers.get(0).getpX()-1, adventurers.get(0).getpY());
        cat.Move(0, adventurers.get(0).getpX(), adventurers.get(0).getpY(), adventurers.get(0).getpX(), adventurers.get(0).getpY()+1);

        int actualX = adventurers.get(0).getpX();
        int actualY = adventurers.get(0).getpY();
        int expectedX = 0;
        int expectedY = 3;

        Assert.assertEquals(expectedX, actualX);
        Assert.assertEquals(expectedY, actualY);
        Assert.assertEquals(map[actualX][actualY], adventurers.get(0).getName());
    }

    @Test
    public void TestMightBeAdventurer() {

        Assert.assertTrue(cat.mightBeAdventurer(adventurers.get(0).getpX(), adventurers.get(0).getpY()));

    }

    @Test
    public void TestMightBeAdventurerFail() {
        Assert.assertFalse(cat.mightBeAdventurer(0, 3));
    }
}
