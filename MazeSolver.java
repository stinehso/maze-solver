import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.Iterator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;




public class MazeSolver extends Application {
    Stage vindu;
    GridPane labPane;
    Labyrint labObj;
    Rute[][] lab;
    int[] start;
    int i, j = 0;                                               // Brukes til bredde og høyde av tabell

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage vindu) {
        this.vindu = vindu;
        BorderPane rot = new BorderPane();

        labPane = new GridPane();
        rot.setCenter(labPane);                                // Plasserer labyrinten som et grid midt i bildet
        BorderPane.setMargin(labPane, new Insets(60));         // Margin på alle sider av gridet

        Button open = new Button("Åpne fil");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lesInnFil();
                lagGrid();
            }
        });
        rot.setTop(open);                                       // Plasserer knappen 'åpne' øverst i bildet

        Scene scene = new Scene(rot, 500, 500);                 // TODO mulig fjerne tall, spesifisere lenger ned
        vindu.setScene(scene);
        vindu.setTitle("Labyrintløser");
        vindu.show();
    }



    /**
     * Open file dialog with filters for custom .in files or all files,
     * read chosen file into Labyrint object
     */
    private void lesInnFil() {
        FileChooser filleser = new FileChooser();
        filleser.setTitle("Åpne fil");
        filleser.getExtensionFilters().addAll(
        new ExtensionFilter("Labyrintfil", "*.in"),
        new ExtensionFilter("All Files", "*.*"));

        try {
            File valgtFil = filleser.showOpenDialog(vindu);
            labObj = Labyrint.lesFraFil(valgtFil);
        } catch(FileNotFoundException e) {
            //e.printStackTrace();
        }
    }



    /**
     * Create panes in the UI from the array of the Labyrint object
     * Uses the custom class GUIRute for each pane, which each is a square in the maze
     */
    private void lagGrid() {
        lab = labObj.hentRuteArray();                          // henter selve arrayet
        int[] str = labObj.hentRader();                        // henter dimensjonene
        Pane pane;
        Rectangle r;

        if(!labPane.getChildren().isEmpty()) {                 // fjerner tidligere åpnet labyrint
            labPane.getChildren().clear();
        }

        j = 0;
        for (Rute[] rad : lab) {
            i = 0;
            for (Rute rute : rad) {
                if(rute instanceof HvitRute) {
                    pane = new GUIRute(true, i+1, j+1);         // GUIRute objekt representerer en enkelt rute i arrayet
                } else {
                    pane = new GUIRute(false, i+1, j+1);
                }

                GridPane.setMargin(pane, new Insets(5));        // Margin mellom hver enkelt rute, fungerer kun når rutene er store nok
                labPane.getChildren().add(pane);
                GridPane.setConstraints(pane, i, j);
                i++;
            }
            j++;
        }
    }



    private void fargUtvei(int kol, int rad) {
        labObj.settMinimalUtskrift();

        int startKol = kol;
        int startRad = rad;

        Liste<String> utveier = labObj.finnUtveiFra(startKol, startRad);        // selve løsningen av labyrinten gjøres her

        nullstillFarge();

        boolean done = false;
        if (!utveier.erTom()) {
            for (String s : utveier) {
                if (done) {break;}
                System.out.println(s);
                boolean[][] losning = losningStringTilTabell(s, i, j);
                System.out.println(losning);
                int x = 0;
                int y = 0;
                Iterator it = labPane.getChildren().iterator();
                for (boolean[] raden : losning) {
                    for (boolean rute : raden) {
                        GUIRute temp = (GUIRute)it.next();
                        if (rute) {
                            temp.oppdaterFarge((float)Math.cos((float)Math.toRadians(x++*0.1)),
                                y*0.01); //M
                            done=true; //M
                        }
                        x++;
                    }
                    y++;
                }
            }
        }
        else {

        }
    }



    /**
     * Iterates through squares to reset colors to original grey and black
     */
    private void nullstillFarge() {
        Iterator it = labPane.getChildren().iterator();
        while (it.hasNext()) {
            GUIRute temp = (GUIRute)it.next();
            temp.oppdaterFarge();
        }
    }



    /**
    * Konverterer losning-String til en boolean[][]-representasjon
    * av losningstien.
    * @param losningString String-representasjon av utveien
    * @param bredde        bredde til labyrinten
    * @param hoyde         hoyde til labyrinten
    * @return              2D-representasjon av rutene der true indikerer at
    *                      ruten er en del av utveien.
    */
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while(m.find()) {
            int x = Integer.parseInt(m.group(1))-1;
            int y = Integer.parseInt(m.group(2))-1;
            losning[y][x] = true;
        }
        return losning;
    }








    private class GUIRute extends Pane {
        boolean hvit;
        int kol, rad;
        Rectangle r;
        boolean klikkPaaVegg = false;

        /**
         * The class GUIRute holds properties for each square in the maze
         * @param   hvit    boolean that is true if this is a white square
         * @param   kol     the column we are in
         * @param   rad     the row we are in
         */
        public GUIRute(boolean hvit, int kol, int rad) {
            super();
            this.hvit = hvit;
            this.kol = kol;
            this.rad = rad;

            setStyle("-fx-background-color: white;");
            //setPrefSize(60, 60);

            r = new Rectangle(60, 60);                 // la Rute inneholde et kvadrat som enkelt kan farges
            if (hvit) {
                r.setFill(Color.GAINSBORO);
            } else {
                r.setFill(Color.BLACK);
            }
            getChildren().add(r);

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(hvit) {
                        nullstillFarge();
                        fargUtvei(kol, rad);
                    } else {
                        System.out.println("It is very dark inside the wall");
                        r.setFill(Color.FIREBRICK);
                        klikkPaaVegg = true;
                    }
                }
            });

            setOnMouseExited(
                event -> {
                    if(klikkPaaVegg) {
                        System.out.println("Phew");
                        r.setFill(Color.BLACK);
                        klikkPaaVegg = false;
                    }
                }
            );

            // setOnMouseExited(new EventHandler<MouseEvent>() {
            //     public void handle(MouseEvent event) {
            //         if(klikkPaaVegg) {
            //             System.out.println("exit square");
            //             r.setFill(Color.BLUE);
            //             klikkPaaVegg = false;
            //         }
            //     }
            // });
        }



        /**
         * Fills the square with a color calculated from the coordinates for an
         * ombre feel
         * @param   x   float, scaled and varied version of x coordinate
         * @param   y   double, scaled version of y coordinate
         */
        public void oppdaterFarge(float x, double y) {
            Color mathiasfarge = Color.color(0,Math.abs(x-y)/2,Math.abs(x-y)/2);
            r.setFill(mathiasfarge);
        }


        /**
         * Resets colors to original grey and black
         */
        public void oppdaterFarge() {
            if (hvit) {
                r.setFill(Color.GAINSBORO);
            } else {
                r.setFill(Color.BLACK);
            }
        }

    }

}
