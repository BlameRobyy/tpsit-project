import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
  private Date date;
  private double amount;
  private String type;

  public Transaction(Date date, double amount, String type) {
    this.date = date;
    this.amount = amount;
    this.type = type;
  }

  @Override
  public String toString() {
    return "Transaction{" +
            "type='" + type + '\'' +
            ", amount=" + amount +
            ", date=" + date +
            '}';
  }

  public String getType() {
    return type;
  }

  public double getAmount() {
    return amount;
  }

  public Date getDate() {
    return date;
  }
}
