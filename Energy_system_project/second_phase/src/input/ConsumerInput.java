package input;

import utils.Contract;

public final class ConsumerInput implements EntityInput {
    private int id;
    private int initialBudget;
    private int monthlyIncome;
    private boolean hasPenalty;
    private DistributorInput actualD = null;
    private Contract actualC = null;
    private DistributorInput newD = null;
    private boolean isBankrupt;
    private Contract newC = null;

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public boolean getHasPenalty() {
        return hasPenalty;
    }

    public void setHasPenalty(final boolean hasPenalty) {
        this.hasPenalty = hasPenalty;
    }

    public DistributorInput getNewD() {
        return newD;
    }

    public void setNewD(final DistributorInput newD) {
        this.newD = newD;
    }

    public Contract getNewC() {
        return newC;
    }

    public void setNewC(final Contract newC) {
        this.newC = newC;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public DistributorInput getActualD() {
        return actualD;
    }

    public void setActualD(final DistributorInput actualD) {
        this.actualD = actualD;
    }

    public Contract getActualC() {
        return actualC;
    }

    public void setActualC(final Contract actualC) {
        this.actualC = actualC;
    }

    /**
     * computes total budget of a consumer per month
     */
    public void totalBudget() {
        initialBudget += monthlyIncome;
    }
}
