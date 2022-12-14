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
        int lineNumber = 0;
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

                lineNumber ++;
                State stateFromTxt = new State(shortcut, name, higherVat, lowerVat, haveSpecialVat);
                listOfStates.add(stateFromTxt);
                listOfStatesForFile.add(stateFromTxt);
            }
        }catch (IOException e){
            throw new StateException("nastala chyba p??i ??ten?? ze souboru na ????dku: "+ lineNumber + " "+ e.getLocalizedMessage());
        }
    }


    public void writeStatesToFile(String filename) throws StateException{
        int lineNumber = 0;


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            writer.println("Seznam st??t??: ");
            writer.println(" ");

            for (State state : listOfStatesForFile){
                lineNumber++;
                String fullOutputLineForList =
                        state.getName() + TAB
                        +"("+state.getShortcut()+")" + TAB
                        + state.getHigherVat() + "%";
                writer.println(fullOutputLineForList);
            }

            writer.println("\nSt??ty, kter?? maj?? da?? z p??idan?? hodnoty v??t???? jak 20% a p??itom nepou????vaj?? speci??ln?? sazbu: \n");
            Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
            for (State state : listOfStates){
                if (state.getHigherVat() > 20 && !state.isHaveSpecialVat()){
                    writer.println(state.getName()+" ("+state.getShortcut()+") "+state.getHigherVat()+" %");
                }
            }

            writer.println("====================");
            writer.print(("Sazba VAT 20% nebo ni?????? nebo pou????vaj?? speci??ln?? sazbu: "));
            for (State state : listOfStates){
                if (state.getHigherVat() <= 20 || state.isHaveSpecialVat()) {
                    String outputShortcutsLine = (state.getShortcut() + ", ");
                    writer.print(outputShortcutsLine);
                }
            }


        }catch (IOException e){
            throw new StateException("nastala chyba p??i z??pisu do souboru na ????dku: "+ lineNumber+ " "+ e.getLocalizedMessage());
        }
    }

    public void customWriteStatesToFile(String filename) throws StateException{
        int lineNumber = 0;


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))){
            writer.println("Seznam st??t??: ");
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

            String statesAbove21Vat = "St??ty s DPH nad "+userVat.getUserVat()+" % kter?? nepou????vaj?? speci??ln?? da??:";
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
            String outputLineAfterCycle = "====================" + "\nSazba VAT "+userVat.getUserVat()+" a ni?????? nebo zaveden?? speci??ln?? dan??: ";
            writer.print(outputLineAfterCycle);
            Collections.sort(listOfStates, Collections.reverseOrder(new VatComparator()));
            for (State state : listOfStates){
                if (state.getHigherVat() <= userVat.getUserVat() || state.isHaveSpecialVat()) {
                    String outputShortcutsLine = (state.getShortcut()+ ", ");
                    writer.print(outputShortcutsLine);
                }
            }

        }catch (IOException e){
            throw new StateException("nastala chyba p??i z??pisu do souboru na ????dku: "+ lineNumber+ " "+ e.getLocalizedMessage());
        }
    }





    public List<State> getListOfStates() {
        return new ArrayList<>(listOfStates);}




}
