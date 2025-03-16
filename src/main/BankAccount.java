import java.util.ArrayList;
import java.util.List;

public class BankAccount {
  private final String personalCodeBank;
  private final List<Investment> investmentList;
  private double personalBalance;
  private final boolean checkInvestmentList;
  private final String username;
  private final ArrayList<Transaction> transactionHistory;

  public BankAccount(String personalCodeBank, String username) {
    this.personalCodeBank = personalCodeBank;
    this.investmentList = new ArrayList<>();
    this.transactionHistory = new ArrayList<>();
    this.username = username;
    this.checkInvestmentList = false;
    this.personalBalance = personalBalance;
  }

  public void withdraw(double amount) {
    setPersonalBalance(-amount);
  }

  public void deposit(double amount) {
    setPersonalBalance(amount);
  }

  public String getPersonalCodeBank() {
    return personalCodeBank;
  }

  public double getPersonalBalance() {
    return personalBalance;
  }

  public void setPersonalBalance(double z) {
    personalBalance += z;
  }

  public List<Investment> getInvestmentList() {
    return investmentList;
  }

  public void addInvestmentList(Investment i) {
    if (personalBalance >= i.getQInvest()) {
      personalBalance -= i.getQInvest();
      investmentList.add(i);
    } else {
      System.out.println("You can't invest, your personal balance is lower than money in your investment");
      System.out.println("Deposit for invest");
    }
  }

  public void printInvestmentStatus() {
    int counter = 1;
    for (Investment i : investmentList) {
      System.out.println("-------------------------");
      System.out.println(counter);
      counter++;
      System.out.println("Tot Invest: " + i.getQInvest());
      System.out.println("Duration: " + i.getDuration());
      System.out.println("Risk: " + i.getRisk());
      System.out.println("Result: " + i.getResult());
      if (i.getLimit() < 0) {
        System.out.println("This investment is finished");
      } else {
        System.out.println("Finish in: " + i.getLimit() + " month/s");
      }
    }
  }



  public ArrayList<Transaction> getTransactionHistory() {
    return transactionHistory;
  }
}