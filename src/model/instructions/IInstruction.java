package model.instructions;

// Molde das instruções tipo I
public class IInstruction extends Instruction {

    // 000000 00000 00000 0000000000000000
    // op rs rt im

    public IInstruction(int instruction) {
        super(instruction);

        if (getOpcode() == 003 || getOpcode() == 002 || getOpcode() == 000) {
            throw new IllegalArgumentException("Operation code must not be 0b00 or less than 0b03");
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

    // Retorna o valor do operando ou offset da instrução
    public int getIm() {
        return (getInstruction() & 0x0000FFFF);
    }

}
