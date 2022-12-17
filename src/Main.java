import java.io.IOException;
import java.util.Collections;
import java.util.List;
public class Main extends StateData {
    @Override
    public Double lineReader() throws IOException {
        return super.lineReader();
    }
    public static final String INPUTFILENAME = "vat-eu.txt";
    public static void main(String[] args) throws IOException, StateException, NumberFormatException{
        System.out.println("Zadejte prosím výši sazby VAT. Bez zadaní sazby bude po stisknutí enteru nastavena výše sazby na 20% ");
        StateData list = new StateData();

        try{
            list.readStatesFromFile(INPUTFILENAME);
        }catch (StateException e){
            System.err.println("chyba při čtení souboru: " + e.getLocalizedMessage());
        }

//Print list of states in format: state name, state shortcut, state Vat
        System.out.println("Seznam států: \n");

        List<State> listOfStates = list.getListOfStates();


        for(State state : listOfStates){
            System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
        }

        //Print list of states with entered VAT wich haven´t special VAT in same format:
System.out.println("\nStáty s DPH nad "+list.userVat.getUserVat()+" % které nepoužívájí speciální daň: \n");
        Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
        for (State state : listOfStates){
            if (state.getHigherVat() > list.userVat.getUserVat() && ! state.isHaveSpecialVat()){
                System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
            }
        }
        System.out.println("====================");
        //Print shortcut´s of states from list with entered VAT or lower and also with special VAT in using:
        System.out.print("Sazba VAT "+list.userVat.getUserVat()+" a nižší nebo zavedení speciální daně: ");
        System.out.println(" ");
//        Custom VAT value filer and creating new custom file
        try {
            list.writeStatesToFile("vat-over-"+ list.userVat.getUserVat()+".txt");
        }catch (StateException e){
            System.err.println("chyba při zápisu do souboru " + e.getLocalizedMessage());
        }
    }
}