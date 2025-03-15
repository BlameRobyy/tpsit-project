import java.util.Scanner;
import java.io.Serializable;
public class User implements Serializable{
  private final String personalCodeUser;
  private double personalWallet;

  public User(String pcu) {
    this.personalCodeUser = pcu;
    this.personalWallet = 100.0;
  }

  public String getPersonalCodeUser() {
    return personalCodeUser;
  }

  public void deposit(Bank b) {
    try {
      if (b.checkPersonalCode(this)) {
        System.out.println("Your wallet: " + personalWallet);
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much do you want to deposit?");

        // Controllo se l'input è valido
        if (!scanner.hasNextDouble()) {
          System.out.println("Invalid input. Please enter a valid amount.");
          return;
        }

        double money = scanner.nextDouble();

        // Verifica se l'utente ha abbastanza denaro nel wallet
        if (money <= 0) {
          System.out.println("Deposit amount must be greater than zero.");
          return;
        }

        if (money > personalWallet) {
          System.out.println("You don't have enough money in your wallet.");
          return;
        }

        // Trova l'account associato all'utente
        boolean foundAccount = false;
        for (BankAccount account : b.getAccountList()) {
          if (account.getOwner() == this) {  // Controllo corretto sul proprietario
            personalWallet -= money;  // Dedurre il denaro dal wallet
            account.setPersonalBalance(account.getPersonalBalance() + money);  // Aggiornare il saldo del conto
            System.out.println("Deposit successful!");
            foundAccount = true;
            break;
          }
        }

        if (!foundAccount) {
          System.out.println("Account not found.");
        }
      } else {
        System.out.println("Personal code is invalid.");
      }
    } catch (Exception e) {
      System.out.println("An error occurred during the deposit: " + e.getMessage());
    }
  }



  public void withdraw(Bank b) {
    if (b.checkPersonalCode(this)) {
      for (int i = 0; i < b.getAccountList().size(); i++) {
        if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
          System.out.println("In your bank account: " + b.getAccountList().get(i).getPersonalBalance() + "$");
        }
      }
      System.out.print("How much money do you want to withdraw? ");
      Scanner scanner = new Scanner(System.in);
      double money = scanner.nextDouble();
      for (int i = 0; i < b.getAccountList().size(); i++) {
        if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
          if (money > b.getAccountList().get(i).getPersonalBalance()) {
            System.out.println("You have less money in your personal balance.");
            return;
          } else {
            personalWallet += money;
            b.getAccountList().get(i).setPersonalBalance(-money);
            System.out.println("You have withdrawn: " + money + "$. Your new wallet balance: " + personalWallet + "$.");
            return;
          }
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
          System.out.println(
              "In your bank account there are: "
                  + b.getAccountList().get(i).getPersonalBalance()
                  + '$');
          break;
        }
      }
    }
  }

  public void setPersonalWallet(double w) {
    personalWallet += w;
  }
}
