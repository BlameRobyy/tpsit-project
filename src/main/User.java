import java.util.Scanner;

public class User {
    private String personalCodeUser;
    private double personalWallet;

    public User(String pcu) {
        this.personalCodeUser = pcu;
        this.personalWallet = 100.0;
    }

    public String getPersonalCodeUser() {
        return personalCodeUser;
    }

    public void deposit(Bank b) {
        if (b.checkPersonalCode(this)) {
            System.out.println("Your wallet: " + personalWallet);
            System.out.print("How much money do you want to deposit? ");
            Scanner scanner = new Scanner(System.in);
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

    public void withdraw(Bank b) {
        if (b.checkPersonalCode(this)) {
            for (int i = 0; i < b.getAccountList().size(); i++) {
                if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
                    System.out.println("In your bank account: " +
                            b.getAccountList().get(i).getPersonalBalance());
                }
            }
            System.out.print("How much money do you want to withdraw? ");
            Scanner scanner = new Scanner(System.in);
            double money = scanner.nextDouble();
            for (int i = 0; i < b.getAccountList().size(); i++) {
                if (b.getAccountList().get(i).getPersonalCodeBank().equals(personalCodeUser)) {
                    if (money > b.getAccountList().get(i).getPersonalBalance()) {
                        System.out.println("You have less money in your personal balance");
                        break;
                    } else {
                        personalWallet += money;
                        b.getAccountList().get(i).setPersonalBalance(-1 * (money));
                        break;
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
                    System.out.println("In your bank account there are: " +
                            b.getAccountList().get(i).getPersonalBalance() + '$');
                    break;
                }
            }
        }
    }

    public void setPersonalWallet(double w) {
        personalWallet += w;
    }
}

