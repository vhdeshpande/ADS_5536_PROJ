public class Operation {

    private OperationCode operationCode;

    private Integer input1;

    private Integer input2;

    private Integer input3;

    public Operation(OperationCode operationCode, Integer input1, Integer input2) {
        this.operationCode = operationCode;
        this.input1 = input1;
        this.input2 = input2;
    }

    public Operation(OperationCode operationCode, Integer input1, Integer input2, Integer input3) {
        this.operationCode = operationCode;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
    }

    public Operation(OperationCode operationCode, Integer input1) {
        this.operationCode = operationCode;
        this.input1 = input1;
    }

    public Operation(OperationCode operationCode) {
        this.operationCode = operationCode;
    }

    public OperationCode getOperationCode() {
        return this.operationCode;
    }

    public Integer getInput1() {
        return this.input1;
    }

    public Integer getInput2() {
        return this.input2;
    }

    public Integer getInput3() {
        return input3;
    }

    public String toString() {
        String outputStr = operationCode.toString();
        if (input1 != null) {
            outputStr = outputStr + " " + input1;
        }
        if (input2 != null) {
            outputStr = outputStr + " " + input2;
        }
        if (input3 != null){
            outputStr = outputStr + " "+ input3;
        }

        return outputStr;
    }
}