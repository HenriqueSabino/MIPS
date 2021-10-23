/*
 * Projeto - MIPS
 *
 * Grupo:
 *
 * Henrique Sabino
 * Hyan Batista
 * Nelson Lins
 * Silas Augusto
 *
 */

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

    // Variável utilizada para determinar caso o programa deve imprimir a dump dos
    // registradores
    private boolean hasRegisterDump;

    public ControlUnit(RegisterBank registerBank, InstructionMemory instructionMemory, ArithmeticLogicUnit alu) {

        this.instructionMemory = instructionMemory;
        this.alu = alu;
        this.registerBank = registerBank;
    }

    // Roda as instruções lidas na memória
    public void runInstruction() {

        try {
            int PC = registerBank.getPC();

            int instruction = Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 1));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 2));
            instruction = (instruction << 8)
                    | (int) Byte.toUnsignedInt(instructionMemory.getInstructionMemory().get(PC + 3));

            decodeInstruction(instruction);

            if (currentInstruction != null) {
                registerBank.setPC(PC + 4); // Próxima instrução
                run();
            }

        } catch (Exception e) {
            currentInstruction = null;
            currentInstructionStr = "";
        }
    }

    // Identifica qual função que vai ser executada para rodar as instruções
    private void run() {
        if (currentInstruction.getClass() == RInstruction.class) {
            runRInstruction();
        } else if (currentInstruction.getClass() == IInstruction.class) {
            runIInstruction();
        } else {
            runJInstruction();
        }
    }

    // Roda as instruções do tipo R
    private void runRInstruction() {

        RInstruction rInstruction = (RInstruction) currentInstruction;
        int rd = rInstruction.getRd();
        int rs = rInstruction.getRs();
        int rt = rInstruction.getRt();
        int shamt = rInstruction.getShamt();

        hasRegisterDump = true;

        // Identifica especificamente a instrução do tipo R baseado no codigo de função
        switch (rInstruction.getFunction()) {
        case 0x00:
            currentInstructionStr = "sll $" + rd + ", $" + rt + ", " + shamt;

            registerBank.setRegister(rInstruction.getRd(), alu.shiftLeftLogical(rt, shamt));
            break;
        case 0x02:
            currentInstructionStr = "srl $" + rd + ", $" + rt + ", " + shamt;

            registerBank.setRegister(rd, alu.shiftRightLogical(rt, shamt));
            break;
        case 0x03:
            currentInstructionStr = "sra $" + rd + ", $" + rt + ", " + shamt;

            registerBank.setRegister(rd, alu.shiftRightArithmetic(rt, shamt));
            break;
        case 0x04:
            currentInstructionStr = "sllv $" + rd + ", $" + rt + ", $" + rs;

            registerBank.setRegister(rd, alu.shiftLeftLogicalVariable(rt, rs));
            break;
        case 0x06:
            currentInstructionStr = "srlv $" + rd + ", $" + rt + ", $" + rs;

            registerBank.setRegister(rd, alu.shiftRightLogicalVariable(rt, rs));
            break;
        case 0x07:
            currentInstructionStr = "srav $" + rd + ", $" + rt + ", $" + rs;

            registerBank.setRegister(rd, alu.shiftRightArithmeticVariable(rt, rs));
            break;
        case 0x08:
            hasRegisterDump = false;
            currentInstructionStr = "jr $" + rs;
            break;
        case 0x0c:
            hasRegisterDump = false;
            currentInstructionStr = "syscall";
            break;
        case 0x10:
            currentInstructionStr = "mfhi $" + rd;

            registerBank.setRegister(rd, registerBank.getHI());
            break;
        case 0x12:
            currentInstructionStr = "mflo $" + rd;

            registerBank.setRegister(rd, registerBank.getLO());
            break;
        case 0x18:
            currentInstructionStr = "mult $" + rs + ", $" + rt;

            alu.multiply(rs, rt);
            break;
        case 0x19:
            currentInstructionStr = "multu $" + rs + ", $" + rt;

            alu.multiplyUnsigned(rs, rt);
            break;
        case 0x1a:
            currentInstructionStr = "div $" + rs + ", $" + rt;

            alu.divide(rs, rt);
            break;
        case 0x1b:
            currentInstructionStr = "divu $" + rs + ", $" + rt;

            alu.divideUnsigned(rs, rt);
            break;
        case 0x20:
            currentInstructionStr = "add $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.add(rs, rt));
            break;
        case 0x21:
            currentInstructionStr = "addu $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.addUnsigned(rs, rt));
            break;
        case 0x22:
            currentInstructionStr = "sub $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.subtract(rs, rt));
            break;
        case 0x23:
            currentInstructionStr = "subu $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.subtractUnsigned(rs, rt));
            break;
        case 0x24:
            currentInstructionStr = "and $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.bitwiseAnd(rs, rt));
            break;
        case 0x25:
            currentInstructionStr = "or $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.bitwiseOr(rs, rt));
            break;
        case 0x26:
            currentInstructionStr = "xor $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.bitwiseExclusiveOr(rs, rt));
            break;
        case 0x27:
            currentInstructionStr = "nor $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.bitwiseNor(rs, rt));
            break;
        case 0x2a:
            currentInstructionStr = "slt $" + rd + ", $" + rs + ", $" + rt;

            registerBank.setRegister(rd, alu.setLessThan(rs, rt));
            break;
        }
    }

    // Roda as instruções do tipo I
    private void runIInstruction() {

        IInstruction iInstruction = (IInstruction) currentInstruction;
        int rs = iInstruction.getRs();
        int rt = iInstruction.getRt();
        int im = iInstruction.getIm();

        hasRegisterDump = false;

        // Identifica especificamente a instrução do tipo I baseado no codigo de
        // operação
        switch (iInstruction.getOpcode()) {
        case 0x01: // 1
            currentInstructionStr = "bltz $" + rs + ", " + im;

            alu.branchLessThanZero(iInstruction.getRs(), iInstruction.getIm());
            break;
        case 0x04: // 4
            currentInstructionStr = "beq $" + rs + ", $" + rt + ", " + im;

            alu.branchEqual(iInstruction.getRs(), iInstruction.getRt(), iInstruction.getIm());
            break;
        case 0x05: // 5
            currentInstructionStr = "bne $" + rs + ", $" + rt + ", " + im;

            alu.branchNotEqual(iInstruction.getRs(), iInstruction.getRt(), iInstruction.getIm());
            break;
        case 0x08: // 8
            hasRegisterDump = true;
            currentInstructionStr = "addi $" + rt + ", $" + rs + ", " + im;

            registerBank.setRegister(rt, alu.addImmediate(rs, signExtend(im)));
            break;
        case 0x09: // 9
            hasRegisterDump = true;
            currentInstructionStr = "addiu $" + rt + ", $" + rs + ", " + im;

            registerBank.setRegister(rt, alu.addImmediateUnsigned(rs, signExtend(im)));
            break;
        case 0x0a: // 10
            hasRegisterDump = true;
            currentInstructionStr = "slti $" + rt + ", $" + rs + ", " + im;

            registerBank.setRegister(rt, alu.setLessThanImmediate(rs, signExtend(im)));
            break;
        case 0x0c: // 12
            hasRegisterDump = true;
            currentInstructionStr = "andi $" + rt + ", $" + rs + ", " + im;
            // im já está zeroestendido
            registerBank.setRegister(rt, alu.bitwiseAndImmediate(rs, im));
            break;
        case 0x0d: // 13
            hasRegisterDump = true;
            currentInstructionStr = "ori $" + rt + ", $" + rs + ", " + im;
            // im já está zeroestendido
            registerBank.setRegister(rt, alu.bitwiseOrImmediate(rs, im));
            break;
        case 0x0e: // 14
            hasRegisterDump = true;
            currentInstructionStr = "xori $" + rt + ", $" + rs + ", " + im;
            // im já está zeroestendido
            registerBank.setRegister(rt, alu.bitwiseXorImmediate(rs, im));
            break;
        case 0x0f: // 15
            currentInstructionStr = "lui $" + rt + ", " + im;
            break;
        case 0x20: // 32
            currentInstructionStr = "lb $" + rt + ", " + im + "($" + rs + ")";
            break;
        case 0x23: // 35
            currentInstructionStr = "lw $" + rt + ", " + im + "($" + rs + ")";
            break;
        case 0x24: // 36
            currentInstructionStr = "lbu $" + rt + ", " + im + "($" + rs + ")";
            break;
        case 0x28: // 40
            currentInstructionStr = "sb $" + rt + ", " + im + "($" + rs + ")";
            break;
        case 0x2b: // 43
            currentInstructionStr = "sw $" + rt + ", " + im + "($" + rs + ")";
            break;
        }
    }

    // Calcula os endereços de instruções de desvio
    public int address(int im) {
        return registerBank.getPC() + signExtend(im) * 4;
    }

    private int signExtend(int im) {
        short imShort = (short) im; // 16bits
        return imShort; // Java extende o sinal automaticamente nesse cast
    }

    // Roda as instruções do tipo J
    private void runJInstruction() {

        hasRegisterDump = false;
        JInstruction jInstruction = (JInstruction) currentInstruction;

        // Cálculo do endereço para desvio
        int address = (registerBank.getPC() & 0xF0000000) | ((jInstruction.getAd() << 2));

        // Identifica especificamente a instrução do tipo J baseado no codigo de
        // operação
        switch (jInstruction.getOpcode()) {
        case 0x02: // 2
            currentInstructionStr = "j " + jInstruction.getAd();
            break;
        case 0x03: // 3
            currentInstructionStr = "jal " + jInstruction.getAd();
            break;
        }
    }

    // Identifica o tipo da instrução
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

    // Retorna a instrução atual
    public String getCurrentInstructionStr() {
        return currentInstructionStr;
    }

    // Retorna caso o MIPS deve retornar um dump dos registradores
    public boolean getHasRegisterDump() {
        return hasRegisterDump;
    }
}