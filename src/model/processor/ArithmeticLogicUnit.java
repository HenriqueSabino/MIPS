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

    // >>> corresponde ao shiftRightLogical do Java
    public int shiftRightLogical(int rt, int shamt) {

        rt = registerBank.getRegister(rt);
        return rt >>> shamt;
    }

    // >> corresponde ao shiftRightArithmetic do Java
    public int shiftRightArithmetic(int rt, int shamt) {

        rt = registerBank.getRegister(rt);
        return rt >> shamt;
    }

    public int shiftLeftLogicalVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt << rs;
    }

    // >>> corresponde ao shiftRightLogical do Java
    public int shiftRightLogicalVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt >>> rs;
    }

    // >> corresponde ao shiftRightArithmetic do Java
    public int shiftRightArithmeticVariable(int rt, int rs) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);
        return rt >> rs;
    }

    public void multiply(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        // Capturando o resultado em um long
        long result = (long) rt * (long) rs;

        // Definindo HI como a parte superior do resultado
        registerBank.setHI((int) (result >>> 32));
        // Definindo LO como a parte inferior do resultado
        registerBank.setLO((int) (result & 0xFFFFFFFF));
    }

    public void multiplyUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        // Transformando unsigned int em long
        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        // Capturando o resultado em um long
        long result = rsu * rtu;

        // Definindo HI como a parte superior do resultado
        registerBank.setHI((int) (result >>> 32));
        // Definindo LO como a parte superior do resultado
        registerBank.setLO((int) (result & 0xFFFFFFFF));
    }

    public void divide(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        // Previnindo a excessão de dividir por 0
        if (rt != 0) {

            // Definindo LO como o quociente
            registerBank.setLO(rs / rt);
            // Definindo HI como o resto
            registerBank.setHI(rs % rt);
        }
    }

    public void divideUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        // Previnindo a excessão de dividir por 0
        if (rt != 0) {

            // Definindo LO como o quociente sem sinal
            registerBank.setLO((int) Long.divideUnsigned(rs, rt));
            // Definindo HI como o resto sem sinal
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

        // Transformando unsigned int em long
        long rsu = Integer.toUnsignedLong(rs);
        long rtu = Integer.toUnsignedLong(rt);

        return (int) (rsu + rtu);
    }

    public int subtract(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return rs - rt;
    }

    public int subtractUnsigned(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        // Transformando unsigned int em long
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

    // Retorna 1 caso rs seja menor que rt, 0 caso contrário
    public int setLessThan(int rs, int rt) {

        rt = registerBank.getRegister(rt);
        rs = registerBank.getRegister(rs);

        return (rs < rt) ? 1 : 0;
    }

    // Operações do tipo I

    // Desvios condicionais
    public void branchLessThanZero(int rs, int im) {
    }

    public void branchEqual(int rs, int rt, int im) {
    }

    public void branchNotEqual(int rs, int rt, int im) {
    }

    // Operações lógicas ou aritméticas
    public int addImmediate(int rs, int im) {
        return registerBank.getRegister(rs) + im;
    }

    // Adição com os dois operandos sem sinal
    public int addImmediateUnsigned(int rs, int im) {

        long rsu = Integer.toUnsignedLong(registerBank.getRegister(rs));
        long imu = Integer.toUnsignedLong(im);

        return (int) (rsu + imu);
    }

    // Retorna 1 caso rs seja menor que im, 0 caso contrário
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
}
