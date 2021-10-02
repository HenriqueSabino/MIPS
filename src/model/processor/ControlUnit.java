package model.processor;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.instructions.IInstruction;
import model.instructions.Instruction;
import model.instructions.JInstruction;
import model.instructions.RInstruction;

public class ControlUnit {

    InstructionMemory instructionMemory;
    ArithmeticLogicUnit alu;
    Instruction currentInstruction;

    int PC = 0x00000000;

    public ControlUnit() throws FileNotFoundException, IOException {
        instructionMemory = new InstructionMemory();
        alu = new ArithmeticLogicUnit();
    }

    public boolean hasNextInstruction() {
        return currentInstruction != null;
    }

    public void readInstruction() {

        try {
            int instruction = Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 1));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 2));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 3));

            PC += 4;
            decodeInstruction(instruction);
        } catch (Exception e) {
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
                return alu.shiftLeftLogical(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getShamt());
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
                return "jr " + jumpR(rInstruction);
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
        return "$" + rInstruction.getRd() + ", $" + rInstruction.getRt() + ", " + rInstruction.getShamt();
    }

    private String jumpR(RInstruction rInstruction) {
        return "$" + rInstruction.getRs();
    }

    private String assemblyIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;

        switch (iInstruction.getOpcode()) {
            case 0x01: // 1
                return "bltz " + branchOneRegisterI(iInstruction);
            case 0x04: // 4
                return "beq " + branchIInstruction(iInstruction);
            case 0x05: // 5
                return "bne " + branchIInstruction(iInstruction);
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
                return "sb " + displacementIInstruction(iInstruction);
            case 0x2b: // 43
                return "sw " + displacementIInstruction(iInstruction);
        }

        return "";
    }

    private String twoRegisterI(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", $" + iInstruction.getRs() + ", " + iInstruction.getIm();
    }

    private String branchIInstruction(IInstruction iInstruction) {
        short im = (short) iInstruction.getIm(); // Should be 16bits -18 (ints)
        int signExtendedIm = im; // Java auto signextends

        int address = PC + signExtendedIm * 4;

        return "$" + iInstruction.getRs() + ", $" + iInstruction.getRt() + ", " + address;
    }

    private String branchOneRegisterI(IInstruction iInstruction) {
        short im = (short) iInstruction.getIm(); // Should be 16bits
        int signExtendedIm = im; // Java auto sign extends

        int address = PC + signExtendedIm * 4;

        return "$" + iInstruction.getRs() + ", " + address;
    }

    private String displacementIInstruction(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", " + iInstruction.getIm() + "($" + iInstruction.getRs() + ")";
    }

    private String loadIInstruction(IInstruction iInstruction) {
        return "$" + iInstruction.getRt() + ", " + iInstruction.getIm();
    }

    private String assemblyJInstruction() {

        JInstruction jInstruction = (JInstruction) currentInstruction;

        int address = (PC & 0xF0000000) | ((jInstruction.getAd() << 2));

        switch (jInstruction.getOpcode()) {
            case 0x02: // 2
                return "j " + address;
            case 0x03: // 3
                return "jal " + address;
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