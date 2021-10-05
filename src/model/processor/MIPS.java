package model.processor;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MIPS {

    RegisterBank registerBank;
    ArithmeticLogicUnit alu;
    InstructionMemory instructionMemory;
    ControlUnit controlUnit;

    public MIPS(String instructionPath) throws FileNotFoundException, IOException {

        registerBank = new RegisterBank();
        alu = new ArithmeticLogicUnit();
        instructionMemory = new InstructionMemory(instructionPath);
        controlUnit = new ControlUnit(registerBank, instructionMemory, alu);
    }

    public void runInstruction() {
        controlUnit.runInstruction();
    }

    public String currentInstruction() {
        return "";
    }

    public String registerDump() {
        return "";
    }
}