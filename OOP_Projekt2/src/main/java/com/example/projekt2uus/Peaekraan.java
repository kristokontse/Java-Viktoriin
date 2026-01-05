package com.example.projekt2uus;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

// Meie viktoriini programmi peamenüü
public class Peaekraan extends Application {

    public void start(Stage lava) {

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

        Label suurPealkiri = new Label("VIKTORIIN");
        suurPealkiri.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        suurPealkiri.setEffect(vari2);
        suurPealkiri.setTextFill(Color.WHITE);

        Separator joon1 = new Separator();
        joon1.setEffect(vari1);
        VBox.setMargin(joon1, new Insets(10, 0, 10, 0));
        joon1.setMaxWidth(400);

        Label pealkiri1 = new Label("RASKUSASTMED");
        pealkiri1.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        pealkiri1.setEffect(vari2);
        pealkiri1.setTextFill(Color.WHITE);
        VBox.setMargin(pealkiri1, new Insets(0, 0, 10, 0));

        Button kergeNupp = new Button("KERGE");
        Button keskmineNupp = new Button("KESKMINE");
        Button raskeNupp = new Button("RASKE");

        Separator joon2 = new Separator();
        joon2.setEffect(vari1);
        joon2.setMaxWidth(400);
        VBox.setMargin(joon2, new Insets(15, 0, 10, 0));

        Label pealkiri2 = new Label("ENDA VIKTORIIN");
        pealkiri2.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        pealkiri2.setEffect(vari2);
        pealkiri2.setTextFill(Color.WHITE);
        VBox.setMargin(pealkiri2, new Insets(0, 0, 10, 0));

        Button looUus = new Button("DISAINI VIKTORIIN");
        Button muudetavNupp = new Button("MÄNGI");

        // Kõikide nuppude stiili muutmine korraga
        for (Button nupp : List.of(kergeNupp, keskmineNupp, raskeNupp, looUus, muudetavNupp)) {
            nupp.setMinWidth(160);
            nupp.setStyle(
                    "-fx-background-color: white;"+
                            "-fx-background-radius: 15;"+
                            "-fx-padding: 5 5 5 5;"+
                            "-fx-font-size: 15px;"+
                            "-fx-border-radius: 15;"

            );
            nupp.setEffect(vari2);

            // Hover efekt ehk kui viibida nupu peal
            nupp.setOnMouseEntered(e -> {
                nupp.setScaleX(1.05); // Nupu suuruse muutmine
                nupp.setScaleY(1.05);
                nupp.setStyle(
                        "-fx-background-color: #e0e0e0;"+  // Värv natuke tumedam kui valge
                                "-fx-background-radius: 15;"+
                                "-fx-padding: 5 5 5 5;"+
                                "-fx-font-size: 15px;"+
                                "-fx-border-radius: 15;"
                );
            });

            // Kui hiir läheb ära, taastub esialgne seis
            nupp.setOnMouseExited(e -> {
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

        // Nuppude tegevused
        kergeNupp.setOnAction(e -> new Mäng().alusta("kerge.txt"));
        keskmineNupp.setOnAction(e -> new Mäng().alusta("keskmine.txt"));
        raskeNupp.setOnAction(e -> new Mäng().alusta("raske.txt"));
        muudetavNupp.setOnAction(e -> new Mäng().alusta("muudetav.txt"));
        looUus.setOnAction(e -> new Mäng().loo());

        // Paigutus
        VBox paigutus = new VBox(12);
        paigutus.setAlignment(Pos.CENTER);
        paigutus.setPadding(new Insets(30));
        paigutus.getChildren().addAll(suurPealkiri, joon1, pealkiri1, kergeNupp, keskmineNupp, raskeNupp, joon2, pealkiri2, looUus, muudetavNupp);
        paigutus.setStyle("-fx-background-color: #0394fc");

        Scene stseen = new Scene(paigutus, 400, 520);
        lava.setMinWidth(400);
        lava.setMinHeight(520);
        lava.setScene(stseen);
        lava.setTitle("Viktoriin");
        lava.show();
    }
}

