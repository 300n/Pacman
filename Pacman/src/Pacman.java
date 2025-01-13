
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;

public class Pacman {

    Vector2 coords = new Vector2(5,1);
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
        this.coords.x = 3;
        this.coords.y = 1;
        this.previous_x = 2;
        this.previous_y = 1;
        this.facing = 0;
    }

    public void reset() {
        this.coords.x = 2;
        this.coords.y = 1;
        this.previous_x = 2;
        this.previous_y = 1;
        this.facing = 0;
    }

    public void draw_Pacman(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);

        if (this.previous_x == this.coords.x && this.previous_y == this.coords.y) {
            gc.fillOval(p.board_top_x + p.width / p.tab[0].length * this.coords.x - this.radius_pacman / 2 + p.width / p.tab[0].length / 2, p.board_top_y + p.height
                    / p.tab.length * this.coords.y + p.height / p.tab.length / 2 - this.radius_pacman / 2, this.radius_pacman, this.radius_pacman);

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
            if (this.coords.x > this.previous_x) {
                direction = 0; // Droite
            } else if (this.coords.x < this.previous_x) {
                direction = 180; // Gauche
            } else if (this.coords.y > this.previous_y) {
                direction = 270; // Bas
            } else if (this.coords.y < this.previous_y) {
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
        return targetX >= 0 && targetX <= this.p.tab[0].length-1 && targetY >= 0 && targetY <= this.p.tab.length-1;
    }

    public void update() {
        this.previous_x = this.coords.x;
        this.previous_y = this.coords.y;
        double targetX = this.coords.x;
        double targetY = this.coords.y;
        //System.out.println("facing = "+this.facing+" this.y = "+this.y+" this.x + this.speed = " +(this.x+this.speed));
        if (this.facing == 0 && this.coords.y == 10 && this.coords.x + this.speed >= 21) {
            this.coords.x = 0;
            this.previous_x = this.coords.x;
        } else if (this.facing == 180 && this.coords.y == 10 && this.coords.x - this.speed <= 0) {
            this.coords.x = 20;
            this.previous_x = this.coords.x;
        } else {
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
                int checkX = (int) (this.coords.x + (this.facing == 0 ? i : this.facing == 180 ? -i : 0));
                int checkY = (int) (this.coords.y + (this.facing == 90 ? -i : this.facing == 270 ? i : 0));
                
            
                
                
                if (!this.targets_in_bounds(checkX, checkY) || this.p.tab[checkY][checkX] == 1) {
                    No_walls_between = false;
                    break;
                }
            }
            //System.out.println("targetX = "+targetX+" targetY = "+targetY);
            //System.out.println("this.targets_in_bounds(Math.ceil(targetX), Math.ceil(targetY)) = "+this.targets_in_bounds(Math.ceil(targetX), Math.ceil(targetY)));
            //System.out.println("this.p.tab[(int) Math.ceil(targetY)][(int) Math.ceil(targetX)] = "+this.p.tab[(int) Math.ceil(targetY)][(int) Math.ceil(targetX)]);
            //System.out.println("No_walls_between = " + No_walls_between);
            if (this.targets_in_bounds(Math.ceil(targetX), Math.ceil(targetY)) && this.p.tab[(int) Math.ceil(targetY)][(int) Math.ceil(targetX)] != 1 && No_walls_between) {
                //System.out.println("a");
                this.coords.x = targetX;
                this.coords.y = targetY;

            } else if (this.p.tab[(int) this.coords.y][(int) this.coords.x] != 1) {
                //System.out.println("b");
                double temp = this.speed;
                double Offset_x = this.facing == 0 ? Math.ceil(speed - temp - 0.1) : this.facing == 180 ? -Math.ceil(this.speed - temp - 0.1) : 0;
                double Offset_y = this.facing == 90 ? -Math.ceil(speed - temp - 0.1) : this.facing == 270 ? Math.ceil(this.speed - temp - 0.1) : 0;

                while (this.targets_in_bounds(this.coords.x + Offset_x, this.coords.y + Offset_y) && p.tab[(int) (this.coords.y + Offset_y)][(int) (this.coords.x + Offset_x)] != 1 && temp > 0) {
                    temp -= 0.1;
                    temp = Math.round(temp * 100) / 100.0;

                    Offset_x = this.facing == 0 ? (speed - temp + 0.8) : this.facing == 180 ? -(this.speed - temp - 0.1) : 0;
                    Offset_y = this.facing == 90 ? -(speed - temp - 0.1) : this.facing == 270 ? (this.speed - temp + 0.8) : 0;
                }

                if (temp + 0.11 < this.speed) {
                    if (p.tab[(int) (this.coords.y + Offset_y)][(int) (this.coords.x + Offset_x)] == 1) {
                        temp += 0.2;
                    }
                    this.coords.x += Offset_x != 0 ? (Math.signum(Offset_x)) * (speed - temp) : 0;
                    this.coords.y += Offset_y != 0 ? (Math.signum(Offset_y)) * (speed - temp) : 0;
                }

            }
            double epsilon = 0.09;
            if (Math.abs(this.coords.x - Math.floor(this.coords.x)) < epsilon) {
                this.coords.x = Math.floor(this.coords.x);
            } else if (Math.abs(this.coords.x - Math.ceil(this.coords.x)) < epsilon) {
                this.coords.x = Math.ceil(this.coords.x);
            }
            if (Math.abs(this.coords.y - Math.floor(this.coords.y)) < epsilon) {
                this.coords.y = Math.floor(this.coords.y);
            } else if (Math.abs(this.coords.y - Math.ceil(this.coords.y)) < epsilon) {
                this.coords.y = Math.ceil(this.coords.y);
            }
        }
    }

    public void direction_changed(ArrayList<Buffer_Input> Input_Buffer, Touches_joueur Touches) {
        
        double epsilon = 0.8;
        // Ne change la direction que si Pacman a terminé son mouvement actuel (x ou y est un entier)
        //System.out.println("(Math.abs(this.interpolatedX - Math.floor(this.x)) = "+(Math.abs(this.interpolatedX - Math.floor(this.x))));
        //System.out.println("Math.abs(this.interpolatedY - Math.floor(this.y)) = "+Math.abs(this.interpolatedY - Math.floor(this.y)));
        //System.out.println("Input_Buffer.size() = "+Input_Buffer.size());
        //System.out.println("p.tab["+((int) this.y)+"]["+((int) this.x+1)+"] = "+p.tab[(int) this.y][(int) this.x+1]);
        //System.out.println("p.tab["+((int) this.y)+"]["+((int) this.x-1)+"] = "+p.tab[(int) this.y][(int) this.x-1]);
        //if(!Input_Buffer.isEmpty()){System.out.println("Input_Buffer.get(0) = "+Input_Buffer.get(0)+" && Touches.mooves.get(2).toLowerCase() = "+Touches.mooves.get(2).toLowerCase());
        //System.out.println(" Touches.mooves.get(0).toUpperCase().toLowerCase() = "+Touches.mooves.get(0).toUpperCase().toLowerCase()+" Input_Buffer.get(0).equals(Touches.mooves.get(1).toLowerCase() = "+Input_Buffer.get(0).equals(Touches.mooves.get(1).toLowerCase()));}
        //System.out.println("Touches.mooves.get(3).toLowerCase() = "+Touches.mooves.get(3).toLowerCase());
        if (!Input_Buffer.isEmpty()) {
            // Vérification des directions possibles
            if (Input_Buffer.get(0).Input.equals(Touches.mooves.get(0).toUpperCase().toLowerCase())) { // Moove_up
                //System.out.println("interpolatedY = "+ interpolatedY+ " && interpolatedX = "+interpolatedX);
                //System.out.println("p.tab["+((int) Math.round(this.interpolatedY-1))+"]["+((int)Math.round(this.interpolatedX))+"] = "+p.tab[(int) Math.round(this.interpolatedY)-1][(int) Math.round(this.interpolatedX)]);
                if (p.tab[(int) Math.round(this.interpolatedY) - 1][(int)Math.round(this.interpolatedX)] != 1) {
                    this.facing = 90;
                    this.coords.x = (int)Math.round(this.interpolatedX);
                    Input_Buffer.remove(0);
                }
            } else if (Input_Buffer.get(0).Input.equals(Touches.mooves.get(1).toLowerCase())) { // Moove_down
                //System.out.println("interpolatedY = "+ interpolatedY+ " && interpolatedX = "+interpolatedX);
                //System.out.println("p.tab["+((int) Math.round(this.interpolatedY+1))+"]["+((int)Math.round(this.interpolatedX))+"] = "+p.tab[(int) Math.round(this.interpolatedY)+1][(int) Math.round(this.interpolatedX)]);
                if (p.tab[(int) Math.round(this.interpolatedY) + 1][(int)Math.round(this.interpolatedX)] != 1) {
                    this.facing = 270;
                    this.coords.x = (int)Math.round(this.interpolatedX);
                    Input_Buffer.remove(0);
                }
            } else if (Input_Buffer.get(0).Input.equals(Touches.mooves.get(2).toLowerCase())) { // Moove_left
                //System.out.println("interpolatedY = "+ interpolatedY+ " && interpolatedX = "+interpolatedX);
                //System.out.println("p.tab["+((int) Math.round(this.interpolatedY))+"]["+((int)Math.round(this.interpolatedX) - 1)+"] = "+p.tab[(int) Math.round(this.interpolatedY)][(int) Math.round(this.interpolatedX) - 1]);
                if (p.tab[(int)Math.round(this.interpolatedY)][(int) Math.round(this.interpolatedX) - 1] != 1) {
                    this.facing = 180;
                    this.coords.y = (int)Math.round(this.interpolatedY);
                    Input_Buffer.remove(0);
                }
            } else if (Input_Buffer.get(0).Input.equals(Touches.mooves.get(3).toLowerCase())) { // Moove_right
                //System.out.println("interpolatedY = "+ interpolatedY+ " && interpolatedX = "+interpolatedX);
                //System.out.println("p.tab["+((int) Math.round(this.interpolatedY))+"]["+((int)Math.round(this.interpolatedX) + 1)+"] = "+p.tab[(int) Math.round(this.interpolatedY)][(int) Math.round(this.interpolatedX) + 1]);
                if (p.tab[(int)Math.round(this.interpolatedY)][(int) Math.round(this.interpolatedX) + 1] != 1) {
                    this.facing = 0;
                    this.coords.y = Math.round(this.interpolatedY);
                    Input_Buffer.remove(0);
                }
            }
            
        }
        //System.out.println("this.facing = "+this.facing);
    }

    public void draw_vies(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        for (int i = 0; i < this.vies; i++) {

            gc.fillArc(p.board_top_x+ p.width/25 - p.width / (p.tab[0].length * 2) - this.radius_pacman / 2 + this.radius_pacman * i + (p.width / p.tab[0].length - this.radius_pacman) * i,
                    
                    p.board_top_y +p.width+p.width/10 - this.radius_pacman * 2,
                    this.radius_pacman, this.radius_pacman,
                    0 + 30, // Angle de début
                    360 - 2 * 30, // Taille de l'arc
                    ArcType.ROUND);
        }
    }
}
