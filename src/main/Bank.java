import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Bank {
    private ArrayList<BankAccount> accountList = new ArrayList<>();
    private int day;

    public Bank() {
    }

    public Bank(ArrayList<BankAccount> accountList, int day) {
        this.accountList = accountList;
        this.day = day;
    }

    // Add method to manage accounts, e.g., createAccountList(), removeAccount(), etc.
    public void createAccountList(BankAccount o) {
        accountList.add(o);
    }


    // Method to check if a user's personal code matches an account
    public boolean checkPersonalCode(User u) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getPersonalCodeBank().equals(u.getPersonalCodeUser())) {
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
                        if (!accountList.get(i).getInvestmentList().get(c).getFinish() &&
                                accountList.get(i).getInvestmentList().get(c).getLimit() == 0) {

                            accountList.get(i).setPersonalBalance(accountList.get(i).getInvestmentList().get(c).payment());
                            accountList.get(i).getInvestmentList().get(c).setFinish();
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

}
