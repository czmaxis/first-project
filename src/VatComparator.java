import java.util.Comparator;

public class VatComparator implements Comparator<State> {
    @Override
    public int compare(State state1, State state2){
        return (state1.getHigherVat().compareTo(state2.getHigherVat()));
    }
    
}
