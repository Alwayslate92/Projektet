package dm550.tictactoe;

import android.graphics.CornerPathEffect;
import android.util.Log;

/**
 * represents a tic tac toe board of a given size
 */
public class TTTBoard {

    /**
     * 2-dimensional array representing the board
     * coordinates are counted from top-left (0,0) to bottom-right (size-1, size-1)
     * board[x][y] == 0   signifies free at position (x,y)
     * board[x][y] == i   for i > 0 signifies that Player i made a move on (x,y)
     */
    private int[][] board;

    /**
     * size of the (quadratic) board
     */
    private int size;

    /**
     * constructor for creating a copy of the board
     * not needed in Part 1 - can be viewed as an example
     */
    public TTTBoard(TTTBoard original) {
        this.size = original.size;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[y][x] = original.board[y][x];
            }
        }
    }

    /**
     * constructor for creating an empty board for a given number of players
     */
    public TTTBoard(int numPlayers) {
        /* Her bliver størrelsen af boardet lavet. Hvis der er 2 spillere
        Så bliver størelsen 3. Derefter sætter den størelsen med getters
        i bordet, så x bliver lig med 3, og y bliver lig med 3.
        */
        this.size = 7;
        // Den kan ikke lide, at jeg retter i størrelsen her
        this.board = new int[7][6];
    }

    /**
     * checks whether the board is free at the given position
     */
    public boolean isFree(Coordinate c) {
        if (board[c.getX()][c.getY()] == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns the players that made a move on (x,y) or 0 if the positon is free
     */
    public int getPlayer(Coordinate c) {
        if (isFree(c) == false) {
            return board[c.getX()][c.getY()];
        } else {
            return 0;
        }
    }

    /**
     * record that a given player made a move at the given position
     * checks that the given positions is on the board
     * checks that the player number is valid
     * Det er her, jeg skal få den til, at "ligge" brikkerne korrekt.
     * prøv at starte med at tjekke fra bunden.
     */
    public void addMove(Coordinate c, int player) {
        // Hvis den ikke er uden for boardet
        if (c.checkBoundaries(board.length, board[0].length) == true) {
            // for alle y som er lig 6 - 1. y skal også være >=0 og så går vi "nedad" med y--.
            for (int y = board[0].length - 1; y >= 0; y--) {
                // Tag den givet x-værdi
                int x = c.getX();
//                System.out.println(x + " " + y);
                /* Tjek alle y-værdierne i den kolonne der er angivet. Gå nedefra og op, den
                første kordinat den finder som er lig 0, skal nu være lig med player*/
                if (board[x][y] == 0) {
                    board[c.getX()][y] = player;
                    return;
                }
            }
        }
    }


    /* returns true if, and only if, there are no more free positions on the board
     Jeg skal have den til at løbe igennem alle elementer på boardet, og checke om de er
     != 0. Så hvis den tjekker om der er nogle elementer som er isFree for et eller andet cordinat,
     som er på boadet frie, skal den returne false.
     prøv at lave et for loop, der går igennem alle x og y værdier.

     */
    public boolean checkFull() {
        // Jeg skal trække en fra her, så draw virker (der er en mindre kolonne end der er rækker).
        for (int y = 0; y < board.length - 1; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][y] == 0) {
                    return false;
                }
            }
        }

        return true;

    }


    /**
     * returns 0 if no player has won (yet)
     * otherwise returns the number of the player that has three in a row
     */
    public int checkWinning() {

        /*Her tjekker jeg for diagonalen. Fra højre hjørne og nedad.
        Den tjekker for alle x og y værdier, derfor skal den ikke kunne ryge ud af defenitonsmægden
         ligegyldig, hvorhenne man starter fra. Selv hvis jeg ikke klikker på en, hvor den
         fungerer, så vil den stadig give fejl, fordi at den automatisk tjekker for alle muligheder.
         Derfor skal jeg minuse getSize() -3 for at sørge for, at den aldrig ryger ud af boardet

         getSize() = 7
         board[0].length = 6

         */


        for (int y = 3; y < board[0].length - 3; y++) {
            for (int x = 0; x < getSize() - 3; x++) {
                //System.out.println("Test af diagonal x: " + x +" y: " + y + " Coodinatets værdi: " +board[x][y]);
                XYCoordinate c = new XYCoordinate(x, y);
                Coordinate c1 = c.shift(1, 1);
                Coordinate c2 = c.shift(2, 2);
                Coordinate c3 = c.shift(3, 3);
                // Den kan ikke lide, at jeg tilføjer et nyt kordinat, og prøver at tjekke med den.
                if (getPlayer(c) == getPlayer(c1) && getPlayer(c) == getPlayer(c2) && getPlayer(c) == getPlayer(c3) && getPlayer(c) != 0) {
                    return getPlayer(c);
                }


            }

        }


        /* Den virker nu, tjekker fra højre hjørne og ned.
         getSize() = 7
         board[0].length = 6

         Grunden til at y < board[0].length = 4 er, at y´s længde er (0, 1, 2, 3)

         Grunden til at x < getSize() = 5 er, at x´s længde er (0, 1, 2, 3, 4). Når vi så bruger shift
         og laver tre nye kordinater, bliver x summeret med 1, 2 og 3. Hvilke vil sige, at vi skal sørge for
         at x + 3 stadig er under 5.
         5 - 3 = 2,    x < 2 = 1
         x + 3 = 4
         4 < 5.

         */
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < getSize() - 3; x++) {
                //System.out.println("Test af diagonal x: " + x +" y: " + y + " Coodinatets værdi: " +board[x][y]);
                XYCoordinate c = new XYCoordinate(x, y);
                Coordinate c1 = c.shift(1, -1);
                Coordinate c2 = c.shift(2, -2);
                Coordinate c3 = c.shift(3, -3);
                if (getPlayer(c) == getPlayer(c1) && getPlayer(c) == getPlayer(c2) && getPlayer(c) == getPlayer(c3) && getPlayer(c) != 0) {
                    return getPlayer(c);
                }


            }

        }


        /*

        board[0].length = 6
        getSize() = 7
        Grunden til at y < 4 er, at y´s længde er: (0, 1, 2 ,3, 4, 5)
        Grunden til at x < 2 er, at der bliver lagt 3 til i if staement, og x´s længde er: (0, 1, 2, 3, 4, 5, 6)
         */
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < getSize()-3; x++) {
                XYCoordinate c = new XYCoordinate(x, y);
                if (board[x][y] == board[x + 1][y] && board[x][y] == board[x + 2][y] && board[x][y] == board[x + 3][y] && getPlayer(c) != 0) {

                    return getPlayer(c);

                }


            }
        }
        /* Den virker nu. Grunden til at y < 1, er at der bliver lagt 3 til nede i if statment.
        Derfor vil den komme op på y = 3, hvilke er boardets maks længde i y ( 0, 1 , 2 , 3, 4, 5)
        Hvis jeg sætter y til at være lig med 2, vil den overskride boardets grænse.

        board[0].length = 6
        getSize() = 7

        Grunden til at x < 7, er at boardets længde i x er (0, 1, 2, 3 , 4, 5, 6)

         */
        for (int y = 0; y < board[0].length-3; y++) {
            for (int x = 0; x < getSize(); x++) {
//                System.out.println("Test af diagonal x: " + x + " y: " + y + " Coodinatets værdi: " + board[x][y]);
                XYCoordinate c = new XYCoordinate(x, y);
                if (board[x][y] == board[x][y + 1] && board[x][y] == board[x][y + 2] && board[x][y] == board[x][y + 3] && getPlayer(c) != 0) {

                    return getPlayer(c);

                }
            }
        }
        return 0;
    }

    /**
     * internal helper function checking one row, column, or diagonal
     */
    private int checkSequence(Coordinate start, int dx, int dy) {
        return 0;
    }

    /**
     * getter for size of the board
     */
    public int getSize() {
        return this.size;
    }

    /**
     * pretty printing of the board
     * usefule for debugging purposes
     */
    public String toString() {
        String result = "";
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                result += this.board[y][x] + " ";
            }
            result += "\n";
        }
        return result;
    }

}
