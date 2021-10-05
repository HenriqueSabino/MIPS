package model.processor;

import model.instructions.IInstruction;
import model.instructions.Instruction;
import model.instructions.JInstruction;
import model.instructions.RInstruction;

// Controla o fluxo dos dados do processador
public class ControlUnit {

    private InstructionMemory instructionMemory;
    private ArithmeticLogicUnit alu;
    private RegisterBank registerBank;
    private Instruction currentInstruction;
    private String currentInstructionStr;

    int PC = 0x00000000; // ponteiro da instrução atual

    public ControlUnit(RegisterBank registerBank, InstructionMemory instructionMemory, ArithmeticLogicUnit alu) {

        this.instructionMemory = instructionMemory;
        this.alu = alu;
        this.registerBank = registerBank;
    }

    // Roda as instruções lidas na memória
    public void runInstruction() {

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
                run();
            }

        } catch (Exception e) {
            currentInstruction = null;
        }
    }

    private void run() {
        if (currentInstruction.getClass() == RInstruction.class) {
            runRInstruction();
        } else if (currentInstruction.getClass() == IInstruction.class) {
            runIInstruction();
        } else {
            runJInstruction();
        }
    }

    private void runRInstruction() {

        RInstruction rInstruction = (RInstruction) currentInstruction;
        int rd = rInstruction.getRd();
        int rs = rInstruction.getRs();
        int rt = rInstruction.getRs();
        int shamt = rInstruction.getShamt();

        switch (rInstruction.getFunction()) {
            case 0x00:
                currentInstructionStr = "sll $" + rd + ", $" + rt + ", " + shamt;

                registerBank.setRegister(rInstruction.getRd(),
                        alu.shiftLeftLogical(registerBank.getRegister(rt), registerBank.getRegister(shamt)));
                break;
            case 0x02:
                currentInstructionStr = "srl $" + rd + ", $" + rt + ", " + shamt;

                registerBank.setRegister(rd,
                        alu.shiftRightLogical(registerBank.getRegister(rt), registerBank.getRegister(shamt)));
                break;
            case 0x03:
                currentInstructionStr = "sra $" + rd + ", $" + rt + ", " + shamt;

                registerBank.setRegister(rd,
                        alu.shiftRightArithmetic(registerBank.getRegister(rt), registerBank.getRegister(shamt)));
                break;
            case 0x04:
                currentInstructionStr = "sllv $" + rd + ", $" + rt + ", $" + rs;

                registerBank.setRegister(rd,
                        alu.shiftLeftLogicalVariable(registerBank.getRegister(rt), registerBank.getRegister(rs)));
                break;
            case 0x06:
                currentInstructionStr = "srlv $" + rd + ", $" + rt + ", $" + rs;

                registerBank.setRegister(rd,
                        alu.shiftRightLogicalVariable(registerBank.getRegister(rt), registerBank.getRegister(rs)));
                break;
            case 0x07:
                currentInstructionStr = "srav $" + rd + ", $" + rt + ", $" + rs;

                registerBank.setRegister(rd,
                        alu.shiftRightArithmeticVariable(registerBank.getRegister(rt), registerBank.getRegister(rs)));
                break;
            case 0x08:
                currentInstructionStr = "jr $" + rs;
                break;
            case 0x0c:
                currentInstructionStr = "syscall";
                break;
            case 0x10:
                currentInstructionStr = "mfhi $" + rd;
                // TODO
                break;
            case 0x12:
                currentInstructionStr = "mflo $" + rd;
                // TODO
                break;
            case 0x18:
                currentInstructionStr = "mult $" + rs + ", $" + rt;

                long result = alu.multiply(registerBank.getRegister(rs), registerBank.getRegister(rt));
                // TODO: set HI and LO
                break;
            case 0x19:
                currentInstructionStr = "multu $" + rs + ", $" + rt;

                result = alu.multiplyUnsigned(registerBank.getRegister(rs), registerBank.getRegister(rt));
                // TODO: set HI and LO
                break;
            case 0x1a:
                currentInstructionStr = "div $" + rs + ", $" + rt;

                alu.divide(registerBank.getRegister(rs), registerBank.getRegister(rt));
                // TODO
                break;
            case 0x1b:
                currentInstructionStr = "divu $" + rs + ", $" + rt;

                alu.divideUnsigned(registerBank.getRegister(rs), registerBank.getRegister(rt));
                // TODO
                break;
            case 0x20:
                currentInstructionStr = "add $" + rd + ", $" + rs + ", $" + rt;

                registerBank.setRegister(rd, alu.add(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x21:
                currentInstructionStr = "addu $" + rd + ", $" + rs + ", $" + rt;

                registerBank.setRegister(rd,
                        alu.addUnsigned(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x22:
                currentInstructionStr = "sub $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd, alu.subtract(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x23:
                currentInstructionStr = "sub $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd,
                        alu.subtractUnsigned(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x24:
                currentInstructionStr = "and $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd,
                        alu.bitwiseAnd(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x25:
                currentInstructionStr = "or $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd, alu.bitwiseOr(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x26:
                currentInstructionStr = "xor $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd,
                        alu.bitwiseExclusiveOr(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x27:
                currentInstructionStr = "nor $" + rd + ", $" + rs + ", $" + rt;
                registerBank.setRegister(rd,
                        alu.bitwiseNor(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
            case 0x2a:
                currentInstructionStr = "slt $" + rd + ", $" + rs + ", $" + rt;

                registerBank.setRegister(rd,
                        alu.setLessThan(registerBank.getRegister(rs), registerBank.getRegister(rt)));
                break;
        }
    }

    private void runIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;

        switch (iInstruction.getOpcode()) {
            case 0x01: // 1
                alu.branchLessThanZero(iInstruction.getRs(), address(iInstruction.getIm()));
                break;
            case 0x04: // 4
                alu.branchEqual(iInstruction.getRs(), iInstruction.getRt(), address(iInstruction.getIm()));
                break;
            case 0x05: // 5
                alu.branchNotEqual(iInstruction.getRs(), iInstruction.getRt(), address(iInstruction.getIm()));
                break;
            case 0x08: // 8
                alu.addImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x09: // 9
                alu.addImmediateUnsigned(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x0a: // 10
                alu.setLessThanImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x0c: // 12
                alu.bitwiseAndImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x0d: // 13
                alu.bitwiseOrImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x0e: // 14
                alu.bitwiseXorImmediate(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x0f: // 15
                alu.loadUpperImmediate(iInstruction.getRt(), signExtend(iInstruction.getIm()));
                break;
            case 0x20: // 32
                alu.loadByte(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x23: // 35
                alu.loadWord(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x24: // 36
                alu.loadByteUnsigned(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x28: // 40
                alu.storeByte(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
            case 0x2b: // 43
                alu.storeWord(iInstruction.getRt(), iInstruction.getRs(), signExtend(iInstruction.getIm()));
                break;
        }
    }

    // Calcula os endereços de instruções de desvio
    public int address(int im) {
        return PC + signExtend(im) * 4;
    }

    private int signExtend(int im) {
        short imShort = (short) im; // 16bits
        return imShort; // Java extende o sinal automaticamente nesse cast
    }

    private void runJInstruction() {

        JInstruction jInstruction = (JInstruction) currentInstruction;

        // Cálculo do endereço para desvio
        int address = (PC & 0xF0000000) | ((jInstruction.getAd() << 2));

        switch (jInstruction.getOpcode()) {
            case 0x02: // 2
                currentInstructionStr = "j " + address;
                break;
            case 0x03: // 3
                currentInstructionStr = "jal " + address;
                break;
        }
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