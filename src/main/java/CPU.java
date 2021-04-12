import java.util.HashMap;

public class CPU {
  public HashMap<Integer,Instruction> operations = new HashMap<Integer, Instruction>();
  public  int[] memory = new int[256];
  public  int pc = 0;
  public  int a = 0;
  public  int x = 0;
  public  int y = 0;
  public boolean [] flags = new boolean[1];
  public  boolean running = true;
  public boolean debug = false;

  public CPU() {
    operations.put(0x00,Instruction.BRK);
    operations.put(0x01,Instruction.LDA);
    operations.put(0x02,Instruction.ADC);
    operations.put(0x03,Instruction.STA);
    operations.put(0x04,Instruction.LDX);
    operations.put(0x05,Instruction.INX);
    operations.put(0x06,Instruction.CMY);
    operations.put(0x07,Instruction.BNE);
    operations.put(0x08,Instruction.STAX);
    operations.put(0x09,Instruction.DEY);
    operations.put(10,Instruction.LDY);
  }

  public void loadProgram() {
    String opcodes = "4, 128, 1, 0x77, 8, 5, 1, 0x68, 8, 5, 1, 0x6F, 8, 5, 1, 0x20, 8, 5, 1, 0x6c, 8, 5, 1, 0x65, 8, 5, 1, 0x74, 8, 5, 1, 0x20, 8, 5, 1, 0x74, 8, 5, 1, 0x68, 8, 5, 1, 0x65, 8, 5, 1, 0x20, 8, 5, 1, 0x64, 8, 5, 1, 0x6F, 8, 5, 1, 0x67, 8, 5, 1, 0x73, 8, 5, 1, 0x20, 8, 5, 1, 0x6F, 8, 5, 1, 0x75, 8, 5, 1, 0x74, 8, 5, 1, 0x20, 8, 5, 10, 3, 1, 0x77, 8, 5, 1, 0x68, 8, 5, 1, 0x6F, 8, 5, 1, 0x20, 8, 5, 9, 6, 0, 7, -20, 0";
    String [] data = opcodes.split(", ");
    for (int i = 0; i < data.length; i++) {
      String s = data[i];
      if(s.startsWith("0x")){
        s = s.replace("0x","");
        memory[i] = Integer.parseInt(s,16);
      } else {
        memory[i] = Integer.parseInt(s, 10);
      }
    }
  }

  public void runCycle() {
    if (debug) {
      System.out.println("pc: " + pc + " x: " + x + " y: " + y + " a: " + a);
      System.out.println(memory[pc] + " " + memory[pc + 1] + " " + memory[pc + 2]);
    }
    Instruction instruction = operations.get(memory[pc]);
    if (debug) {
      System.out.println(instruction.toString() + " " + memory[pc + 1] + " " + memory[pc + 2]);
    }
    switch (instruction) {

      case BRK:
          running = false;
        break;
      case LDA:
          a = memory[pc + 1];
          pc += 2;
        break;
      case ADC:
          a += memory[pc + 1];
         pc += 2;
        break;
      case STA:
        memory[memory[pc + 1]]  = a;
        pc += 2;
        break;
      case LDX:
        x = memory[pc + 1];
        pc += 2;
        break;
      case INX:
        x++;
        pc += 1;
        break;
      case CMY:
        flags[0] = (y == memory[pc + 1]);
        pc += 2;
        break;
      case BNE:
        if(!flags[0]) {
          pc += memory[pc+1];
          pc += 1;
        } else {
          flags[0] = false;
          pc += 2;
        }
        break;
      case STAX:
        memory[x] = a;
        pc += 1;
        break;
      case DEY:
        y--;
        pc += 1;
        break;
      case LDY:
        y = memory[pc + 1];
        pc += 2;
        break;
    }
  }

  public void execute() {
    while (running) {
      runCycle();
      displayScreeen();
    }
  }

  private void displayScreeen() {
    String data = "";
    for (int i = 128; i < 256; i++) {
        data+= (char)memory[i];
    }
    System.out.println(data);
  }

}
