public class Buffer_Input {
    String Input;
    int Lifespan = 40;
    
    public Buffer_Input(String input) {
        this.Input = input; ;
    }
    
    public boolean decreament_lifespan() {
        if (this.Lifespan>0) {
            this.Lifespan--;
            return true;
        } else {
            return false;
        }
    }
}
