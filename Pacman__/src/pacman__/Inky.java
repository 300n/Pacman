package pacman__;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;

public class Inky extends Ghost {

    public Inky() {
        super(10, 10, Color.rgb(135, 255, 252, 0.8));
    }

    public void reset() {
        this.x = 10;
        this.y = 10;
        this.previous_x = 10;
        this.previous_y = 10;
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

    public void Dijkstra(int desti_x, int desti_y, int pac_facing, int[][] tab, int blinky_x, int blinky_y) {

        // On prédit la position de pacman dans 2 tour :
        switch (pac_facing) {
            case 0 -> {
                for (int i = 0; i < 2; i++) {
                    if (this.p.tab[desti_y][desti_x + 1] != 1) {
                        desti_x++;
                    }
                }
            }
            case 90 -> {
                for (int i = 0; i < 2; i++) {
                    if (this.p.tab[desti_y - 1][desti_x] != 1) {
                        desti_y--;
                    }
                }
            }
            case 180 -> {
                for (int i = 0; i < 2; i++) {
                    if (this.p.tab[desti_y][desti_x - 1] != 1) {
                        desti_x--;
                    }
                }
            }
            case 270 -> {
                for (int i = 0; i < 2; i++) {
                    if (this.p.tab[desti_y + 1][desti_x] != 1) {
                        desti_y++;
                    }
                }
            }
        }
        
        int x_cible = blinky_x + 2*(desti_x-blinky_x);
        int y_cible = blinky_y + 2*(desti_y-blinky_y);

        int[][] dist = new int[this.p.tab.length][this.p.tab[0].length];
        int[][] parentX = new int[this.p.tab.length][this.p.tab[0].length];
        int[][] parentY = new int[this.p.tab.length][this.p.tab[0].length];

        for (int i = 0; i < this.p.tab.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[this.y][this.x] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.dist));
        pq.add(new Node(this.x, this.y, 0)); // Attention à l'ordre ici : x, y

        boolean found = false;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int x = node.x, y = node.y, d = node.dist;

            if (x == x_cible && y == y_cible) {
                found = true;
                break;
            }

            // Exploration des voisins
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];

                // Vérification des limites de la grille et obstacles
                if (nx >= 0 && nx < this.p.tab[0].length && ny >= 0 && ny < this.p.tab.length && this.p.tab[ny][nx] != 1 && dist[ny][nx] > d + 1) {
                    dist[ny][nx] = d + 1;
                    parentX[ny][nx] = x;  // Mise à jour du parent en X
                    parentY[ny][nx] = y;  // Mise à jour du parent en Y
                    pq.add(new Node(nx, ny, dist[ny][nx]));
                }
            }
        }

        if (found) {
            this.previous_x = x;
            this.previous_y = y;
            this.reconstruire_chemin(parentX, parentY, y_cible, x_cible, tab);
        } else {
            this.update_randomly(tab);
        }
    }

    public void reconstruire_chemin(int[][] parentX, int[][] parentY, int desti_y, int desti_x, int[][] tab) {
        int firstMoveX = desti_x, firstMoveY = desti_y;
        int previous_moove_x = -1, previous_moove_y = -1;

        // Remonter le chemin jusqu'au point de départ
        while (firstMoveX != this.x || firstMoveY != this.y) {
            previous_moove_x = firstMoveX;
            previous_moove_y = firstMoveY;

            int tempX = parentX[firstMoveY][firstMoveX];
            int tempY = parentY[firstMoveY][firstMoveX];

            // Vérification si le chemin est invalide
            if (tempX == 0 && tempY == 0 && (firstMoveX != this.x || firstMoveY != this.y)) {
                System.out.println("Chemin invalide détecté.");
                return;
            }

            firstMoveX = tempX;
            firstMoveY = tempY;

            //System.out.println("firstMoveY = " + firstMoveY + " & y = " + this.y + " & firstMoveX = " + firstMoveX + " & x = " + this.x);
        }

        // Calcul du premier mouvement
        if (previous_moove_y == this.y - 1 && (tab[this.y - 1][this.x] != 3 && tab[this.y - 1][this.x] != 4)) {
            this.y--; // Haut
        } else if (previous_moove_y == this.y + 1 && (tab[this.y + 1][this.x] != 3 && tab[this.y + 1][this.x] != 4)) {
            this.y++; // Bas
        } else if (previous_moove_x == this.x - 1 && (tab[this.y][this.x - 1] != 3 && tab[this.y][this.x - 1] != 4)) {
            this.x--; // Gauche
        } else if (previous_moove_x == this.x + 1 && (tab[this.y][this.x + 1] != 3 && tab[this.y][this.x + 1] != 4)) {
            this.x++; // Droite
        }

        //System.out.println("Premier mouvement effectué : (" + this.x + ", " + this.y + ")");
    }
}
