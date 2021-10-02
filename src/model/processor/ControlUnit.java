package model.processor;

import model.instructions.IInstruction;
import model.instructions.Instruction;
import model.instructions.JInstruction;
import model.instructions.RInstruction;

import java.io.FileNotFoundException;
import java.io.IOException;

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
                return alu.shiftRightLogical(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getShamt());
            case 0x03:
                return alu.shiftRightArithmetic(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getShamt());
            case 0x04:
                return alu.shiftLeftLogicalVariable(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getRs());
            case 0x06:
                return alu.shiftRightLogicalVariable(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getRs());
            case 0x07:
                return alu.shiftRightArithmeticVariable(rInstruction.getRd(), rInstruction.getRt(), rInstruction.getRs());
            case 0x08:
                return alu.jumpRegister(rInstruction.getRs());
            case 0x0c:
                return alu.systemCall();
            case 0x10:
                return alu.moveFromHI(rInstruction.getRd());
            case 0x12:
                return alu.moveFromLO(rInstruction.getRd());
            case 0x18:
                return alu.multiply(rInstruction.getRs(),rInstruction.getRt());
            case 0x19:
                return alu.multiplyUnsigned(rInstruction.getRs(),rInstruction.getRt());
            case 0x1a:
                return alu.divide(rInstruction.getRs(),rInstruction.getRt());
            case 0x1b:
                return alu.divideUnsigned(rInstruction.getRs(),rInstruction.getRt());
            case 0x20:
                return alu.add(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x21:
                return alu.addUnsigned(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x22:
                return alu.subtract(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x23:
                return alu.subtractUnsigned(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x24:
                return alu.bitwiseAnd(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x25:
                return alu.bitwiseOr(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x26:
                return alu.bitwiseExclusiveOr(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x27:
                return alu.bitwiseNor(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());
            case 0x2a:
                return alu.setLessThan(rInstruction.getRd(),rInstruction.getRs(),rInstruction.getRt());

        }
        return "";
    }

    private String assemblyIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;

        switch (iInstruction.getOpcode()) {
            case 0x01: // 1
                return alu.branchLessThanZero(iInstruction.getRs(),iInstruction.getIm(), PC);
            case 0x04: // 4
                return alu.branchEqual(iInstruction.getRs(),iInstruction.getRt(),iInstruction.getIm(), PC);
            case 0x05: // 5
                return alu.branchNotEqual(iInstruction.getRs(),iInstruction.getRt(),iInstruction.getIm(), PC);
            case 0x08: // 8
                return alu.addImmediate(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x09: // 9
                return alu.addImmediateUnsigned(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x0a: // 10
                return alu.setLessThanImmediate(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x0c: // 12
                return alu.bitwiseAndImmediate(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x0d: // 13
                return alu.bitwiseOrImmediate(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x0e: // 14
                return alu.bitwiseXorImmediate(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x0f: // 15
                return alu.loadUpperImmediate(iInstruction.getRt(),iInstruction.getIm());
            case 0x20: // 32
                return alu.loadByte(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x23: // 35
                return alu.loadWord(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x24: // 36
                return alu.loadByteUnsigned(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x28: // 40
                return alu.storeByte(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
            case 0x2b: // 43
                return alu.storeWord(iInstruction.getRt(),iInstruction.getRs(),iInstruction.getIm());
        }

        return "";
    }

    private String assemblyJInstruction() {

        JInstruction jInstruction = (JInstruction) currentInstruction;

        int address = (PC & 0xF0000000) | ((jInstruction.getAd() << 2));

        switch (jInstruction.getOpcode()) {
            case 0x02: // 2
                return alu.jump(address);
            case 0x03: // 3
                return alu.jumpAndLink(address);
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