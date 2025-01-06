
import java.util.ArrayList;


public class Touches_joueur {
    ArrayList <String> mooves = new ArrayList();
    
    public Touches_joueur(String Moove_up, String Moove_left, String Moove_down, String Moove_right) {
        this.mooves.add(Moove_up); this.mooves.add(Moove_down); this.mooves.add(Moove_left); this.mooves.add(Moove_right);
    }
    
    public boolean Touches_deja_utilisée(String a) {
        return (a == null ? this.mooves.get(0) != null : !a.equals(this.mooves.get(0))) && (a == null ? this.mooves.get(1) != null : !a.equals(this.mooves.get(1))) 
                && (a == null ? this.mooves.get(2) != null : !a.equals(this.mooves.get(2))) && (a == null ? this.mooves.get(3) != null : !a.equals(this.mooves.get(3)));
    }
}
