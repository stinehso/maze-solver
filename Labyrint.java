import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class Labyrint {
    private Rute[][] lab;
    private int rader;
    private int kolonner;
    private static Rute[][] lTemp;
    private static int raderTemp;
    private static int kolonnerTemp;
    private boolean print = true;
    private OrdnetLenkeliste<Utvei> utveier;

    /**
     * Labyrint is the class representing the entire maze from a file
     * See lesFraFil for maze formatting
     * @param   l           2D Rute array for holding all the squares
     * @param   rader       number of rows
     * @param   kolonner    number of columns
     */
    private Labyrint(Rute[][] l, int rader, int kolonner) {
        lab = l;
        this.rader = rader;
        this.kolonner = kolonner;

        Rute nord, syd, vest, oest;

        for (int i = 0; i<rader; i++) {
            for (int j = 0; j<kolonner; j++) {
                try {
                    nord = lab[i-1][j];
                } catch(IndexOutOfBoundsException e) {
                    nord = null;
                }
                try {
                    syd = lab[i+1][j];
                } catch(IndexOutOfBoundsException e) {
                    syd = null;
                }
                try {
                    vest = lab[i][j-1];
                } catch(IndexOutOfBoundsException e) {
                    vest = null;
                }
                try {
                    oest = lab[i][j+1];
                } catch(IndexOutOfBoundsException e) {
                    oest = null;
                }
                lab[i][j].settNaboReferanser(this, nord, syd, vest, oest);
            }
        }
    }


    /**
     * Read and import a maze from a properly formatted file
     * The first line of the file states the number of rows and columns
     * separated by a space. E.g. '5 7' for 5 rows and 7 columns.
     * The maze itself must be formatted with # representing black space (walls)
     * and . representing white (open) space (and no enclosing frame)
     * Here is an example:
     * 8 9
     * #####.###
     * #.....#.#
     * #.#####.#
     * #.#.....#
     * #.#.###.#
     * #.#.#.#.#
     * #.....#.#
     * #########
     * @param   fil     the file to read
     * @return the labyrint object
     */
    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
        Scanner sc = null;
        try {
            sc = new Scanner(fil);
        } catch(FileNotFoundException e) {
            throw e;
        }

        String[] tall = sc.nextLine().split(" ");
        raderTemp = Integer.parseInt(tall[0]);
        kolonnerTemp = Integer.parseInt(tall[1]);
        lTemp = new Rute[raderTemp][kolonnerTemp];

        for (int i= 0; i<raderTemp; i++) {
            String linje = sc.nextLine();
            for (int j = 0; j<kolonnerTemp; j++) {
                char tegn = linje.charAt(j);

                switch(tegn) {
                    case '#' :
                    lTemp[i][j] = new SortRute(i+1, j+1);
                    break;

                    case '.' :
                    if (i == 0 || i == raderTemp-1 || j == 0 || j == kolonnerTemp-1) {
                        lTemp[i][j] = new Aapning(i+1, j+1);
                    } else {
                        lTemp[i][j] = new HvitRute(i+1, j+1);
                    }
                    break;

                    default :
                    break;
                }
            }
        }

        Labyrint labyrint = new Labyrint(lTemp, raderTemp, kolonnerTemp);

        return labyrint;
    }


    /**
     * Create a string representation of the maze similar to the input file
     * @return  complete maze representation
     */
    public String toString() {
        String tegnrepresentasjon = "";
        for (Rute[] rad : lab) {
            for (Rute rute : rad) {
                tegnrepresentasjon = tegnrepresentasjon + rute.tilTegn();
            }
            tegnrepresentasjon = tegnrepresentasjon + "\n";
        }
        return tegnrepresentasjon;
    }


    /**
     * Look for all paths out from selected square
     * @param   kol     column of starting point
     * @param   rad     row of starting point
     * @return  Koe (FIFO list) of all the paths found represented as strings
     */
    public Liste<String> finnUtveiFra(int kol, int rad) {
        utveier = new OrdnetLenkeliste<Utvei>();

        if (lab[rad-1][kol-1] instanceof SortRute) {
            Liste<String> feilKoordinater = new Koe<String>();
            feilKoordinater.settInn("Kan ikke finne utvei fra en sort rute");
            return feilKoordinater;
        }

        lab[rad-1][kol-1].finnUtvei();

        Liste<String> alleUtveiene = new Koe<String>();
        for (Utvei u : utveier) {
            alleUtveiene.settInn(u.toString());
        }

        alleUtveiene.settInn(String.format("Totalt %d utvei(er)", alleUtveiene.storrelse()));

        return alleUtveiene;
    }


    /**
     * Set minimal print to the terminal,
     * affects all the squares in the maze
     */
    public void settMinimalUtskrift() {
        print = false;
        for (Rute[] rad : lab) {
            for (Rute rute : rad) {
                rute.printAv();
            }
        }
    }


    /**
     * Add path to global list that allows them to be compared for length
     * @param   utvei   string representation of the path
     */
    public void leggTilUtvei(String utvei) {
        Utvei nyUtvei = new Utvei(utvei);
        utveier.settInn(nyUtvei);
    }


    /**
    * Return the array of squares
    * @return   the Rute array
    */
    public Rute[][] hentRuteArray() {
        return lab;
    }


    /**
     * Return the number of rows and columns in an array, and in that order
     * @return array containing number of rows and number of columns
     */
    public int[] hentRader() {
        int[] str = new int[2];
        str[0] = rader;
        str[1] = kolonner;
        return str;
    }


    /**
     * Print coordinates from each square in its position
     * (for debugging)
     */
    // public void skrivUtKoordinater() {
    //     for (Rute[] rad : lab) {
    //         for (Rute rute : rad) {
    //             System.out.print(rute.koord());
    //         }
    //         System.out.println("");
    //     }
    // }




}
