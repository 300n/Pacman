package pacman__;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

public class Pacman {

    int x, y;
    int vies = 3;
    int previous_x, previous_y;
    int facing;
    int radius_pacman = 28;
    Pacman__ p = new Pacman__();
    double interpolatedX, interpolatedY;
    final int[] mouthAngle = {0};   // Animation de la bouche
    final boolean[] mouthOpening = {true}; // Indique si la bouche est en train de s'ouvrir

    public Pacman() {
        this.x = 2;
        this.y = 1;
        this.previous_x = 2;
        this.previous_y = 1;
        this.facing = 0;
    }

    public void reset() {
        this.x = 2;
        this.y = 1;
        this.previous_x = 2;
        this.previous_y = 1;
        this.facing = 0;
    }

    public void draw_Pacman(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);

        if (this.previous_x == this.x && this.previous_y == this.y) {
            gc.fillOval(p.board_top_x + p.width / p.tab[0].length * this.x + p.width / p.tab[0].length / 2 - this.radius_pacman / 2, p.board_top_y + p.height
                    / p.tab.length * this.y + p.height / p.tab.length / 2 - this.radius_pacman / 2, this.radius_pacman, this.radius_pacman);

        } else {

            gc.setFill(Color.YELLOW);

            // Mise à jour de l'animation de la bouche
            if (this.mouthOpening[0]) {
                this.mouthAngle[0] += 5; // Augmente l'angle d'ouverture
                if (this.mouthAngle[0] >= 45) {
                    this.mouthOpening[0] = false; // Inverse à 45°
                }
            } else {
                this.mouthAngle[0] -= 5; // Réduit l'angle d'ouverture
                if (this.mouthAngle[0] <= 0) {
                    this.mouthOpening[0] = true; // Inverse à 0°
                }
            }

            // Ici on n'utilise pas l'argument facing car malgré le fait qu'il nous permet de ne pas effectuer les calculs il donne une impression 
            // d'un pacman qui glisse sur le terrain car la direction change avant que le pacman tourne 
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
            gc.fillArc(p.board_top_x + p.width / p.tab[0].length * this.interpolatedX + p.width / p.tab[0].length / 2 - this.radius_pacman / 2, p.board_top_y
                    + p.height / p.tab.length * this.interpolatedY + p.height / p.tab.length / 2 - this.radius_pacman / 2,
                    this.radius_pacman, this.radius_pacman,
                    direction + mouthAngle[0], // Angle de début
                    360 - 2 * mouthAngle[0], // Taille de l'arc
                    ArcType.ROUND);
        }
    }

    public void draw_dying_animation(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        this.mouthAngle[0] += 5;
        this.mouthOpening[0] = true;
        gc.fillArc(p.board_top_x + p.width / p.tab[0].length * this.interpolatedX + p.width / p.tab[0].length / 2 - this.radius_pacman / 2, p.board_top_y
                + p.height / p.tab.length * this.interpolatedY + p.height / p.tab.length / 2 - this.radius_pacman / 2,
                this.radius_pacman, this.radius_pacman,
                90 + mouthAngle[0], // Angle de début
                360 - 2 * mouthAngle[0], // Taille de l'arc
                ArcType.ROUND);
    }

    public void update() {
        this.previous_x = x;
        this.previous_y = y;
        switch (this.facing) {
            case 0 -> {
                if (p.tab[this.y][this.x + 1] != 1) {
                    this.x++;
                }
            }
            case 180 -> {
                if (p.tab[this.y][this.x - 1] != 1) {
                    this.x--;
                }
            }
            case 270 -> {
                if (p.tab[this.y + 1][this.x] != 1) {
                    this.y++;
                }
            }
            default -> {
                if (p.tab[this.y - 1][this.x] != 1) {
                    this.y--;
                }
            }
        }

    }

    public void direction_changed(KeyEvent event) {
        switch (event.getCode()) {
            case Z:
                if (p.tab[this.y - 1][this.x] != 1) {
                    this.facing = 90;
                    break;
                }
            case S:
                if (p.tab[this.y + 1][this.x] != 1) {
                    this.facing = 270;
                    break;
                }
            case Q:
                if (p.tab[this.y][this.x - 1] != 1) {
                    this.facing = 180;
                    break;
                }
            case D:
                if (p.tab[this.y][this.x + 1] != 1 ) {
                    this.facing = 0;
                    break;
                }
            default:
                break;
        }
    }

    public void draw_vies(GraphicsContext gc) {
        for (int i = 0; i < this.vies; i++) {

            gc.fillArc(p.board_top_x + p.width - p.width/(p.tab[0].length*2) - this.radius_pacman / 2 + this.radius_pacman*i + (p.width/p.tab[0].length - this.radius_pacman)*i,
                    p.board_top_y- this.radius_pacman * 2 - p.width / 160,
                    this.radius_pacman, this.radius_pacman,
                    0 + 30, // Angle de début
                    360 - 2 * 30, // Taille de l'arc
                    ArcType.ROUND);
        }
    }
}
