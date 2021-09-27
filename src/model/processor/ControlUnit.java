package model.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.instructions.IInstruction;
import model.instructions.Instruction;
import model.instructions.JInstruction;
import model.instructions.RInstruction;

public class ControlUnit {

    // arquivo de entrada
    private File instructionMemory;
    private FileReader fr;
    private BufferedReader br;

    String currentInstructionStr;
    Instruction currentInstruction;

    public ControlUnit() throws FileNotFoundException {

        instructionMemory = new File("entrada.txt");
        fr = new FileReader(instructionMemory);
        br = new BufferedReader(fr);
    }

    public boolean hasNextInstruction() {
        return currentInstructionStr != null;
    }

    public void readInstruction() {

        try {
            currentInstructionStr = br.readLine();

            if (currentInstructionStr != null) {
                int instruction = Integer.parseUnsignedInt(currentInstructionStr.substring(2), 16);

                decodeInstruction(instruction);
            }

        } catch (IOException e) {
            currentInstruction = null;
        }
    }

    public String assemblyInstruction() {
        if (currentInstruction.getClass() == RInstruction.class) {
            return assemblyRInstruction();
        } else if (currentInstruction.getClass() == IInstruction.class) {
            return assemblyIInstruction();
        } else {
            return assemblyJInstruction();
        }
    }

    private String assemblyRInstruction() {

        RInstruction rInstruction = (RInstruction) currentInstruction;

        switch (rInstruction.getFunction()) {
            case 0x00:
                return "sll " + shiftInstruction(rInstruction);
            case 0x02:
                return "srl " + shiftInstruction(rInstruction);
            case 0x03:
                return "sra " + shiftInstruction(rInstruction);
            case 0x04:
                return "sllv " + variableShift(rInstruction);
            case 0x06:
                return "srlv " + variableShift(rInstruction);
            case 0x08:
                return "jr " + oneRegisterR(rInstruction);
            case 0x10:
                return "mfhi " + oneRegisterR(rInstruction);
            case 0x12:
                return "mflo " + oneRegisterR(rInstruction);
            case 0x1a:
                return "div " + twoRegisterR(rInstruction);
            case 0x1b:
                return "divu " + twoRegisterR(rInstruction);
            case 0x20:
                return "add " + threeRegisterR(rInstruction);
            case 0x21:
                return "addu " + threeRegisterR(rInstruction);
            case 0x22:
                return "sub " + threeRegisterR(rInstruction);
            case 0x23:
                return "subu " + threeRegisterR(rInstruction);
            case 0x24:
                return "and " + threeRegisterR(rInstruction);
            case 0x25:
                return "or " + threeRegisterR(rInstruction);
            case 0x26:
                return "xor " + threeRegisterR(rInstruction);
            case 0x27:
                return "nor " + threeRegisterR(rInstruction);

        }
        return "";
    }

    private String threeRegisterR(RInstruction rInstruction) {
        return "$" + rInstruction.getRd() + ", $" + rInstruction.getRs() + ", $" + rInstruction.getRt();
    }

    private String twoRegisterR(RInstruction rInstruction) {
        return "$" + rInstruction.getRs() + ", $" + rInstruction.getRt();
    }

    private String oneRegisterR(RInstruction rInstruction) {
        return "$" + rInstruction.getRd();
    }

    private String variableShift(RInstruction rInstruction) {
        return "$" + rInstruction.getRd() + ", $" + rInstruction.getRt() + ", $" + rInstruction.getRs();
    }

    private String shiftInstruction(RInstruction rInstruction) {
        return "$" + rInstruction.getRd() + ", $" + rInstruction.getRs() + ", " + rInstruction.getShamt();
    }

    private String assemblyIInstruction() {
        return "";
    }

    private String assemblyJInstruction() {
        return "";
    }

    private void decodeInstruction(int instruction) {

        try {
            currentInstruction = new RInstruction(instruction);
        } catch (IllegalArgumentException e) {

            try {
                currentInstruction = new IInstruction(instruction);
            } catch (IllegalArgumentException e2) {

                try {
                    currentInstruction = new JInstruction(instruction);
                } catch (IllegalArgumentException e3) {
                    e.printStackTrace();
                }
            }
        }
    }
}