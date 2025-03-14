import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Bank SanPaolo = new Bank();
        BankAccount s1 = new BankAccount("001");
        BankAccount s2 = new BankAccount("002");
        BankAccount s3 = new BankAccount("003");

        SanPaolo.createAccountList(s1);
        SanPaolo.createAccountList(s2);
        SanPaolo.createAccountList(s3);

        Bank Generali = new Bank();
        BankAccount g1 = new BankAccount("001");
        BankAccount g2 = new BankAccount("002");
        BankAccount g3 = new BankAccount("003");

        Generali.createAccountList(g1);
        Generali.createAccountList(g2);
        Generali.createAccountList(g3);

        System.out.println("Hi, I will help you with your bank account.");
        System.out.println("Which bank do you want to use? (San Paolo = 0 | Generali = 1)");

        Scanner scanner = new Scanner(System.in);

        int checkBank = scanner.nextInt();
        scanner.nextLine();

        if (checkBank == 0 || checkBank == 1) {
            Bank selectedBank = (checkBank == 0) ? SanPaolo : Generali;
            System.out.println("Insert your personal code bank:");
            String code = scanner.nextLine();
            User u1 = new User(code);

            if (selectedBank.checkPersonalCode(u1)) {
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
                        case 0 -> u1.deposit(selectedBank);
                        case 1 -> u1.withdraw(selectedBank);
                        case 2 -> u1.lookWallet();
                        case 3 -> u1.lookPersonalBalance(selectedBank);
                        case 4 -> selectedBank.timeTravel(u1);
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
                                        if (account.getPersonalCodeBank().equals(u1.getPersonalCodeUser())) {
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
                                if (account.getPersonalCodeBank().equals(u1.getPersonalCodeUser())) {
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

        scanner.close();
    }
}