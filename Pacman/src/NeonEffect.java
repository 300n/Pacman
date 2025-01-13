import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NeonEffect extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de la scène et du groupe de nœuds
        Group root = new Group();
        Scene scene = new Scene(root);

        // Création du canvas pour dessiner
        Canvas canvas = new Canvas(400, 200);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Réagir au redimensionnement de la fenêtre
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
            redraw(gc, canvas);
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue());
            redraw(gc, canvas);
        });
        

        // Initialiser le dessin
        redraw(gc, canvas);

        primaryStage.setTitle("Effet Néon Redimensionnable");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void redraw(GraphicsContext gc, Canvas canvas) {
        // Récupérer les nouvelles dimensions de la fenêtre
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        
        // Calculer des positions et dimensions dynamiques en fonction de la taille de la fenêtre
        double startX = width * 0.1;
        double startY = height * 0.5;
        double endX = width * 0.9;
        double endY = height * 0.5;

        // Effacer l'ancien dessin
        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, width, height);
        // Dessiner l'effet néon détaillé sur une ligne (adaptée à la taille de la fenêtre)
        drawNeonLine(gc, startX, startY, endX, endY);
    }

    private void drawNeonLine(GraphicsContext gc, double startX, double startY, double endX, double endY) {
        // Couleur de base pour l'effet néon (cyan)
        Color neonColor = Color.rgb(0, 255, 255);

        // Nombre d'itérations pour ajouter plus de détails
        int iterations = 15; // Augmenter pour plus de détails

        // Simuler un effet de lueur avec plusieurs itérations
        for (int i = 0; i < iterations; i++) {
            // Lueur progressive : on réduit l'intensité de la couleur à chaque itération
            double opacity = 1 - (i * 0.05); // Réduire progressivement l'opacité à chaque tour
            Color currentColor = neonColor.deriveColor(0, 1, 1, opacity);

            // Variation de la largeur de la ligne à chaque itération
            gc.setStroke(currentColor);
            gc.setLineWidth(10 + i * 0.5); // Augmenter légèrement la largeur pour plus de flou

            // Déplacement très léger pour chaque itération pour créer la lueur
            gc.strokeLine(
                startX - i * 0.5, 
                startY + i * 0.5, 
                endX - i * 0.5, 
                endY + i * 0.5
            );
        }

        // Dessiner la ligne principale avec un style plus fin et lumineux
        gc.setStroke(Color.rgb(255, 255, 255)); // Couleur blanche pour la ligne principale
        gc.setLineWidth(3); // Fine largeur pour le texte principal
        gc.strokeLine(startX, startY, endX, endY);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
