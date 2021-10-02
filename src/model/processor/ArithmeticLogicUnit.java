package model.processor;

public class ArithmeticLogicUnit {

    public String shiftLeftLogical(int rd, int rt, int shamt) {
        return "sll $" + rd + ", $" + rt + ", " + shamt;
    }
}
