package output;

import java.util.List;
import utils.Contract;
import input.DistributorInput;

public final class DistributorOutput implements EntityOutput {
    private int id;
    private int budget;
    private boolean isBankrupt;
    private List<Contract> contracts;

    public DistributorOutput(final DistributorInput distributorInput) {
        this.id = distributorInput.getId();
        this.isBankrupt = distributorInput.getIsBankrupt();
        this.budget = distributorInput.getInitialBudget();
        this.contracts = distributorInput.getContracts();
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }
}
