
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;

public class Clyde extends Ghost {

    public Clyde(int facing,int radius) {
        super(12, 10, Color.rgb(255, 127, 0, 0.8), facing, radius);
    }

    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public void reset() {
        this.x = 10;
        this.y = 10;
        this.previous_x = 10;
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
