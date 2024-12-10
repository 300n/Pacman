package pacman__;

import static java.lang.Math.abs;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Pacman__ extends Application {

    public int window_width = 1000;
    public int window_height = 1000;
    public int width = window_width * 80 / 100;
    public int height = window_height * 80 / 100;
    public int board_top_x = (window_width - width) / 2;
    public int board_top_y = (window_height - height) / 2;
    public int score = 0;
    public int[][] tab = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    private int iterationCount = 0;
    double epsilon = 1; 

    @Override
    public void start(Stage primaryStage) {
        // Créer un Canvas
        Canvas canvas = new Canvas(window_width, window_height);
        Pacman pac = new Pacman();
        Blinky blinky = new Blinky();
        Clyde clyde = new Clyde();
        Pinky pinky = new Pinky();
        Inky inky = new Inky();
        // Obtenir le GraphicsContext pour dessiner
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dessiner sur le Canvas
        drawShapes(gc);
        pac.draw_Pacman(gc);
        // Ajouter le Canvas dans un conteneur
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Configurer la scène
        Scene scene = new Scene(root, window_width, window_height);
        
        
        try {
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/Ressources/Nabla-Regular.ttf"), 20);
            if (customFont == null) {
                throw new Exception("Font could not be loaded. Ensure the file path is correct.");
            }
            gc.setFont(customFont);
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            Font customFont = Font.font("Arial", 20);
            gc.setFont(customFont);
        }
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Timeline main_menu = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, window_width, window_height);
        
        }));
        
        main_menu.setCycleCount(Timeline.INDEFINITE);
        main_menu.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            // Effacer l'écran

            scene.setOnKeyPressed(event -> pac.direction_changed(event));
            scene.setOnKeyReleased(event -> pac.direction_changed(event));

            pac.update();


            
            blinky.Dijkstra(pac.x, pac.y, pac.p.tab);
            if(pac.p.tab[blinky.y][blinky.x] == 0){pac.p.tab[blinky.y][blinky.x] = 3;} else if (pac.p.tab[blinky.y][blinky.x] == 2) {pac.p.tab[blinky.y][blinky.x] = 4;}
            if(pac.p.tab[blinky.previous_y][blinky.previous_x] == 3){pac.p.tab[blinky.previous_y][blinky.previous_x] = 0;} else if (pac.p.tab[blinky.previous_y][blinky.previous_x] == 4)
            {pac.p.tab[blinky.previous_y][blinky.previous_x] = 2;}
            clyde.Dijkstra(pac.x, pac.y, pac.p.tab);
            if(pac.p.tab[clyde.y][clyde.x] == 0){pac.p.tab[clyde.y][clyde.x] = 3;} else if (pac.p.tab[clyde.y][clyde.x] == 2) {pac.p.tab[clyde.y][clyde.x] = 4;}
            if(pac.p.tab[clyde.previous_y][clyde.previous_x] == 3){pac.p.tab[clyde.previous_y][clyde.previous_x] = 0;} else if (pac.p.tab[clyde.previous_y][clyde.previous_x] == 4)
            {pac.p.tab[clyde.previous_y][clyde.previous_x] = 2;}
            pinky.Dijkstra(pac.x, pac.y, pac.facing, pac.p.tab);
            if(pac.p.tab[pinky.y][pinky.x] == 0){pac.p.tab[pinky.y][pinky.x] = 3;} else if (pac.p.tab[pinky.y][pinky.x] == 2) {pac.p.tab[pinky.y][pinky.x] = 4;}
            if(pac.p.tab[pinky.previous_y][pinky.previous_x] == 3){pac.p.tab[pinky.previous_y][pinky.previous_x] = 0;} else if (pac.p.tab[pinky.previous_y][pinky.previous_x] == 4)
            {pac.p.tab[pinky.previous_y][pinky.previous_x] = 2;}
            inky.Dijkstra(pac.x,pac.y,pac.facing,pac.p.tab,blinky.x,blinky.y);
            if(pac.p.tab[inky.y][inky.x] == 0){pac.p.tab[inky.y][inky.x] = 3;} else if (pac.p.tab[inky.y][inky.x] == 2) {pac.p.tab[inky.y][inky.x] = 4;}
            if(pac.p.tab[inky.previous_y][inky.previous_x] == 3){pac.p.tab[inky.previous_y][inky.previous_x] = 0;} else if (pac.p.tab[inky.previous_y][inky.previous_x] == 4)
            {pac.p.tab[inky.previous_y][inky.previous_x] = 2;}
            
        
           
            
            
            
            iterationCount = 0;
            pac.mouthOpening[0] = true;
            pac.mouthAngle[0] = 0;
            Timeline timeline_animation = new Timeline(new KeyFrame(Duration.millis(10), d -> {
                pac.p.drawShapes(gc);
                pac.p.drawNumbers(gc);
                pac.draw_vies(gc);
                iterationCount++;
                pac.interpolatedX = pac.previous_x + (pac.x - pac.previous_x) * (iterationCount / 50.0);
                pac.interpolatedY = pac.previous_y + (pac.y - pac.previous_y) * (iterationCount / 50.0);

                blinky.interpolatedX = blinky.previous_x + (blinky.x - blinky.previous_x) * (iterationCount / 50.0);
                blinky.interpolatedY = blinky.previous_y + (blinky.y - blinky.previous_y) * (iterationCount / 50.0);

                clyde.interpolatedX = clyde.previous_x + (clyde.x - clyde.previous_x) * (iterationCount / 50.0);
                clyde.interpolatedY = clyde.previous_y + (clyde.y - clyde.previous_y) * (iterationCount / 50.0);

                pinky.interpolatedX = pinky.previous_x + (pinky.x - pinky.previous_x) * (iterationCount / 50.0);
                pinky.interpolatedY = pinky.previous_y + (pinky.y - pinky.previous_y) * (iterationCount / 50.0);

                inky.interpolatedX = inky.previous_x + (inky.x - inky.previous_x) * (iterationCount / 50.0);
                inky.interpolatedY = inky.previous_y + (inky.y - inky.previous_y) * (iterationCount / 50.0);
                
                pac.draw_Pacman(gc);
                blinky.draw_ghost_moving(gc);
                clyde.draw_ghost_moving(gc);
                pinky.draw_ghost_moving(gc);
                inky.draw_ghost_moving(gc);
                
                if(check_collision(pac,blinky)||check_collision(pac,clyde)||check_collision(pac,pinky)||check_collision(pac,inky)) {                        
                        pac.reset();
                        blinky.reset();
                        clyde.reset();
                        pinky.reset();
                        inky.reset();
                        for (int i = 0; i < pac.p.tab.length; i++) {
                            for (int j = 0; j < pac.p.tab[0].length; j++) {
                               if (pac.p.tab[i][j]==3) {pac.p.tab[i][j]=0;}
                               if (pac.p.tab[i][j]==4) {pac.p.tab[i][j]=2;}
                            }
                        }
                        
                        pac.vies--;
                }
            }));
            timeline_animation.setCycleCount(50);
            timeline_animation.play();
            
            if (pac.p.tab[pac.previous_y][pac.previous_x] == 0) {
                pac.p.tab[pac.previous_y][pac.previous_x] = 2;
                pac.p.score+=10;
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void drawShapes(GraphicsContext gc) {
        // Remplir l'arrière-plan
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, window_width, window_height);

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.rgb(0, 0, 255));
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (tab[i][j] == 1) {
                    gc.setLineWidth(1);
                    gc.strokeRect(board_top_x + width / tab[0].length * j, board_top_y + height / tab.length * i, width / tab[0].length, height / tab.length);
                } else if (tab[i][j] == 0 || tab[i][j] == 3) {
                    gc.fillOval(board_top_x + width / tab[0].length * j + width / tab[0].length / 2 - 2.5, board_top_y + height / tab.length * i + height / tab.length / 2 - 2.5, 5, 5);
                }
            }

        }
        draw_points(gc, score);
        //draw_ghost(gc, 300, 50);
    }
    
    public void drawNumbers(GraphicsContext gc) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                Text text = new Text(""+this.tab[i][j]);
                gc.fillText(""+this.tab[i][j], this.board_top_x + this.width / this.tab[0].length * j + this.width / this.tab[0].length / 2 - text.getBoundsInLocal().getWidth() / 1, this.board_top_y + this.height
                    / this.tab.length * i + this.height / this.tab.length / 2+ text.getBoundsInLocal().getHeight() / 2);
                
            }
        }
    }
    
    public void draw_points(GraphicsContext gc, int score) {
        gc.setStroke(Color.rgb(0, 0, 255));
        gc.strokeRect(board_top_x / 4, board_top_y / 4, 150, 50);
        Text text = new Text("Score : " + score);
        text.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText("Score : " + score, board_top_x / 4 + 150 / 10, board_top_y / 4 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);

    }
    
    public boolean check_collision(Pacman a, Ghost b){
        return (abs(a.interpolatedX - b.interpolatedX)+ abs(a.interpolatedY - b.interpolatedY)) < epsilon;
    }
    
    public void reset(Pacman a, Ghost b, Ghost c, Ghost d, Ghost e){
        a = new Pacman();
        b = new Blinky();
        c = new Clyde();
        d = new Pinky();
        e = new Inky();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
