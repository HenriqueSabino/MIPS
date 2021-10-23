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

// Molde das instruções tipo J
public class JInstruction extends Instruction {

    // 000000 00000000000000000000000000
    // op ad

    // Construtor das instruções tipo J, retorna uma exceção caso o OpCode seja diferente de 002 ou 003
    public JInstruction(int instruction) {
        super(instruction);

        if (getOpcode() != 002 && getOpcode() != 003) {
            throw new IllegalArgumentException("Operation code must be 0b02 or 0b03");
        }
    }

    // Retorna o valor do endereço da instrução
    public int getAd() {
        return getInstruction() & 0x03FFFFFF;
    }

}
