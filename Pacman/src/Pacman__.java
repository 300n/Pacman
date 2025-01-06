
import static java.lang.Math.abs;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Pacman__ {

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
        {1, 5, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 5, 1},
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
        {1, 5, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 5, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    private int iterationCount = 0;
    double epsilon = 0.5;
    boolean game_over = false;
    Font customFont;
    String mode = "normal";
    int t = 0;
    ArrayList<Ghost> List_Ghost = new ArrayList();

    public void start(Stage primaryStage, int nb_de_joueur, ArrayList<Touches_joueur> touches_joueur) {
        // Créer un Canvas
        Canvas canvas = new Canvas(window_width, window_height);
        Pacman pac = new Pacman();
        pac.p.List_Ghost.add(new Blinky());
        pac.p.List_Ghost.add(new Clyde());
        pac.p.List_Ghost.add(new Pinky());
        pac.p.List_Ghost.add(new Inky());
        // Obtenir le GraphicsContext pour dessiner
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ajouter le Canvas dans un conteneur
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Configurer la scène
        Scene scene = new Scene(root, window_width, window_height);

        try {
            this.customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 26);
            if (customFont == null) {
                throw new Exception("Font could not be loaded. Ensure the file path is correct.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            this.customFont = Font.font("Arial", 20);
        }
        gc.setFont(this.customFont);

        Timeline main_menu = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, window_width, window_height);

        }));

        main_menu.setCycleCount(Timeline.INDEFINITE);
        main_menu.play();
        primaryStage.setScene(scene);
        primaryStage.show();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent e) -> {

            scene.setOnKeyPressed(event -> pac.direction_changed(event, touches_joueur.get(0)));
            scene.setOnKeyReleased(event -> pac.direction_changed(event, touches_joueur.get(0)));

            pac.update();
            for (Ghost g : pac.p.List_Ghost) {
                g.Dijkstra((int) pac.x, (int) pac.y, pac.facing, pac.p.tab, pac.p.List_Ghost.get(0).x, pac.p.List_Ghost.get(0).y, this.mode);
                pac.p.tab[g.y][g.x] = (pac.p.tab[g.y][g.x] == 0) ? 3 : (pac.p.tab[g.y][g.x] == 2 ? 4 : pac.p.tab[g.y][g.x]);
                pac.p.tab[g.previous_y][g.previous_x] = (pac.p.tab[g.previous_y][g.previous_x] == 3) ? 0
                        : (pac.p.tab[g.previous_y][g.previous_x] == 4 ? 2 : pac.p.tab[g.previous_y][g.previous_x]);
            }

            //if (pac.p.tab[blinky.y][blinky.x] == 0) {pac.p.tab[blinky.y][blinky.x] =  3;} else {pac.p.tab[blinky.y][blinky.x] = 4;}
            //if (pac.p.tab[blinky.previous_y][blinky.previous_x] == 3) {pac.p.tab[blinky.y][blinky.x] =  0;} else { pac.p.tab[blinky.previous_y][blinky.previous_x] = 2;}
            iterationCount = 0;
            pac.mouthOpening[0] = true;
            pac.mouthAngle[0] = 0;
            Timeline timeline_animation = new Timeline();
            timeline_animation.getKeyFrames().add(new KeyFrame(Duration.millis(10), d -> {
                pac.p.drawShapes(gc, nb_de_joueur);
                // pac.p.drawNumbers(gc);
                pac.draw_vies(gc);
                iterationCount++;
                pac.interpolatedX = pac.previous_x + (pac.x - pac.previous_x) * (iterationCount / 50.0);
                pac.interpolatedY = pac.previous_y + (pac.y - pac.previous_y) * (iterationCount / 50.0);
                pac.draw_Pacman(gc);
                scene.setOnKeyPressed(event -> pac.direction_changed(event, touches_joueur.get(0)));
                scene.setOnKeyReleased(event -> pac.direction_changed(event, touches_joueur.get(0)));
                if (pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 0 || pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 3) {
                    pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                    pac.p.score += 10;
                } else if (pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 5) {
                    pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                    pac.p.score += 50;
                    this.mode = "frightened";
                    for (Ghost ghost : pac.p.List_Ghost) {
                        ghost.couleur = new Color(0, 0, 1, 1);
                    }
                    System.out.println("frightened");
                    this.t = 15;
                }

                for (Ghost ghost : pac.p.List_Ghost) {
                    ghost.interpolatedX = ghost.previous_x + (ghost.x - ghost.previous_x) * (iterationCount / 50.0);
                    ghost.interpolatedY = ghost.previous_y + (ghost.y - ghost.previous_y) * (iterationCount / 50.0);
                    ghost.draw_ghost_moving(gc);
                }

                boolean in_collision = false;
                for (Ghost ghost : pac.p.List_Ghost) {
                    if (check_collision(pac, ghost)) {
                        in_collision = true;
                    }
                }
                scene.setOnKeyPressed(event -> pac.direction_changed(event, touches_joueur.get(0)));
                scene.setOnKeyReleased(event -> pac.direction_changed(event, touches_joueur.get(0)));
                if (in_collision) {
                    if ("normal".equals(mode)) {

                        timeline_animation.pause();
                        timeline.pause();
                        int[] temp = {30};
                        Timeline tempAnimation = new Timeline();
                        tempAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), g -> {
                            if (temp[0] > 0) {
                                pac.p.drawShapes(gc, nb_de_joueur);
                                pac.draw_vies(gc);
                                pac.draw_dying_animation(gc, temp);
                                for (Ghost other_ghost : pac.p.List_Ghost) {
                                    other_ghost.draw_ghost_moving(gc);
                                }
                                temp[0]--;
                            } else {
                                tempAnimation.stop(); // Arrêtez directement le Timeline
                                timeline_animation.play();
                                timeline.play();
                                pac.vies--;
                                pac.reset();
                                for (Ghost ghost : pac.p.List_Ghost) {
                                    ghost.reset();
                                }
                                for (int[] tab1 : pac.p.tab) {
                                    for (int j = 0; j < pac.p.tab[0].length; j++) {
                                        if (tab1[j] == 3) {
                                            tab1[j] = 0;
                                        }
                                        if (tab1[j] == 4) {
                                            tab1[j] = 2;
                                        }
                                    }
                                }
                            }
                        }));
                        tempAnimation.setCycleCount(Timeline.INDEFINITE);
                        tempAnimation.play();

                    } else if ("frightened".equals(mode)) {
                        for (Ghost ghost : pac.p.List_Ghost) {
                            if (check_collision(pac, ghost)) {
                                Text s200 = new Text("200");
                                pac.p.score += 200;
                                timeline_animation.pause();
                                timeline.pause();
                                int[] temp = {30}; // Compteur pour les frames
                                Timeline tempAnimation = new Timeline();
                                tempAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), g -> {
                                    if (temp[0] > 0) {
                                        pac.p.drawShapes(gc, nb_de_joueur);
                                        gc.setFill(temp[0] % 4 == 0 ? Color.WHITE : Color.BLUE);
                                        gc.fillText("200", board_top_x + pac.x * width / this.tab[0].length + width / this.tab[0].length / 2 - s200.getBoundsInLocal().getWidth(),
                                                board_top_y + pac.y * height / this.tab.length + height / this.tab.length / 2 - s200.getBoundsInLocal().getHeight());
                                        pac.draw_vies(gc);
                                        for (Ghost other_ghost : pac.p.List_Ghost) {
                                            if (ghost != other_ghost) {
                                                other_ghost.draw_ghost_moving(gc);
                                            }
                                        }

                                        temp[0]--;
                                    } else {
                                        tempAnimation.stop(); // Arrêtez directement le Timeline
                                        timeline_animation.play();
                                        timeline.play();
                                        ghost.reset();
                                        pac.p.tab[ghost.y][ghost.x] = pac.p.tab[ghost.y][ghost.x] == 3? 0: 2;
                                    }
                                }));
                                tempAnimation.setCycleCount(Timeline.INDEFINITE);
                                tempAnimation.play();
                            }
                        }
                    }

                }
            }));
            timeline_animation.setCycleCount(50);
            timeline_animation.play();

            if ("frightened".equals(mode)) {
                if (t > 0) {
                    this.t--;
                } else {
                    this.mode = "normal";
                    for (Ghost ghost : pac.p.List_Ghost) {
                        if (ghost.getClass() == Blinky.class) {
                            ghost.couleur = new Color(1, 0, 0, 0.8);
                        } else if (ghost.getClass() == Clyde.class) {
                            ghost.couleur = new Color(1, 0.5, 0, 0.8);
                        } else if (ghost.getClass() == Pinky.class) {
                            ghost.couleur = new Color(0.966, 0.69, 0.95, 0.8);
                        } else if (ghost.getClass() == Inky.class) {
                            ghost.couleur = new Color(0.529, 1, 0.988, 0.8);
                        }
                    }
                    System.out.println("normal");
                }
            }
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

        this.game_over = true;
    }

    public void drawShapes(GraphicsContext gc, int nb_de_joueur) {
        // Remplir l'arrière-plan   
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, window_width, window_height);

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.rgb(0, 0, 255));
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                switch (tab[i][j]) {
                    case 1 -> {
                        gc.setLineWidth(1);
                        gc.setStroke(Color.rgb(0, 0, 255));
                        gc.strokeRect(board_top_x + width / tab[0].length * j, board_top_y + height / tab.length * i, width / tab[0].length, height / tab.length);
                    }
                    case 0, 3 -> {
                        gc.setFill(Color.WHITE);
                        gc.fillOval(board_top_x + width / tab[0].length * j + width / tab[0].length / 2 - 2.5, board_top_y + height / tab.length * i + height / tab.length / 2 - 2.5, 5, 5);
                    }
                    case 5 -> {
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(board_top_x + width / tab[0].length * j + width / tab[0].length / 2 - 7.5, board_top_y + height / tab.length * i + height / tab.length / 2 - 7.5, 15, 15);
                        gc.setStroke(Color.rgb(255, 255, 255));
                        gc.setLineWidth(4);
                        gc.strokeOval(board_top_x + width / tab[0].length * j + width / tab[0].length / 2 - 7.5, board_top_y + height / tab.length * i + height / tab.length / 2 - 7.5, 15, 15);
                    }
                    default -> {
                    }
                }
            }

        }
        draw_points(gc, score);
        draw_nb_joueur(gc, nb_de_joueur);
        //draw_ghost(gc, 300, 50);
        this.drawNumbers(gc);
    }

    public void drawNumbers(GraphicsContext gc) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                Text text = new Text("" + this.tab[i][j]);
                gc.fillText("" + this.tab[i][j], this.board_top_x + this.width / this.tab[0].length * j + this.width / this.tab[0].length / 2 - text.getBoundsInLocal().getWidth() / 1, this.board_top_y + this.height
                        / this.tab.length * i + this.height / this.tab.length / 2 + text.getBoundsInLocal().getHeight() / 2);

            }
        }
    }

    public void draw_nb_joueur(GraphicsContext gc, int nb_joueur) {
        String txt = nb_joueur == 1 ? nb_joueur + "  joueur" : nb_joueur + "  joueurs";
        Text text = new Text(txt);
        gc.fillText(txt, this.board_top_x + this.width / 4 - text.getBoundsInLocal().getWidth(), this.board_top_y - height / 25 - text.getBoundsInLocal().getHeight() / 2);
    }

    public void draw_points(GraphicsContext gc, int score) {
        gc.setStroke(Color.rgb(0, 0, 255));
        gc.strokeRect(board_top_x / 4, board_top_y / 4, 150, 50);
        Text text = new Text("Score : " + score);
        text.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText("Score : " + score, board_top_x / 4 + 150 / 10, board_top_y / 4 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);

    }

    public boolean check_collision(Pacman a, Ghost b) {
        return (abs(a.interpolatedX - b.interpolatedX) + abs(a.interpolatedY - b.interpolatedY)) < epsilon;
    }

    public void reset(Pacman a, Ghost b, Ghost c, Ghost d, Ghost e) {
        a = new Pacman();
        b = new Blinky();
        c = new Clyde();
        d = new Pinky();
        e = new Inky();
    }

}
