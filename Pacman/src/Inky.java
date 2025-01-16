
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;

public class Inky extends Ghost {

    public Inky(int facing,int radius) {
        super(10, 10, Color.rgb(135, 255, 252, 0.8), facing, radius);
    }

    public void reset() {
        this.x = 11;
        this.y = 10;
        this.previous_x = 11;
        this.previous_y = 11;
        this.facing = 90;
    }
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static class Node {

        int x, y, dist;

        public Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}