import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import model.processor.ControlUnit;

public class App {
    public static void main(String[] args) throws Exception {

        ControlUnit cu = new ControlUnit();

        File out = new File("out.txt");
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);

        while (true) {
            cu.readInstruction();

            if (!cu.hasNextInstruction())
                break;
            else
                bw.write("\n");

            bw.write(cu.assemblyInstruction());
        }

        bw.close();
        fw.close();
    }

    private static String toBinary(int i) {
        return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
    }
}
