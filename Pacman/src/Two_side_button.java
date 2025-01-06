
import javafx.scene.control.Button;

public class Two_side_button {

    int min, max, value;
    Button left_arrow = new Button("<");
    Button right_arrow = new Button(">");

    public Two_side_button(int Min, int Max, int Value) {
        this.min = Min;
        this.max = Max;
        this.value = Value;
    }
}
