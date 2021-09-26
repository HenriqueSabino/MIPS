import model.instructions.*;

public class App {
    public static void main(String[] args) throws Exception {

        IInstruction inst = new IInstruction(0x90410064);

        System.out.println(toBinary(inst.getInstruction()));
        System.out.println("Op: " + inst.getOpcode());
        System.out.println("Rs: " + inst.getRs());
        System.out.println("Rd: " + inst.getRt());
        System.out.println("Im: " + inst.getIm());
    }

    private static String toBinary(int i) {
        return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
    }
}
