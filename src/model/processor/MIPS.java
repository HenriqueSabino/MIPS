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

import java.io.FileNotFoundException;
import java.io.IOException;

// Builda a string que vai ser retornada no arquivo de saida
public class MIPS {

    RegisterBank registerBank;
    ArithmeticLogicUnit alu;
    InstructionMemory instructionMemory;
    ControlUnit controlUnit;

    public MIPS(String instructionPath) throws FileNotFoundException, IOException {

        registerBank = new RegisterBank();
        alu = new ArithmeticLogicUnit(registerBank);
        instructionMemory = new InstructionMemory(instructionPath);
        controlUnit = new ControlUnit(registerBank, instructionMemory, alu);
    }

    public void runInstruction() {
        controlUnit.runInstruction();
    }

    public String currentInstruction() {
        return controlUnit.getCurrentInstructionStr();
    }

    public String registerDump() {

        StringBuilder sb = new StringBuilder();

        if (controlUnit.getHasRegisterDump()) {
            sb.append('[');

            for (int i = 0; i < 32; i++) {

                if (i != 0)
                    sb.append(";");

                sb.append("$" + i + "=" + registerBank.getRegister(i));
            }

            sb.append(']');
        }

        return sb.toString();
    }
}