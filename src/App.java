import model.instructions.*;

public class App {
    public static void main(String[] args) throws Exception {

        RInstruction inst = new RInstruction(0x00020a82);

        System.out.println(toBinary(inst.getInstruction()));
        System.out.println("Op: " + inst.getOpcode());
        System.out.println("Rd: " + inst.getRd());
        System.out.println("Rs: " + inst.getRs());
        System.out.println("Rt: " + inst.getRt());
        System.out.println("Shamt: " + inst.getShamt());
        System.out.println("Funct: " + inst.getFunction());
    }

    private static String toBinary(int i) {
        return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
    }
}
