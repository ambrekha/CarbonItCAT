/**
 * @author A. Khamassi
 */

public class CATImpl {

    public String[][] map;
    public Adventurer adventurer;

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
    public void Move(int depX, int depY, int arrX, int arrY) {

        // to prevent IndexOutOfBoundsException, we check first if we are in the range of the map
        if(arrX < 0 || arrX >= map.length || arrY < 0 || arrY >= map[0].length)
            return;

        if(isMountain(arrX, arrY))
            return;
        adventurer.setPosition(arrX, arrY);
        map[depX][depY] = ".";
        map[arrX][arrY] = adventurer.getName();

        if(isTreasure(arrX, arrY)) CollectTreasure(arrX, arrY);
    }

    /**
     * Adventurer collects treasures
     * They will leave and return to the cell as long as there are treasures to collect
     * Check if the adventurer can move to collect it, moves on the first cell available
     * @param x position on X axis of the treasure(s) on the map
     * @param y position on Y axis of the treasure(s) on the map
     */
    public void CollectTreasure(int x, int y) {

        int nbTreasures = Integer.parseInt(map[x][y].replaceAll("[\\D]", ""));

        while(nbTreasures > 0) {
            if(!isMountain(x+1, y)) {
                Move(x, y, x+1, y);
                Move(x+1, y, x, y);
            }
            else if(!isMountain(x, y+1)) {
                Move(x, y, x, y+1);
                Move(x, y+1, x, y);
            }
            else if(!isMountain(x-1, y)) {
                Move(x, y, x-1, y);
                Move(x-1, y, x, y);
            }
            else if(!isMountain(x, y-1)) {
                Move(x, y, x, y-1);
                Move(x, y-1, x, y);
            }

            nbTreasures--;
        }

        System.out.println("Collected");

        // update the map to indicate there is no treasure left
        map[x][y] = ".";
    }

    /**
     * Returns whether the given cell is a mountain or not
     * @param x position on X axis of the potential mountain
     * @param y position on Y axis of the potential mountain
     * @return boolean
     */
    public boolean isMountain(int x, int y) {

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

        if(map[x][y].matches("^\\b[T][0-9]{1,1}?$")) return true;
        return false;
    }
}
