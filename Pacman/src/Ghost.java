
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public abstract class Ghost {

    int x, y, facing, previous_x, previous_y;
    Color couleur;
    Pacman__ p = new Pacman__();
    int radius = 28;
    double interpolatedX, interpolatedY;
    int[] offsets = {radius / 2 - radius / 14, radius / 2 - radius / 14, radius / 4 - radius / 28, radius / 4 - radius / 28, radius / 7, radius / 7, radius / 14, radius / 14, radius / 14, radius / 14,
        0, 0, 0, 0, 0, 0, 0, 0, radius / 14, radius / 14, radius / 14, radius / 14, radius / 7, radius / 7, radius / 4 - radius / 28, radius / 4 - radius / 28, radius / 2 - radius / 14, radius / 2 - radius / 14};
    int[] ranges = {radius / 2 + radius / 14 + 1, radius / 2 + radius / 14 + 1, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius - 1, radius - 1, radius + 1,
        radius + 1, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7, radius + 1, radius + 1, radius - 1, radius - 1, radius * 3 / 4 + radius / 7, radius * 3 / 4 + radius / 7,
        radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius * 3 / 4, radius / 2 + radius / 14 + 1, radius / 2 + radius / 14 + 1};
    boolean mooving = false;

    public Ghost(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.facing = 90;
        this.couleur = couleur;
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

    public void draw_ghost(GraphicsContext gc) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        int top_left_corner_x = p.board_top_x + p.width / p.tab[0].length * this.x + p.width / p.tab[0].length / 2 - radius / 2;
        int top_left_corner_y = p.board_top_y + p.height / p.tab.length * this.y + p.height / p.tab.length / 2 - radius / 2;
        for (int col = 0; col < offsets.length; col++) {
            for (int i = 1; i < ranges[col]; i++) {
                pixelWriter.setColor(top_left_corner_x + col, top_left_corner_y + offsets[col] + i, this.couleur);
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
                draw_ghost_eyes_looking_left(gc, top_left_corner_x, top_left_corner_y);
            case 180 ->
                draw_ghost_eyes_looking_right(gc, top_left_corner_x, top_left_corner_y);
            case 270 ->
                draw_ghost_eyes_looking_down(gc, top_left_corner_x, top_left_corner_y);
            default ->
                draw_ghost_eyes_looking_up(gc, top_left_corner_x, top_left_corner_y);
        }
    }

    public void draw_ghost_moving(GraphicsContext gc) {
        /*
        if (this.mooving) {
            System.out.println("Sauce1");
            this.ranges[11] += 2;
            this.ranges[12] += 2;
            this.ranges[16] += 2;
            this.ranges[17] += 2;
            this.mooving = false;
        } else {
            System.out.println("Sauce2");
            this.mooving = true;
            this.ranges[11] -= 2;
            this.ranges[12] -= 2;
            this.ranges[16] -= 2;
            this.ranges[17] -= 2;
        }*/
        PixelWriter pixelWriter = gc.getPixelWriter();
        double top_left_corner_x = p.board_top_x + p.width / p.tab[0].length * this.interpolatedX + p.width / p.tab[0].length / 2 - radius / 2;
        double top_left_corner_y = p.board_top_y + p.height / p.tab.length * this.interpolatedY + p.height / p.tab.length / 2 - radius / 2;
        for (int col = 0; col < offsets.length; col++) {
            for (int i = 1; i < ranges[col]; i++) {
                pixelWriter.setColor((int) top_left_corner_x + col, (int) top_left_corner_y + offsets[col] + i, this.couleur);
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
                draw_ghost_eyes_looking_left(gc, (int) top_left_corner_x, (int) top_left_corner_y);
            case 180 ->
                draw_ghost_eyes_looking_right(gc, (int) top_left_corner_x, (int) top_left_corner_y);
            case 270 ->
                draw_ghost_eyes_looking_down(gc, (int) top_left_corner_x, (int) top_left_corner_y);
            default ->
                draw_ghost_eyes_looking_up(gc, (int) top_left_corner_x, (int) top_left_corner_y);
        }
    }

    public void draw_ghost_eyes_looking_left(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6, y + 8, 8, 4);
        gc.fillRect(x + 8, y + 6, 4, 8);
        gc.fillRect(x + 18, y + 8, 8, 4);
        gc.fillRect(x + 20, y + 6, 4, 8);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 10, y + 8, 4, 4);
        gc.fillRect(x + 22, y + 8, 4, 4);
    }

    public void draw_ghost_eyes_looking_right(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 2, y + 8, 8, 4);
        gc.fillRect(x + 4, y + 6, 4, 8);
        gc.fillRect(x + 14, y + 8, 8, 4);
        gc.fillRect(x + 16, y + 6, 4, 8);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 2, y + 8, 4, 4);
        gc.fillRect(x + 14, y + 8, 4, 4);
    }

    public void draw_ghost_eyes_looking_up(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6, y + 8, 8, 4);
        gc.fillRect(x + 8, y + 6, 4, 8);
        gc.fillRect(x + 14, y + 8, 8, 4);
        gc.fillRect(x + 16, y + 6, 4, 8);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 8, y + 6, 4, 4);
        gc.fillRect(x + 16, y + 6, 4, 4);
    }

    public void draw_ghost_eyes_looking_down(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.WHITE);
        gc.fillRect(x + 6, y + 8, 8, 4);
        gc.fillRect(x + 8, y + 6, 4, 8);
        gc.fillRect(x + 14, y + 8, 8, 4);
        gc.fillRect(x + 16, y + 6, 4, 8);
        gc.setFill(Color.BLUE);
        gc.fillRect(x + 8, y + 10, 4, 4);
        gc.fillRect(x + 16, y + 10, 4, 4);
    }

    public void frightened_mode(int pac_x, int pac_y, int[][] tab) {
        ArrayList<int[]> mooving_to = new ArrayList();
        this.previous_x = this.x;
        this.previous_y = this.y;
        mooving_to.add(pac_x - this.x > 0 ? new int[]{0, -1} : new int[]{0, 1});
        mooving_to.add(pac_y - this.y > 0 ? new int[]{-1, 0} : new int[]{1, 0});
        int old_x = this.x;
        int old_y = this.y;

        if (tab[this.y][this.x + mooving_to.get(0)[1]] != 1) {
            if (tab[this.y + mooving_to.get(1)[0]][this.x] != 1) {
                int[] randomMove = mooving_to.get((int) (Math.random() * 2));
                this.x += randomMove[1];
                this.y += randomMove[0];
            } else {
                this.x += mooving_to.get(0)[1];
            }
        } else {
            if (tab[this.y + mooving_to.get(1)[0]][this.x] != 1) {
                this.y += mooving_to.get(1)[0];
            }
        }
        if (old_x == this.x && old_y == this.y) {
            this.update_randomly(tab);
        }
    }
    public abstract void Dijkstra(int desti_x, int desti_y, int pac_facing, int[][] tab, int blinky_x, int blinky_y, String mode);
    public abstract void reset();

}
