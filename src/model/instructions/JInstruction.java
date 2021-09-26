package model.instructions;

public class JInstruction extends Instruction {

    // 000000 00000000000000000000000000
    // op ad

    public JInstruction(int instruction) {
        super(instruction);

        if (getOpcode() != 002 && getOpcode() != 003) {
            throw new IllegalArgumentException("Operation code must be 0b02 or 0b03");
        }
    }

    public int getAd() {
        return getInstruction() & 0x03FFFFFF;
    }

}
