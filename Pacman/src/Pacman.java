
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;

public class Pacman {

    double x = 5, y = 1;
    int vies = 3;
    double speed = 2;
    double previous_x, previous_y;
    int facing;
    int radius_pacman = 28;
    Pacman__ p = new Pacman__();
    double interpolatedX, interpolatedY;
    final int[] mouthAngle = {0};   // Animation de la bouche
    final boolean[] mouthOpening = {true}; // Indique si la bouche est en train de s'ouvrir

    public Pacman() {
        this.x = 3;
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
            gc.fillOval(p.board_top_x + p.width / p.tab[0].length * this.x - this.radius_pacman / 2 + p.width / p.tab[0].length / 2, p.board_top_y + p.height
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
            gc.fillArc(p.board_top_x + p.width / p.tab[0].length * this.interpolatedX - this.radius_pacman / 2 + p.width / p.tab[0].length / 2, p.board_top_y
                    + p.height / p.tab.length * this.interpolatedY + p.height / p.tab.length / 2 - this.radius_pacman / 2,
                    this.radius_pacman, this.radius_pacman,
                    direction + mouthAngle[0], // Angle de début
                    360 - 2 * mouthAngle[0], // Taille de l'arc
                    ArcType.ROUND);
        }
    }

    public void draw_dying_animation(GraphicsContext gc, int[] temp) {
        gc.setFill(Color.YELLOW);
        this.mouthAngle[0] += 5;
        this.mouthOpening[0] = true;
        gc.fillArc(p.board_top_x + p.width / p.tab[0].length * this.interpolatedX + p.width / p.tab[0].length / 2 - this.radius_pacman / 2, p.board_top_y
                + p.height / p.tab.length * this.interpolatedY + p.height / p.tab.length / 2 - this.radius_pacman / 2,
                this.radius_pacman, this.radius_pacman,
                90 + mouthAngle[0], // Angle de début
                360 - 2 * mouthAngle[0], // Taille de l'arc
                ArcType.ROUND);
    }

    

    public boolean targets_in_bounds(double targetX, double targetY) {
        return targetX > 0 && targetX < this.p.tab[0].length && targetY > 0 && targetY < this.p.tab.length;
    }

    public void update() {
        this.previous_x = x;
        this.previous_y = y;
        double targetX = this.x;
        double targetY = this.y;

        switch (this.facing) {
            case 0 -> {
                targetX += this.speed;
                break;
            }
            case 180 -> {
                targetX -= this.speed;
                break;
            }
            case 270 -> {
                targetY += this.speed;
                break;
            }
            default -> {
                targetY -= this.speed;
                break;
            }
        }
        boolean No_walls_between = true;
        for (int i = 0; i <= (int) Math.ceil(this.speed); i++) {
            int checkX = (int) (this.x + (this.facing == 0 ? i : this.facing == 180 ? -i : 0));
            int checkY = (int) (this.y + (this.facing == 90 ? -i : this.facing == 270 ? i : 0));
            if (!this.targets_in_bounds(checkX, checkY) || this.p.tab[checkY][checkX] == 1) {
                No_walls_between = false;
                break;
            }
        }

        if (this.targets_in_bounds(Math.ceil(targetX), Math.ceil(targetY)) && this.p.tab[(int) Math.ceil(targetY)][(int) Math.ceil(targetX)] != 1 && No_walls_between) {
            this.x = targetX;
            this.y = targetY;

        } else if (this.p.tab[(int) this.y][(int) this.x] != 1) {

            double temp = this.speed;
            double Offset_x = this.facing == 0 ? Math.ceil(speed - temp - 0.1) : this.facing == 180 ? -Math.ceil(this.speed - temp - 0.1) : 0;
            double Offset_y = this.facing == 90 ? -Math.ceil(speed - temp - 0.1) : this.facing == 270 ? Math.ceil(this.speed - temp - 0.1) : 0;

            while (this.targets_in_bounds(this.x + Offset_x, this.y + Offset_y) && p.tab[(int) (this.y + Offset_y)][(int) (this.x + Offset_x)] != 1 && temp > 0) {
                temp -= 0.1;
                temp = Math.round(temp * 100) / 100.0;

                Offset_x = this.facing == 0 ? (speed - temp + 0.8) : this.facing == 180 ? -(this.speed - temp - 0.1) : 0;
                Offset_y = this.facing == 90 ? -(speed - temp - 0.1) : this.facing == 270 ? (this.speed - temp + 0.8) : 0;
            }

            if (temp + 0.11 < this.speed) {
                if (p.tab[(int) (this.y + Offset_y)][(int) (this.x + Offset_x)] == 1) {
                    temp += 0.2;
                }
                this.x += Offset_x != 0 ? (Math.signum(Offset_x)) * (speed - temp) : 0;
                this.y += Offset_y != 0 ? (Math.signum(Offset_y)) * (speed - temp) : 0;
            }

        }
        double epsilon = 0.09;
        if (Math.abs(this.x - Math.floor(this.x)) < epsilon) {
            this.x = Math.floor(this.x);
        } else if (Math.abs(this.x - Math.ceil(this.x)) < epsilon) {
            this.x = Math.ceil(this.x);
        }
        if (Math.abs(this.y - Math.floor(this.y)) < epsilon) {
            this.y = Math.floor(this.y);
        } else if (Math.abs(this.y - Math.ceil(this.y)) < epsilon) {
            this.y = Math.ceil(this.y);
        }
    }

    public void direction_changed(KeyEvent event, Touches_joueur Touches) {
        String code = event.getCode().toString();
        double epsilon = 0.5;
        // Ne change la direction que si Pacman a terminé son mouvement actuel (x ou y est un entier)
        if ((Math.abs(this.x - Math.floor(this.x)) < epsilon) && (Math.abs(this.y - Math.floor(this.y)) < epsilon)) {
            // Vérification des directions possibles
            if (code.equals(Touches.mooves.get(0))) { // Moove_up
                if (p.tab[(int) this.y - 1][(int) this.x] != 1) {
                    this.facing = 90;
                }
            } else if (code.equals(Touches.mooves.get(1))) { // Moove_down
                if (p.tab[(int) this.y + 1][(int) this.x] != 1) {
                    this.facing = 270;
                }
            } else if (code.equals(Touches.mooves.get(2))) { // Moove_left
                if (p.tab[(int) this.y][(int) this.x - 1] != 1) {
                    this.facing = 180;
                }
            } else if (code.equals(Touches.mooves.get(3))) { // Moove_right
                if (p.tab[(int) this.y][(int) this.x + 1] != 1) {
                    this.facing = 0;
                }
            }
        }
    }

    public void draw_vies(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        for (int i = 0; i < this.vies; i++) {

            gc.fillArc(p.board_top_x + p.width - p.width / (p.tab[0].length * 2) - this.radius_pacman / 2 + this.radius_pacman * i + (p.width / p.tab[0].length - this.radius_pacman) * i,
                    p.board_top_y - this.radius_pacman * 2 - p.width / 160,
                    this.radius_pacman, this.radius_pacman,
                    0 + 30, // Angle de début
                    360 - 2 * 30, // Taille de l'arc
                    ArcType.ROUND);
        }
    }
}
