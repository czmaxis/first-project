import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class StateData {
    public Double lineReader() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
      String userInput = reader.readLine();
        if (userInput.isEmpty())
        {
            userInput = "20";
        }

        double userInputDouble = Double.parseDouble(userInput);

        return userInputDouble;
    }
    UserVat userVat;

    {
        try {
            userVat = new UserVat(lineReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    ArrayList<State> listOfStates = new ArrayList<>();
    ArrayList<State> listOfStatesForFile = new ArrayList<>();


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
                listOfStatesForFile.add(stateFromTxt);
            }
        }
    }


    public void writeStatesToFile(String filename) throws StateException{
        int lineNumber = 0;


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            writer.println("Seznam států: ");
            writer.println(" ");

            for (State state : listOfStatesForFile){
                lineNumber++;
                String fullOutputLineForList =
                        state.getName() + TAB
                        +"("+state.getShortcut()+")" + TAB
                        + state.getHigherVat() + "%";
                writer.println(fullOutputLineForList);
            }
            writer.println(" ");
            String statesAbove21Vat = "Státy s DPH nad "+userVat.getUserVat()+" % které nepoužívají speciální daň:";
            writer.println(statesAbove21Vat);
            writer.println(" ");


            Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
            for (State state : listOfStates){
                if (state.getHigherVat() > userVat.getUserVat() && state.isHaveSpecialVat() == false){
                lineNumber ++;
                String outputLineForList =
                        state.getName() + TAB
                        +"("+state.getShortcut()+")" + TAB
                        +state.getHigherVat()+" %" + TAB;

                writer.println(outputLineForList);
                }
            }
            lineNumber ++;
            String outputLineAfterCycle = "====================" + "\nSazba VAT "+userVat.getUserVat()+" a nižší nebo zavedení speciální daně:";
            writer.print(outputLineAfterCycle);
            Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
            for (State state : listOfStates){
                if (state.getHigherVat() <= userVat.getUserVat() || state.isHaveSpecialVat()) {
                    String outputShortcutsLine = (state.getShortcut() + ", ");
                    writer.print(outputShortcutsLine);
                }
            }

        }catch (IOException e){
            throw new StateException("nastala chyba při zápisu do souboru na řádku: "+ lineNumber+ " "+ e.getLocalizedMessage());
        }
    }

    public List<State> getListOfStates() {
        return new ArrayList<>(listOfStates);}




}
