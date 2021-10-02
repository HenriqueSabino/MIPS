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
            case 0x07:
                return "srav " + variableShift(rInstruction);
            case 0x08:
                return "jr " + jumopR(rInstruction);
            case 0x0c:
                return "syscall";
            case 0x10:
                return "mfhi " + oneRegisterR(rInstruction);
            case 0x12:
                return "mflo " + oneRegisterR(rInstruction);
            case 0x18:
                return "mult " + twoRegisterR(rInstruction);
            case 0x19:
                return "multu " + twoRegisterR(rInstruction);
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
            case 0x2a:
                return "slt " + threeRegisterR(rInstruction);

        }
        return Integer.toHexString(rInstruction.getFunction());
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
        return "$" + rInstruction.getRd() + ", $" + rInstruction.getRt() + ", " + rInstruction.getShamt();
    }

    private String jumopR(RInstruction rInstruction) {
        return "$" + rInstruction.getRs();
    }

    private String assemblyIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;

        switch (iInstruction.getOpcode()) {
            case 0x01: // 1
                return "bltz " + branchOneRegisterI(iInstruction);
            case 0x04: // 4
                return "beq " + banchIInstruction(iInstruction);
            case 0x05: // 5
                return "bne " + banchIInstruction(iInstruction);
            case 0x08: // 8
                return "addi " + twoRegisterI(iInstruction);
            case 0x09: // 9
                return "addiu " + twoRegisterI(iInstruction);
            case 0x0a: // 10
                return "slti " + twoRegisterI(iInstruction);
            case 0x0c: // 12
                return "andi " + twoRegisterI(iInstruction);
            case 0x0d: // 13
                return "ori " + twoRegisterI(iInstruction);
            case 0x0e: // 14
                return "xori " + twoRegisterI(iInstruction);
            case 0x0f: // 15
                return "lui " + loadIInstruction(iInstruction);
            case 0x20: // 32
                return "lb " + displacementIInstruction(iInstruction);
            case 0x23: // 35
                return "lw " + displacementIInstruction(iInstruction);
            case 0x24: // 36
                return "lbu " + displacementIInstruction(iInstruction);
            case 0x28: // 40
                return "sb" + displacementIInstruction(iInstruction);
            case 0x2b: // 43
                return "sw " + displacementIInstruction(iInstruction);
        }

        return Integer.toHexString(iInstruction.getOpcode());
    }

    private String twoRegisterI(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", $" + iInstruction.getRs() + ", " + iInstruction.getIm();
    }

    private String banchIInstruction(IInstruction iInstruction) {
        return "$" + iInstruction.getRs() + ", $" + iInstruction.getRt() + ", " + iInstruction.getIm();
    }

    private String branchOneRegisterI(IInstruction iInstruction) {
        return "$" + iInstruction.getRs() + ", " + iInstruction.getIm();
    }

    private String displacementIInstruction(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", " + iInstruction.getIm() + "($" + iInstruction.getRs() + ")";
    }

    private String loadIInstruction(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", " + iInstruction.getIm();
    }

    private String assemblyJInstruction() {

        JInstruction jInstruction = (JInstruction) currentInstruction;

        switch (jInstruction.getOpcode()) {
            case 0x02: // 2
                return "";
            case 0x03: // 3
                return "";
        }
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