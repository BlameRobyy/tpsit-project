import java.util.HashMap;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    UserManager userManager = new UserManager();

    // Creazione delle banche
    Bank SanPaolo = new Bank();
    Bank Generali = new Bank();

    // Creazione degli account bancari
    BankAccount s1 = new BankAccount("001");
    BankAccount s2 = new BankAccount("002");
    BankAccount s3 = new BankAccount("003");

    BankAccount g1 = new BankAccount("001");
    BankAccount g2 = new BankAccount("002");
    BankAccount g3 = new BankAccount("003");

    // Aggiunta degli account bancari alle rispettive banche
    SanPaolo.createAccountList(s1);
    SanPaolo.createAccountList(s2);
    SanPaolo.createAccountList(s3);

    Generali.createAccountList(g1);
    Generali.createAccountList(g2);
    Generali.createAccountList(g3);

    // Creazione degli utenti
    User u1 = new User("user001");
    User u2 = new User("user002");
    User u3 = new User("user003");
    User u4 = new User("user004");

    userManager.addUser(u1);
    userManager.addUser(u2);
    userManager.addUser(u3);
    userManager.addUser(u4);

    System.out.println("Hi, I will help you with your bank account.");
    System.out.println("Which bank do you want to use? (San Paolo = 0 | Generali = 1)");

    Scanner scanner = new Scanner(System.in);

    int checkBank = scanner.nextInt();
    scanner.nextLine();

    Bank selectedBank = null;
    HashMap<String, User> selectedUsers = null;

    if (checkBank == 0 || checkBank == 1) {
      selectedBank = (checkBank == 0) ? SanPaolo : Generali;

      System.out.println("Insert your personal code:");
      String code = scanner.nextLine();

      // Trova l'utente dalla mappa
      User currentUser = userManager.getUser(code);

      if (currentUser != null) {
        System.out.println("Perfect, account found.");
        System.out.println("Now you can do these operations:");
        System.out.println("- Deposit (code 0)");
        System.out.println("- Withdraw (code 1)");
        System.out.println("- Look wallet (code 2)");
        System.out.println("- Look your personal balance (code 3)");
        System.out.println("- Do a time-travel (code 4)");
        System.out.println("- Create an investment (code 5)");
        System.out.println("- Look your investment list (code 6)");
        System.out.println("Do you want to execute one of them? (y/n)");

        char answer1 = scanner.next().charAt(0);
        while (answer1 == 'y') {
          System.out.println("Which one?");
          int operation = scanner.nextInt();

          switch (operation) {
            case 0 -> currentUser.deposit(selectedBank);
            case 1 -> currentUser.withdraw(selectedBank);
            case 2 -> currentUser.lookWallet();
            case 3 -> currentUser.lookPersonalBalance(selectedBank);
            case 4 -> selectedBank.timeTravel(currentUser);
            case 5 -> {
              System.out.println("How much do you want to invest?");
              double invest1 = scanner.nextDouble();
              System.out.println("Duration? (short=0, mid=1, long=2)");
              int duration = scanner.nextInt();

              if (duration >= 0 && duration <= 2) {
                System.out.println("Risk? (low=0, mid=1, high=2)");
                int risk = scanner.nextInt();

                if (risk >= 0 && risk <= 2) {
                  for (BankAccount account : selectedBank.getAccountList()) {
                    if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
                      Investment invest = new Investment(invest1, duration, risk);
                      account.addInvestmentList(invest);
                    }
                  }
                } else {
                  System.out.println("Wrong type of risk.");
                  System.exit(1);
                }
              } else {
                System.out.println("Wrong type of duration.");
                System.exit(1);
              }
            }
            case 6 -> {
              System.out.println("Investment status:");
              for (BankAccount account : selectedBank.getAccountList()) {
                if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
                  account.printInvestmentStatus();
                }
              }
            }
            default -> System.out.println("Invalid operation.");
          }

          System.out.println("Do you want to execute another operation? (y/n)");
          answer1 = scanner.next().charAt(0);
        }
      } else {
        System.out.println("There is no account with this code in this bank.");
        System.exit(1);
      }
    } else {
      System.out.println("Bank not found.");
      System.exit(1);
    }
    userManager.saveUsers();
    scanner.close();
  }
}
