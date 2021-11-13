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

    private RegisterBank registerBank;
    private ArithmeticLogicUnit alu;
    private InstructionMemory instructionMemory;
    private RAM ram;
    private ControlUnit controlUnit;

    // Inicializando partes do processador
    public MIPS(String instructionPath) throws FileNotFoundException, IOException {

        registerBank = new RegisterBank();
        alu = new ArithmeticLogicUnit(registerBank);
        instructionMemory = new InstructionMemory(instructionPath);
        ram = new RAM(1024);
        controlUnit = new ControlUnit(registerBank, instructionMemory, ram, alu);
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

    // Retorna uma string do estado atual da memória ram
    // Imprimindo apenas os bytes diferentes de zero
    public String memoryDump() {

        StringBuilder sb = new StringBuilder();

        sb.append("MEM[");

        int count = 0;
        for (int i = 0; i < ram.getSize(); i++) {

            byte read = ram.readAt(i);
            if (read != 0) {
                if (count != 0)
                    sb.append(";");
                sb.append(i + ":" + read);
                count++;
            }
        }

        sb.append(']');

        String out = sb.toString();

        if (out.equals("MEM[]")) {
            return "";
        }

        return out;
    }
}