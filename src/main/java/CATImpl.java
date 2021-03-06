import java.util.ArrayList;

/**
 * @author A. Khamassi
 */

public class CATImpl {

    public String[][] map;
    public ArrayList<Adventurer> adventurers;

    public CATImpl() {}

    /**
     * Allows the adventurer to wander on the map, regarding obstacles and treasures
     * If arrival cell is a mountain, adventurer doesn't move
     * Else adventurer position is updated and map is updated
     * @param depX departure of the adventurer on X avis
     * @param depY departure of the adventurer on Y axis
     * @param arrX arrival of the adventurer on X axis
     * @param arrY arrival of the adventurer on Y axis
     */
    public void Move(int index, int depX, int depY, int arrX, int arrY) {

        // to prevent IndexOutOfBoundsException, we check first if we are in the range of the map array
        if(arrX < 0 || arrX >= map.length || arrY < 0 || arrY >= map[0].length)
            return;

        // if the arrival cell is a mountain or has another adventurer, the current adventurer cannot move
        if(isMountain(arrX, arrY) || mightBeAdventurer(arrX, arrY))
            return;

        // otherwise the position of the current adventurer and the map are updated
        adventurers.get(index).setPosition(arrX, arrY);
        map[depX][depY] = ".";

        // if the adventurer is lucky, the arrival cell has at least one treasure
        if(isTreasure(arrX, arrY)) CollectTreasure(index, arrX, arrY);
        else {
            map[arrX][arrY] = adventurers.get(index).getName();
        }
    }

    /**
     * Adventurer collects treasures
     * They will leave and return to the cell as long as there are treasures to collect
     * Check if the adventurer can move to collect it, moves on the first cell available (based on N, S, E or W movement)
     * @param x position on X axis of the treasure(s) on the map
     * @param y position on Y axis of the treasure(s) on the map
     */
    public void CollectTreasure(int index, int x, int y) {

        // parse the String of the cell to extract number of treasures
        // cell String is of form "T{X}" with {X} a number > 0
        int nbTreasures = Integer.parseInt(map[x][y].replaceAll("[\\D]", ""));
        int collected = 0;

        while(nbTreasures > 0) {
            if(!isMountain(x+1, y)) {
                Move(index, x, y, x+1, y);
                Move(index,x+1, y, x, y);
            }
            else if(!isMountain(x, y+1)) {
                Move(index, x, y, x, y+1);
                Move(index, x, y+1, x, y);
            }
            else if(!isMountain(x-1, y)) {
                Move(index, x, y, x-1, y);
                Move(index, x-1, y, x, y);
            }
            else if(!isMountain(x, y-1)) {
                Move(index, x, y, x, y-1);
                Move(index, x, y-1, x, y);
            }

            nbTreasures--;
            adventurers.get(index).setTreasures(++collected);
        }

        // update the map to indicate there is no treasure left
        // not treasure left is indicated by a symbol other than "T{X}"
        map[x][y] = adventurers.get(index).getName();
    }

    /**
     * Returns whether the given cell is a mountain or not
     * @param x position on X axis of the potential mountain
     * @param y position on Y axis of the potential mountain
     * @return boolean
     */
    public boolean isMountain(int x, int y) {

        // Since map is filled with Strings, we simply check if the symbol for "Mountain" is stored at (x, y)
        if(map[x][y] == "M")
            return true;
        return false;
    }

    /**
     * Returns whether the given cell contains treasures or not
     * @param x position on X axis of the potential treasure cell
     * @param y position on Y axis of the potential treasure cell
     * @return boolean
     */
    public boolean isTreasure(int x, int y) {

        // Since map is filled with Strings, we simply check if the symbol for "Treasure" is store ar (x, y)
        if(map[x][y].matches("^\\b[T][0-9]{1,1}?$")) return true;
        return false;
    }

    public boolean mightBeAdventurer(int x, int y) {

        // Since map is filled with Strings, we simply check if the cell is NOT a treasure cell or NOT a mountain or NOT "empty"
        if(map[x][y] == "." || isTreasure(x, y) || isMountain(x, y) ) return false;
        return true;
    }
}
