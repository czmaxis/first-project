import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class StateData {

    public String getVat() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput = reader.readLine();
        if (userInput.isEmpty())
        {
            userInput = "20";
        }
        return userInput;
    }
    ArrayList<State> listOfStates = new ArrayList<>();
    List<String> vstup;
    ArrayList<State> customListOfStates =  new ArrayList<>();
    public static final String TAB = "\t";

    public void readStatesFromFile(String filename) throws StateException, FileNotFoundException {
        String nextLine ="";
        String[] items = new String[0];
        String shortcut = null;
        String name = null;
        Double higherVat = null;
        Double lowerVat = null;
        boolean haveSpecialVat = false;


        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            while (scanner.hasNextLine()){
                nextLine = scanner.nextLine();
                items = nextLine.split(TAB);


                shortcut = items[0];
                name = items[1];
                higherVat = Double.parseDouble(items[2].replaceAll(",", "."));
                lowerVat = Double.parseDouble(items[3].replaceAll(",", "."));
                haveSpecialVat = Boolean.parseBoolean(items[4]);


                State stateFromTxt = new State(shortcut, name, higherVat, lowerVat, haveSpecialVat);
                listOfStates.add(stateFromTxt);
            }
        }
    }


    public void writeStatesToFile(String filename) throws StateException{
        int lineNumber = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            for (State state : listOfStates){
                if (state.getHigherVat() > 20 && state.isHaveSpecialVat() == false){
                lineNumber ++;
                String outputLineForList =
                        state.getName() + TAB
                        +state.getShortcut() + TAB
                        +state.getHigherVat() + TAB
                        +state.getLowerVat() + TAB;
                writer.println(outputLineForList);
                }
            }
            lineNumber ++;
            String outputLineAfterCycle = "====================" + "\nSazba VAT 20 % nebo nižší a zároveň používají speciální sazbu: ";
            writer.print(outputLineAfterCycle);
            Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
            for (State state : listOfStates){
                if (state.getHigherVat() <= 20 || state.isHaveSpecialVat() == true) {
                    String outputShortcutsLine = (state.getShortcut() + ", ");
                    writer.print(outputShortcutsLine);
                }
            }

        }catch (IOException e){
            throw new StateException("nastala chyba při zápisu do souboru na řádku: "+ lineNumber+ " "+ e.getLocalizedMessage());
        }
    }


    public void customWriteStatesToFile (String filename)throws StateException, IOException {

            Double customVat = Double.parseDouble(getVat().replaceAll(",", "."));
            for (State state : listOfStates) {
                if (state.getHigherVat() > customVat) {
                    customListOfStates.add(state);
                }
            }
            int lineNumber = 0;
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                for (State state : customListOfStates) {
                    lineNumber++;
                    String outputLineForList =
                            state.getName() + TAB
                                    + state.getShortcut() + TAB
                                    + state.getHigherVat() + TAB
                                    + state.getLowerVat() + TAB;
                    writer.println(outputLineForList);
                }
            }catch (IOException e){
                throw new StateException("nastala chyba při zápisu do souboru na řádku: "+ lineNumber + e.getLocalizedMessage());
            }
            }
    public List<State> getListOfStates() {
        return new ArrayList<>(listOfStates);}
}
