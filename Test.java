import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;



class TestMain {
    public static void main(String[] args) {
        File fil = new File("1.in");
        Labyrint lab = null;
        try {
            lab = Labyrint.lesFraFil(fil);
        } catch (Exception e) {}

        //System.out.println(lab.toString());
        //lab.skrivUtKoordinater();
        //lab.tilRute(6, 2);
        //lab.typeRute(6, 1);
        //lab.settMinimalUtskrift();

        lab.finnUtveiFra(8, 7);
        System.out.println(" dsfghgfdsa");
        lab.finnUtveiFra(2, 3);

        /* finn alle utveiene, lagre dem, print hvor mange og hele den korteste
        sjekk sykliske labyrinter */




    }
}
