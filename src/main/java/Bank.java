import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
      BankAccount currentAccount = null;  // Variabile per tenere traccia dell'account corrente
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        // Se la riga contiene informazioni sull'account
        if (parts.length == 5) {
          // Passa anche il valore del wallet dal file
          double personalWallet = Double.parseDouble(parts[3]);
          User user = new User(parts[2], parts[0], personalWallet); // Inizializza l'utente con il wallet corretto
          Acc acc = new Acc(parts[0], parts[1], user);
          BankAccount account = new BankAccount(parts[2], parts[0]);
          account.setPersonalBalance(Double.parseDouble(parts[4]));

          // Aggiungi l'account e l'utente alla lista del banco
          bank.userList.add(acc);
          bank.accountList.add(account);
          currentAccount = account;  // Imposta l'account corrente
        }
        // Se la riga è una transazione
        else if (line.startsWith("Transaction")) {
          // Aggiungi la transazione all'account corrente
          if (currentAccount != null) {
            String[] transactionParts = line.split(", ");
            String type = transactionParts[0].split(": ")[1];
            double amount = Double.parseDouble(transactionParts[1].split(": ")[1]);
            String dateString = transactionParts[2].split(": ")[1];

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            // Crea una nuova transazione e aggiungila all'account
            Transaction transaction = new Transaction(date, amount, type);
            currentAccount.getTransactionHistory().add(transaction);
          }
        }
      }
      return bank;
    } catch (IOException | NumberFormatException | ParseException e) {
      e.printStackTrace();
      return null;
    }
  }




  public void createAccountList(BankAccount o) {
    accountList.add(o);
  }


  public boolean checkPersonalCode(User u) {
    for (BankAccount account : accountList) {
      if (account.getPersonalCodeBank().equals(u.getPersonalCodeUser())) {
        return true;
      }
    }
    return false;
  }


  public ArrayList<BankAccount> getAccountList() {
    return accountList;
  }


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
    try {
      // Leggi il file esistente
      List<String> existingLines = new ArrayList<>();
      File file = new File(filename);
      if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
          String line;
          while ((line = reader.readLine()) != null) {
            existingLines.add(line);
          }
        }
      }

      // Aggiungi nuovi dati solo se non sono già presenti
      try (PrintWriter writer = new PrintWriter(new FileWriter(filename, false))) {  // Usa false per sovrascrivere il file
        for (Acc acc : userList) {
          BankAccount account = findAccount(acc.getUser().getPersonalCodeUser());
          if (account != null) {
            // Scrivi i dati dell'utente e delle transazioni
            writer.println(
                    acc.getUsername()
                            + "," + acc.getPassword()
                            + "," + acc.getUser().getPersonalCodeUser()
                            + "," + acc.getUser().getPersonalWallet()
                            + "," + account.getPersonalBalance());
            for (Transaction transaction : account.getTransactionHistory()) {
              SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
              String transactionDate = date1.format(transaction.getDate());
              writer.println("Transaction: "
                      + transaction.getType()
                      + ", Amount: "
                      + transaction.getAmount()
                      + ", Date: "
                      + transactionDate);
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  private BankAccount findAccount(String personalCode) {
    for (BankAccount account : accountList) {
      if (account.getPersonalCodeBank().equals(personalCode)) {
        return account;
      }
    }
    return null;
  }
}
