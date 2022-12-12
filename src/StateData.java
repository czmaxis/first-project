import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StateData {
    ArrayList<State> listOfStates = new ArrayList<>();

    public static final String SPACE  = "\t";

    public void readStatesFromFile(String filename) throws StateException, FileNotFoundException {
        String nextLine ="";
        String[] items = new String[0];
        String shortcut = null;
        String name = null;
        Double higherVat = null;
        Double lowerVat = null;
        boolean haveSpecialVat = false;
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            while (scanner.hasNextLine()){
                nextLine = scanner.nextLine();
                items = nextLine.split(SPACE);
                lineNumber ++;

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

    public void WriteStatesToFile (String filename) throws StateException{
        int lineNumber = 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            for (State state : listOfStates){
                lineNumber ++;
                String outputLineForList =
                        state.getName() + SPACE
                        +state.getShortcut() + SPACE
                        +state.getHigherVat() + SPACE
                        +state.getLowerVat() + SPACE;
                writer.println(outputLineForList);
            }
            lineNumber ++;
            String outputLineAfterCycle = "====================" + "\n Sazba VAT 20 % nebo nižší nebo používají speciální sazbu: ";
            writer.println(outputLineAfterCycle);
            for (State state : listOfStates){
                if (state.getHigherVat() <= 20 || state.isHaveSpecialVat() == true) {
                    String outputLineForShortcuts =
                            "(" + state.getShortcut() + ")";
                    writer.print(outputLineForShortcuts);
                }
            }

        }catch (IOException e){
            throw new StateException("nastala chyba při zápisu do souboru na řádku: "+ lineNumber+ " "+ e.getLocalizedMessage());


    }


    }
    public List<State> getListOfStates() {
        return new ArrayList<>(listOfStates);}

}
