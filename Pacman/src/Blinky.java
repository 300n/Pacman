
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import javafx.scene.paint.Color;

public class Blinky extends Ghost {

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public Blinky() {
        super(11, 8, Color.rgb(255, 0, 0, 0.8));
    }

    public void reset() {
        this.x = 11;
        this.y = 8;
        this.previous_x = 11;
        this.previous_y = 8;
        this.facing = 90;
    }

    public static class Node {

        int x, y, dist;

        public Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}
