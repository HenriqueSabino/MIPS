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

            bw.write(cu.assemblyInstruction() + "\n");
        }

        bw.close();
        fw.close();
    }

}
