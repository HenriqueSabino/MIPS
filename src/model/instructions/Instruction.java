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

// Classe que define que uma instrução deve ao menos ter um código de operação
public class Instruction {

    private int instruction;

    public Instruction(int instruction) {
        this.instruction = instruction;
    }

    public int getInstruction() {
        return instruction;
    }

    // Retorna o OpCode da instrução
    public int getOpcode() {
        // Java não tem unsigned int, então temos que forçar a interpretação utilizando
        // toUnsignedLong
        return instruction >>> 26;
    }
}
