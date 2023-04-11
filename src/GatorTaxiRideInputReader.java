import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatorTaxiRideInputReader {

//    Read the operations from input file
    public List<Operation> getOperationsFromFile(String filename) throws FileNotFoundException {
        List<Operation> operations = new ArrayList<>();
        File file = new File(filename);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            Operation operation = this.readRecordFromLine(sc.nextLine());
            if (operation != null)
                operations.add(operation);
        }

        return operations;
    }


//    Reads a line and creates a Java object for the action and its input values.
    private Operation readRecordFromLine(String line) {
        Operation operation = null;
        Pattern pattern = Pattern.compile("^([A-Za-z]+)\\(([\\-0-9,]*)\\)$");
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.find()) {
            String tempOp = matcher.group(1);
            String data = matcher.group(2);
            Integer input1 = null;
            Integer input2 = null;
            String values[] = null;
            switch (OperationCode.valueOf(tempOp)) {

                case Print:
                    values = data.split(",");
                    input1 = Integer.parseInt(values[0]);
                    input2 = null;
                    if (values.length > 1) {
                        input2 = Integer.parseInt(values[1]);
                    }
                    operation = new Operation(OperationCode.Print, input1, input2);
                    break;

                case Insert:
                    values = data.split(",");
                    input1 = Integer.parseInt(values[0]);
                    input2 = Integer.parseInt(values[1]);
                    Integer  input3 = Integer.parseInt(values[2]);
                    operation = new Operation(OperationCode.Insert, input1, input2, input3);
                    break;

                case GetNextRide:
                    operation = new Operation(OperationCode.GetNextRide);
                    break;

                case CancelRide:
                    operation = new Operation(OperationCode.CancelRide, Integer.parseInt(data));
                    break;

                case UpdateTrip:
                    values = data.split(",");
                    input1 = Integer.parseInt(values[0]);
                    input2 = Integer.parseInt(values[1]);
                    operation = new Operation(OperationCode.UpdateTrip, input1, input2);
                    break;

                default:
                    break;
            }
        }
        return operation;
    }

}
