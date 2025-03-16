import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class Bank {
  private ArrayList <BankAccount> accountList = new ArrayList<>();
  private ArrayList <Acc> userList = new ArrayList<>();
  private int day;


  public Bank() {
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

  public static Bank uploadData(String nomeFile) {
    Bank bank = new Bank();
    try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
      String line;
      User currentUser = null;
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 4
                && !parts[0].equals("Account")
                && !parts[0].equals("Transaction")
                && !parts[0].equals("Investment")) {
          String personalCode = parts[0];
          String usernameUser = parts[1];
          String usernameAccount = parts[2];
          String password = parts[3];

          User user = new User(personalCode, usernameUser);
          bank.registerUser(usernameAccount, password, user);
          currentUser = user;

        } else if (parts.length >= 3 && parts[0].equals("Account")) {

          if (currentUser != null) {
            String personalCode = parts[1];
            double personalBalance = Double.parseDouble(parts[2]);

            BankAccount bankAccount = new BankAccount(personalCode, currentUser.getUsername());
            bankAccount.setPersonalBalance(personalBalance);
            bank.accountList.add(bankAccount);

          } else {
            System.err.println("Errore: Riga 'Account' trovata prima di un utente valido.");
          }
        } else if (parts.length >= 4 && parts[0].equals("Transaction")) {
          try {
            Date date = formatter.parse(parts[1]); // analizza la stringa di data
            double amount = Double.parseDouble(parts[2]);
            String type = parts[3];
            String description = parts[4];

            Transaction transaction = new Transaction(date, amount, type);

            for (BankAccount account : bank.accountList) {
              if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
                account.getTransactionHistory().add(transaction);
                break;
              }
            }
          } catch (ParseException e) {
            System.err.println("Errore durante l'analisi della data: " + e.getMessage());
          }
        } else if (parts.length >= 3 && parts[0].equals("Investment")) {
          double qInvest = Double.parseDouble(parts[1]);
          int duration = Integer.parseInt(parts[2]);
          int risk = Integer.parseInt(parts[3]);

          Investment investment = new Investment(qInvest, duration, risk);

          for (BankAccount account : bank.accountList) {
            if (account.getPersonalCodeBank().equals(currentUser.getPersonalCodeUser())) {
              account.getInvestmentList().add(investment);
              break;
            }
          }
        }
      }
      System.out.println("Dati caricati con successo.");
      return bank;
    } catch (IOException e) {
      System.err.println("Error loading data:" + e.getMessage());
      return null;
    }
  }

  public void saveData(String nomeFile) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile))) {
      for (int i = 0; i < userList.size(); i++) {
        Acc account = userList.get(i);
        User user = account.getUser();
        writer.println(
                user.getPersonalCodeUser()
                        + ","
                        + user.getUsername()
                        + ","
                        + account.getUsername()
                        + ","
                        + account.getPassword());

        for (int j = 0; j < accountList.size(); j++) {
          BankAccount bankAccount = accountList.get(j);
          if (bankAccount.getPersonalCodeBank().equals(user.getPersonalCodeUser())) {
            writer.println(
                    "Account,"
                            + bankAccount.getPersonalCodeBank()
                            + ","
                            + bankAccount.getPersonalBalance());

            for (int k = 0; k < bankAccount.getTransactionHistory().size(); k++) {
              Transaction transaction = bankAccount.getTransactionHistory().get(k);
              writer.println("Transaction," + transaction.toString());
            }

            for (int l = 0; l < bankAccount.getInvestmentList().size(); l++) {
              Investment investment = bankAccount.getInvestmentList().get(l);
              writer.println("Investment," + investment.toString());
            }
            break;
          }
        }
      }
      System.out.println("Data saved successfully.");
    } catch (IOException e) {
      System.err.println("Error saving data: " + e.getMessage());
    }
  }

}