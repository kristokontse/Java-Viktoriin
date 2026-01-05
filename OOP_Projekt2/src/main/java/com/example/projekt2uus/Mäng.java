package com.example.projekt2uus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mäng {

    private final List<Küsimus> küsimused = new ArrayList<>();
    private int indeks = 0;
    private int punktid = 0;

    // Kasutaja enda tehtud viktoriini loomise menüü
    public void loo() {
        Stage lava = new Stage();
        lava.setTitle("Loo uus viktoriin");

        // Kõikide nuppude, efektide, pealkirjade jne isendite loomine õiges järjekorras
        DropShadow vari1 = new DropShadow();
        vari1.setOffsetY(1);
        vari1.setOffsetX(1);
        vari1.setRadius(2);
        vari1.setColor(Color.BLACK);

        DropShadow vari2 = new DropShadow();
        vari2.setOffsetY(1);
        vari2.setOffsetX(1);
        vari2.setRadius(5);
        vari2.setColor(Color.BLACK);

        Label suurPealkiri = new Label("LOO VIKTORIIN");
        suurPealkiri.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        suurPealkiri.setEffect(vari2);
        suurPealkiri.setTextFill(Color.WHITE);

        Separator joon1 = new Separator();
        joon1.setEffect(vari1);
        VBox.setMargin(joon1, new Insets(10,0,10,0));
        joon1.setMaxWidth(400);

        Label küsimuseSilt = new Label("Küsimus:");
        küsimuseSilt.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        küsimuseSilt.setTextFill(Color.WHITE);
        küsimuseSilt.setEffect(vari2);

        TextField küsimuseVäli = new TextField();
        küsimuseVäli.setMaxWidth(380);
        küsimuseVäli.setEffect(vari1);

        ToggleGroup õigeVastusGrupp = new ToggleGroup();
        List<TextField> valikuteVäljad = new ArrayList<TextField>();
        List<RadioButton> valikuteNupud = new ArrayList<RadioButton>();
        VBox valikuteKast = new VBox(5);

        for (int i = 0; i < 4; i++) { // Rea kaupa vastusevariantide ridade tegemine
            TextField vastuseVariant = new TextField();
            RadioButton õigeNupp = new RadioButton();
            õigeNupp.setToggleGroup(õigeVastusGrupp); // Et eelnevalt tehtud radio nuppude hulgast saaks ainult üks aktiivne olla
            valikuteVäljad.add(vastuseVariant);
            valikuteNupud.add(õigeNupp);
            HBox rida = new HBox(10);
            rida.getChildren().addAll(õigeNupp, vastuseVariant); // Et radio nupp ja vastusevariandi kast saaksid olla ilusti üksteise kõrval
            valikuteKast.getChildren().add(rida);
            rida.setEffect(vari1);
        }

        Label juhis = new Label("Sisesta vastusevariandid ja märgi õige vastus:");
        juhis.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        juhis.setTextFill(Color.WHITE);
        juhis.setEffect(vari1);
        VBox.setMargin(juhis, new Insets(10, 0, 0, 0));

        Separator joon2 = new Separator();
        joon2.setEffect(vari1);
        VBox.setMargin(joon2, new Insets(10, 0, 10, 0));
        joon2.setMaxWidth(400);

        Button lisaKüsimus = new Button("LISA KÜSIMUS");
        Button salvesta = new Button("SALVESTA");

        Label teade = new Label();
        teade.setTextFill(Color.WHITE);
        teade.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        teade.setEffect(vari1);

        List<String> küsimusteList = new ArrayList<String>();

        for (Button nupp : List.of(lisaKüsimus,salvesta)) { // Et nuppude stiili üheaegselt muuta
            nupp.setMinWidth(160);
            nupp.setStyle(
                    "-fx-background-color: white;"+
                            "-fx-background-radius: 15;"+
                            "-fx-padding: 5 5 5 5;"+
                            "-fx-font-size: 15px;"+
                            "-fx-border-radius: 15;"
            );
            nupp.setEffect(vari2);

            nupp.setOnMouseEntered(e -> { // Kui hiirega peale liikuda, siis muutub suurus ja värv
                nupp.setScaleX(1.05);
                nupp.setScaleY(1.05);
                nupp.setStyle(
                        "-fx-background-color: #e0e0e0;"+
                                "-fx-background-radius: 15;"+
                                "-fx-padding: 5 5 5 5;"+
                                "-fx-font-size: 15px;"+
                                "-fx-border-radius: 15;"
                );
            });
            nupp.setOnMouseExited(e -> { // Kui nupu pealt lahkuda
                nupp.setScaleX(1.0);
                nupp.setScaleY(1.0);
                nupp.setStyle(
                        "-fx-background-color: white;"+
                                "-fx-background-radius: 15;"+
                                "-fx-padding: 5 5 5 5;"+
                                "-fx-font-size: 15px;"+
                                "-fx-border-radius: 15;"
                );
            });
        }

        lisaKüsimus.setOnAction(e -> { // Küsimuse lisamise nupule vajutamine
            String küsimus = küsimuseVäli.getText().trim();
            String v1 = valikuteVäljad.get(0).getText().trim();
            String v2 = valikuteVäljad.get(1).getText().trim();
            String v3 = valikuteVäljad.get(2).getText().trim();
            String v4 = valikuteVäljad.get(3).getText().trim();
            Toggle valitudVastus = õigeVastusGrupp.getSelectedToggle();

            if (küsimus.isEmpty() || v1.isEmpty() || v2.isEmpty() || v3.isEmpty() || v4.isEmpty() || valitudVastus == null) {
                teade.setText("Täida kõik väljad ja vali õige vastus."); // Kui mõni väli on veel tühi
                return;
            }

            int õige = valikuteNupud.indexOf(valitudVastus) + 1; // Õige vastuse järjekorranumbri saamine

            küsimusteList.add(küsimus);
            küsimusteList.add(v1);
            küsimusteList.add(v2);
            küsimusteList.add(v3);
            küsimusteList.add(v4);
            küsimusteList.add(String.valueOf(õige));

            // Kõikide väljade puhtaks tegemine
            küsimuseVäli.clear();
            for (int i = 0; i < valikuteVäljad.size(); i++) {
                valikuteVäljad.get(i).clear();
            }
            õigeVastusGrupp.selectToggle(null);
            teade.setText("Küsimus lisatud!");
        });

        salvesta.setOnAction(e -> { // Faili salvestamise nupule vajutamine
            try (PrintWriter pw = new PrintWriter("muudetav.txt", StandardCharsets.UTF_8);) { // Faili kirjutamine
                for (int i = 0; i < küsimusteList.size(); i++) {
                    pw.println(küsimusteList.get(i));
                }
                pw.close();
                lava.close();
            } catch (Exception erind) {
                teade.setText("Viga salvestamisel: " + erind.getMessage());
            }
        });

        // Paigutus
        VBox paigutus = new VBox(10);
        paigutus.setPadding(new Insets(15));
        paigutus.setStyle("-fx-background-color: #0394fc;");
        paigutus.setAlignment(Pos.CENTER_LEFT);
        paigutus.getChildren().addAll(suurPealkiri,joon1,küsimuseSilt,küsimuseVäli,juhis,valikuteKast,teade,joon2,lisaKüsimus,salvesta);

        Scene stseen = new Scene(paigutus, 400, 520);
        lava.setMinWidth(400);
        lava.setMinHeight(520);
        lava.setScene(stseen);
        lava.show();
    }


    public void alusta(String failiNimi) {
        loeKüsimused(failiNimi);

        Stage lava = new Stage();

        // Kõikide nuppude, efektide, pealkirjade jne isendite loomine õiges järjekorras
        DropShadow vari1 = new DropShadow();
        vari1.setOffsetY(1);
        vari1.setOffsetX(1);
        vari1.setRadius(4);
        vari1.setColor(Color.BLACK);

        DropShadow vari2 = new DropShadow();
        vari2.setOffsetY(1);
        vari2.setOffsetX(1);
        vari2.setRadius(5);
        vari2.setColor(Color.BLACK);

        Label küsimus = new Label();
        küsimus.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        küsimus.setTextFill(Color.WHITE);
        küsimus.setEffect(vari2);

        Separator joon1 = new Separator();
        joon1.setEffect(vari1);
        VBox.setMargin(joon1, new Insets(10, 0, 10, 0));
        joon1.setMaxWidth(400);

        TextArea valikud = new TextArea();
        valikud.setEditable(false);
        valikud.setWrapText(true);
        valikud.setFont(Font.font("Arial", 14));
        valikud.setStyle("-fx-font-size: 14px;");
        valikud.setEffect(vari2);
        valikud.setMaxHeight(140);
        valikud.setStyle( // Stiilimuudatused, et muuta valikute kasti natuke ilusamaks
                "-fx-control-inner-background: #ffffff;" +
                        "-fx-focus-color: transparent;" +
                        "-fx-faint-focus-color: transparent;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5;"
        );

        TextField vastuseVäli = new TextField();
        vastuseVäli.setEffect(vari1);


        Label juhis = new Label("Sisesta vastusevariandi number [1-4]:");
        juhis.setTextFill(Color.WHITE);
        juhis.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        juhis.setEffect(vari1);

        Label tagasiside = new Label();
        tagasiside.setTextFill(Color.WHITE);
        tagasiside.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        tagasiside.setEffect(vari1);

        Separator joon2 = new Separator();
        joon2.setEffect(vari1);
        VBox.setMargin(joon2, new Insets(15, 0, 10, 0));
        joon2.setMaxWidth(400);

        Button vastaNupp = new Button("VASTA");
        VBox.setMargin(vastaNupp, new Insets(10, 0, 0, 0));

        Button lahkuNupp = new Button("LAHKU");

        for (Button nupp : List.of(vastaNupp, lahkuNupp)) { // Et nuppude stiil muuta ühe korraga ära
            nupp.setMinWidth(160);
            nupp.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 5 5 5 5;" +
                            "-fx-font-size: 15px;" +
                            "-fx-border-radius: 15;"
            );
            nupp.setEffect(vari2);

            nupp.setOnMouseEntered(e -> {
                nupp.setScaleX(1.05);
                nupp.setScaleY(1.05);
                nupp.setStyle(
                        "-fx-background-color: #e0e0e0;" +
                                "-fx-background-radius: 15;" +
                                "-fx-padding: 5 5 5 5;" +
                                "-fx-font-size: 15px;" +
                                "-fx-border-radius: 15;"
                );
            });

            nupp.setOnMouseExited(e -> {
                nupp.setScaleX(1.0);
                nupp.setScaleY(1.0);
                nupp.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-background-radius: 15;" +
                                "-fx-padding: 5 5 5 5;" +
                                "-fx-font-size: 15px;" +
                                "-fx-border-radius: 15;"
                );
            });
        }
        VBox.setMargin(lahkuNupp, new Insets(10, 0, 0, 0));
        lahkuNupp.setOnAction(e -> lava.close());


        // Paigutus
        VBox kast = new VBox(10, küsimus, joon1, valikud, tagasiside, juhis, vastuseVäli, joon2, vastaNupp, lahkuNupp);
        kast.setPadding(new Insets(15));
        kast.setStyle("-fx-background-color: #0394fc;");
        kast.setAlignment(Pos.CENTER);

        Scene stseen = new Scene(kast, 400, 520);
        lava.setMinWidth(400);
        lava.setMinHeight(520);
        lava.setScene(stseen);
        lava.setTitle("Viktoriin");
        lava.show();

        kuvaKüsimus(küsimus, valikud, vastuseVäli, tagasiside, vastaNupp, lava);
    }

    private void loeKüsimused(String fail) {
        try {
            // Loeme kõik read failist
            List<String> read = Files.readAllLines(Paths.get(fail));
            // for-tsükliga töötleme ridu, iga küsimusele vastab 6 rida failis
            for (int i = 0; i < read.size(); i += 6) {
                if (i + 5 < read.size()) { // kontroll, et indeks ei ületaks faili pikkust
                    String tekst = read.get(i); // Küsimus
                    List<String> valikud = read.subList(i + 1, i + 5); // valikvastused
                    int õige = Integer.parseInt(read.get(i + 5).trim()); // õige vastus
                    küsimused.add(new Küsimus(tekst, valikud, õige)); // küsimus lisatakse listi
                }
            }
        } catch (Exception e) {
            System.out.println("Viga faili lugemisel: " + e.getMessage());
        }
    }

    /**
     * @param küsimuseLabel - siia kuvatake küsimus
     * @param valikuteArea - vastusevalikute kast
     * @param vastuseVäli - vastusekast kuhu saab kasutaja sisestada oma vastuse
     * @param tagasiside - label, mis kuvab kas oli õige või vale vastus
     * @param vastaNupp - küsimuse vastuse esitamise nupp
     * @param lava - lava
     */
    private void kuvaKüsimus(Label küsimuseLabel, TextArea valikuteArea, TextField vastuseVäli,
                             Label tagasiside, Button vastaNupp, Stage lava) {

        // Kui küsimused on otsas
        if (indeks >= küsimused.size()) {
            // Punktisumma kuvamine
            küsimuseLabel.setText("Viktoriin läbi! Õigeid vastuseid: " + punktid + "/" + küsimused.size());
            valikuteArea.setText("");  // puhastab valikvastuse kasti
            vastaNupp.setDisable(true); // keelab vastuse nupu kasutamise
            return;
        }

        // Küsimused ja nende kuvamine
        Küsimus k = küsimused.get(indeks);
        küsimuseLabel.setText(k.getTekst());

        // Valikvastustele loodud sb, et neid kuvada
        StringBuilder sb = new StringBuilder();
        // Iga valiku lisamine koos järjekorranumbriga
        for (int i = 0; i < k.getValikud().size(); i++) {
            sb.append((i + 1)).append(". ").append(k.getValikud().get(i)).append("\n");
        }
        // Küsimuste kuvamine tekstikasti ja eelmise vastuse välja puhastamine
        valikuteArea.setText(sb.toString());
        vastuseVäli.clear();
        tagasiside.setText(""); // eemaldatakse eelmise küsimuse tagasiside

        // Nupule vajutades
        vastaNupp.setOnAction(e -> {
            try {
                // Kasutaja sisend täisarvuks ja vastuse kontroll (kas õige ja kas sisend on korrektne)
                int vastus = Integer.parseInt(vastuseVäli.getText());
                if (vastus < 1 || vastus > 4){
                    tagasiside.setText("Sisesta korrektne vastuse variant [1-4].");
                    return;
                }
                if (vastus == k.getÕige()) {
                    tagasiside.setText("Õige!");
                    punktid++;
                } else {
                    String õigeVastus = k.getValikud().get(k.getÕige() - 1);
                    tagasiside.setText("Vale! Õige vastus oli: " + õigeVastus);
                }

                // Lõimega lisame viivituse enne uut küsimust, et kasutaja näeks kas vastas küsimuse õigesti, enne kui kuvatakse uus küsimus
                new Thread(() -> {
                    try {
                        Thread.sleep(1500); // Programm puhkab 1.5 sekundit
                        // Liigume järgmise küsimuse juurde
                        indeks++;
                        // tagab, et kasutajaliidese uuendamine toimub õiges lõimes, ilma selleta või programm edasi minna vanas lõimes või visata veakoodi
                        // see peab olema siin thread.sleep tõttu, sest sleep toimub eraldi lõimes.
                        javafx.application.Platform.runLater(() -> {
                            // Järgmine küsimus
                            kuvaKüsimus(küsimuseLabel, valikuteArea, vastuseVäli, tagasiside, vastaNupp, lava);
                        });
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }).start();

            } catch (NumberFormatException ex) {
                tagasiside.setText("Sisesta korrektne vastuse variant [1-4]");
            }
        });
    }
}
