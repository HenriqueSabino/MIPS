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

// Define o processador MIPS
public class MIPS {

    RegisterBank registerBank;
    ArithmeticLogicUnit alu;
    InstructionMemory instructionMemory;
    ControlUnit controlUnit;

    // Inicializando partes do processador
    public MIPS(String instructionPath) throws FileNotFoundException, IOException {

        registerBank = new RegisterBank();
        alu = new ArithmeticLogicUnit(registerBank);
        instructionMemory = new InstructionMemory(instructionPath);
        controlUnit = new ControlUnit(registerBank, instructionMemory, alu);
    }

    // Executa a próxima instrução
    public void runInstruction() {
        controlUnit.runInstruction();
    }

    // Retorna a string em assembly da instrução sendo executada
    public String currentInstruction() {
        return controlUnit.getCurrentInstructionStr();
    }

    // Retorna uma string do estado atual dos registradores, caso
    // 'ControlUnit.hasRegisterDump' esteja como true
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