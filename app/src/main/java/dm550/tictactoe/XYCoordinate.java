package dm550.tictactoe;

public class XYCoordinate implements Coordinate {

    /**
     * variables specifying horizontal position on the board
     */
    private int x;

    /**
     * variable specifying vertical positoin on the board
     */
    private int y;

    /**
     * constructor creating a Coordinate from x and y values
     */
    public XYCoordinate(int x, int y) {
        this.x = x;
        this.y = y;


    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public boolean checkBoundaries(int xSize, int ySize) {

        /*Boardet er ikke symetrisk, som det var i tictaktoe. Derfor er antallet af rækker og kolonner heller ikke lige mere.
        Der er en mere kolonne, end der er rækker. xSize er antallet af rækker. ySize er antallet af kolonner. Derfor skal
        der være en mindre i ySize, end i xSize. */
        xSize = xSize - 1;
        ySize =  ySize- 2;
        if (xSize >= getX() & ySize >= getY() & getY() >= 0 & getX() >= 0) {
            return true;
        }
        else {

            return false;
        }

    }


    @Override
    public XYCoordinate shift(int dx, int dy) {
        /*
        • The method shift needs to construct a new object of class Coordinate with coordinates
        shifted dx to the right and dy down. For example, calling pos.shift(-1,1) with
        position (1,1) would result in a new position (0,2).*/
        XYCoordinate ShiftedCordinates = new XYCoordinate(dx +getX(), dy+getY());


        return ShiftedCordinates;
    }



}


