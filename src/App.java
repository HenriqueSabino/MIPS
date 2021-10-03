import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import model.processor.ControlUnit;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o caminho para o arquivo de entrada:");
        String input = scanner.nextLine();

        ControlUnit cu = new ControlUnit(input);

        System.out.println("Digite o caminho para o arquivo de saída:");
        input = scanner.nextLine();

        File out = new File(input);
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);

        boolean firstLine = true;

        while (true) {
            String output = cu.runInstruction();

            if (!output.isEmpty()) {

                if (firstLine) {
                    firstLine = false;
                } else {
                    bw.write('\n');
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
