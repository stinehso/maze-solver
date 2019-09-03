import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;



class TestMain {
    /**
     * Tests reading in a file, creating maze object from it and solving
     * from two different places in the maze. Prints the shortest path
     * as coordinates and as ASCII representation to the terminal.
     * Note: coordinates starts with x or column cordinate.
     */
    public static void main(String[] args) {
        File fil = new File("../input_files/1.in");
        Labyrint lab = null;

        try {
            lab = Labyrint.lesFraFil(fil);
        } catch (Exception e) {
        }

        lab.finnUtveiFra(8, 7);
        lab.finnUtveiFra(2, 3);
    }
}
