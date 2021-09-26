package model.instructions;

// Classe abstrata que define que uma instrução deve ao menos ter um código de operação
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
        return instruction >> 26;
    }
}
