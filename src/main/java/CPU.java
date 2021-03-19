import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Dictionary;
import java.util.HashMap;

public class CPU {
  public HashMap<Integer,Instruction> operations = new HashMap<Integer, Instruction>();
  public  int[] memory = new int[16];
  public  int pc = 0;
  public  int a = 0;
  public  boolean running = true;

  public CPU() {
    operations.put(0x00,Instruction.BRK);
    operations.put(0xa9,Instruction.LDA);
    operations.put(0x69,Instruction.ADC);
    operations.put(0x8d,Instruction.STA);
  }

  public void loadProgram() {
    String opcodes = "a9 64 69 7 8d e 00 00";
    String [] data = opcodes.split(" ");
    for (int i = 0; i < data.length; i++) {
      String s = data[i];
      memory[i] = Integer.parseInt(s,16);
    }
  }

  public void runCycle() {
    Instruction instruction =  operations.get(memory[pc]);
    switch (instruction) {

      case BRK:
          running = false;
        break;
      case LDA:
          a = memory[pc + 1];
        break;
      case ADC:
          a += memory[pc + 1];
        break;
      case STA:
         memory[memory[pc + 1]]  = a;
        break;
    }
  }

  public void execute() {
    while (running) {
      runCycle();
      pc += 2;
    }
  }

}
