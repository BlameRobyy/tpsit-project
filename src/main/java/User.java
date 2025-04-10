import java.util.ArrayList;
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

  public boolean deposit(Bank b, Double amount) {
    if (b.checkPersonalCode(this)) {
      Scanner scanner = new Scanner(System.in);

      if (amount == null) {
        System.out.println("Your wallet: " + personalWallet);
        System.out.println("How much money do you want to deposit?");

        int attempts = 0;
        while (true) {
          try {
            amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
              System.out.println("Invalid amount. Please enter a positive value.");
            } else if (amount > personalWallet) {
              System.out.println("You have less money in your wallet");
            } else {
              break;
            }
            attempts = 0;
          } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
            scanner.nextLine();
            attempts++;
            if (attempts >= 3) {
              System.out.println("Too many invalid attempts. Deposit operation cancelled.");
              return false;
            }
          }
        }
      }

      if (amount <= 0 || amount > personalWallet) {
        return false;
      }

      personalWallet -= amount;
      for (BankAccount account : b.getAccountList()) {
        if (account.getPersonalCodeBank().equals(personalCodeUser)) {
          account.deposit(amount);

          return true;
        }
      }
    }
    return false;
  }


  public void withdraw(Bank b, Double amount) {
    if (b.checkPersonalCode(this)) {
      Scanner scanner = new Scanner(System.in);
      BankAccount userAccount = null;

      for (BankAccount account : b.getAccountList()) {
        if (account.getPersonalCodeBank().equals(personalCodeUser)) {
          userAccount = account;
          break;
        }
      }

      if (userAccount != null) {
        if (amount == null) {
          System.out.println("In your bank account: " + userAccount.getPersonalBalance());
          System.out.println("How much money do you want to withdraw?");

          while (true) {
            try {
              amount = Double.parseDouble(scanner.nextLine());

              if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive value.");
              } else if (amount > userAccount.getPersonalBalance()) {
                System.out.println("You have less money in your personal balance");
              } else {
                break;
              }
            } catch (NumberFormatException e) {
              System.out.println("Invalid input. Please enter a numeric value.");
            }
          }
        }

        if (amount > 0 && amount <= userAccount.getPersonalBalance()) {
          userAccount.withdraw(amount);
          personalWallet += amount;
        }
      }
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