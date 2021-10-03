import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import model.processor.ControlUnit;

public class App {
    public static void main(String[] args) throws Exception {

        ControlUnit cu = new ControlUnit();

        File out = new File("saida.txt");
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
    }

}
