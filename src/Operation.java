public class Operation {
    private OpCode opcode;
    private Integer val1;
    private Integer val2;
    private Integer val3;

    public Operation(OpCode opcode, Integer val1, Integer val2) {
        this.opcode = opcode;
        this.val1 = val1;
        this.val2 = val2;
    }

    public Operation(OpCode opcode, Integer val1, Integer val2, Integer val3) {
        this.opcode = opcode;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public Operation(OpCode opcode, Integer val1) {
        this.opcode = opcode;
        this.val1 = val1;
    }

    public Operation(OpCode opcode) {
        this.opcode = opcode;
    }

    public OpCode getOpcode() {
        return this.opcode;
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
        String str = opcode.toString();
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