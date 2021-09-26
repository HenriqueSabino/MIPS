package model.instructions;

public class RInstruction extends Instruction {

    // 000000 00000 00000 00000 00000 000000
    // op rs rt rd sa ft

    public RInstruction(int instruction) {
        super(instruction);

        if (getOpcode() != 000) {
            throw new IllegalArgumentException("Operation code must be 0b00");
        }
    }

    // Retorna o Registrador de origem 1 da instrução
    public int getRs() {
        return (getInstruction() & 000370000000) >> 21;
    }

    // Retorna o Registrador de origem 2 da instrução
    public int getRt() {
        return (getInstruction() & 000007600000) >> 16;
    }

    // Retorna o Registrador de destino da instrução
    public int getRd() {
        return (getInstruction() & 000000174000) >> 11;
    }

    // Retorna a quantidade de shift da instrução
    public int getShamt() {
        return (getInstruction() & 000000003700) >> 6;
    }

    // Retorna os últimos 6 bits da instrução
    public int getFunction() {
        return getInstruction() & 000000000077;
    }
}
