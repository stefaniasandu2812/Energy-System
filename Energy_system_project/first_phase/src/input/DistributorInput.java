package input;

import utils.Contract;
import java.util.ArrayList;
import java.util.List;

public final class DistributorInput implements EntityInput {
    private int id;
    private int contractLength;
    private int initialBudget;
    private int initialInfrastructureCost;
    private int initialProductionCost;
    private int noClients = 0;
    private boolean isBankrupt = false;
    private int actualContractPrice;
    private List<ConsumerInput> totalConsumers = new ArrayList<>();
    private List<Contract> contracts = new ArrayList<>();
    private List<ConsumerInput> penaltyConsumers = new ArrayList<>();
    public static final double PROFIT_CONSTANT = 0.2;

    public List<ConsumerInput> getTotalConsumers() {
        return totalConsumers;
    }

    public void setTotalConsumers(final List<ConsumerInput> totalConsumers) {
        this.totalConsumers = totalConsumers;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<ConsumerInput> getPenaltyConsumers() {
        return penaltyConsumers;
    }

    public int getActualContractPrice() {
        return actualContractPrice;
    }

    public void setActualContractPrice(final int actualContractPrice) {
        this.actualContractPrice = actualContractPrice;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public int getNoClients() {
        return noClients;
    }

    public void setNoClients(final int noClients) {
        this.noClients = noClients;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getInitialProductionCost() {
        return initialProductionCost;
    }

    public void setInitialProductionCost(final int initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }

    /**
     * computes profit for a distributor
     * @return
     */
    public int profit() {
        return (int) Math.round(Math.floor(PROFIT_CONSTANT * initialProductionCost));
    }
}
