import java.util.Scanner;

public class User{
  private String personalCodeUser;
  private double personalWallet;
  private final String username;

  public User(String pcu, String username) {
    this.personalCodeUser = pcu;
    this.personalWallet = 100.0;
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public String getPersonalCodeUser() {
    return personalCodeUser;
  }

  public void setPersonalCodeUser(String a){
    personalCodeUser=a;
  }

  public void deposit(Bank b, Scanner scanner) {
    if (b.checkPersonalCode(this)) {
      System.out.println("Your wallet: " + personalWallet);
      System.out.print("How much money do you want to deposit? ");
      double money = scanner.nextDouble();
      if (money > personalWallet) {
        System.out.println("You have less money in your wallet");
      } else {
        for (int i = 0; i < b.getAccountList().size(); i++) {
          if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
            personalWallet -= money;
            b.getAccountList().get(i).setPersonalBalance(money);
          }
        }
      }
    }
  }

  public void withdraw(Bank b, Scanner scanner) {
    if (b.checkPersonalCode(this)) {
      BankAccount account = null;
      for (BankAccount acc : b.getAccountList()) {
        if (acc.getPersonalCodeBank().equals(personalCodeUser)) {
          account = acc;
          break;
        }
      }

      if (account != null) {
        System.out.println("In your bank account: " + account.getPersonalBalance());
        double money = -1;
        while (money < 0 || money > account.getPersonalBalance()) {
          try {
            System.out.print("How much money do you want to withdraw? ");
            money = Double.parseDouble(scanner.nextLine());
            if (money < 0) {
              System.out.println("Amount must be positive.");
            } else if (money > account.getPersonalBalance()) {
              System.out.println("You have less money in your personal balance.");
            }
          } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
          }
        }

        personalWallet += money;
        account.setPersonalBalance(-money);
        System.out.println("Withdrawal successful. New balance: " + account.getPersonalBalance());
      } else {
        System.out.println("Account not found.");
      }
    } else {
      System.out.println("User not associated with any bank account.");
    }
  }

  public void lookWallet() {
    System.out.println("In your wallet there are: " + personalWallet + '$');
  }

  public void lookPersonalBalance(Bank b) {
    if (b.checkPersonalCode(this)) {
      for (int i = 0; i < b.getAccountList().size(); i++) {
        if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
          System.out.println("In your bank account there are: " +
                  b.getAccountList().get(i).getPersonalBalance() + '$');
          break;
        }
      }
    }
  }

  public double getPersonalWallet() {
    return personalWallet;
  }

  public void setPersonalWallet(double w) {
    personalWallet += w;
  }

}