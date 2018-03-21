
public class BadTransactionException extends Exception {
  int amount;
  public BadTransactionException(int badAmount) {
    super("Invalid amount number: " + badAmount);
    amount = badAmount;
  }
}
