package model.processor;

public class ArithmeticLogicUnit {

    //Rinstructions
    public String shiftLeftLogical(int rd, int rt, int shamt) {
        return "sll $" + rd + ", $" + rt + ", " + shamt;
    }

    public String shiftRightLogical(int rd, int rt, int shamt) {
        return "srl $" + rd + ", $" + rt + ", " + shamt;
    }

    public String shiftRightArithmetic(int rd, int rt, int shamt) {
        return "sra $" + rd + ", $" + rt + ", " + shamt;
    }

    public String shiftLeftLogicalVariable(int rd, int rt, int rs) {
        return "sllv $" + rd + ", $" + rt + ", $" + rs;
    }

    public String shiftRightLogicalVariable(int rd, int rt, int rs) {
        return "srlv $" + rd + ", $" + rt + ", $" + rs;
    }

    public String shiftRightArithmeticVariable(int rd, int rt, int rs) {
        return "srav $" + rd + ", $" + rt + ", $" + rs;
    }

    public String jumpRegister(int rs) {
        return "jr $" + rs;
    }

    public String systemCall(){
        return "syscall";
    }

    public String moveFromHI(int rd) {
        return "mfhi $" + rd;
    }

    public String moveFromLO(int rd) {
        return "mflo $" + rd;
    }

    public String multiply(int rs, int rt) {
        return "mult $" + rs + ", $" + rt;
    }

    public String multiplyUnsigned(int rs, int rt) {
        return "multu $" + rs + ", $" + rt;
    }

    public String divide(int rs, int rt) {
        return "div $" + rs + ", $" + rt;
    }

    public String divideUnsigned(int rs, int rt) {
        return "divu $" + rs + ", $" + rt;
    }

    public String add(int rd, int rs, int rt) {
        return "add $" + rd + ", $" + rs + ", $" + rt;
    }

    public String addUnsigned(int rd, int rs, int rt) {
        return "addu $" + rd + ", $" + rs + ", $" + rt;
    }

    public String subtract(int rd, int rs, int rt) {
        return "sub $" + rd + ", $" + rs + ", $" + rt;
    }

    public String subtractUnsigned(int rd, int rs, int rt) {
        return "subu $" + rd + ", $" + rs + ", $" + rt;
    }

    public String bitwiseAnd(int rd, int rs, int rt) {
        return "and $" + rd + ", $" + rs + ", $" + rt;
    }

    public String bitwiseOr(int rd, int rs, int rt) {
        return "or $" + rd + ", $" + rs + ", $" + rt;
    }

    public String bitwiseExclusiveOr(int rd, int rs, int rt) {
        return "xor $" + rd + ", $" + rs + ", $" + rt;
    }

    public String bitwiseNor(int rd, int rs, int rt) {
        return "nor $" + rd + ", $" + rs + ", $" + rt;
    }

    public String setLessThan(int rd, int rs, int rt) {
        return "slt $" + rd + ", $" + rs + ", $" + rt;
    }

    //Iinstructions
    public int address(int im, int pc){
        short imAux = (short) im; // Should be 16bits
        int signExtendedIm = imAux; // Java auto sign extends

        return pc + signExtendedIm * 4;
    }

    public String branchLessThanZero(int rs, int im, int pc){
        return "bltz $" + rs + ", " + address(im, pc);
    }

    public String branchEqual(int rs, int rt, int im, int pc){
        return "beq $" + rs + ", $" + rt + ", " + address(im, pc);
    }

    public String branchNotEqual(int rs, int rt, int im, int pc){
        return "bne $" + rs + ", $" + rt + ", " + address(im, pc);
    }

    public String addImmediate(int rt, int rs, int im){
        return "addi $" + rt + ", $" + rs + ", " + im;
    }

    public String addImmediateUnsigned(int rt, int rs, int im){
        return "addiu $" + rt + ", $" + rs + ", " + im;
    }

    public String setLessThanImmediate(int rt, int rs, int im){
        return "slti $" + rt + ", $" + rs + ", " + im;
    }

    public String bitwiseAndImmediate(int rt, int rs, int im){
        return "andi $" + rt + ", $" + rs + ", " + im;
    }

    public String bitwiseOrImmediate(int rt, int rs, int im){
        return "ori $" + rt + ", $" + rs + ", " + im;
    }

    public String bitwiseXorImmediate(int rt, int rs, int im){
        return "xori $" + rt + ", $" + rs + ", " + im;
    }

    public String loadUpperImmediate(int rt, int im){
        return "lui $" + rt + ", " + im;
    }

    public String loadByte(int rt, int rs, int im){
        return "lb $" + rt + ", " + im + "($" + rs + ")";
    }

    public String loadWord(int rt, int rs, int im){
        return "lw $" + rt + ", " + im + "($" + rs + ")";
    }

    public String loadByteUnsigned(int rt, int rs, int im){
        return "lbu $" + rt + ", " + im + "($" + rs + ")";
    }

    public String storeByte(int rt, int rs, int im){
        return "sb $" + rt + ", " + im + "($" + rs + ")";
    }

    public String storeWord(int rt, int rs, int im){
        return "sw $" + rt + ", " + im + "($" + rs + ")";
    }

    //Jinstructions
    public String jump(int address){
        return "j " + address;
    }

    public String jumpAndLink(int address){
        return "jal " + address;
    }
}
