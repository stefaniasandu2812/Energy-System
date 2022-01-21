package output;

import input.ConsumerInput;

public final class ConsumerOutput implements EntityOutput {
    private int id;
    private boolean isBankrupt;
    private int budget;

    public ConsumerOutput(final ConsumerInput consumerInput) {
        this.id = consumerInput.getId();
        this.isBankrupt = consumerInput.getIsBankrupt();
        this.budget = consumerInput.getInitialBudget();
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }
}
