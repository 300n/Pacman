
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class option_button_display {

    Touches_joueur j;
    ArrayList<Button> Button_List = new ArrayList();
    final boolean[] waitingForKey = {false, false, false, false};
    String[] Cores_touches = {"Se déplacer vers le haut : ", "Se déplacer vers le bas : ", "Se déplacer vers la gauche : ", "Se déplacer vers la droite : "};

    public option_button_display(Touches_joueur J) {
        this.j = J;
        this.Button_List.add(new Button(J.mooves.get(0)));
        this.Button_List.add(new Button(J.mooves.get(1)));
        this.Button_List.add(new Button(J.mooves.get(2)));
        this.Button_List.add(new Button(J.mooves.get(3)));
    }

    public void Button_display(AnchorPane layout, double top_x, double top_y, double width, double height, int num) {
        Rectangle rectangle = new Rectangle(top_x, top_y, 500, 250); // x, y, largeur, hauteur
        rectangle.setFill(Color.BLACK); //fond
        rectangle.setStroke(Color.WHITE); //bordure
        rectangle.setStrokeWidth(3);
        layout.getChildren().add(rectangle);
        
        Text title = new Text("Joueur "+num);
        title.setFill(Color.rgb(255, 255, 255));
        title.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 26));
        AnchorPane.setTopAnchor(title, top_y + 10 - title.getBoundsInLocal().getHeight()/2);
        AnchorPane.setLeftAnchor(title, top_x + 500/2 - title.getBoundsInLocal().getWidth()/2);
        layout.getChildren().add(title);
        for (int i = 0; i < this.Button_List.size(); i++) {
            this.Button_List.get(i).setStyle(
                    "-fx-background-color: rgb(0, 0, 0); "
                    + // Couleur de fond (RGB)
                    "-fx-border-color: rgb(255, 255, 255); "
                    + // Couleur de la bordure (RGB)
                    "-fx-border-width: 3px; "
                    + // Épaisseur de la bordure
                    "-fx-border-radius: 10px; "
                    + // Coins arrondis
                    "-fx-background-radius: 12px;"
                    + // Coins arrondis pour le fond 
                    "-fx-font-family: 'Arial'; "
                    + // Police
                    "-fx-font-size: 16px; "
                    + // Taille de police
                    "-fx-font-weight: bold; "
                    + // Gras
                    "-fx-text-fill: rgb(255, 255, 255);" 
            );
        }
        for (int i = 0; i < this.Button_List.size(); i++) {
            Text text = new Text(this.Button_List.get(i).getText());
            text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
            double y = top_y + 50.0 + i * 50 - text.getBoundsInLocal().getHeight() / 2;
            double x = top_x  + 500/2 - 35;
            AnchorPane.setTopAnchor(this.Button_List.get(i), y);
            AnchorPane.setLeftAnchor(this.Button_List.get(i), x);
            text = new Text(this.Cores_touches[i]);
            text.setFill(Color.rgb(255, 255, 255));
            text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));
            AnchorPane.setTopAnchor(text,y + text.getBoundsInLocal().getHeight() / 2);
            AnchorPane.setLeftAnchor(text, x - text.getBoundsInLocal().getWidth());
            layout.getChildren().add(text);

        }

        for (int i = 0; i < this.Button_List.size(); i++) {
            layout.getChildren().add(this.Button_List.get(i));
        }
    }

    public void KeyPressed(AnchorPane layout, KeyEvent event, double top_x, double top_y, double width, double height) {
        for (int i = 0; i < this.Button_List.size(); i++) {
            if (this.waitingForKey[i]) {
                if (this.j.Touches_deja_utilisée(event.getCode().toString())) {
                    System.out.println(this.j.mooves.get(i));
                    this.j.mooves.set(i,event.getCode().toString());
                    this.Button_List.get(i).setText(this.j.mooves.get(i));
                    this.waitingForKey[i] = false;
                } else {
                    this.Button_List.get(i).setText("Touche déjà utilisée");
                    layout.requestFocus();
                }
                Text text = new Text(this.Button_List.get(i + 1).getText());
                text.setFont(javafx.scene.text.Font.font("Arial", 16));
                AnchorPane.setTopAnchor(this.Button_List.get(i),top_y + 50 + i * 50 - text.getBoundsInLocal().getHeight() / 2);
                AnchorPane.setLeftAnchor(this.Button_List.get(i), top_x  + 500/2 - 40);
            }
        }
    }

    public void set_button_action(AnchorPane layout, double top_x, double top_y, double width, double height) {
        for (int i = 0; i < this.Button_List.size(); i++) {
            final int b = i;
            this.Button_List.get(i).setOnAction(e -> {
                if (!this.waitingForKey[0] && !this.waitingForKey[1] && !this.waitingForKey[2] && !this.waitingForKey[3]) {
                    this.waitingForKey[b] = true;
                    layout.requestFocus();
                    this.Button_List.get(b).setText("Veuillez choisir une touche");
                    Text text = new Text(this.Button_List.get(b).getText());
                    text.setFont(javafx.scene.text.Font.font("Arial", 16));
                    AnchorPane.setTopAnchor(this.Button_List.get(b), top_y + 50 + b * 50 - text.getBoundsInLocal().getHeight() / 2);
                    AnchorPane.setLeftAnchor(this.Button_List.get(b), top_x  + 500/2 - 40);
                }
            });
        }
    }
}