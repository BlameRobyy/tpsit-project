import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String personalCodeBank;
    private double personalBalance;
    private List<Investment> investmentList;
    private boolean hasInvestments;

    public BankAccount(String pC) {
        this.personalCodeBank = pC;
        this.personalBalance = 0.0;
        this.hasInvestments = false;
        this.investmentList = new ArrayList<>();
    }

    public String getPersonalCodeBank() {
        return personalCodeBank;
    }

    public void setPersonalBalance(double z) {
        personalBalance += z;
    }

    public double getPersonalBalance() {
        return personalBalance;
    }

    public List<Investment> getInvestmentList() {
        return investmentList;
    }

    public void addInvestmentList(Investment i) {
        if (personalBalance >= i.getQInvest()) {
            hasInvestments = true;
            personalBalance -= i.getQInvest();
            investmentList.add(i);
        } else {
            System.out.println("You can't invest, your personal balance is lower than money in your investment");
            System.out.println("Deposit for invest");
        }
    }

    public void printInvestmentStatus() {
        int counter = 1;
        for (Investment i : investmentList) {
            System.out.println("-------------------------");
            System.out.println(counter);
            counter++;
            System.out.println("Tot Invest: " + i.getQInvest());
            System.out.println("Duration: " + i.getDuration());
            System.out.println("Risk: " + i.getRisk());
            System.out.println("Result: " + i.getResult());
            if (i.getLimit() < 0) {
                System.out.println("This investment is finished");
            } else {
                System.out.println("Finish in: " + i.getLimit() + " month/s");
            }
        }
    }
}
