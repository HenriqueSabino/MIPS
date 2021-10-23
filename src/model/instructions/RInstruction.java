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

package model.instructions;

// Molde das instruções tipo R
public class RInstruction extends Instruction {

    // 000000 00000 00000 00000 00000 000000
    // op rs rt rd sa ft

    // Construtor das instruções tipo R, retorna uma exceção caso o OpCode seja diferente de 000
    public RInstruction(int instruction) {
        super(instruction);

        if (getOpcode() != 000) {
            throw new IllegalArgumentException("Operation code must be 0b00");
        }
    }

    // Retorna o Registrador de origem 1 da instrução
    public int getRs() {
        return (getInstruction() & 0x03E00000) >>> 21;
    }

    // Retorna o Registrador de origem 2 da instrução
    public int getRt() {
        return (getInstruction() & 0x001F0000) >>> 16;
    }

    // Retorna o Registrador de destino da instrução
    public int getRd() {
        return (getInstruction() & 0x0000F800) >>> 11;
    }

    // Retorna a quantidade de shift da instrução
    public int getShamt() {
        return (getInstruction() & 0x000007C0) >>> 6;
    }

    // Retorna os últimos 6 bits da instrução
    public int getFunction() {
        return getInstruction() & 0x0000003F;
    }
}
