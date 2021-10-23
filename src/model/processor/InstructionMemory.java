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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Lê as instruções do programa e armazena na memória
public class InstructionMemory {

    private File instructionFile;
    private FileReader fr;
    private BufferedReader br;
    private List<Byte> instructionMemory;

    public InstructionMemory(String inputMemotyPath) throws FileNotFoundException, IOException {

        instructionFile = new File(inputMemotyPath);
        fr = new FileReader(instructionFile);
        br = new BufferedReader(fr);
        instructionMemory = new ArrayList<>();

        String currentLine = br.readLine();

        while (currentLine != null) {
            int fullInstruction = Integer.parseUnsignedInt(currentLine.substring(2), 16);

            instructionMemory.add((byte) (fullInstruction >>> 24));
            instructionMemory.add((byte) ((fullInstruction & 0x00ff0000) >>> 16));
            instructionMemory.add((byte) ((fullInstruction & 0x0000ff00) >>> 8));
            instructionMemory.add((byte) (fullInstruction & 0x000000ff));

            currentLine = br.readLine();
        }

    }

    public List<Byte> getInstructionMemory() {
        return instructionMemory;
    }
}
