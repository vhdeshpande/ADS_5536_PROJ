import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatorTaxiInputReader {

    /**
     * Read the operations from input file
     * @param inputFileName - input file name
     * @return list of operations read from the input file
     * @throws FileNotFoundException
     */
    public List<Operation> getOperationListFromInputFile(String inputFileName) throws FileNotFoundException {
        List<Operation> operationList = new ArrayList<>();
        File file = new File(inputFileName);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            Operation operation = this.getOperationFromInputStr(sc.nextLine());
            if (operation != null)
                operationList.add(operation);
        }

        return operationList;
    }

    /**
     * Creates an object for the operation to perform and its input values.
     * @param s - operation to perform string from the input file
     * @return operation to perform
     */
    private Operation getOperationFromInputStr(String s) {
        Operation operation = null;
        Pattern pattern = Pattern.compile("^([A-Za-z]+)\\(([\\-0-9,]*)\\)$");
        Matcher matcher = pattern.matcher(s.trim());
        if (matcher.find()) {
            String op = matcher.group(1);
            String inputValues = matcher.group(2);
            Integer input1 = null;
            Integer input2 = null;
            String inputStr[] = null;
            switch (OperationCode.valueOf(op)) {

                case Print:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = null;
                    if (inputStr.length > 1) {
                        input2 = Integer.parseInt(inputStr[1]);
                    }
                    operation = new Operation(OperationCode.Print, input1, input2);
                    break;

                case Insert:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = Integer.parseInt(inputStr[1]);
                    Integer  input3 = Integer.parseInt(inputStr[2]);
                    operation = new Operation(OperationCode.Insert, input1, input2, input3);
                    break;

                case GetNextRide:
                    operation = new Operation(OperationCode.GetNextRide);
                    break;

                case CancelRide:
                    operation = new Operation(OperationCode.CancelRide, Integer.parseInt(inputValues));
                    break;

                case UpdateTrip:
                    inputStr = inputValues.split(",");
                    input1 = Integer.parseInt(inputStr[0]);
                    input2 = Integer.parseInt(inputStr[1]);
                    operation = new Operation(OperationCode.UpdateTrip, input1, input2);
                    break;

                default:
                    break;
            }
        }
        return operation;
    }

}
