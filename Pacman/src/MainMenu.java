
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {

    ArrayList<Button> Button_List = new ArrayList();

    private Stage primaryStage;
    ArrayList<Touches_joueur> List_Touches_joueurs = new ArrayList();
    Two_side_button nb_player = new Two_side_button(1, 4, 1);
    int width = 1150, height = 800;
    Font customFont;
    Font customFontTitle;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 30);
            this.customFontTitle = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 50);
            if (customFont == null) {
                throw new Exception("Font could not be loaded. Ensure the file path is correct.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            this.customFont = Font.font("Arial", 20);
        }
        this.primaryStage = primaryStage;
        this.List_Touches_joueurs.add(new Touches_joueur("z", "q", "s", "d"));
        this.List_Touches_joueurs.add(new Touches_joueur("UP", "LEFT", "DOWN", "RIGHT"));
        this.List_Touches_joueurs.add(new Touches_joueur("t", "f", "g", "h"));
        this.List_Touches_joueurs.add(new Touches_joueur("i", "j", "k", "l"));
        // Créer les scènes
        Scene mainMenuScene = createMainMenu();
        Scene newGameScene = createNewGameScene();
        Scene optionsScene = createOptionsScene();
        Scene HighScoreScene = createHighScoreScene();

        // Configurer la scène principale
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Pacman");
        primaryStage.show();
    }

    private Scene createMainMenu() {
        long startTime = System.currentTimeMillis();
        AnchorPane menuLayout = new AnchorPane();
        menuLayout.setStyle("-fx-background-color: rgb(20, 20, 20);");
        // Boutons du menu
        ArrayList<Button> Button_List = new ArrayList();
        Button_List.add(new Button("Nouvelle Partie"));
        Button_List.add(new Button("Options"));
        Button_List.add(new Button("Highscore"));
        Button_List.add(new Button("Quitter"));

        Image image = new Image("/ressources/Pacman_main_logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        AnchorPane.setTopAnchor(imageView, height / 3 - imageView.getFitHeight() / 2);
        AnchorPane.setLeftAnchor(imageView, width / 2 - imageView.getFitWidth() / 2);
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawLogo(gc, 575-225, 225, 300, 200);
        menuLayout.getChildren().add(canvas);
        // Actions des boutons
        Button_List.get(0).setOnAction(e -> {
            Pacman__ game = new Pacman__();
            
            boolean started = game.start(primaryStage, nb_player.value, List_Touches_joueurs, () -> {
                // Rappel lorsque le jeu se termine
                primaryStage.setScene(createMainMenu());
            });
        });
        
        Button_List.get(2).setOnAction(e -> switchToScene(createHighScoreScene()));
        Button_List.get(1).setOnAction(e -> switchToScene(createOptionsScene()));
        Button_List.get(3).setOnAction(e -> primaryStage.close());

        for (int i = 0; i < Button_List.size(); i++) {
            Button_List.get(i).setStyle(
                    "-fx-background-color: rgb(20, 20, 20); " // Couleur de fond (noir)
                    + "-fx-border-color: rgba(0, 255, 255, 0.8); " // Couleur néon avec opacité
                    + "-fx-border-width: 3px; " // Épaisseur de la bordure
                    + "-fx-border-radius: 10px; " // Coins arrondis pour la bordure
                    + "-fx-background-radius: 12px; " // Coins arrondis pour le fond
                    + "-fx-font-size: 26px; " // Taille de la police
                    + "-fx-font-weight: bold; " // Texte en gras
                    + "-fx-text-fill: rgb(255, 255, 255); " // Couleur du texte (néon cyan)
                    + "-fx-effect: dropshadow(gaussian, rgba(0, 255, 255, 0.8), 10, 0.5, 0, 0);" // Effet de lueur néon
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
    private static int extraireScore(String ligne) {
        try {
            // Exemple de ligne : "- 150 par nomUtilisateur réalisé le : 12/01/2025"
            String[] parties = ligne.split(" "); // Divise la ligne en mots
            return Integer.parseInt(parties[1]); // Le score est supposé être le 2ème élément
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erreur lors de l'extraction du score dans la ligne : " + ligne);
            return 0; // Retourne 0 par défaut en cas de problème
        }
    }
    
    
    private Scene createHighScoreScene() {
        AnchorPane layout = new AnchorPane();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Button Quitter = new Button("Retour au menu");
        layout.setStyle("-fx-background-color: rgb(20, 20, 20);");
        Quitter.setOnAction(e -> switchToScene(createMainMenu()));
        Quitter.setStyle(
                    "-fx-background-color: rgb(20, 20, 20); " // Couleur de fond (noir)
                    + "-fx-border-color: rgba(0, 255, 255, 0.8); " // Couleur néon avec opacité
                    + "-fx-border-width: 3px; " // Épaisseur de la bordure
                    + "-fx-border-radius: 10px; " // Coins arrondis pour la bordure
                    + "-fx-background-radius: 12px; " // Coins arrondis pour le fond
                    + "-fx-font-size: 20px; " // Taille de la police
                    + "-fx-font-weight: bold; " // Texte en gras
                    + "-fx-text-fill: rgb(255, 255, 255); " // Couleur du texte (néon cyan)
                    + "-fx-effect: dropshadow(gaussian, rgba(0, 255, 255, 0.8), 10, 0.5, 0, 0);" // Effet de lueur néon
            );
        Text text = new Text(Quitter.getText());
        text.setFont(customFont);
        Quitter.setLayoutX(width/2 - text.getBoundsInLocal().getWidth()/2);
        Quitter.setLayoutY(height - height/10 - text.getBoundsInLocal().getHeight()/2);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 30));
        gc.setFill(Color.WHITE);
        gc.fillText("Rang", width/10 ,height/10*2);
        text = new Text("Rang");
        gc.fillText("Score", width/10*2.25 ,height/10*2);
        Text text2 = new Text("Score");
        text2.setFont(customFont);
        gc.fillText("Utilisateur", width/10*3.5 ,height/10*2);
        Text text3 = new Text("Utilisateur");
        text3.setFont(customFont);
        gc.fillText("Date", width/10*5.75 ,height/10*2);
        Text text4 = new Text("Date");
        text4.setFont(customFont);
        
        String cheminFichier = "./Users_Highscore/Highscores.txt";
        try {
            List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));
            int i = 1;
            for (String ligne: lignes) {
                String[] parties = ligne.split(" ");
                gc.fillText(""+i, width/10+text.getBoundsInLocal().getWidth(), height/10*2.2 + height/30*i);
                
                Text text5 = new Text(""+Integer.parseInt(parties[1]));
                text5.setFont(customFont);
                gc.fillText(""+Integer.parseInt(parties[1]), width/10*2.25+text2.getBoundsInLocal().getWidth()-text5.getBoundsInLocal().getWidth(),
                        height/10*2.2 + height/30*i);
                
                Text text6 = new Text(""+parties[3]);
                text6.setFont(customFont);
                gc.fillText(""+parties[3], width/10*3.5+text3.getBoundsInLocal().getWidth()/2-text6.getBoundsInLocal().getWidth()/2, height/10*2.2 + height/30*i);
                
                
                Text text7 = new Text(""+parties[7]);
                text7.setFont(customFont);
                gc.fillText(""+parties[7], width/10*5.75+text4.getBoundsInLocal().getWidth()/2-text7.getBoundsInLocal().getWidth()/2, height/10*2.2 + height/30*i);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 50));
        Text text8 = new Text("High Scores");
        text8.setFont(customFontTitle);
        gc.setFill(Color.YELLOW);
        gc.fillText("High Scores",width/2 - text8.getBoundsInLocal().getWidth()/2 ,height/10);
        gc.fillArc(width/2 + width/8.5, height/16,
                    28, 28,
                    0 + 30, // Angle de début
                    360 - 2 * 30, // Taille de l'arc
                    ArcType.ROUND);
        
        gc.fillArc(width/2 - width/7, height/16,
                    28, 28,
                    0 + 210, // Angle de début
                    360 - 2 * 30, // Taille de l'arc
                    ArcType.ROUND);
        
        layout.getChildren().add(canvas);
        layout.getChildren().add(Quitter);
        return new Scene(layout, width, height);
    }
    
    
    
    
    private Scene createOptionsScene() {
        AnchorPane layout = new AnchorPane();

        layout.setStyle("-fx-padding: 10; -fx-alignment: center;-fx-background-color: rgb(20, 20, 20);");
        

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

    /**
     * Trace un arc de cercle entre deux points.
     *
     * @param gc GraphicsContext pour dessiner.
     * @param x Coordonnée X du premier point.
     * @param y Coordonnée Y du premier point.
     * @param x1 Coordonnée X du second point.
     * @param y1 Coordonnée Y du second point.
     * @param radius Rayon de l'arc.
     */
    public static void drawArcBetweenPoints(GraphicsContext gc, double x, double y, double x1, double y1, double radius) {
        // Calcul du centre de l'arc
        double midX = (x + x1) / 2;
        double midY = (y + y1) / 2;

        // Calcul de la distance entre les points pour déterminer le rectangle englobant
        double dx = x1 - x;
        double dy = y1 - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Vérification : le rayon doit être au moins égal à la moitié de la distance entre les deux points
        if (radius < distance / 2) {
            System.err.println("Le rayon est trop petit pour contenir les deux points.");
            return;
        }

        // Calcul de la hauteur du cercle en fonction du rayon
        double offset = Math.sqrt(radius * radius - (distance / 2) * (distance / 2));

        // Choisir si l'arc est au-dessus ou en dessous des points
        double centerX = midX - dy * offset / distance; // Centre décalé perpendiculairement
        double centerY = midY + dx * offset / distance;

        // Calcul des angles de départ et de l'arc
        double startAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        double endAngle = Math.toDegrees(Math.atan2(y1 - centerY, x1 - centerX));
        double length = endAngle - startAngle;

        // Corriger l'angle si nécessaire pour un arc positif
        if (length < 0) {
            length += 360;
        }

        // Calcul du rectangle englobant
        double rectX = centerX - radius;
        double rectY = centerY - radius;

        // Tracer l'arc
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeArc(rectX, rectY, 2 * radius, 2 * radius, startAngle, length, javafx.scene.shape.ArcType.OPEN);
    }

    
    
    
    
    
    /**
     * Dessine un logo en PixelArt dans un rectangle.
     *
     * @param gc GraphicsContext pour dessiner.
     * @param imagePath Chemin vers l'image source (le logo).
     * @param startX Coordonnée X de départ.
     * @param startY Coordonnée Y de départ.
     * @param width Largeur du rectangle.
     * @param height Hauteur du rectangle.
     */
    public static void drawLogo(GraphicsContext gc, int startX, int startY, int width, int height) {
        gc.setLineWidth(2);
        int factor = 5;
        gc.setStroke(new Color(1, 1, 1, 1));
        //I
        gc.strokeLine(startX + 2 * factor, startY + 16 * factor, startX + 2 * factor, startY + 20 * factor);
        gc.strokeLine(startX + 4 * factor, startY + 16 * factor, startX + 4 * factor, startY + 20 * factor);
        gc.strokeLine(startX + 1 * factor, startY + 16 * factor, startX + 2 * factor, startY + 16 * factor);
        gc.strokeLine(startX + 4 * factor, startY + 16 * factor, startX + 5 * factor, startY + 16 * factor);
        gc.strokeLine(startX + 1 * factor, startY + 14 * factor, startX + 5 * factor, startY + 14 * factor);
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 16 * factor, startX + 1 * factor, startY + 14 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 5 * factor, startY + 14 * factor, startX + 5 * factor, startY + 16 * factor, 1 * factor);

        gc.strokeLine(startX + 1 * factor, startY + 20 * factor, startX + 2 * factor, startY + 20 * factor);
        gc.strokeLine(startX + 4 * factor, startY + 20 * factor, startX + 5 * factor, startY + 20 * factor);
        gc.strokeLine(startX + 1 * factor, startY + 22 * factor, startX + 5 * factor, startY + 22 * factor);
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 22 * factor, startX + 1 * factor, startY + 20 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 5 * factor, startY + 20 * factor, startX + 5 * factor, startY + 22 * factor, 1 * factor);
        
        
        // M
        gc.strokeLine(startX + 7 * factor, startY + 15 * factor, startX + 7 * factor, startY + 21 * factor);

        gc.strokeLine(startX + 8.5 * factor, startY + 14 * factor, startX + 12 * factor, startY + 18 * factor);
        gc.strokeLine(startX + 9 * factor, startY + 17 * factor, startX + 12 * factor, startY + 21 * factor);

        gc.strokeLine(startX + 9 * factor, startY + 17 * factor, startX + 9 * factor, startY + 21 * factor);

        drawArcBetweenPoints(gc, startX + 9 * factor, startY + 15 * factor, startX + 7 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 7 * factor, startY + 21 * factor, startX + 9 * factor, startY + 21 * factor, 1 * factor);

        int centerX = 12; // Centre de l'axe de symétrie en "unités de facteur"

        gc.strokeLine(startX + (2 * centerX - 7) * factor, startY + 15 * factor,startX + (2 * centerX - 7) * factor, startY + 21 * factor); 
        gc.strokeLine(startX + (2 * centerX - 8.5) * factor, startY + 14 * factor,startX + (2 * centerX - 12) * factor, startY + 18 * factor); 
        gc.strokeLine(startX + (2 * centerX - 9) * factor, startY + 17 * factor,startX + (2 * centerX - 12) * factor, startY + 21 * factor); 
        gc.strokeLine(startX + (2 * centerX - 9) * factor, startY + 17 * factor, startX + (2 * centerX - 9) * factor, startY + 21 * factor); 
        
        drawArcBetweenPoints(gc, startX + 17 * factor, startY + 15 * factor, startX + 15 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 15 * factor, startY + 21 * factor, startX + 17 * factor, startY + 21 * factor, 1 * factor);
        
        // T
        startX += 19*factor;
        gc.strokeLine(startX + 2 * factor, startY + 16 * factor, startX + 2 * factor, startY + 21 * factor);
        gc.strokeLine(startX + 4 * factor, startY + 16 * factor, startX + 4 * factor, startY + 21 * factor);
        gc.strokeLine(startX + 0 * factor, startY + 16 * factor, startX + 2 * factor, startY + 16 * factor);
        gc.strokeLine(startX + 4 * factor, startY + 16 * factor, startX + 6 * factor, startY + 16 * factor);
        gc.strokeLine(startX + 0 * factor, startY + 14 * factor, startX + 6 * factor, startY + 14 * factor);
        drawArcBetweenPoints(gc, startX + 0 * factor, startY + 16 * factor, startX + 0 * factor, startY + 14 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 6 * factor, startY + 14 * factor, startX + 6 * factor, startY + 16 * factor, 1 * factor);
        
        drawArcBetweenPoints(gc, startX + 2 * factor, startY + 21 * factor, startX + 4 * factor, startY + 21 * factor, 1 * factor);
        
        startX += 6*factor;
        
        // M
        gc.strokeLine(startX + 7 * factor, startY + 15 * factor, startX + 7 * factor, startY + 21 * factor);

        gc.strokeLine(startX + 8.5 * factor, startY + 14 * factor, startX + 12 * factor, startY + 18 * factor);
        gc.strokeLine(startX + 9 * factor, startY + 17 * factor, startX + 12 * factor, startY + 21 * factor);

        gc.strokeLine(startX + 9 * factor, startY + 17 * factor, startX + 9 * factor, startY + 21 * factor);

        drawArcBetweenPoints(gc, startX + 9 * factor, startY + 15 * factor, startX + 7 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 7 * factor, startY + 21 * factor, startX + 9 * factor, startY + 21 * factor, 1 * factor);

        gc.strokeLine(startX + (2 * centerX - 7) * factor, startY + 15 * factor,startX + (2 * centerX - 7) * factor, startY + 21 * factor); 
        gc.strokeLine(startX + (2 * centerX - 8.5) * factor, startY + 14 * factor,startX + (2 * centerX - 12) * factor, startY + 18 * factor); 
        gc.strokeLine(startX + (2 * centerX - 9) * factor, startY + 17 * factor,startX + (2 * centerX - 12) * factor, startY + 21 * factor); 
        gc.strokeLine(startX + (2 * centerX - 9) * factor, startY + 17 * factor, startX + (2 * centerX - 9) * factor, startY + 21 * factor); 
        
        drawArcBetweenPoints(gc, startX + 17 * factor, startY + 15 * factor, startX + 15 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 15 * factor, startY + 21 * factor, startX + 17 * factor, startY + 21 * factor, 1 * factor);
        
        
        // i 
        
        startX += 18*factor;
        gc.strokeLine(startX + 1*factor, startY + 17 * factor, startX + 1*factor, startY+21*factor);
        gc.strokeLine(startX + 3*factor, startY + 17 * factor, startX + 3*factor, startY+21*factor);
        
        drawArcBetweenPoints(gc, startX + 3 * factor, startY + 17 * factor, startX + 1 * factor, startY + 17 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 21 * factor, startX + 3 * factor, startY + 21 * factor, 1 * factor);
        
        
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 15 * factor, startX + 3 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 3 * factor, startY + 15 * factor, startX + 1 * factor, startY + 15 * factor, 1 * factor);
        
        // n
        startX += 4*factor;
        gc.strokeLine(startX + 1*factor, startY + 17 * factor, startX + 1*factor, startY+21*factor);
        gc.strokeLine(startX + 3*factor, startY + 17 * factor, startX + 3*factor, startY+21*factor);
        
        drawArcBetweenPoints(gc, startX + 3 * factor, startY + 17 * factor, startX + 1 * factor, startY + 17 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 21 * factor, startX + 3 * factor, startY + 21 * factor, 1 * factor);
        
        drawArcBetweenPoints(gc, startX + 6 * factor, startY + 21 * factor, startX + 8 * factor, startY + 21 * factor, 1 * factor);
        
        drawArcBetweenPoints(gc, startX + 8 * factor, startY + 18.5 * factor, startX + 3 * factor, startY + 18.5 * factor, 2.5 * factor);
        gc.strokeLine(startX + 8*factor, startY + 19 * factor, startX + 8*factor, startY+21*factor);
        gc.strokeLine(startX + 6*factor, startY + 20.5 * factor, startX + 6*factor, startY+21*factor);
        drawArcBetweenPoints(gc, startX + 6 * factor, startY + 20.5 * factor, startX + 3 * factor, startY + 20.5 * factor, 1.5 * factor);
        
        
        // e
        startX += 8*factor;
        gc.strokeLine(startX + 3*factor, startY + 18.5 * factor, startX + 5*factor, startY+18.5*factor);
        drawArcBetweenPoints(gc, startX + 5*factor, startY + 18.5 * factor, startX + 3*factor, startY+18.5*factor, 1 * factor);

        drawArcBetweenPoints(gc, startX + 4.5*factor, startY + 16 * factor, startX + 4.5*factor, startY+20*factor, 2 * factor);
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 22 * factor, startX + 4*factor, startY + 16 * factor, 3 * factor);
        
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 21 * factor, startX + 4*factor, startY + 20 * factor, 0.5 * factor);
        gc.strokeLine(startX + 4*factor, startY + 21 * factor, startX + 6*factor, startY+21*factor);
        gc.strokeLine(startX + 4*factor, startY + 22 * factor, startX + 6*factor, startY+22*factor);
        drawArcBetweenPoints(gc, startX + 6*factor, startY + 21 * factor, startX + 6*factor, startY + 22 * factor, 0.5 * factor);
        
        gc.strokeLine(startX + 4*factor, startY + 16 * factor, startX + 4.5*factor, startY+16*factor);
        gc.strokeLine(startX + 4*factor, startY + 20 * factor, startX + 4.5*factor, startY+20*factor);
        
        //s
        startX += 7*factor;
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 18.5 * factor, startX + 3*factor, startY + 17 * factor, 0.75 * factor);
        drawArcBetweenPoints(gc, startX + 2*factor, startY + 19.5 * factor, startX + 2*factor, startY + 21 * factor, 0.75 * factor);
        drawArcBetweenPoints(gc, startX + 2*factor, startY + 19.5 * factor, startX + 2*factor, startY + 16 * factor, 1.75 * factor);
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 18.5 * factor, startX + 3*factor, startY + 22 * factor, 1.75 * factor);
        gc.strokeLine(startX + 2*factor, startY + 16 * factor, startX + 4*factor, startY+16*factor);
        gc.strokeLine(startX + 3*factor, startY + 22 * factor, startX + 1*factor, startY+22*factor);
        gc.strokeLine(startX + 3*factor, startY + 17 * factor, startX + 4*factor, startY+17*factor);
        gc.strokeLine(startX + 2*factor, startY + 21 * factor, startX + 1*factor, startY+21*factor);
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 16 * factor, startX + 4*factor, startY + 17 * factor, 0.5 * factor);
        drawArcBetweenPoints(gc, startX + 1*factor, startY + 22 * factor, startX + 1*factor, startY + 21 * factor, 0.5 * factor);
        
        
        // A 
        startX += 7*factor;
        
        gc.strokeLine(startX + 2*factor, startY + 22 * factor, startX + 5*factor, startY+14*factor);
        gc.strokeLine(startX + 12*factor, startY + 22 * factor, startX + 9*factor, startY+14*factor);
        gc.strokeLine(startX + 4*factor, startY + 22 * factor, startX + 6*factor, startY+19*factor);
        gc.strokeLine(startX + 10*factor, startY + 22 * factor, startX + 8*factor, startY+19*factor);
        gc.strokeLine(startX + 8*factor, startY + 19 * factor, startX + 6*factor, startY+19*factor);
        gc.strokeLine(startX + 2*factor, startY + 22 * factor, startX + 4*factor, startY+22*factor);
        gc.strokeLine(startX + 10*factor, startY + 22 * factor, startX + 12*factor, startY+22*factor);
        gc.strokeLine(startX + 5*factor, startY + 14 * factor, startX + 9*factor, startY+14*factor);
        
        gc.strokeLine(startX + 5.5*factor, startY + 17.5 * factor, startX + 8.5*factor, startY+17.5*factor);
        gc.strokeLine(startX + 6*factor, startY + 15.5 * factor, startX + 8*factor, startY+15.5*factor);
        gc.strokeLine(startX + 6*factor, startY + 15.5 * factor, startX + 5.5*factor, startY+17.5*factor);
        gc.strokeLine(startX + 8*factor, startY + 15.5 * factor, startX + 8.5*factor, startY+17.5*factor);
        
        // l 
        startX += 12*factor;
        gc.strokeLine(startX + 1*factor, startY + 15 * factor, startX + 1*factor, startY+21*factor);
        gc.strokeLine(startX + 3*factor, startY + 15 * factor, startX + 3*factor, startY+21*factor);
        
        drawArcBetweenPoints(gc, startX + 3 * factor, startY + 15 * factor, startX + 1 * factor, startY + 15 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 1 * factor, startY + 21 * factor, startX + 3 * factor, startY + 21 * factor, 1 * factor);
        
        // e
        startX += 3*factor;
        gc.strokeLine(startX + 3*factor, startY + 18.5 * factor, startX + 5*factor, startY+18.5*factor);
        drawArcBetweenPoints(gc, startX + 5*factor, startY + 18.5 * factor, startX + 3*factor, startY+18.5*factor, 1 * factor);

        drawArcBetweenPoints(gc, startX + 4.5*factor, startY + 16 * factor, startX + 4.5*factor, startY+20*factor, 2 * factor);
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 22 * factor, startX + 4*factor, startY + 16 * factor, 3 * factor);
        
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 21 * factor, startX + 4*factor, startY + 20 * factor, 0.5 * factor);
        gc.strokeLine(startX + 4*factor, startY + 21 * factor, startX + 6*factor, startY+21*factor);
        gc.strokeLine(startX + 4*factor, startY + 22 * factor, startX + 6*factor, startY+22*factor);
        drawArcBetweenPoints(gc, startX + 6*factor, startY + 21 * factor, startX + 6*factor, startY + 22 * factor, 0.5 * factor);
        
        gc.strokeLine(startX + 4*factor, startY + 16 * factor, startX + 4.5*factor, startY+16*factor);
        gc.strokeLine(startX + 4*factor, startY + 20 * factor, startX + 4.5*factor, startY+20*factor);
        
        // è 
        factor -=1;
        startX +=4;
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 16 * factor, startX + 5*factor, startY + 14 * factor, 2 * factor);
        drawArcBetweenPoints(gc, startX + 2*factor, startY + 17 * factor, startX + 4*factor, startY + 15 * factor, 2 * factor);
        
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 17.6 * factor, startX + 2*factor, startY + 16.6 * factor, 1 * factor);
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 17 * factor, startX + 5*factor, startY + 18 * factor, 1 * factor);
        startX -=4;
        factor +=1;
        //s
        startX += 7*factor;
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 18.5 * factor, startX + 3*factor, startY + 17 * factor, 0.75 * factor);
        drawArcBetweenPoints(gc, startX + 2*factor, startY + 19.5 * factor, startX + 2*factor, startY + 21 * factor, 0.75 * factor);
        drawArcBetweenPoints(gc, startX + 2*factor, startY + 19.5 * factor, startX + 2*factor, startY + 16 * factor, 1.75 * factor);
        drawArcBetweenPoints(gc, startX + 3*factor, startY + 18.5 * factor, startX + 3*factor, startY + 22 * factor, 1.75 * factor);
        gc.strokeLine(startX + 2*factor, startY + 16 * factor, startX + 4*factor, startY+16*factor);
        gc.strokeLine(startX + 3*factor, startY + 22 * factor, startX + 1*factor, startY+22*factor);
        gc.strokeLine(startX + 3*factor, startY + 17 * factor, startX + 4*factor, startY+17*factor);
        gc.strokeLine(startX + 2*factor, startY + 21 * factor, startX + 1*factor, startY+21*factor);
        drawArcBetweenPoints(gc, startX + 4*factor, startY + 16 * factor, startX + 4*factor, startY + 17 * factor, 0.5 * factor);
        drawArcBetweenPoints(gc, startX + 1*factor, startY + 22 * factor, startX + 1*factor, startY + 21 * factor, 0.5 * factor);
        
    }
    
    

    private void switchToScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
