
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;

public class Pinky extends Ghost {

    public Pinky(int facing,int radius) {
        super(11, 10, Color.rgb(254, 176, 243, 0.8), facing, radius);
    }

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public void reset() {
        this.x = 9;
        this.y = 10;
        this.previous_x = 9;
        this.previous_y = 11;
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
