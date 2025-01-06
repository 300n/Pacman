
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {

    ArrayList<Button> Button_List = new ArrayList();

    private Stage primaryStage;
    ArrayList<Touches_joueur> List_Touches_joueurs = new ArrayList();
    Two_side_button nb_player = new Two_side_button(1, 4, 1);
    int width = 1150, height = 1000;
    Font customFont;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 26);
            if (customFont == null) {
                throw new Exception("Font could not be loaded. Ensure the file path is correct.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            this.customFont = Font.font("Arial", 20);
        }
        this.primaryStage = primaryStage;
        this.List_Touches_joueurs.add(new Touches_joueur("Z", "Q", "S", "D"));
        this.List_Touches_joueurs.add(new Touches_joueur("UP", "LEFT", "DOWN", "RIGHT"));
        this.List_Touches_joueurs.add(new Touches_joueur("T", "F", "G", "H"));
        this.List_Touches_joueurs.add(new Touches_joueur("I", "J", "K", "L"));
        // Créer les scènes
        Scene mainMenuScene = createMainMenu();
        Scene newGameScene = createNewGameScene();
        Scene optionsScene = createOptionsScene();

        // Configurer la scène principale
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Pacman");
        primaryStage.show();
    }

    private Scene createMainMenu() {
        AnchorPane menuLayout = new AnchorPane();
        menuLayout.setStyle("-fx-background-color: rgb(50, 50, 50);");
        Canvas canvas = new Canvas(width, height); // Dimensions arbitraires
        drawGrid(canvas, 25); // Appel de la méthode pour dessiner la grille

        // Ajout du Canvas au layout
        menuLayout.getChildren().add(canvas);
        // Boutons du menu
        ArrayList<Button> Button_List = new ArrayList();
        Button_List.add(new Button("Nouvelle Partie"));
        Button_List.add(new Button("Options"));
        Button_List.add(new Button("Quitter"));

        Image image = new Image("/ressources/Pacman_main_logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        AnchorPane.setTopAnchor(imageView, height / 3 - imageView.getFitHeight() / 2);
        AnchorPane.setLeftAnchor(imageView, width / 2 - imageView.getFitWidth() / 2);
        /*
        Image image1 = new Image("/ressources/backgorund.png");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(width);
        imageView1.setFitHeight(height);
        //imageView1.setPreserveRatio(true);
        AnchorPane.setTopAnchor(imageView1, 0.);
        AnchorPane.setLeftAnchor(imageView1, 0.);
        menuLayout.getChildren().add(imageView1);*/

        // Actions des boutons
        Button_List.get(0).setOnAction(e -> {
            Pacman__ game = new Pacman__();
            game.start(primaryStage, nb_player.value, List_Touches_joueurs); // Lancer le jeu dans la même fenêtre
        });
        Button_List.get(1).setOnAction(e -> switchToScene(createOptionsScene()));
        Button_List.get(2).setOnAction(e -> primaryStage.close());

        for (int i = 0; i < Button_List.size(); i++) {
            Button_List.get(i).setStyle(
                    "-fx-background-color: rgb(0, 0, 0); "
                    + // Couleur de fond (RGB)
                    "-fx-border-color: rgb(255, 255, 255); "
                    + // Couleur de la bordure (RGB)
                    "-fx-border-width: 3px; "
                    + // Épaisseur de la bordure
                    "-fx-border-radius: 10px; "
                    + // Coins arrondis
                    "-fx-background-radius: 12px;"
                    + // Coins arrondis pour le fond 
                    "-fx-font-size: 26px; "
                    + // Taille de police
                    "-fx-font-weight: bold; "
                    + // Gras
                    "-fx-text-fill: rgb(255, 255, 255);" // Couleur du texte (blanc)
            );
        }
        for (Button b : Button_List) {
            b.setFont(customFont);
        }
        for (int i = 0; i < Button_List.size(); i++) {
            Text text = new Text(Button_List.get(i).getText());
            text.setFont(customFont);

            AnchorPane.setTopAnchor(Button_List.get(i), height / 2 - text.getBoundsInLocal().getHeight() / 2 + i * text.getBoundsInLocal().getHeight() * 3.125);
            AnchorPane.setLeftAnchor(Button_List.get(i), width / 2 - text.getBoundsInLocal().getWidth() / 2 - width / 50);
        }

        for (int i = 0; i < Button_List.size(); i++) {
            menuLayout.getChildren().add(Button_List.get(i));
        }
        menuLayout.getChildren().add(imageView);

        return new Scene(menuLayout, width, height);
    }

    private Scene createNewGameScene() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        //Button backButton = new Button("Retour au menu");
        //backButton.setOnAction(e -> switchToScene(createMainMenu()));
        //layout.getChildren().add(backButton);
        return new Scene(layout, width, height);
    }

    private void drawGrid(Canvas canvas, int smoothingRadius) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(50, 50, 50));
        gc.fillRect(0, 0, width, height);
        // Définition de la couleur des lignes
        gc.setStroke(Color.rgb(255, 255, 255));
        // Dessin des lignes verticales
        for (int i = 1; i <= width / smoothingRadius; i++) {
            double x = i * smoothingRadius;
            gc.strokeLine(x, 0, x, height);
        }

        // Dessin des lignes horizontales   
        for (int i = 1; i <= height / smoothingRadius; i++) {
            double y = i * smoothingRadius;
            gc.strokeLine(0, y, width, y);
        }
    }

    private Scene createOptionsScene() {
        AnchorPane layout = new AnchorPane();

        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Création des boutons avec les valeurs initiales
        ArrayList<Button> Button_List = new ArrayList();
        Button_List.add(new Button("Retour au menu"));
        Button_List.add(nb_player.left_arrow);
        Button_List.add(nb_player.right_arrow);
        Button_List.add(new Button(nb_player.value + " joueur(s)"));
        ArrayList<option_button_display> option_button_display_List = new ArrayList();
        for (int i = 0; i < nb_player.value; i++) {
            option_button_display_List.add(new option_button_display(this.List_Touches_joueurs.get(i)));
        }

        // Création du Canvas pour dessiner la grille
        Canvas canvas = new Canvas(width, height); // Dimensions arbitraires
        drawGrid(canvas, 25); // Appel de la méthode pour dessiner la grille

        // Ajout du Canvas au layout
        layout.getChildren().add(canvas);

        // Bouton pour retourner au menu principal
        Button_List.get(0).setOnAction(e -> switchToScene(createMainMenu()));

        Runnable updateOptions = () -> {
            layout.getChildren().clear();
            layout.getChildren().add(canvas);

            Text text = new Text(Button_List.get(0).getText());
            text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
            AnchorPane.setTopAnchor(Button_List.get(0), height - 50 - text.getBoundsInLocal().getHeight() / 2);
            AnchorPane.setLeftAnchor(Button_List.get(0), width / 2 - text.getBoundsInLocal().getWidth() / 1.45);
            layout.getChildren().add(Button_List.get(0));

            String txt = nb_player.value == 1 ? nb_player.value + " joueur" : nb_player.value + " joueurs";
            text = new Text(txt);
            text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
            Button_List.get(3).setText(txt);

            Text t2 = new Text(nb_player.left_arrow.getText());
            t2.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
            AnchorPane.setTopAnchor(nb_player.left_arrow, height / 40 - t2.getBoundsInLocal().getHeight() / 2);
            AnchorPane.setLeftAnchor(nb_player.left_arrow, (width / 40.0) * 2.75 - text.getBoundsInLocal().getWidth() / 2 - t2.getBoundsInLocal().getWidth() / 2);
            t2 = new Text(nb_player.right_arrow.getText());
            AnchorPane.setTopAnchor(nb_player.right_arrow, height / 40 - t2.getBoundsInLocal().getHeight() / 2);
            AnchorPane.setLeftAnchor(nb_player.right_arrow, (width / 40.0) * 2.75 + text.getBoundsInLocal().getWidth() * 1.5 - t2.getBoundsInLocal().getWidth() / 2);

            AnchorPane.setTopAnchor(Button_List.get(3), height / 40 - text.getBoundsInLocal().getHeight() / 2);
            AnchorPane.setLeftAnchor(Button_List.get(3), (width / 40.0) * 2.75);
            layout.getChildren().add(Button_List.get(3));
            layout.getChildren().add(nb_player.left_arrow);
            layout.getChildren().add(nb_player.right_arrow);
            for (int i = 0; i < nb_player.value; i++) {
                final int b = (i == 0) ? 0 : (i % 2 == 0) ? 1 : (i == 3) ? 1 : 0;
                final int c = (i % 2 == 1) ? 1 : 0;
                final int d = i;

                option_button_display_List.get(i).set_button_action(layout, 50 + c * 550, 100 + b * 300, width, height);
                layout.setOnKeyPressed(event -> {
                    option_button_display_List.get(d).KeyPressed(layout, event, 50 + c * 550, 100 + b * 300, width, height);

                });

                option_button_display_List.get(i).Button_display(layout, 50 + c * 550, 100 + b * 300, width, height, i + 1);
            }
        };

        nb_player.left_arrow.setOnAction(e -> {
            if (nb_player.value > 1) {
                nb_player.value--;
                Button_List.get(3).setText(nb_player.value + " joueur(s)");
                option_button_display_List.remove(option_button_display_List.get(option_button_display_List.size() - 1));
                updateOptions.run();
            }
        });
        nb_player.right_arrow.setOnAction(e -> {
            if (nb_player.value < 4) {
                nb_player.value++;
                Button_List.get(3).setText(nb_player.value + " joueur(s)");
                option_button_display_List.add(new option_button_display(this.List_Touches_joueurs.get(option_button_display_List.size())));
                updateOptions.run();
            }
        });

        for (int i = 0; i < Button_List.size(); i++) {
            Button_List.get(i).setStyle(
                    "-fx-background-color: rgb(0, 0, 0); "
                    + // Couleur de fond (RGB)
                    "-fx-border-color: rgb(255, 255, 255); "
                    + // Couleur de la bordure (RGB)
                    "-fx-border-width: 3px; "
                    + // Épaisseur de la bordure
                    "-fx-border-radius: 10px; "
                    + // Coins arrondis
                    "-fx-background-radius: 12px;"
                    + // Coins arrondis pour le fond 
                    "-fx-font-family: 'Arial'; "
                    + // Police
                    "-fx-font-size: 16px; "
                    + // Taille de police
                    "-fx-font-weight: bold; "
                    + // Gras
                    "-fx-text-fill: rgb(255, 255, 255);" // Couleur du texte (blanc) 
            );

        }

        updateOptions.run();
        return new Scene(layout, width, height);
    }

    private void switchToScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
