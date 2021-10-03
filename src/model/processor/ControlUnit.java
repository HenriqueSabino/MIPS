package model.processor;

import model.instructions.IInstruction;
import model.instructions.Instruction;
import model.instructions.JInstruction;
import model.instructions.RInstruction;

import java.io.FileNotFoundException;
import java.io.IOException;

// Controla o fluxo dos dados do processador
public class ControlUnit {

    InstructionMemory instructionMemory;
    ArithmeticLogicUnit alu;
    Instruction currentInstruction;

    int PC = 0x00000000; // ponteiro da instrução atual

    public ControlUnit(String inputMemotyPath) throws FileNotFoundException, IOException {
        instructionMemory = new InstructionMemory(inputMemotyPath);
        alu = new ArithmeticLogicUnit();
    }

    // Roda as instruções lidas na memória
    public String runInstruction() {

        try {
            int instruction = Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 1));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 2));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 3));

            decodeInstruction(instruction);

            if (currentInstruction != null) {
                PC += 4; // Próxima instrução
                return run();
            } else {
                return "";
            }

        } catch (Exception e) {
            currentInstruction = null;
            return "";
        }
    }

    private String run() {
        if (currentInstruction.getClass() == RInstruction.class) {
            return runRInstruction();
        } else if (currentInstruction.getClass() == IInstruction.class) {
            return runIInstruction();
        } else {
            return runJInstruction();
        }
    }

    private String runRInstruction() {

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
                return alu.shiftRightArithmeticVariable(rInstruction.getRd(), rInstruction.getRt(),
                        rInstruction.getRs());
            case 0x08:
                return alu.jumpRegister(rInstruction.getRs());
            case 0x0c:
                return alu.systemCall();
            case 0x10:
                return alu.moveFromHI(rInstruction.getRd());
            case 0x12:
                return alu.moveFromLO(rInstruction.getRd());
            case 0x18:
                return alu.multiply(rInstruction.getRs(), rInstruction.getRt());
            case 0x19:
                return alu.multiplyUnsigned(rInstruction.getRs(), rInstruction.getRt());
            case 0x1a:
                return alu.divide(rInstruction.getRs(), rInstruction.getRt());
            case 0x1b:
                return alu.divideUnsigned(rInstruction.getRs(), rInstruction.getRt());
            case 0x20:
                return alu.add(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x21:
                return alu.addUnsigned(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x22:
                return alu.subtract(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x23:
                return alu.subtractUnsigned(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x24:
                return alu.bitwiseAnd(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x25:
                return alu.bitwiseOr(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x26:
                return alu.bitwiseExclusiveOr(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x27:
                return alu.bitwiseNor(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());
            case 0x2a:
                return alu.setLessThan(rInstruction.getRd(), rInstruction.getRs(), rInstruction.getRt());

        }
        return "";
    }

    private String runIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;

        switch (iInstruction.getOpcode()) {
            case 0x01: // 1
                return alu.branchLessThanZero(iInstruction.getRs(), address(iInstruction.getIm()), PC);
            case 0x04: // 4
                return alu.branchEqual(iInstruction.getRs(), iInstruction.getRt(), address(iInstruction.getIm()), PC);
            case 0x05: // 5
                return alu.branchNotEqual(iInstruction.getRs(), iInstruction.getRt(), address(iInstruction.getIm()),
                        PC);
            case 0x08: // 8
                return alu.addImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
            case 0x09: // 9
                return alu.addImmediateUnsigned(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x0a: // 10
                return alu.setLessThanImmediate(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x0c: // 12
                return alu.bitwiseAndImmediate(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x0d: // 13
                return alu.bitwiseOrImmediate(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x0e: // 14
                return alu.bitwiseXorImmediate(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x0f: // 15
                return alu.loadUpperImmediate(iInstruction.getRt(), signExtend(iInstruction.getIm()));
            case 0x20: // 32
                return alu.loadByte(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
            case 0x23: // 35
                return alu.loadWord(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
            case 0x24: // 36
                return alu.loadByteUnsigned(iInstruction.getRt(), iInstruction.getRs(),
                        signExtend(iInstruction.getIm()));
            case 0x28: // 40
                return alu.storeByte(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
            case 0x2b: // 43
                return alu.storeWord(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
        }

        return "";
    }

    // Calcula os endereços de instruções de desvio
    public int address(int im) {
        return PC + signExtend(im) * 4;
    }

    private int signExtend(int im) {
        short imShort = (short) im; // 16bits
        return imShort; // Java extende o sinal automaticamente nesse cast
    }

    private String runJInstruction() {

        JInstruction jInstruction = (JInstruction) currentInstruction;

        // Cálculo do endereço para desvio
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