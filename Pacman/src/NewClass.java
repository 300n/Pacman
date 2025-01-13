
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NewClass extends Application {

    public class RoundedLogo {

        /**
         * Dessine un logo avec des formes arrondies inspiré du style du logo
         * Google.
         *
         * @param gc GraphicsContext pour dessiner.
         * @param startX Coordonnée X de départ.
         * @param startY Coordonnée Y de départ.
         * @param width Largeur totale du dessin.
         * @param height Hauteur totale du dessin.
         */
        public static void drawRoundedLogo(GraphicsContext gc, int startX, int startY, int width, int height) {
            // Définir les couleurs utilisées pour les lettres
            Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};

            // Taille d'une "case" de la grille
            int cellWidth = width / 14;  // Divisé en 14 colonnes
            int cellHeight = height / 7; // Divisé en 7 lignes

            // Dessiner les lettres avec des bords arrondis
            gc.setFill(colors[0]); // Bleu pour le "G"
            gc.fillRoundRect(startX, startY + cellHeight, 4 * cellWidth, 3 * cellHeight, 20, 20); // "G" principal
            gc.clearRect(startX + cellWidth * 2.5, startY + cellHeight * 2, cellWidth, cellHeight); // Trou dans le "G"

            gc.setFill(colors[1]); // Rouge pour le "o"
            gc.fillRoundRect(startX + 5 * cellWidth, startY + cellHeight, 2 * cellWidth, 3 * cellHeight, 20, 20); // "o"

            gc.setFill(colors[2]); // Jaune pour le second "o"
            gc.fillRoundRect(startX + 8 * cellWidth, startY + cellHeight, 2 * cellWidth, 3 * cellHeight, 20, 20); // "o"

            gc.setFill(colors[3]); // Vert pour le "l"
            gc.fillRoundRect(startX + 11 * cellWidth, startY + cellHeight, cellWidth, 3 * cellHeight, 20, 20); // "l"

            gc.setFill(colors[1]); // Rouge pour le "e"
            gc.fillRoundRect(startX + 13 * cellWidth, startY + cellHeight, 2 * cellWidth, 3 * cellHeight, 20, 20); // "e"
            gc.clearRect(startX + 14 * cellWidth, startY + cellHeight * 2, cellWidth, cellHeight); // Trou dans le "e"

            // Dessiner les points dans le labyrinthe
            gc.setFill(Color.WHITE);
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 7; j++) {
                    if ((i + j) % 2 == 0) {
                        gc.fillOval(startX + i * cellWidth + cellWidth / 2.5, startY + j * cellHeight + cellHeight / 2.5, 5, 5);
                    }
                }
            }

            // Dessiner les fantômes (cercles colorés représentant les fantômes)
            gc.setFill(Color.PINK); // Fantôme rose
            gc.fillOval(startX + cellWidth * 3, startY, cellWidth, cellHeight);

            gc.setFill(Color.ORANGE); // Fantôme orange
            gc.fillOval(startX + cellWidth * 7, startY + 3 * cellHeight, cellWidth, cellHeight);

            gc.setFill(Color.LIGHTBLUE); // Fantôme bleu clair
            gc.fillOval(startX + cellWidth * 9, startY + 5 * cellHeight, cellWidth, cellHeight);

            gc.setFill(Color.RED); // Fantôme rouge
            gc.fillOval(startX + cellWidth * 11, startY + cellHeight * 4, cellWidth, cellHeight);
        }
    }
        public void start(Stage primaryStage) {
            // Créer un Canvas
            Canvas canvas = new Canvas(700, 300); // Taille de la zone de dessin
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Dessiner le logo avec des formes arrondies
            RoundedLogo.drawRoundedLogo(gc, 50, 50, 600, 200);

            // Ajouter le Canvas à la scène
            StackPane root = new StackPane(canvas);
            Scene scene = new Scene(root, 700, 300);

            primaryStage.setTitle("Logo Inspiré de Google");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    

}
