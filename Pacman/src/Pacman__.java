
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.Animation;
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
    public int score = 0;
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
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},// 9
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},// 10
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
    //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20

    public int[][] levels_phase = {
        // temp en ms de chaque phase pour chaque level
        // level  scatter     chase       scatter   chase      scatter    chase      scatter 
        {1, 7_000, 20_000, 7_000, 20_000, 5_000, 20_000, 5_000},
        {234, 7_000, 20_000, 7_000, 20_000, 5_000, 1_033_140, 10},
        {5 + 0, 5_000, 20_000, 5_000, 20_000, 5_000, 1_037_140, 10},};

    private int iterationCount = 0;
    double epsilon = 0.9;
    boolean game_over = false;
    Font customFont;
    String mode = "scatter";
    int t = 0;
    ArrayList<Ghost> List_Ghost = new ArrayList();
    ArrayList<Buffer_Input> Input_Buffer = new ArrayList();

    Timer timer = new Timer();

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
            this.customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/Jaro.ttf"), 30);
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

        timer.start();
        main_menu.setCycleCount(Timeline.INDEFINITE);
        main_menu.play();
        primaryStage.setScene(scene);
        primaryStage.show();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent e) -> {
            System.out.println("this.mode = " + pac.p.mode);
            if (pac.p.List_Ghost.get(0).couleur.equals(new Color(1, 0, 0, 0.8))) {
                for (Ghost g : pac.p.List_Ghost) {
                    g.previous_mode = pac.p.mode;
                }
                pac.p.mode = Determine_mode_based_on_time(timer.get_Time());
            }
            scene.setOnKeyPressed(event -> {
                String keyPressed = event.getText().isEmpty()
                        ? event.getCode().toString()
                        : event.getText();
                pac.p.Input_Buffer.add(new Buffer_Input(keyPressed));

            });
            for (Ghost g : pac.p.List_Ghost) {
                g.mode = pac.p.mode;
            }

            pac.update();

            for (Ghost g : pac.p.List_Ghost) {
                g.update(pac.coords, t, pac.facing, new Vector2(pac.p.List_Ghost.get(0).x, pac.p.List_Ghost.get(0).y));

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
                pac.p.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop);
                // pac.p.drawNumbers(gc);
                pac.draw_vies(gc);

                pac.interpolatedX = pac.previous_x + (pac.coords.x - pac.previous_x) * (iterationCount / 50.0);
                pac.interpolatedY = pac.previous_y + (pac.coords.y - pac.previous_y) * (iterationCount / 50.0);
                //System.out.println("1 - interpolatedY = " + pac.interpolatedY + " && interpolatedX = " + pac.interpolatedX);

                pac.draw_Pacman(gc);
                for (Ghost g : pac.p.List_Ghost) {
                    g.mode = pac.p.mode;
                }
                if (pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 0 || pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 3) {
                    pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                    pac.p.score += 10;
                } else if (pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] == 5) {
                    pac.p.tab[(int) pac.interpolatedY][(int) pac.interpolatedX] = 2;
                    pac.p.score += 50;
                    pac.p.mode = "frightened";
                    for (Ghost ghost : pac.p.List_Ghost) {
                        ghost.couleur = new Color(0, 0, 1, 1);
                    }
                    this.t = 15;
                    timer.Stop_Timer();
                }

                for (Ghost ghost : pac.p.List_Ghost) {
                    ghost.interpolatedX = ghost.previous_x + (ghost.x - ghost.previous_x) * (iterationCount / 50.0);
                    ghost.interpolatedY = ghost.previous_y + (ghost.y - ghost.previous_y) * (iterationCount / 50.0);
                    ghost.draw_ghost_moving(gc);
                }
                iterationCount++;
                boolean in_collision = false;
                for (Ghost ghost : pac.p.List_Ghost) {
                    if (check_collision(pac, ghost)) {
                        in_collision = true;
                    }
                }

                //for (Buffer_Input bi : pac.p.Input_Buffer) {
                //  System.out.println("bi.Input = " + bi.Input + " bi.Lifespan = " + bi.Lifespan);
                //}
                pac.direction_changed(pac.p.Input_Buffer, touches_joueur.get(0));
                pac.p.Input_Buffer.removeIf(bi -> !bi.decreament_lifespan()); // Enlève tout les élément dont le cycle de vie a atteint 0 & décrémente les autres cycles 
                if (in_collision) {
                    if ("chase".equals(pac.p.mode)) {

                        timeline_animation.pause();
                        timeline.pause();
                        int[] temp = {30};
                        Timeline tempAnimation = new Timeline();
                        tempAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.05), g -> {
                            if (temp[0] > 0) {
                                pac.p.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop);
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
                                if (pac.vies == 0) {
                                    timeline.stop();
                                    timeline_animation.stop();
                                    pac.p.update_Highscore();
                                    onGameEnd.run();
                                }
                            }
                        }));
                        tempAnimation.setCycleCount(Timeline.INDEFINITE);
                        tempAnimation.play();

                    } else if ("frightened".equals(pac.p.mode)) {
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
                                        pac.p.drawShapes(gc, nb_de_joueur, timer.get_Time(), timer.timeStop);
                                        gc.setFill(temp[0] % 4 == 0 ? Color.WHITE : Color.BLUE);
                                        gc.fillText("200", board_top_x + pac.coords.x * width / this.tab[0].length + width / this.tab[0].length / 2 - s200.getBoundsInLocal().getWidth(),
                                                board_top_y + pac.coords.y * height / this.tab.length + height / this.tab.length / 2 - s200.getBoundsInLocal().getHeight());
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
                                        pac.p.tab[ghost.y][ghost.x] = pac.p.tab[ghost.y][ghost.x] == 3 ? 0 : 2;
                                    }
                                }));
                                tempAnimation.setCycleCount(Timeline.INDEFINITE);
                                tempAnimation.play();
                            }
                        }
                    }

                }
                //System.out.println("iterationCount = " + iterationCount);
            }));
            timeline_animation.setCycleCount(50);
            timeline_animation.play();

            if ("frightened".equals(pac.p.mode)) {
                if (t > 0) {
                    this.t--;
                } else {
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

                    timer.start_again();
                }
            }

            if (pac.p.no_more_coins()) {
                timeline.stop();
                timeline_animation.stop();
                pac.p.update_Highscore();
                onGameEnd.run();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

        return true;
    }

    public void drawShapes(GraphicsContext gc, int nb_de_joueur, long duration, long timestop) {
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
        draw_high_score(gc);
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

    public void draw_points(GraphicsContext gc, int score) {
        gc.setStroke(Color.rgb(0, 0, 255));
        //gc.strokeRect(board_top_x / 4, board_top_y / 4, 150, 50);
        Text text = new Text("" + score);
        Text text2 = new Text("1UP");
        text.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText("Score : " + score, board_top_x / 4 + 150, board_top_y / 2 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);

        // Apparition/Disparition de 1UP à une cadence de 400ms pour obtenir le même effet que sur le jeu originel
        if ((timer.get_Time() % 800 < 400)) {
            gc.fillText("1UP", board_top_x / 4 + 150, board_top_y / 2 + text2.getBoundsInLocal().getHeight() / 4);
        }

    }

    public void draw_high_score(GraphicsContext gc) {
        gc.setStroke(Color.rgb(0, 0, 255));
        String cheminFichier = "./Users_Highscore/Highscores.txt";
        String[] parties = null;
        try {
            List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));
            parties = lignes.get(0).split(" ");
        } catch (IOException ex) {
            Logger.getLogger(Pacman__.class.getName()).log(Level.SEVERE, null, ex);
        }

        Text text = new Text("" + parties[1]);
        Text text2 = new Text("HIGH SCORE");
        gc.fillText("" + parties[1], board_top_x / 4 + 400 + text2.getBoundsInLocal().getWidth() - text.getBoundsInLocal().getWidth(), board_top_y / 2 + 50 / 2 + text.getBoundsInLocal().getHeight() / 4);
        gc.fillText("HIGH SCORE", board_top_x / 4 + 400, board_top_y / 2 + text2.getBoundsInLocal().getHeight() / 4);
    }

    public void draw_mode(GraphicsContext gc, long duration, long timestop) {
        if ("frightened".equals(this.mode)) {
            duration -= timestop;
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

    public void reset(Pacman a, Ghost b, Ghost c, Ghost d, Ghost e) {
        a = new Pacman();
        b = new Blinky();
        c = new Clyde();
        d = new Pinky();
        e = new Inky();
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

    public void update_Highscore() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateFormatee = LocalDateTime.now().format(formatter);

        String nomUtilisateur = System.getProperty("user.name");

        String cheminFichier = "./Users_Highscore/Highscores.txt";
        String contenu = "- " + this.score + " par " + nomUtilisateur + " réalisé le : " + dateFormatee;

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
