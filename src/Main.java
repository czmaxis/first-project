import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;


public class Main {
    public static String INPUTFILENAME = "vat-eu.txt";


    public static void main(String[] args) throws FileNotFoundException , StateException {

        StateData list = new StateData();
        try{
            list.readStatesFromFile(INPUTFILENAME);
        }catch (StateException e){
            System.err.println("chyba při čtení souboru: " + e.getLocalizedMessage());
        }

//Print list of states in format: state name, state shortcut, state Vat
        System.out.println("List of States:\n");

        List<State> listOfStates = list.getListOfStates();
        for(State state : listOfStates){
            System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
        }

//Print list of states with VAT above 20% and haven´t special VAT in same format:
System.out.println("\nStates with VAT above 20%:\n");

        for (State state : listOfStates){
            if (state.getHigherVat() > 20 && state.isHaveSpecialVat() == false){
                System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
            }
        }
        Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));

    }


}