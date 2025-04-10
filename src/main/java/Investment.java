import java.util.Random;

public class Investment {
  private final double qInvest;
  private final int duration; // 0 1 2
  private final int riskEarning; // 0 1 2
  private int limit;
  private boolean isFinish;
  private double result;

  public Investment(double q, int d, int r) {
    this.qInvest = q;
    this.duration = d;
    this.limit = getDurationLimit(d);
    this.riskEarning = r;
    this.isFinish = true;
    this.result = 0.0;
  }

  private int getDurationLimit(int duration) {
    switch (duration) {
      case 0: return 12;
      case 1: return 60;
      case 2: return 120;
      default: throw new IllegalArgumentException("Invalid duration value");
    }
  }

  private double calculatePerCentDuration() {
    switch (duration) {
      case 0: return (qInvest * 1) / 100;
      case 1: return (qInvest * 5) / 100;
      case 2: return (qInvest * 10) / 100;
      default: throw new IllegalArgumentException("Invalid duration value");
    }
  }

  private double calculatePerCentRiskEarning() {
    Random rand = new Random();
    int die = rand.nextInt(10) + 1;

    switch (riskEarning) {
      case 0:
        return (die == 1) ? -((qInvest * 10) / 100) : ((qInvest * 10) / 100);
      case 1:
        return (die <= 3) ? -((qInvest * 50) / 100) : ((qInvest * 50) / 100);
      case 2:
        return (die <= 5) ? -((qInvest * 70) / 100) : ((qInvest * 70) / 100);
      default:
        throw new IllegalArgumentException("Invalid risk earning value");
    }
  }

  public double payment() {
    double perCentDuration = calculatePerCentDuration();
    double perCentRiskEarning = calculatePerCentRiskEarning();

    System.out.println("Investment is finished");
    this.setResult(qInvest + perCentDuration + perCentRiskEarning);
    return qInvest + perCentDuration + perCentRiskEarning;
  }

  // Getter and Setter methods
  public int getDuration() {
    return duration;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit() {
    limit -= 1;
  }

  public boolean getIsFinish() {
    return isFinish;
  }

  public void setIsFinish() {
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
