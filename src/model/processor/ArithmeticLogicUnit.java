package model.processor;

// Performa as operações lógicas e aritméticas do processador
public class ArithmeticLogicUnit {

    // Rinstructions
    public int shiftLeftLogical(int rt, int shamt) {
        return rt << shamt;
    }

    public int shiftRightLogical(int rt, int shamt) {
        return rt >>> shamt;
    }

    public int shiftRightArithmetic(int rt, int shamt) {
        return rt >> shamt;
    }

    public int shiftLeftLogicalVariable(int rt, int rs) {
        return rt << rs;
    }

    public int shiftRightLogicalVariable(int rt, int rs) {
        return rt >>> rs;
    }

    public int shiftRightArithmeticVariable(int rt, int rs) {
        return rt >> rs;
    }

    public long multiply(int rs, int rt) {
        // TODO
        return (long) rs * (long) rt;
    }

    public long multiplyUnsigned(int rs, int rt) {

        // TODO
        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return rsu * rtu;
    }

    public int divide(int rs, int rt) {
        // TODO
        return rs / rt;
    }

    public int divideUnsigned(int rs, int rt) {
        // TODO
        return Integer.divideUnsigned(rs, rt);
    }

    public int add(int rs, int rt) {
        return rs + rt;
    }

    public int addUnsigned(int rs, int rt) {
        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return (int) (rsu + rtu);
    }

    public int subtract(int rs, int rt) {
        return rs - rt;
    }

    public int subtractUnsigned(int rs, int rt) {
        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return (int) (rsu - rtu);
    }

    public int bitwiseAnd(int rs, int rt) {
        return rs & rt;
    }

    public int bitwiseOr(int rs, int rt) {
        return rs | rt;
    }

    public int bitwiseExclusiveOr(int rs, int rt) {
        return rs ^ rt;
    }

    public int bitwiseNor(int rs, int rt) {
        return ~(rs | rt);
    }

    public int setLessThan(int rs, int rt) {
        return (rs < rt) ? 1 : 0;
    }

    public void branchLessThanZero(int rs, int im) {
        return "bltz $" + rs + ", " + im;
    }

    public void branchEqual(int rs, int rt, int im) {
        return "beq $" + rs + ", $" + rt + ", " + im;
    }

    public void branchNotEqual(int rs, int rt, int im) {
        return "bne $" + rs + ", $" + rt + ", " + im;
    }

    public int addImmediate(int rt, int rs, int im) {
        return "addi $" + rt + ", $" + rs + ", " + im;
    }

    public int addImmediateUnsigned(int rt, int rs, int im) {
        return "addiu $" + rt + ", $" + rs + ", " + im;
    }

    public int setLessThanImmediate(int rt, int rs, int im) {
        return "slti $" + rt + ", $" + rs + ", " + im;
    }

    public int bitwiseAndImmediate(int rt, int rs, int im) {
        return "andi $" + rt + ", $" + rs + ", " + im;
    }

    public int bitwiseOrImmediate(int rt, int rs, int im) {
        return "ori $" + rt + ", $" + rs + ", " + im;
    }

    public int bitwiseXorImmediate(int rt, int rs, int im) {
        return "xori $" + rt + ", $" + rs + ", " + im;
    }

    public int loadUpperImmediate(int rt, int im) {
        return "lui $" + rt + ", " + im;
    }

    public int loadByte(int rt, int rs, int im) {
        return "lb $" + rt + ", " + im + "($" + rs + ")";
    }

    public int loadWord(int rt, int rs, int im) {
        return "lw $" + rt + ", " + im + "($" + rs + ")";
    }

    public int loadByteUnsigned(int rt, int rs, int im) {
        return "lbu $" + rt + ", " + im + "($" + rs + ")";
    }

    public int storeByte(int rt, int rs, int im) {
        return "sb $" + rt + ", " + im + "($" + rs + ")";
    }

    public int storeWord(int rt, int rs, int im) {
        return "sw $" + rt + ", " + im + "($" + rs + ")";
    }
}
