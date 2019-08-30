import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import java.util.Iterator;
import java.lang.*; //M




public class Oblig7 extends Application {
    Stage vindu;
    GridPane labyrint;
    Labyrint l;
    Rute[][] lab;
    int[] start;
    int i, j = 0;

    @Override
    public void start(Stage vindu) {
        this.vindu = vindu;
        BorderPane rot = new BorderPane();
        labyrint = new GridPane();
        //labyrint.setPadding(new Insets(5.0));
        //labyrint.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, null, null)));

        rot.setCenter(labyrint);
        BorderPane.setMargin(labyrint, new Insets(60));

        Button open = new Button("Åpne fil");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lesInnFil();
                lagGrid();
            }
        });
        rot.setTop(open);



        Scene scene = new Scene(rot, 500, 500);
        vindu.setScene(scene);
        vindu.setTitle("Labyrintløser");
        vindu.show();

    }

    private void lesInnFil() {
        FileChooser filleser = new FileChooser();
        filleser.setTitle("Åpne fil");
        filleser.getExtensionFilters().addAll(
        new ExtensionFilter("Labyrintfil", "*.in"),
        new ExtensionFilter("All Files", "*.*"));
        try {
            File valgtFil = filleser.showOpenDialog(vindu);

            l = Labyrint.lesFraFil(valgtFil);

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void lagGrid() {
        lab = l.hentRuteArray();

        int[] str = l.hentRader();
        Pane pane;
        Rectangle r;
        if(!labyrint.getChildren().isEmpty()) {
            labyrint.getChildren().clear();
        }
        for (Rute[] rad : lab) {
            i = 0;
            for (Rute rute : rad) {
                if(rute instanceof HvitRute) {
                    pane = new GUIRute(true, i+1, j+1);
                } else {
                    pane = new GUIRute(false, i+1, j+1);
                }

                GridPane.setMargin(pane, new Insets(5));
                labyrint.getChildren().add(pane);
                GridPane.setConstraints(pane, i, j);
                i++;
            }
            j++;
        }
    }


    private void fargUtvei(int kol, int rad) {
        l.settMinimalUtskrift();
        int startKol = kol;
        int startRad = rad;
        Liste<String> utveier = l.finnUtveiFra(startKol, startRad);
        boolean done=false; //m
        if (!utveier.erTom()) {
            for (String s : utveier) {
                if(done){break;} //M
                System.out.println(s);
                boolean[][] losning = losningStringTilTabell(s, i, j);
                //System.out.println(losning);
                int x = 0;
                int y = 0;
                Iterator it = labyrint.getChildren().iterator();
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
    }







    /**
    * Konverterer losning-String fra oblig 5 til en boolean[][]-representasjon
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

        public GUIRute(boolean hvit, int kol, int rad) {
            super();
            this.hvit = hvit;
            this.kol = kol;
            this.rad = rad;

            setStyle("-fx-background-color: white;");
            setPrefSize(60, 60);
            r = new Rectangle(60, 60);
            if (hvit) {
                r.setFill(Color.GAINSBORO);
            } else {
                r.setFill(Color.BLACK);
            }
            getChildren().add(r);

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if(hvit) {
                        start = new int[2];
                        start[0] = kol;
                        start[1] = rad;
                        fargUtvei(start[0], start[1]);
                    }
                }
            });
        }


        public void oppdaterFarge(float x, double y) {
            //setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

            Color mathiasfarge = Color.color(0,Math.abs(x-y)/2,Math.abs(x-y)/2); //m
            r.setFill(mathiasfarge); //M
            //r.setFill(Color.GREEN);
            //System.out.println(kol + "," + rad);
        }


        //pane.setBorder(new Border(new BorderStroke(Color.RED, null, null, new BorderWidths(5.0))));
        //pane.setPadding(new Insets(5.0));


    }
}
