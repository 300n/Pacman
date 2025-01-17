
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public abstract class Ghost {

    int x, y, facing, previous_x, previous_y;
    Color couleur;
    double speed = 1;
    Pacman__ p = new Pacman__();
    int radius = 28;
    double interpolatedX, interpolatedY;

    boolean mooving = false;
    String mode, previous_mode;
    int animation_eating_count = 1;

    ArrayList<Vector2> next_positions_while_eaten = new ArrayList();

    public Ghost(int x, int y, Color couleur, int facing, int radius) {
        this.x = x;
        this.y = y;
        this.facing = 90;
        this.couleur = couleur;
        this.facing = facing;
        this.radius = radius;
    }

    public void update_randomly(int[][] tab) {
        this.previous_x = x;
        this.previous_y = y;
        ArrayList<int[]> moves_available = new ArrayList<>();
        if (p.tab[this.y][this.x + 1] == 0 || p.tab[this.y][this.x + 1] == 2) {
            moves_available.add(new int[]{0, 1});
        }
        if (p.tab[this.y][this.x - 1] == 0 || p.tab[this.y][this.x - 1] == 2) {
            moves_available.add(new int[]{0, -1});
        }
        if (p.tab[this.y + 1][this.x] == 0 || p.tab[this.y + 1][this.x] == 2) {
            moves_available.add(new int[]{1, 0});
        }
        if (p.tab[this.y - 1][this.x] == 0 || p.tab[this.y - 1][this.x] == 2) {
            moves_available.add(new int[]{-1, 0});
        }
        Random r = new Random();
        int[] move = moves_available.get(r.nextInt(moves_available.size()));
        if ((tab[this.y + move[0]][this.x + move[1]] != 3 && tab[this.y + move[0]][this.x + move[1]] != 4)) {
            this.x += move[1];
            this.y += move[0];
        }
    }

    public void draw_ghost(GraphicsContext gc, int scale) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        int top_left_corner_x = (int) (p.board_top_x + p.width / p.tab[0].length * this.x
                + p.width / p.tab[0].length / 2 - radius / 2);
        int top_left_corner_y = (int) (p.board_top_y + p.height / p.tab.length * this.y
                + p.height / p.tab.length / 2 - radius / 2);
        int[] offsets = {
            (int) ((radius / 2 - radius / 14) * scale), (int) ((radius / 2 - radius / 14) * scale),
            (int) ((radius / 4 - radius / 28) * scale), (int) ((radius / 4 - radius / 28) * scale),
            (int) ((radius / 7) * scale), (int) ((radius / 7) * scale),
            (int) ((radius / 14) * scale), (int) ((radius / 14) * scale),
            (int) ((radius / 14) * scale), (int) ((radius / 14) * scale),
            (int) ((0) * scale), (int) ((0) * scale),
            (int) ((0) * scale), (int) ((0) * scale),
            (int) ((0) * scale), (int) ((0) * scale),
            (int) ((radius / 14) * scale), (int) ((radius / 14) * scale),
            (int) ((radius / 14) * scale), (int) ((radius / 14) * scale),
            (int) ((radius / 7) * scale), (int) ((radius / 7) * scale),
            (int) ((radius / 4 - radius / 28) * scale), (int) ((radius / 4 - radius / 28) * scale),
            (int) ((radius / 2 - radius / 14) * scale), (int) ((radius / 2 - radius / 14) * scale)
        };

        int[] ranges = {
            (int) ((radius / 2 + radius / 14 + 1) * scale), (int) ((radius / 2 + radius / 14 + 1) * scale),
            (int) ((radius * 3 / 4) * scale), (int) ((radius * 3 / 4) * scale),
            (int) ((radius * 3 / 4) * scale), (int) ((radius * 3 / 4) * scale),
            (int) ((radius * 3 / 4 + radius / 7) * scale), (int) ((radius * 3 / 4 + radius / 7) * scale),
            (int) ((radius - 1) * scale), (int) ((radius - 1) * scale),
            (int) ((radius + 1) * scale), (int) ((radius + 1) * scale),
            (int) ((radius * 3 / 4 + radius / 7) * scale), (int) ((radius * 3 / 4 + radius / 7) * scale),
            (int) ((radius * 3 / 4 + radius / 7) * scale), (int) ((radius * 3 / 4 + radius / 7) * scale),
            (int) ((radius + 1) * scale), (int) ((radius + 1) * scale),
            (int) ((radius - 1) * scale), (int) ((radius - 1) * scale),
            (int) ((radius * 3 / 4 + radius / 7) * scale), (int) ((radius * 3 / 4 + radius / 7) * scale),
            (int) ((radius * 3 / 4) * scale), (int) ((radius * 3 / 4) * scale),
            (int) ((radius * 3 / 4) * scale), (int) ((radius * 3 / 4) * scale),
            (int) ((radius / 2 + radius / 14 + 1) * scale), (int) ((radius / 2 + radius / 14 + 1) * scale)
        };

        if (!"eaten".equals(this.mode)) {
            for (int col = 0; col < offsets.length; col++) {
                for (int i = 1; i < ranges[col]; i++) {
                    // Répéter chaque pixel horizontal5ement selon le facteur d'échelle
                    for (int x_scale = 0; x_scale < scale; x_scale++) {

                        pixelWriter.setColor(top_left_corner_x + col * (int) scale + x_scale,
                                top_left_corner_y + offsets[col] + i,
                                this.couleur);

                    }
                }
            }
        }

        int direction = 0;
        if (this.x > this.previous_x) {
            direction = 0; // Droite
        } else if (this.x < this.previous_x) {
            direction = 180; // Gauche
        } else if (this.y > this.previous_y) {
            direction = 270; // Bas
        } else if (this.y < this.previous_y) {
            direction = 90; // Haut
        }
        switch (this.facing) {
            case 0 ->
                draw_ghost_eyes_looking_left(gc, top_left_corner_x, top_left_corner_y, scale);
            case 180 ->
                draw_ghost_eyes_looking_right(gc, top_left_corner_x, top_left_corner_y, scale);
            case 270 ->
                draw_ghost_eyes_looking_down(gc, top_left_corner_x, top_left_corner_y, scale);
            default ->
                draw_ghost_eyes_looking_up(gc, top_left_corner_x, top_left_corner_y, scale);
        }
    }

    public void draw_ghost_moving(GraphicsContext gc) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        int top_left_corner_x = (int) (p.board_top_x + p.width / p.tab[0].length * this.interpolatedX
                + p.width / p.tab[0].length / 2 - radius / 2);
        int top_left_corner_y = (int) (p.board_top_y + p.height / p.tab.length * this.interpolatedY
                + p.height / p.tab.length / 2 - radius / 2);
        int[] offsets = {radius / 2 - radius / 14, radius / 2 - radius / 14, radius / 4 - radius / 28, radius / 4 - radius / 28, radius / 7, radius / 7, radius / 14, radius / 14, radius / 14, radius / 14,
            0, 0, 0, 0, 0, 0, 0, 0, radius / 14, radius / 14, radius / 14, radius / 14, radius / 7, radius / 7, radius / 4 - radius / 28, radius / 4 - radius / 28, radius / 2 - radius / 14, radius / 2 - radius / 14};
        int[] ranges = {radius / 2 + radius / 14 + 1, radius / 2 + radius / 14 + 1, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius - 1, radius - 1, radius + 1,
            radius + 1, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius + 1, radius + 1, radius - 1, radius - 1, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7,
            radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius / 2 + radius / 14 + 1, radius / 2 + radius / 14 + 1};

        if (!("eaten".equals(this.mode))) {
            for (int col = 0; col < offsets.length; col++) {
                for (int i = 1; i < ranges[col]; i++) {
                    pixelWriter.setColor((int) top_left_corner_x + col, (int) top_left_corner_y + offsets[col] + i, this.couleur);
                }
            }
        }
        int direction = 0;
        if (this.x > this.previous_x) {
            direction = 0; // Droite
        } else if (this.x < this.previous_x) {
            direction = 180; // Gauche
        } else if (this.y > this.previous_y) {
            direction = 270; // Bas
        } else if (this.y < this.previous_y) {
            direction = 90; // Haut
        }
        switch (direction) {
            case 0 ->
                draw_ghost_eyes_looking_left(gc, (int) top_left_corner_x, (int) top_left_corner_y, 1);
            case 180 ->
                draw_ghost_eyes_looking_right(gc, (int) top_left_corner_x, (int) top_left_corner_y, 1);
            case 270 ->
                draw_ghost_eyes_looking_down(gc, (int) top_left_corner_x, (int) top_left_corner_y, 1);
            default ->
                draw_ghost_eyes_looking_up(gc, (int) top_left_corner_x, (int) top_left_corner_y, 1);
        }
    }

    public void draw_ghost_eyes_looking_left(GraphicsContext gc, int x, int y, int scale) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 8 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.fillRect(x + 18 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 20 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 10 * scale, y + 8 * scale, 4 * scale, 4 * scale);
        gc.fillRect(x + 22 * scale, y + 8 * scale, 4 * scale, 4 * scale);
    }

    public void draw_ghost_eyes_looking_right(GraphicsContext gc, int x, int y, int scale) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 2 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 4 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.fillRect(x + 14 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 16 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 2 * scale, y + 8 * scale, 4 * scale, 4 * scale);
        gc.fillRect(x + 14 * scale, y + 8 * scale, 4 * scale, 4 * scale);
    }

    public void draw_ghost_eyes_looking_up(GraphicsContext gc, int x, int y, int scale) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 8 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.fillRect(x + 14 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 16 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 8 * scale, y + 6 * scale, 4 * scale, 4 * scale);
        gc.fillRect(x + 16 * scale, y + 6 * scale, 4 * scale, 4 * scale);
    }

    public void draw_ghost_eyes_looking_down(GraphicsContext gc, int x, int y, int scale) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 8 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.fillRect(x + 14 * scale, y + 8 * scale, 8 * scale, 4 * scale);
        gc.fillRect(x + 16 * scale, y + 6 * scale, 4 * scale, 8 * scale);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 8 * scale, y + 10 * scale, 4 * scale, 4 * scale);
        gc.fillRect(x + 16 * scale, y + 10 * scale, 4 * scale, 4 * scale);
    }

    public abstract void reset();

    public double norme_2(Vector2 pac_coords, Vector2 next_cords) {
        double dx = pac_coords.x - (this.x + next_cords.x);
        double dy = pac_coords.y - (this.y + next_cords.y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean targets_in_bounds(double targetX, double targetY) {
        return targetX >= 0 && targetX <= this.p.tab[0].length - 1 && targetY >= 0 && targetY <= this.p.tab.length - 1;
    }

    private Vector2 chooseBestMove(ArrayList<Vector2> moves, Vector2 target) {
        double min = Double.MAX_VALUE;
        Vector2 bestMove = null;
        double epsilon = 0.001;

        for (Vector2 move : moves) {
            double distance = norme_2(target, move);

            if (distance < min - epsilon) {
                min = distance;
                bestMove = move;
            } else if (Math.abs(distance - min) < epsilon) {
                int indexBest = bestMove.x != 0 ? (bestMove.x < 0 ? 2 : 4) : (bestMove.y < 0 ? 1 : 3);
                int indexCurrent = move.x != 0 ? (move.x < 0 ? 2 : 4) : (move.y < 0 ? 1 : 3);
                if (indexCurrent < indexBest) {
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    public void update(Vector2 pac_coords, int t, int pac_facing, Vector2 blinky_coords, String mode) {

        int facing;
        if (this.x - this.previous_x < 0) {
            facing = 180;
        } else if (this.x - this.previous_x > 0) {
            facing = 0;
        } else if (this.y - this.previous_y < 0) {
            facing = 90;
        } else if (this.y - this.previous_y > 0) {
            facing = 270;
        } else {
            facing = 0; // Valeur par défaut ou cas où il n'y a pas de mouvement.
        }
        /* Trois différent dans lequels les fantômes font un 180 : 
            - Pacman vient de manger une powerpellet
            - Changement de n'importe quel mode vers le mode chase
            - Changement du mode chase vers le mode scatter 
         */
        if ((t == 15 && "frightened".equals(this.mode)) || ("chase".equals(this.mode) && !"chase".equals(this.previous_mode)) || ("scatter".equals(this.mode) && "chase".equals(this.previous_mode))) {
            facing = (facing + 180) % 360;
        }
        ArrayList<Vector2> mooves_available = new ArrayList();
        // Les fantômes ne pouvant pas faire demi-tour sur eux mêmes, on interdit la direction opposée
        switch (facing) {
            case 0 -> {
                mooves_available.add(new Vector2(1.0, 0.0));
                mooves_available.add(new Vector2(0.0, 1.0));
                mooves_available.add(new Vector2(0.0, -1.0));
            }
            case 90 -> {
                mooves_available.add(new Vector2(1.0, 0.0));
                mooves_available.add(new Vector2(-1.0, 0.0));
                mooves_available.add(new Vector2(0.0, -1.0));
            }
            case 180 -> {
                mooves_available.add(new Vector2(-1.0, 0.0));
                mooves_available.add(new Vector2(0.0, 1.0));
                mooves_available.add(new Vector2(0.0, -1.0));
            }
            case 270 -> {
                mooves_available.add(new Vector2(1.0, 0.0));
                mooves_available.add(new Vector2(-1.0, 0.0));
                mooves_available.add(new Vector2(0.0, 1.0));
            }
        }

        ArrayList<Vector2> valid_moves = new ArrayList<>();
        for (Vector2 moove : mooves_available) {
            if ( targets_in_bounds(this.y + moove.y, this.x + moove.x)&& this.p.tab[(int) (this.y + moove.y)][(int) (this.x + moove.x)] != 1) {
                valid_moves.add(moove);
            }
        }
        mooves_available = valid_moves;
        Vector2 chosenMove = new Vector2(0.0, 0.0);
        if ("frightened".equals(this.mode)) {
            // Cette fois ci on choisit un déplacement aléatoire parmis les déplacements restant
            if (!mooves_available.isEmpty()) {
                Random r = new Random();
                chosenMove = mooves_available.get(r.nextInt(mooves_available.size()));
            }
        } else if ("chase".equals(this.mode)) {
            Vector2 target = pac_coords;
            if (this.getClass() == Blinky.class) {
                target = pac_coords;
            } else if (this.getClass() == Clyde.class) {
                if (Math.sqrt((this.x - pac_coords.x) * (this.x - pac_coords.x) + (this.y - pac_coords.y) * (this.y - pac_coords.y)) <= 8) {
                    target = new Vector2(0, 20);
                } else {
                    target = pac_coords;
                }
            } else if (this.getClass() == Pinky.class) {
                switch (pac_facing) {
                    case 0 ->
                        target = new Vector2(pac_coords.x + 4, pac_coords.y);
                    case 90 ->
                        target = new Vector2(pac_coords.x - 4, pac_coords.y - 4); // bug (overflow) présent sur le jeu de base expliqué ici https://www.youtube.com/watch?v=ataGotQ7ir8&t=358s à 7:27
                    case 180 ->
                        target = new Vector2(pac_coords.x - 4, pac_coords.y);
                    case 270 ->
                        target = new Vector2(pac_coords.x + 4, pac_coords.y);
                }
            } else if (this.getClass() == Inky.class) {
                switch (pac_facing) {
                    case 0 ->
                        target = new Vector2(pac_coords.x + 2, pac_coords.y);
                    case 90 ->
                        target = new Vector2(pac_coords.x - 2, pac_coords.y - 2);
                    case 180 ->
                        target = new Vector2(pac_coords.x - 2, pac_coords.y);
                    case 270 ->
                        target = new Vector2(pac_coords.x + 2, pac_coords.y);
                }
                target = new Vector2(2 * target.x - blinky_coords.x, 2 * target.y - blinky_coords.y);
            }

            chosenMove = chooseBestMove(mooves_available, target);

        } else if ("scatter".equals(this.mode)) {
            // Ici le fantôme cherche à atteindre ça cible dans un des quatres coin de la map
            Vector2 target = new Vector2(0, 0);
            if (this.getClass() == Blinky.class) {
                target = new Vector2(17, 0);
            } else if (this.getClass() == Clyde.class) {
                target = new Vector2(0, 20);
            } else if (this.getClass() == Pinky.class) {
                target = new Vector2(3, 0);
            } else if (this.getClass() == Inky.class) {
                target = new Vector2(20, 20);
            }
            chosenMove = chooseBestMove(mooves_available, target);
        } else if ("eaten".equals(this.mode)) {
            if (this.x == 10 && this.y == 10) {
                this.mode = mode;
            } else {
                Vector2 target = new Vector2(10, 10);
                chosenMove = chooseBestMove(mooves_available, target);
            }

        }

        /* Cas où le fantôme arrive par exemple par la gauche sur un partern du type:
                  1  1
               -> 0  1
                  1  1
           Obligation de revenir en arrière
         */
        if (mooves_available.size() == 0) {
            facing = (facing + 180) % 360;
            switch (facing) {
                case 0 ->
                    chosenMove = new Vector2(1.0, 0.0);
                case 90 ->
                    chosenMove = new Vector2(0.0, -1.0);
                case 180 ->
                    chosenMove = new Vector2(-1.0, 0.0);
                case 270 ->
                    chosenMove = new Vector2(0.0, 1.0);
            }
        }
        if (chosenMove.x != 0.0 || chosenMove.y != 0.0) {

            if (this.y == 10 && this.x == 0 && chosenMove.x < 0) {
                this.x = 20;
                this.previous_x = 20;
                this.facing = 180;
            } else if (this.y == 10 && this.x == 20 && chosenMove.x > 0) {
                this.x = 0;
                this.previous_x = 0;
                this.facing = 0;
            } else {
                this.previous_x = this.x;
                this.previous_y = this.y;
                this.x += chosenMove.x;
                this.y += chosenMove.y;
            }
        } else {
        }

    }

}
