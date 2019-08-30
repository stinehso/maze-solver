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

    Rute(int rad, int kolonne) {
        this.rad = rad;
        this.kolonne = kolonne;
    }



    public void settNaboReferanser(Labyrint labyrint, Rute nord, Rute syd, Rute vest, Rute oest) {
        this.labyrint = labyrint;
        this.nord = nord;
        this.syd = syd;
        this.vest = vest;
        this.oest = oest;
    }



    public void finnUtvei(){
        gaa("ingen", "");
    }



    public void gaa(String komFra, String forelopigVei) {
        //if (print) System.out.println("gikk til " + kolonne + ", " + rad);
        //if (print) System.out.println(labyrint.toString());

        paaVeien = true;

        String denneRuta = String.format("(%d, %d)", kolonne, rad);
        forelopigVei = forelopigVei + denneRuta;



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



    public boolean sjekkPaaVeien() {
        return paaVeien;
    }


/*
    public String koord() {
        return "" + kolonne + ", " + rad + "   ";
    }
*/


    public void printAv() {
        print = false;
    }



    abstract char tilTegn();


}
