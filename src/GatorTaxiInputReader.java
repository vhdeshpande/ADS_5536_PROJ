import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GatorTaxiInputReader {

    private static final String OPERATION_REGEX = "^([A-Za-z]+)\\(([\\-0-9,]*)\\)$";


    /**
     * Read the operations from input file
     * @param inputFileName - input file name
     * @return list of operations read from the input file
     * @throws FileNotFoundException
     */
    public List<GatorTaxiOperation> getOperationListFromInputFile(String inputFileName) throws FileNotFoundException {
        List<GatorTaxiOperation> gatorTaxiOperationList = new ArrayList<>();
        File inputFile = new File(inputFileName);
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()) {
            GatorTaxiOperation gatorTaxiOperation = this.getOperationFromInputStr(scanner.nextLine());
            if (gatorTaxiOperation != null)
                gatorTaxiOperationList.add(gatorTaxiOperation);
        }

        return gatorTaxiOperationList;
    }

    /**
     * Creates an object for the operation to perform and its input values.
     * @param s - operation to perform string from the input file
     * @return operation to perform
     */
    private GatorTaxiOperation getOperationFromInputStr(String s) {
        GatorTaxiOperation gatorTaxiOperation = null;
        Pattern operationRegex = Pattern.compile(OPERATION_REGEX);
        Matcher opMatch = operationRegex.matcher(s.trim());
        if (opMatch.find()) {
            String operation = opMatch.group(1);
            String inputValues = opMatch.group(2);
            Integer input1 = null;
            Integer input2 = null;
            String inputStr[] = null;
            switch (GatorTaxiOperationCode.valueOf(operation)) {

                /**
                 * input1 - ride number
                 * input2 - ride number
                 */
                case Print:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = null;
                    if (inputStr.length > 1) {
                        input2 = Integer.parseInt(inputStr[1]);
                    }
                    gatorTaxiOperation = new GatorTaxiOperation(GatorTaxiOperationCode.Print, input1, input2);
                    break;

                /**
                 * input1 - ride number
                 * input2 - ride cost
                 * input3 - trip duration
                 */
                case Insert:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = Integer.parseInt(inputStr[1]);
                    Integer  input3 = Integer.parseInt(inputStr[2]);
                    gatorTaxiOperation = new GatorTaxiOperation(GatorTaxiOperationCode.Insert, input1, input2, input3);
                    break;

                case GetNextRide:
                    gatorTaxiOperation = new GatorTaxiOperation(GatorTaxiOperationCode.GetNextRide);
                    break;

                case CancelRide:
                    gatorTaxiOperation = new GatorTaxiOperation(GatorTaxiOperationCode.CancelRide, Integer.parseInt(inputValues));
                    break;

                /**
                 * input1 - ride number
                 * input2 - new trip duration
                 */
                case UpdateTrip:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = Integer.parseInt(inputStr[1]);
                    gatorTaxiOperation = new GatorTaxiOperation(GatorTaxiOperationCode.UpdateTrip, input1, input2);
                    break;

                default:
                    break;
            }
        }
        return gatorTaxiOperation;
    }

}
