/*
 * Projeto - MIPS
 *
 * Grupo:
 *
 * Henrique Sabino
 * Hyan Batista
 * Nelson Lins
 * Silas Augusto
 *
 */

package model.processor;

// Performa as operações lógicas e aritméticas do processador
public class ArithmeticLogicUnit {

    private RegisterBank registerBank;

    public ArithmeticLogicUnit(RegisterBank registerBank) {
        this.registerBank = registerBank;
    }

    // Funções que retornam instruções do tipo R
    public int shiftLeftLogical(int rt, int shamt) {

        rt = registerBank.getRegister(rt);
        return rt << shamt;
    }

    public int shiftRightLogical(int rt, int shamt) {

        rt = registerBank.getRegister(rt);
        return rt >>> shamt;
    }

    public int shiftRightArithmetic(int rt, int shamt) {

        rt = registerBank.getRegister(rt);
        return rt >> shamt;
    }

    public int shiftLeftLogicalVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt << rs;
    }

    public int shiftRightLogicalVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt >>> rs;
    }

    public int shiftRightArithmeticVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt >> rs;
    }

    public void multiply(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        long result = (long) rt * (long) rs;

        registerBank.setHI((int) (result >>> 32));
        registerBank.setLO((int) (result & 0xFFFFFFFF));
    }

    public void multiplyUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        long result = rsu * rtu;

        registerBank.setHI((int) (result >>> 32));
        registerBank.setLO((int) (result & 0xFFFFFFFF));
    }

    public void divide(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        if (rt != 0) {
            registerBank.setLO(rs / rt);
            registerBank.setHI(rs % rt);
        }
    }

    public void divideUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        if (rt != 0) {
            registerBank.setLO((int) Long.divideUnsigned(rs, rt));
            registerBank.setHI((int) Long.remainderUnsigned(rs, rt));
        }
    }

    public int add(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs + rt;
    }

    public int addUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return (int) (rsu + rtu);
    }

    public int subtract(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs - rt;
    }

    public int subtractUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return (int) (rsu - rtu);
    }

    public int bitwiseAnd(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs & rt;
    }

    public int bitwiseOr(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs | rt;
    }

    public int bitwiseExclusiveOr(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs ^ rt;
    }

    public int bitwiseNor(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return ~(rs | rt);
    }

    public int setLessThan(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return (rs < rt) ? 1 : 0;
    }

    // Funções que retornam instruções do tipo I
    public void branchLessThanZero(int rs, int im) {
    }

    public void branchEqual(int rs, int rt, int im) {
    }

    public void branchNotEqual(int rs, int rt, int im) {
    }

    public int addImmediate(int rs, int im) {
        return registerBank.getRegister(rs) + im;
    }

    public int addImmediateUnsigned(int rs, int im) {

        long rsu = Integer.toUnsignedLong(registerBank.getRegister(rs));
        long imu = Integer.toUnsignedLong(im);

        return (int) (rsu + imu);
    }

    public int setLessThanImmediate(int rs, int im) {
        return (registerBank.getRegister(rs) < im) ? 1 : 0;
    }

    public int bitwiseAndImmediate(int rs, int im) {
        return registerBank.getRegister(rs) & im;
    }

    public int bitwiseOrImmediate(int rs, int im) {
        return registerBank.getRegister(rs) | im;
    }

    public int bitwiseXorImmediate(int rs, int im) {
        return registerBank.getRegister(rs) ^ im;
    }

    public void loadUpperImmediate(int rt, int im) {
    }

    public void loadByte(int rt, int rs, int im) {
    }

    public void loadWord(int rt, int rs, int im) {
    }

    public void loadByteUnsigned(int rt, int rs, int im) {
    }

    public void storeByte(int rt, int rs, int im) {
    }

    public void storeWord(int rt, int rs, int im) {
    }
}
