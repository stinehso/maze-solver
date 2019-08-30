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



    public void settMinimalUtskrift() {
        print = false;
        for (Rute[] rad : lab) {
            for (Rute rute : rad) {
                rute.printAv();
            }
        }
    }



    public void leggTilUtvei(String utvei) {
        Utvei nyUtvei = new Utvei(utvei);
        utveier.settInn(nyUtvei);
    }



    public Rute[][] hentRuteArray() {
        return lab;
    }


    public int[] hentRader() {
        int[] str = new int[2];
        str[0] = rader;
        str[1] = kolonner;
        return str;
    }

    /*
    public void skrivUtKoordinater() {
    for (Rute[] rad : lab) {
    for (Rute rute : rad) {
    System.out.print(rute.koord());
}
System.out.println("");
}
}
*/



}
