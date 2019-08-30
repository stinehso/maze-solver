//characterEncoding=UTF-8


abstract class Rute {
    protected Labyrint labyrint;
    protected int rad;
    protected int kolonne;
    protected Rute nord;
    protected Rute syd;
    protected Rute vest;
    protected Rute oest;
    protected boolean print = true;
    protected boolean paaVeien = false;

    /**
     * The Rute classes (squares) have references to their own coordinates,
     * neighbouring squares, and container Labyrint (maze)
     * @param   rad     row this square is in
     * @param   kolonne column this square is in
     */
    Rute(int rad, int kolonne) {
        this.rad = rad;
        this.kolonne = kolonne;
    }


    /**
     * Reference container maze and neighbouring squares
     * @param   labyrint    container maze
     * @param   nord        square to the north
     * @param   syd         square to the south
     * @param   vest        square to the west
     * @param   oest        square to the east
     */
    public void settNaboReferanser(Labyrint labyrint, Rute nord, Rute syd, Rute vest, Rute oest) {
        this.labyrint = labyrint;
        this.nord = nord;
        this.syd = syd;
        this.vest = vest;
        this.oest = oest;
    }


    /**
     * Start looking for a way out
     */
    public void finnUtvei(){
        gaa("ingen", "");
    }


    /**
     * Recursively (depth first) create path from current square.
     * Returns immediately if this is an opening, otherwise returns after
     * checking all subsequent squares
     * @param   komFra          string naming the direction we came from
     * @param   forelopigVei    string formatted coordinates on the path
     */
    public void gaa(String komFra, String forelopigVei) {
        //if (print) System.out.println("gikk til " + kolonne + ", " + rad);
        //if (print) System.out.println(labyrint.toString());

        paaVeien = true;

        String denneRuta = String.format("(%d, %d)", kolonne, rad);
        forelopigVei = forelopigVei + denneRuta;                //lagre ruta som String av koordinater

        if (this instanceof Aapning) {                          //lagre utveien hvis aapning
            if (print) System.out.println("Hurra! En utvei!");
            labyrint.leggTilUtvei(forelopigVei + "\n\n");       //lagre i liste
            if (print) System.out.println(forelopigVei);
            if (print) System.out.println(labyrint.toString());
            paaVeien = false;
            return;
        }

        if (oest instanceof HvitRute && komFra != "oest") {     //sjekk at ruta er hvit, og ikke der vi kom fra
            if (!oest.sjekkPaaVeien()) {                        //kun hvis den ikke er paa veien fra foer
                oest.gaa("vest", forelopigVei + " --> ");
            }
        }

        if (syd instanceof HvitRute && komFra != "syd") {
            if (!syd.sjekkPaaVeien()) {
                syd.gaa("nord", forelopigVei + " --> ");
            }
        }

        if (vest instanceof HvitRute && komFra != "vest") {
            if (!vest.sjekkPaaVeien()) {
                vest.gaa("oest", forelopigVei + " --> ");
            }
        }

        if (nord instanceof HvitRute && komFra != "nord") {
            if (!nord.sjekkPaaVeien()) {
                nord.gaa("syd", forelopigVei + " --> ");
            }
        }

        paaVeien = false;                                       //settes tilbake til false paa vei tilbake i kallene

    }



    /**
     * Tell whether this square has been visited
     * @return  whether or not visited
     */
    public boolean sjekkPaaVeien() {
        return paaVeien;
    }



    // public String koord() {
    //     return "" + kolonne + ", " + rad + "   ";
    // }



    /**
     * Turn off printing to terminal
     */
    public void printAv() {
        print = false;
    }


    /**
     * @return  symbol for ASCII representation
     */
    abstract char tilTegn();


}
