import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
  private final ArrayList<BankAccount> accountList = new ArrayList<>();
  private final ArrayList<Acc> userList = new ArrayList<>();
  private int day;

  public Bank() {}

  public static Bank uploadData(String filename) {
    Bank bank = new Bank();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
          User user = new User(parts[2], parts[0]);
          Acc acc = new Acc(parts[0], parts[1], user);
          BankAccount account = new BankAccount(parts[2], parts[0]);
          account.setPersonalBalance(Double.parseDouble(parts[4]));
          bank.userList.add(acc);
          bank.accountList.add(account);
        }
      }
      return bank;
    } catch (IOException | NumberFormatException e) {
      return null;
    }
  }

  // Add method to manage accounts, e.g., createAccountList(), removeAccount(), etc.
  public void createAccountList(BankAccount o) {
    accountList.add(o);
  }

  // Method to check if a user's personal code matches an account
  public boolean checkPersonalCode(User u) {
    for (BankAccount account : accountList) {
      if (account.getPersonalCodeBank().equals(u.getPersonalCodeUser())) {
        return true;
      }
    }
    return false;
  }

  // Method to get the list of bank accounts
  public ArrayList<BankAccount> getAccountList() {
    return accountList;
  }

  // Method for time travel
  public void timeTravel(User u) {
    System.out.println("Day: " + day);
    System.out.print("Do you want to do a time travel (y or n)? ");
    Scanner scanner = new Scanner(System.in);
    char answer = scanner.next().charAt(0);
    while (answer == 'y') {
      if (checkPersonalCode(u)) {
        u.setPersonalWallet(100.00);
        for (int i = 0; i < accountList.size(); i++) {
          for (int c = 0; c < accountList.get(i).getInvestmentList().size(); c++) {
            if (!accountList.get(i).getInvestmentList().get(c).getIsFinish()
                && accountList.get(i).getInvestmentList().get(c).getLimit() == 0) {

              accountList
                  .get(i)
                  .setPersonalBalance(accountList.get(i).getInvestmentList().get(c).payment());
              accountList.get(i).getInvestmentList().get(c).setIsFinish();
            }
            accountList.get(i).getInvestmentList().get(c).setLimit();
          }
        }
      }
      day += 30;
      System.out.println("Day: " + day);
      System.out.print("Do you want to do another time travel (y or n)? ");
      answer = scanner.next().charAt(0);
    }
  }

  public void registerUser(String username, String password, User user) {
    userList.add(new Acc(username, password, user));
  }

  public Acc authenticateUser(String username, String password) {
    for (Acc account : userList) {
      if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
        return account;
      }
    }
    return null;
  }

  public void saveUserData(String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (Acc acc : userList) {
        // Trova l'oggetto BankAccount corrispondente all'utente
        BankAccount account = findAccount(acc.getUser().getPersonalCodeUser());
        if (account != null) {
          writer.println(
              acc.getUsername()
                  + ","
                  + acc.getPassword()
                  + ","
                  + acc.getUser().getPersonalCodeUser()
                  + ","
                  + acc.getUser().getPersonalWallet()
                  + ","
                  + account.getPersonalBalance());
        }
      }
    } catch (IOException e) {
      e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
    }
  }

  private BankAccount findAccount(String personalCode) {
    for (BankAccount account : accountList) {
      if (account.getPersonalCodeBank().equals(personalCode)) {
        return account;
      }
    }
    return null; // Restituisce null se l'account non viene trovato
  }
}
