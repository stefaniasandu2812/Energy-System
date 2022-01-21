package output;

import input.DistributorInput;
import strategies.EnergyChoiceStrategyType;
import utils.Contract;

import java.util.List;

public final class DistributorOutput implements EntityOutput {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private EnergyChoiceStrategyType producerStrategy;
    private boolean isBankrupt;
    private List<Contract> contracts;

    public DistributorOutput(final DistributorInput distributorInput) {
        this.id = distributorInput.getId();
        this.energyNeededKW = distributorInput.getEnergyNeededKW();
        this.contractCost = distributorInput.getActualContractPrice();
        this.producerStrategy = distributorInput.getProducerStrategy();
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

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }
}
