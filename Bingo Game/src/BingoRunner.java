public class BingoRunner {
  public static void main(String[] args) {
    BingoController bc = new BingoController();
    bc.run();
    System.out.printf(Toolkit.GOODBYEMESSAGE);
  }
}
