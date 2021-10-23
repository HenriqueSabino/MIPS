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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import model.processor.MIPS;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o caminho para o arquivo de entrada:");
        String input = scanner.nextLine();

        MIPS mips = new MIPS(input);

        System.out.println("Digite o caminho para o arquivo de saída:");
        input = scanner.nextLine();

        File out = new File(input);
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);

        boolean firstLine = true;

        while (true) {
            mips.runInstruction();
            String output = mips.currentInstruction();

            if (!output.isEmpty()) {

                if (firstLine) {
                    firstLine = false;
                } else {
                    bw.write('\n');
                }

                String dump = mips.registerDump();

                if (!dump.isEmpty()) {
                    output += "\n" + dump;
                }

                bw.write(output);
            } else
                break;
        }

        bw.close();
        fw.close();
        scanner.close();
    }

}
