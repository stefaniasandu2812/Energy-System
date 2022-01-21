package utils;

import input.ConsumerInput;
import input.CostInput;
import input.DistributorInput;
import input.MonthlyUpdateInput;
import java.util.Iterator;
import java.util.List;

public final class EnergySystem {
    private static EnergySystem instance;
    public static final double DEBT_CONSTANT = 1.2;

    private EnergySystem() {
    }

    /**
     * static method to get the instance of EnergySystem class
     * @return an instance
     */
    public static EnergySystem getInstance() {
        if (instance == null) {
            instance = new EnergySystem();
        }
        return instance;
    }

    /**
     * computes the price of a contract/offer
     * @param distributor
     */
    public void contractPrice(final DistributorInput distributor) {
        if (distributor.getNoClients() == 0) {
            distributor.setActualContractPrice(distributor.getInitialInfrastructureCost()
                    + distributor.getInitialProductionCost()
                    + distributor.profit());
        } else {
            distributor.setActualContractPrice((int) Math.round(Math.floor(
                    distributor.getInitialInfrastructureCost()
                                    / distributor.getNoClients()))
            + distributor.getInitialProductionCost() + distributor.profit());
        }
    }

    /**
     * method to choose a contract from distributor for a consumer
     * @param consumer
     * @param distributor
     */
    public void chooseContract(final ConsumerInput consumer, final DistributorInput distributor) {
        if (consumer.getActualC() == null
                || consumer.getActualC().getRemainedContractMonths() == 0) {
            Contract contract = new Contract(consumer.getId(),
                    distributor.getActualContractPrice(),
                    distributor.getContractLength());
            distributor.getContracts().add(contract);
            if (consumer.getActualC() != null) {
                consumer.getActualD().getContracts().remove(consumer.getActualC());
                consumer.getActualD().setNoClients(consumer.getActualD().getNoClients() - 1);
                consumer.getActualD().getTotalConsumers().remove(consumer);
                if (consumer.getHasPenalty()) {
                    consumer.setNewD(distributor);
                    consumer.setNewC(contract);
                } else {
                    consumer.setActualD(distributor);
                    consumer.setActualC(contract);
                }
            } else {
                consumer.setActualD(distributor);
                consumer.setActualC(contract);
            }
            distributor.getTotalConsumers().add(consumer);
            distributor.setNoClients(distributor.getNoClients() + 1);
        }
    }

    /**
     * method to pay the monthly price of contract for a consumer
     * @param consumer
     */
    public void payDebt(final ConsumerInput consumer) {
        if (consumer.getActualD() != null) {
            if (consumer.getHasPenalty()) {
                int penalty = (int) (Math.round(Math.floor(DEBT_CONSTANT
                        * consumer.getActualC().getPrice()))
                        + consumer.getActualC().getPrice());
                if (consumer.getInitialBudget() > penalty) {
                    consumer.setInitialBudget(consumer.getInitialBudget() - penalty);
                    consumer.setHasPenalty(false);
                    consumer.getActualC().setRemainedContractMonths(consumer
                            .getActualC().getRemainedContractMonths() - 1);
                    if (consumer.getNewC() != null) {
                        consumer.setActualC(consumer.getNewC());
                        consumer.setNewC(null);
                        consumer.setActualD(consumer.getNewD());
                        consumer.setNewD(null);
                        payDebt(consumer);
                    }
                } else {
                    consumer.setIsBankrupt(true);
                    consumer.getActualD().getContracts().remove(consumer.getActualC());
                    if (consumer.getNewD() != null) {
                        consumer.getNewD().getContracts().remove(consumer.getNewC());
                    }
                }
            } else {
                if (consumer.getInitialBudget() >= consumer.getActualC().getPrice()) {
                    consumer.setInitialBudget(consumer.getInitialBudget()
                            - consumer.getActualC().getPrice());
                    int newBudget = consumer.getActualD().getInitialBudget()
                            + consumer.getActualC().getPrice();
                    consumer.getActualD().setInitialBudget(newBudget);
                } else {
                    consumer.getActualD().getPenaltyConsumers().add(consumer);
                    consumer.setHasPenalty(true);
                }
                consumer.getActualC().setRemainedContractMonths(consumer
                        .getActualC().getRemainedContractMonths() - 1);
            }
        }
    }

    /**
     * recalculate the budget of a distributor after the costs are payed
     * @param distributor
     */
    public void payCostsDistributor(final DistributorInput distributor) {
        if (distributor.getPenaltyConsumers().size() == 0) {
            int newBudget = distributor.getInitialBudget()
                    - (distributor.getInitialInfrastructureCost()
                    + distributor.getNoClients()
                    * distributor.getInitialProductionCost());
            distributor.setInitialBudget(newBudget);
        } else {
            int newBudget = distributor.getInitialBudget()
                    - (distributor.getInitialInfrastructureCost()
                    + distributor.getNoClients()
                    * distributor.getInitialProductionCost());
            distributor.setInitialBudget(newBudget);
            for (Iterator<ConsumerInput> iterator = distributor.getPenaltyConsumers().iterator();
                 iterator.hasNext();) {
                ConsumerInput consumer = iterator.next();
                if (consumer.getIsBankrupt()) {
                    distributor.setNoClients(distributor.getNoClients() - 1);
                    distributor.getContracts().remove(consumer.getActualC());
                    iterator.remove();
                }
            }
        }
        if (distributor.getInitialBudget() < 0) {
            distributor.setIsBankrupt(true);
            for (ConsumerInput consumer : distributor.getTotalConsumers()) {
                removeContract(consumer);
            }
        }
    }

    /**
     * method to set the updates at the beginning of a month
     * @param consumers
     * @param monthlyUpdate
     * @param distributors
     */
    public void setUpdates(final List<ConsumerInput> consumers,
                           final MonthlyUpdateInput monthlyUpdate,
                           final List<DistributorInput> distributors) {
        if (monthlyUpdate.getNewConsumers().size() != 0) {
            consumers.addAll(monthlyUpdate.getNewConsumers());
        }

        if (monthlyUpdate.getCostsChanges().size() != 0) {
            for (CostInput cost : monthlyUpdate.getCostsChanges()) {
                for (DistributorInput distributor : distributors) {
                    if (distributor.getId() == cost.getId()) {
                        distributor.setInitialInfrastructureCost(cost.getInfrastructureCost());
                        distributor.setInitialProductionCost(cost.getProductionCost());
                    }
                }
            }
        }
    }

    /**
     * sets a consumer's distributor and contract to null
     * if a distributor goes bankrupt
     * @param consumer
     */
    public void removeContract(final ConsumerInput consumer) {
        consumer.setActualC(null);
        consumer.setHasPenalty(false);
        consumer.setActualD(null);
    }
}
