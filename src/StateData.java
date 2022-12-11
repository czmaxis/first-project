import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public List<State> getListOfStates() {
        return new ArrayList<>(listOfStates);}

}
