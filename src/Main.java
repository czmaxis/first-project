import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;


public class Main extends StateData {
    @Override
    public String getVat() throws IOException {
        return super.getVat();
    }
    public static final String INPUTFILENAME = "vat-eu.txt";

    public static final String OUTPUTFILENAME20 = "vat-over-20.txt";
    public static final String OUTPUTFILENAMEtest = "vat-over-.txt";



    //  public static final String CUSTOMOUTPUTFILE = "vat-over-"+ customVat +".txt";


    public static void main(String[] args) throws IOException, StateException, NumberFormatException{

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

//Print list of states with VAT above 20% wich haven´t special VAT in same format:
System.out.println("\nStáty s DPH nad 20%, které nepoužívájí speciální daň: \n");

        for (State state : listOfStates){
            if (state.getHigherVat() > 20 && state.isHaveSpecialVat() == false){
                System.out.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
            }
        }
        Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
        System.out.println("====================");
        //Print shortcut´s of states from list with VAT 20% or lower and also with special VAT in using:
        System.out.print("Sazba VAT 20 % nebo nižší + použití speciální daňe: ");


        for (State state : listOfStates){
            if (state.getHigherVat() <= 20 || state.isHaveSpecialVat() == true){
                System.out.print(state.getShortcut()+", ");
            }

            try{
                list.writeStatesToFile(OUTPUTFILENAME20);
            }catch (StateException e){
                System.err.println("chyba při zápisu do souboru: " + e.getLocalizedMessage());
            }
        }
//        Custom VAT value filer and creating new custom file
        try {

            list.customWriteStatesToFile("vat-over-"+list.getVat()+".txt");
        }catch (StateException e){
            System.err.println("chyba při zápisu do souboru " + e.getLocalizedMessage());
        }


    }
}