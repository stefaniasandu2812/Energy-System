package input;

import strategies.EnergyChoiceStrategyType;
import utils.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class DistributorInput implements EntityInput, Observer {
    private int id;
    private int contractLength;
    private int initialBudget;
    private int initialInfrastructureCost;
    private int infrastructureCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    private int productionCost;
    private int noClients = 0;
    private boolean isBankrupt = false;
    private int actualContractPrice;
    private List<ConsumerInput> totalConsumers = new ArrayList<>();
    private List<Contract> contracts = new ArrayList<>();
    private List<ConsumerInput> penaltyConsumers = new ArrayList<>();
    private List<ProducerInput> chosenProducers = new ArrayList<>();
    private boolean toChangeProducer;
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

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public List<ProducerInput> getChosenProducers() {
        return chosenProducers;
    }

    public void setChosenProducers(List<ProducerInput> chosenProducers) {
        this.chosenProducers = chosenProducers;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public boolean getToChangeProducer() {
        return toChangeProducer;
    }

    public void setToChangeProducer(boolean toChangeProducer) {
        this.toChangeProducer = toChangeProducer;
    }

    /**
     * computes profit for a distributor
     * @return profit
     */
    public int profit() {
        return (int) Math.round(Math.floor(PROFIT_CONSTANT * productionCost));
    }

    @Override
    public void update(Observable o, Object arg) {
        this.toChangeProducer = true;
    }

}
