import java.util.Collections;

public class main {
  public static void main(String[] args) {
    System.out.println("Hello World");
    CPU cpu = new CPU();
    cpu.loadProgram();
    cpu.execute();
  }
}
