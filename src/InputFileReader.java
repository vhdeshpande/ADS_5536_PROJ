import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFileReader {

    //Read the operations from input file and store them in the list to process them later
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


    /*Reads a line and makes a java object for the action and its associated details
    i.e initialize/insert/delete/search
    */
    private Operation readRecordFromLine(String line) {
        Operation operation = null;
        Pattern pattern = Pattern.compile("^([A-Za-z]+)\\(([\\-0-9,]*)\\)$");
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.find()) {
            String tempOp = matcher.group(1);
            String data = matcher.group(2);
            Integer val1 = null;
            Integer val2 = null;
            String temp[] = null;
            switch (OperationCode.valueOf(tempOp)) {

                case Insert:
                    temp = data.split(",");
                    val1 = Integer.parseInt(temp[0]);
                    val2 = Integer.parseInt(temp[1]);
                    Integer  val3= Integer.parseInt(temp[2]);
                    operation = new Operation(OperationCode.Insert, val1, val2, val3);
                    break;

                case GetNextRide:
                    operation = new Operation(OperationCode.GetNextRide);
                    break;

                case UpdateTrip:
                    temp = data.split(",");
                    val1 = Integer.parseInt(temp[0]);
                    val2 = Integer.parseInt(temp[1]);
                    operation = new Operation(OperationCode.UpdateTrip, val1, val2);
                    break;

                case CancelRide:
                    operation = new Operation(OperationCode.CancelRide, Integer.parseInt(data));
                    break;

                case Print:
                    temp = data.split(",");
                    val1 = Integer.parseInt(temp[0]);
                    val2 = null;
                    if (temp.length > 1) {
                        val2 = Integer.parseInt(temp[1]);
                    }
                    operation = new Operation(OperationCode.Print, val1, val2);
                    break;
                default:
                    break;
            }
        }
        return operation;
    }

}
