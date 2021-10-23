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
        // 32 registradores do MIPS
        registers = new int[32];

        // Registradores especiais
        PC = 0;
        HI = 0;
        LO = 0;
    }

    // Retorna o valor do registrador de índice id
    public int getRegister(int id) {
        return registers[id];
    }

    // Define o valor do registrador de índice id para value
    public void setRegister(int id, int value) {
        if (id != 0)
            registers[id] = value;
    }

    // Retorna o PC atual
    public int getPC() {
        return PC;
    }

    // Defina um novo valor para PC
    public void setPC(int PC) {
        this.PC = PC;
    }

    // Retorna o valor de HI
    public int getHI() {
        return HI;
    }

    // Device um novo valor para HI
    public void setHI(int HI) {
        this.HI = HI;
    }

    // Retorna o valor de LO
    public int getLO() {
        return LO;
    }

    // Device um novo valor para LO
    public void setLO(int LO) {
        this.LO = LO;
    }

}
