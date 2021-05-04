public class Adventurer {

    private String orientation;
    private String name;
    private int pX, pY;
    private String[] moves;
    private int treasures = 0;

    /**
     * @param orientation current orientation of the adventurer
     * @param x starting position of the adventurer on axis x, stocked in pX
     * @param y starting position of the adventurer on axis y, stocked in pY
     * @param m moves sequence that the adventurer will have to follow for this run
     * @param name name of the adventurer
     */
    public Adventurer(String orientation, int x, int y, String[] m, String name) {
        this.orientation = orientation;
        this.pX = x;
        this.pY = y;
        this.moves = m;
        this.name = name;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setPosition(int x, int y) {
        this.pX = x;
        this.pY = y;
    }

    public int getpX() {
        return pX;
    }

    public void setpX(int pX) {
        this.pX = pX;
    }

    public int getpY() {
        return pY;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }

    public String[] getMoves() {
        return moves;
    }

    public String getName() {
        return name;
    }

    public void setMoves(String moves) {
        this.moves = moves.split("");
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int t) {
        this.treasures = t;
    }

}
