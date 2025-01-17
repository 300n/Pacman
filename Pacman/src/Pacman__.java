
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
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
    public int[][] tab = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},// 0
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},// 1
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},// 2
        {1, 5, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 5, 1},// 3
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},// 4
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},// 5
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},// 6
        {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},// 7
        {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1},// 8
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 2, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},// 9
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0},// 10
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},// 11
        {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1},// 12
        {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},// 13
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},// 14
        {1, 5, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 5, 1},// 15
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},// 16
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},// 17
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},// 18
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},// 19
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};// 20
    //   0  1  0  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20

    public int[][] levels_phase = {
        // temp en ms de chaque phase pour chaque level
        // level  scatter     chase       scatter   chase      scatter    chase      scatter 
        {1, 7_000, 20_000, 7_000, 20_000, 5_000, 20_000, 5_000},
        {234, 7_000, 20_000, 7_000, 20_000, 5_000, 1_033_140, 10},
        {5 + 0, 5_000, 20_000, 5_000, 20_000, 5_000, 1_037_140, 10},};

    private int iterationCount = 0;
    double epsilon = 0.9;
    Font customFont;
    String mode = "scatter";
    int t = -1;
    ArrayList<Ghost> List_Ghost = new ArrayList();
    ArrayList<ArrayList<Buffer_Input>> Input_Buffer = new ArrayList<>();
    Color[] Pacman_colors = {
        new Color(1, 1, 0, 1),
        new Color(0, 1, 0, 1),
        new Color(1, 1, 1, 1),
        new Color(0.50, 0.50, 0.50, 1)
    };
    Timer timer = new Timer();
    Vector2[] x_y_pacs = {new Vector2(1, 1), new Vector2(19, 19), new Vector2(1, 19), new Vector2(19, 1)};

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

    public boolean start(Stage primaryStage, int nb_de_joueur, ArrayList<Touches_joueur> touches_joueur, Runnable onGameEnd) {
        // Créer un Canvas
        for (int i = 0; i < nb_de_joueur; i++) {
            Input_Buffer.add(new ArrayList<>());
        }

        Canvas canvas = new Canvas(window_width, window_height);
        ArrayList<Pacman> pac_List = new ArrayList();
        for (int i = 0; i < nb_de_joueur; i++) {
            this.List_Ghost.add(new Blinky(90, 28));
            this.List_Ghost.add(new Clyde(90, 28));
            this.List_Ghost.add(new Pinky(90, 28));
            this.List_Ghost.add(new Inky(90, 28));
            pac_List.add(new Pacman(x_y_pacs[i].x, x_y_pacs[i].y, this.Pacman_colors[i]));
        }
        System.out.println("pac_List.size() = " + pac_List.size());
        // Obtenir le GraphicsContext pour dessiner
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ajouter le Canvas dans un conteneur
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.window_width = newValue.intValue();
        });

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.window_height = newValue.intValue();
        });
        // Configurer la scène
        Scene scene = new Scene(root, window_width, window_height);

        try {
            this.customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 30);
            if (customFont == null) {
                throw new Exception("Font could not be loaded. Ensure the file path is correct.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load custom font: " + e.getMessage());
            this.customFont = Font.font("Arial", 20);
        }
        gc.setFont(this.customFont);

        timer.start();
        primaryStage.setScene(scene);
        primaryStage.show();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent e) -> {

            if (this.t < 0) {
                for (Ghost g : this.List_Ghost) {
                    g.previous_mode = this.mode;
                }
                this.mode = Determine_mode_based_on_time(timer.get_Time());
                for (Ghost g : this.List_Ghost) {
                    if (!"eaten".equals(g.mode)) {
                        g.mode = this.mode;
                    }
                }
            }

            scene.setOnKeyPressed(event -> {
                String keyPressed = event.getText().isEmpty()
                        ? event.getCode().toString()
                        : event.getText();

                if (keyPressed.equals(touches_joueur.get(0).mooves.get(0))
                        || keyPressed.equals(touches_joueur.get(0).mooves.get(1))
                        || keyPressed.equals(touches_joueur.get(0).mooves.get(2))
                        || keyPressed.equals(touches_joueur.get(0).mooves.get(3))) {
                    this.Input_Buffer.get(0).add(new Buffer_Input(keyPressed));
                } else if (nb_de_joueur > 1 && (keyPressed.equals(touches_joueur.get(1).mooves.get(0))
                        || keyPressed.equals(touches_joueur.get(1).mooves.get(1))
                        || keyPressed.equals(touches_joueur.get(1).mooves.get(2))
                        || keyPressed.equals(touches_joueur.get(1).mooves.get(3)))) {
                    this.Input_Buffer.get(1).add(new Buffer_Input(keyPressed));
                } else if (nb_de_joueur > 2 && (keyPressed.equals(touches_joueur.get(2).mooves.get(0))
                        || keyPressed.equals(touches_joueur.get(2).mooves.get(1))
                        || keyPressed.equals(touches_joueur.get(2).mooves.get(2))
                        || keyPressed.equals(touches_joueur.get(2).mooves.get(3)))) {
                    this.Input_Buffer.get(2).add(new Buffer_Input(keyPressed));
                } else if (nb_de_joueur > 3 && (keyPressed.equals(touches_joueur.get(3).mooves.get(0))
                        || keyPressed.equals(touches_joueur.get(3).mooves.get(1))
                        || keyPressed.equals(touches_joueur.get(3).mooves.get(2))
                        || keyPressed.equals(touches_joueur.get(3).mooves.get(3)))) {
                    this.Input_Buffer.get(3).add(new Buffer_Input(keyPressed));
                }
            });

            // On met à jour la position de(s) pacman(s)
            for (Pacman pacman : pac_List) {
                pacman.update();
            }

            for (int i = 0; i < this.List_Ghost.size(); i++) {
                Ghost ghost = this.List_Ghost.get(i);
                Pacman pac = pac_List.get((int) i / 4);
                if ("eaten".equals(ghost.mode)) {
                    // Dans le cas où le fantôme a déjà été mangé on reset le tableau d'animation des yeux ainsi que le compteur
                    if (!ghost.next_positions_while_eaten.isEmpty()) {
                        ghost.next_positions_while_eaten.clear();
                        ghost.animation_eating_count = 1;
                    }

                    for (int j = 0; j < 2; j++) {
                        ghost.next_positions_while_eaten.add(new Vector2(ghost.x, ghost.y));
                        ghost.update(pac.coords, t, pac.facing, new Vector2(this.List_Ghost.get(0).x, this.List_Ghost.get(0).y), this.mode);
                    }
                } else {
                    ghost.update(pac.coords, t, pac.facing, new Vector2(this.List_Ghost.get(0).x, this.List_Ghost.get(0).y), this.mode);
                }
                this.tab[ghost.y][ghost.x] = (this.tab[ghost.y][ghost.x] == 0) ? 3 : (this.tab[ghost.y][ghost.x] == 2 ? 4 : this.tab[ghost.y][ghost.x]);
                this.tab[ghost.previous_y][ghost.previous_x] = (this.tab[ghost.previous_y][ghost.previous_x] == 3) ? 0
                        : (this.tab[ghost.previous_y][ghost.previous_x] == 4 ? 2 : this.tab[ghost.previous_y][ghost.previous_x]);
            }

            iterationCount = 0;
            for (Pacman pac : pac_List) {
                pac.mouthOpening[0] = true;
                pac.mouthAngle[0] = 0;
            }

            Timeline timeline_animation = new Timeline();
            timeline_animation.getKeyFrames().add(new KeyFrame(Duration.millis(10), d -> {
                this.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop_f, pac_List);

                for (int i = 0; i < pac_List.size(); i++) {
                    Pacman pac = pac_List.get(i);
                    pac.draw_vies(gc, i);
                    pac.interpolatedX = pac.previous_x + (pac.coords.x - pac.previous_x) * (iterationCount / 50.0);
                    pac.interpolatedY = pac.previous_y + (pac.coords.y - pac.previous_y) * (iterationCount / 50.0);
                    pac.draw_Pacman(gc);

                    if (this.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 0 || this.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 3) {
                        this.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                        pac.score += 10;
                    } else if (this.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 5) {
                        this.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                        pac.score += 50;
                        for (Ghost ghost : this.List_Ghost) {
                            ghost.couleur = new Color(0, 0, 1, 1);
                            ghost.mode = "frightened";
                        }
                        this.t = 15;
                        pac.point_ghost = 200;
                        timer.Stop_Timer();
                    }
                }

                for (Ghost ghost : this.List_Ghost) {
                    if ("frightened".equals(ghost.mode) && this.t < 5 && this.t != -1) {
                        if (this.timer.get_Time() % 400 < 200) {
                            ghost.couleur = new Color(0.9, 0.9, 0.9, 1);

                        } else {
                            ghost.couleur = new Color(0, 0, 1, 1);

                        }
                    }
                }

                for (Ghost ghost : this.List_Ghost) {
                    if ("eaten".equals(ghost.mode) && !ghost.next_positions_while_eaten.isEmpty()) {
                        for (int i = 1; i < 3; i++) {
                            if (ghost.animation_eating_count <= 50) {
                                ghost.interpolatedX = ghost.next_positions_while_eaten.get(0).x + (ghost.next_positions_while_eaten.get(1).x - ghost.next_positions_while_eaten.get(0).x) * (ghost.animation_eating_count / 50.0);
                                ghost.interpolatedY = ghost.next_positions_while_eaten.get(0).y + (ghost.next_positions_while_eaten.get(1).y - ghost.next_positions_while_eaten.get(0).y) * (ghost.animation_eating_count / 50.0);
                            } else if (ghost.animation_eating_count <= 101) {
                                ghost.interpolatedX = ghost.next_positions_while_eaten.get(1).x + (ghost.x - ghost.next_positions_while_eaten.get(1).x) * ((ghost.animation_eating_count - 50) / 50.0);
                                ghost.interpolatedY = ghost.next_positions_while_eaten.get(1).y + (ghost.y - ghost.next_positions_while_eaten.get(1).y) * ((ghost.animation_eating_count - 50) / 50.0);
                            }
                            ghost.draw_ghost_moving(gc);
                            ghost.animation_eating_count++;
                        }
                    } else {
                        ghost.interpolatedX = ghost.previous_x + (ghost.x - ghost.previous_x) * (iterationCount / 50.0);
                        ghost.interpolatedY = ghost.previous_y + (ghost.y - ghost.previous_y) * (iterationCount / 50.0);
                        ghost.draw_ghost_moving(gc);
                    }
                }
                iterationCount++;

                boolean[] in_collision = new boolean[4];
                for (int i = 0; i < pac_List.size(); i++) {
                    Pacman pac = pac_List.get(i);
                    boolean in_collision_with_pac = false;
                    for (Ghost ghost : this.List_Ghost) {
                        if (check_collision(pac, ghost)) {
                            in_collision_with_pac = true;
                        }
                    }
                    in_collision[i] = in_collision_with_pac;
                }
                for (int i = 0; i < pac_List.size(); i++) {
                    Pacman pac = pac_List.get(i);
                    pac.direction_changed(this.Input_Buffer.get(i), touches_joueur.get(i));
                    this.Input_Buffer.get(i).removeIf(bi -> !bi.decreament_lifespan()); // Enlève tout les élément dont le cycle de vie a atteint 0 & décrémente les autres cycles 

                }

                for (int i = 0; i < pac_List.size(); i++) {
                    Pacman pac = pac_List.get(i);
                    if (in_collision[i]) {
                        for (Ghost ghost : this.List_Ghost) {
                            if (check_collision(pac, ghost)) {
                                if ("chase".equals(ghost.mode) || "scatter".equals(ghost.mode)) {
                                    timeline_animation.pause();
                                    timeline.pause();
                                    int[] temp = {30};
                                    int k = i;
                                    Timeline tempAnimation = new Timeline();
                                    tempAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), g -> {
                                        if (temp[0] > 0) {
                                            this.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop_f, pac_List);
                                            pac.draw_vies(gc, k);
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
                                            for (int j = 0; j < this.List_Ghost.size(); j++) {
                                                if (j >= (4 * k) && j < (4 * (k + 1))) {
                                                    this.List_Ghost.get(j).reset();
                                                }
                                            }
                                            for (int[] tab1 : this.tab) {
                                                for (int j = 0; j < this.tab[0].length; j++) {
                                                    if (tab1[j] == 3) {
                                                        tab1[j] = 0;
                                                    }
                                                    if (tab1[j] == 4) {
                                                        tab1[j] = 2;
                                                    }
                                                }
                                            }
                                            if (pac.vies == 0) {
                                                timeline.stop();
                                                timeline_animation.stop();
                                                int max = -1;
                                                int index_winner = 0;
                                                for (int l = 0; l < pac_List.size(); l++) {
                                                    Pacman otherpac = pac_List.get(l);
                                                    if (otherpac != pac) {
                                                        if (Math.max(max, otherpac.score) == otherpac.score) {
                                                            index_winner = l;
                                                        }
                                                        max = Math.max(max, otherpac.score);
                                                    }
                                                }
                                                pac.p.update_Highscore(max);

                                                Timeline winnerAnimation = new Timeline();
                                                int[] temp2 = {50}; // Compteur pour les frames
                                                int l = index_winner;
                                                winnerAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), h -> {
                                                    Text t = null;
                                                    if (nb_de_joueur == 1) {
                                                        t = new Text("Game over");
                                                    } else {
                                                        t = new Text("Pacman " + l + " remporte la partie");
                                                    }
                                                    t.setFont(customFont);
                                                    if (temp2[0] > 0) {
                                                        this.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop_f, pac_List);
                                                        pac.draw_vies(gc, k);
                                                        for (Ghost other_ghost : pac.p.List_Ghost) {
                                                            if (ghost != other_ghost) {
                                                                other_ghost.draw_ghost_moving(gc);
                                                            }
                                                        }
                                                        if (temp2[0] % 5 == 0) {
                                                            gc.setFill(Color.WHITE);
                                                        } else {
                                                            if (nb_de_joueur == 1) {
                                                                gc.setFill(Color.RED);
                                                            } else {
                                                                gc.setFill(pac_List.get(l).pacman_color);
                                                            }
                                                        }
                                                        if (nb_de_joueur == 1) {
                                                            gc.fillText("Game over", width / 2 + t.getBoundsInLocal().getWidth() / 4, height / 2 + t.getBoundsInLocal().getHeight());
                                                        } else {
                                                            gc.fillText("Pacman " + (l + 1) + " remporte la partie", width / 2 - t.getBoundsInLocal().getWidth() / 5, height / 2 + t.getBoundsInLocal().getHeight());
                                                        }
                                                        temp2[0]--;
                                                    } else {
                                                        winnerAnimation.stop();
                                                        onGameEnd.run();
                                                    }

                                                }));
                                                winnerAnimation.setCycleCount(Timeline.INDEFINITE);
                                                winnerAnimation.play();

                                            }
                                        }
                                    }));
                                    tempAnimation.setCycleCount(Timeline.INDEFINITE);
                                    tempAnimation.play();
                                    break;
                                } else if ("frightened".equals(ghost.mode)) {
                                    int k = i;
                                    Text s200 = new Text("" + pac.point_ghost);
                                    pac.score += pac.point_ghost;
                                    timeline_animation.pause();
                                    timeline.pause();
                                    int[] temp = {30}; // Compteur pour les frames
                                    Timeline tempAnimation = new Timeline();
                                    tempAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), g -> {
                                        if (temp[0] > 0) {
                                            this.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop_f, pac_List);
                                            gc.setFill(temp[0] % 4 == 0 ? Color.WHITE : Color.BLUE);
                                            gc.fillText("" + pac.point_ghost, board_top_x + pac.coords.x * width / this.tab[0].length + width / this.tab[0].length / 2 - s200.getBoundsInLocal().getWidth(),
                                                    board_top_y + pac.coords.y * height / this.tab.length + height / this.tab.length / 2 - s200.getBoundsInLocal().getHeight());
                                            pac.draw_vies(gc, k);
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
                                            pac.point_ghost *= 2;
                                            ghost.mode = "eaten";
                                            if (ghost.getClass() == Blinky.class) {
                                                ghost.couleur = new Color(1, 0, 0, 0.8);
                                            } else if (ghost.getClass() == Clyde.class) {
                                                ghost.couleur = new Color(1, 0.5, 0, 0.8);
                                            } else if (ghost.getClass() == Pinky.class) {
                                                ghost.couleur = new Color(0.966, 0.69, 0.95, 0.8);
                                            } else if (ghost.getClass() == Inky.class) {
                                                ghost.couleur = new Color(0.529, 1, 0.988, 0.8);
                                            }
                                            pac.p.tab[ghost.y][ghost.x] = pac.p.tab[ghost.y][ghost.x] == 3 ? 0 : 2;
                                        }
                                    }));
                                    tempAnimation.setCycleCount(Timeline.INDEFINITE);
                                    tempAnimation.play();
                                }

                            }

                        }
                    }
                }
            }));
            timeline_animation.setCycleCount(50);
            timeline_animation.play();

            boolean at_least_one_ghost_frightened = false;
            for (Ghost ghost : this.List_Ghost) {
                at_least_one_ghost_frightened |= "frightened".equals(ghost.mode);
            }

            if (at_least_one_ghost_frightened) {
                if (t > 0) {
                    this.t--;
                } else {
                    for (Ghost ghost : this.List_Ghost) {
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
                    this.t--;
                    timer.start_again();
                    for (Pacman pac : pac_List) {
                        pac.point_ghost = 200;
                    }
                }
            } else if (t > 0) {
                for (Ghost ghost : this.List_Ghost) {
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
                timer.start_again();
                this.t = -1;

            }

            if (this.no_more_coins()) {
                timeline.stop();
                timeline_animation.stop();
                int max = -1;
                int index_winner = -1;
                for (int i = 0; i < pac_List.size(); i++) {
                    Pacman pac = pac_List.get(i);
                    if (Math.max(max, pac.score) == pac.score) {
                        index_winner = i;
                    }
                    max = Math.max(max, pac.score);
                }
                this.update_Highscore(max);
                Pacman pac = pac_List.get(index_winner);
                Timeline winnerAnimation = new Timeline();
                int[] temp2 = {50}; // Compteur pour les frames
                int l = index_winner;
                winnerAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), h -> {
                    Text t = null;
                    if (nb_de_joueur == 1) {
                        t = new Text("Victoire!");
                    } else {
                        t = new Text("Pacman " + l + " remporte la partie");
                    }
                    t.setFont(customFont);
                    if (temp2[0] > 0) {
                        this.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop_f, pac_List);
                        pac.draw_vies(gc, l);
                        for (Ghost ghost : pac.p.List_Ghost) {
                            ghost.draw_ghost_moving(gc);
                        }
                        if (temp2[0] % 5 == 0) {
                            gc.setFill(Color.WHITE);
                        } else {
                            gc.setFill(pac_List.get(l).pacman_color);
                        }
                        if (nb_de_joueur == 1) {
                            gc.fillText("Victoire!", width / 2 + t.getBoundsInLocal().getWidth() / 2, height / 2 + t.getBoundsInLocal().getHeight());
                        } else {
                            gc.fillText("Pacman " + (l + 1) + " remporte la partie", width / 2 - t.getBoundsInLocal().getWidth() / 5, height / 2 + t.getBoundsInLocal().getHeight());
                        }
                        temp2[0]--;
                    } else {
                        winnerAnimation.stop();
                        onGameEnd.run();
                    }

                }));
                winnerAnimation.setCycleCount(Timeline.INDEFINITE);
                winnerAnimation.play();

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

        return true;
    }

    public void drawShapes(GraphicsContext gc, int nb_de_joueur, long duration, long timestop, ArrayList<Pacman> pac_List) {
        // Remplir l'arrière-plan   
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.window_width, this.window_height);
        gc.setFill(Color.DARKSALMON);
        gc.fillRect(board_top_x + width / tab[0].length * 10, board_top_y + height / tab.length * 9 + height / tab.length / 2.5, width / tab[0].length, height / tab.length / 5);
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
        draw_points(gc, pac_List);
        draw_high_score(gc, pac_List);
        draw_mode(gc, duration, timestop);
        //draw_nb_joueur(gc, nb_de_joueur);
        //draw_ghost(gc, 300, 50);
        //this.drawNumbers(gc);
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

    public void draw_points(GraphicsContext gc, ArrayList<Pacman> pac_List) {
        gc.setStroke(Color.rgb(0, 0, 255));
        //gc.strokeRect(board_top_x / 4, board_top_y / 4, 150, 50);

        Text text2 = new Text("1UP");
        for (int i = 0; i < pac_List.size(); i++) {
            Pacman pac = pac_List.get(i);
            Text text = new Text("" + pac.score);
            text.setFont(javafx.scene.text.Font.font("Arial", 20));
            gc.setFill(pac.pacman_color);
            gc.fillText("Score : " + pac.score, board_top_x / 4 + 100 + (i % 2 == 1 ? 175 : 0), board_top_y / 2 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4 + (i > 1 ? 40 : 0));
        }

        // Apparition/Disparition de 1UP à une cadence de 400ms pour obtenir le même effet que sur le jeu originel
        if ((timer.get_Time() % 800 < 400)) {
            gc.setFill(Color.WHITE);
            gc.fillText("1UP", board_top_x / 4 + 100, board_top_y / 2 + text2.getBoundsInLocal().getHeight() / 4);
        }

    }

    public void draw_high_score(GraphicsContext gc, ArrayList<Pacman> pac_List) {
        gc.setStroke(Color.rgb(0, 0, 255));
        gc.setFill(Color.WHITE);
        String cheminFichier = "./Users_Highscore/Highscores3.txt";
        String[] parties = null;

        try {
            List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));
            parties = lignes.get(0).split(" ");
        } catch (IOException ex) {
            Logger.getLogger(Pacman__.class.getName()).log(Level.SEVERE, null, ex);
        }
        int score = Integer.parseInt(parties[1]);
        for (Pacman pac : pac_List) {
            score = Math.max(pac.score, score);
        }
        Text text = new Text("" + score);
        Text text2 = new Text("HIGH SCORE");
        gc.fillText("" + score, board_top_x / 4 + 400 + text2.getBoundsInLocal().getWidth() - text.getBoundsInLocal().getWidth(), board_top_y / 2 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);
        gc.fillText("HIGH SCORE", board_top_x / 4 + 400, board_top_y / 2 + text2.getBoundsInLocal().getHeight() / 4);
    }

    public void draw_mode(GraphicsContext gc, long duration, long timestop) {
        gc.setFill(Color.WHITE);
        boolean at_least_one_ghost_frightened = false;
        for (Ghost ghost : this.List_Ghost) {
            at_least_one_ghost_frightened |= "frightened".equals(ghost.mode);
        }
        if (at_least_one_ghost_frightened) {
            duration = timestop;
        }

        long total_time_cycle = 0;
        for (int i = 1; i < 8; i++) {
            total_time_cycle += levels_phase[0][i];
        }
        duration %= total_time_cycle;
        int k = 0;
        while (duration - levels_phase[0][k + 1] > 0) {
            duration -= levels_phase[0][k + 1];
            k++;
        }
        duration = (long) Math.floor((double) (levels_phase[0][k + 1] - duration) / 10);

        Text text2 = new Text(this.mode);
        Text text = new Text("" + duration);
        gc.fillText("" + duration, board_top_x / 4 + 700 + text2.getBoundsInLocal().getWidth() - text.getBoundsInLocal().getWidth(), board_top_y / 2 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);
        gc.fillText(this.mode.toUpperCase(), board_top_x / 4 + 700, board_top_y / 2 + text2.getBoundsInLocal().getHeight() / 4);
    }

    public boolean check_collision(Pacman a, Ghost b) {
        return (abs(a.interpolatedX - b.interpolatedX) + abs(a.interpolatedY - b.interpolatedY)) < epsilon;
    }

    public String Determine_mode_based_on_time(long duration) {
        long total_time_cycle = 0;
        for (int i = 1; i < 8; i++) {
            total_time_cycle += levels_phase[0][i];
        }
        duration %= total_time_cycle;
        int i = 0;
        while (duration > 0) {
            duration -= levels_phase[0][i + 1];
            i++;
        }
        if (i % 2 == 0) {
            return "chase";
        } else {
            return "scatter";
        }
    }

    public boolean no_more_coins() {
        boolean No_more_coins = true;
        for (int i = 0; i < this.tab.length; i++) {
            for (int j = 0; j < this.tab[0].length; j++) {
                No_more_coins &= this.tab[i][j] != 0;
                if (!(No_more_coins)) {
                    break;
                }
            }
        }
        return No_more_coins;
    }

    public void update_Highscore(int score) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateFormatee = LocalDateTime.now().format(formatter);

        String nomUtilisateur = System.getProperty("user.name");

        String cheminFichier = "./Users_Highscore/Highscores3.txt";
        String contenu = "- " + score + " par " + nomUtilisateur + " réalisé le : " + dateFormatee;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier, true))) {
            writer.write(contenu);
            writer.newLine(); // Ajoute une nouvelle ligne
            System.out.println("Écriture terminée avec succès !");
        } catch (IOException l) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + l.getMessage());
        }

        try {
            List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));
            List<String> lignesTriees = lignes.stream()
                    .sorted((ligne1, ligne2) -> {
                        // Extraire les scores des deux lignes
                        int score1 = extraireScore(ligne1);
                        int score2 = extraireScore(ligne2);

                        // Comparer les scores de manière décroissante
                        return Integer.compare(score2, score1);
                    })
                    .collect(Collectors.toList());
            Files.write(Paths.get(cheminFichier), lignesTriees);
            System.out.println("Fichier trié avec succès !");

        } catch (IOException ex) {
            Logger.getLogger(Pacman__.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
