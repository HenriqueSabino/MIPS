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

// Armazena o conjunto de registradores
public class RegisterBank {

    private int[] registers;
    private int PC;
    private int HI;
    private int LO;

    public RegisterBank() {
        // 32 MIPS registers
        registers = new int[32];
        PC = 0;
        HI = 0;
        LO = 0;
    }

    public int getRegister(int id) {
        return registers[id];
    }

    public void setRegister(int id, int value) {
        if (id != 0)
            registers[id] = value;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int pC) {
        PC = pC;
    }

    public int getHI() {
        return HI;
    }

    public void setHI(int hI) {
        HI = hI;
    }

    public int getLO() {
        return LO;
    }

    public void setLO(int lO) {
        LO = lO;
    }

}
