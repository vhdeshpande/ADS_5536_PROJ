public class GatorTaxiOperation {

    private GatorTaxiOperationCode gatorTaxiOperationCode;

    /**
     * Input parameter 1
     */
    private Integer input1;

    /**
     * Input parameter 2
     */
    private Integer input2;

    /**
     * Input parameter 3
     */
    private Integer input3;

    public GatorTaxiOperation(GatorTaxiOperationCode gatorTaxiOperationCode, Integer input1, Integer input2) {
        this.gatorTaxiOperationCode = gatorTaxiOperationCode;
        this.input1 = input1;
        this.input2 = input2;
    }

    public GatorTaxiOperation(GatorTaxiOperationCode gatorTaxiOperationCode, Integer input1, Integer input2, Integer input3) {
        this.gatorTaxiOperationCode = gatorTaxiOperationCode;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
    }

    public GatorTaxiOperation(GatorTaxiOperationCode gatorTaxiOperationCode, Integer input1) {
        this.gatorTaxiOperationCode = gatorTaxiOperationCode;
        this.input1 = input1;
    }

    public GatorTaxiOperation(GatorTaxiOperationCode gatorTaxiOperationCode) {
        this.gatorTaxiOperationCode = gatorTaxiOperationCode;
    }

    public GatorTaxiOperationCode getOperationCode() {
        return this.gatorTaxiOperationCode;
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
        String outputStr = gatorTaxiOperationCode.toString();
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