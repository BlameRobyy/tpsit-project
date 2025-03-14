import java.util.Random;

public class Investment {
    private final double qInvest;
    private final int duration; // 0 1 2
    private int limit;
    private final int riskEarning; // 0 1 2
    private boolean isFinish;
    private double result;

    public Investment(double q, int d, int r) {
        this.qInvest = q;
        this.duration = d;
        if (duration == 0) {
            this.limit = 12;
        } else if (duration == 1) {
            this.limit = 60;
        } else {
            this.limit = 120;
        }
        this.riskEarning = r;
        this.isFinish = true;
        this.result = 0;
    }

    public double payment() {
        Random rand = new Random();

        double perCentDuration = 0;
        if (duration == 0) {
            perCentDuration = (qInvest * 1) / 100;
        } else if (duration == 1) {
            perCentDuration = (qInvest * 5) / 100;
        } else if (duration == 2) {
            perCentDuration = (qInvest * 10) / 100;
        }

        double perCentRiskEarning = 0;
        int die = rand.nextInt(10) + 1;
        if (riskEarning == 0) {
            if (die == 1) {
                perCentRiskEarning = -1 * ((qInvest * 10) / 100);
            } else {
                perCentRiskEarning = ((qInvest * 10) / 100);
            }
        } else if (riskEarning == 1) {
            if (die <= 3) {
                perCentRiskEarning = -1 * ((qInvest * 50) / 100);
            } else {
                perCentRiskEarning = ((qInvest * 50) / 100);
            }
        } else if (riskEarning == 2) {
            if (die <= 5) {
                perCentRiskEarning = -1 * ((qInvest * 70) / 100);
            } else {
                perCentRiskEarning = ((qInvest * 70) / 100);
            }
        }

        System.out.println("investment is finish");
        this.setResult((((qInvest) + (perCentDuration)) + (perCentRiskEarning)));
        return (((qInvest) + (perCentDuration)) + (perCentRiskEarning));
    }

    public int getDuration() {
        return duration;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit() {
        this.limit -= 1;
    }

    public boolean getFinish() {
        return isFinish;
    }

    public void setFinish() {
        isFinish = false;
    }

    public double getQInvest() {
        return qInvest;
    }

    public int getRisk() {
        return riskEarning;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double b) {
        result = b;
    }
}
