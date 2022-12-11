import java.io.FileNotFoundException;
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

        List<State> listOfStates = list.getListOfStates();
        for(State state : listOfStates){
            System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
        }




    }


}