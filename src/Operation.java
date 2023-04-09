public class Operation {
    private OperationCode operationCode;
    private Integer val1;
    private Integer val2;
    private Integer val3;

    public Operation(OperationCode operationCode, Integer val1, Integer val2) {
        this.operationCode = operationCode;
        this.val1 = val1;
        this.val2 = val2;
    }

    public Operation(OperationCode operationCode, Integer val1, Integer val2, Integer val3) {
        this.operationCode = operationCode;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public Operation(OperationCode operationCode, Integer val1) {
        this.operationCode = operationCode;
        this.val1 = val1;
    }

    public Operation(OperationCode operationCode) {
        this.operationCode = operationCode;
    }

    public OperationCode getOperationCode() {
        return this.operationCode;
    }

    public Integer getVal1() {
        return this.val1;
    }

    public Integer getVal2() {
        return this.val2;
    }

    public Integer getVal3() {
        return val3;
    }

    public String toString() {
        String str = operationCode.toString();
        if (val1 != null) {
            str = str + " " + val1;
        }
        if (val2 != null) {
            str = str + " " + val2;
        }
        if (val3 != null){
            str = str + " "+ val3;
        }

        return str;
    }
}