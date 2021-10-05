package model.processor;

public class RegisterBank {

    private int[] registers;

    public RegisterBank() {
        // 32 MIPS registers
        registers = new int[32];
    }

    public int getRegister(int id) {
        return registers[id];
    }

    public void setRegister(int id, int value) {
        registers[id] = value;
    }
}
