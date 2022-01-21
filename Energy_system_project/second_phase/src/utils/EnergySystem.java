package utils;

import input.ConsumerInput;
import input.DistributorInput;
import input.MonthlyStat;
import input.MonthlyUpdateInput;
import input.ProducerChange;
import input.ProducerInput;
import strategies.EnergyChoiceStrategy;
import strategies.EnergyChoiceStrategyType;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public final class EnergySystem {
    private static EnergySystem instance;
    public static final double DEBT_CONSTANT = 1.2;
    public static final int COST_CONSTANT = 10;

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
     * computing the production cost for distributor
     * @param distributor
     */
    public void computeProductionCost(DistributorInput distributor) {
        int cost = 0;

        for (ProducerInput producer : distributor.getChosenProducers()) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }

        distributor.setProductionCost((int) Math.round(Math.floor(cost / COST_CONSTANT)));
    }

    /**
     *
     * @param distributor
     * @return the chosen strategy
     */
    public EnergyChoiceStrategy chooseEnergyType(DistributorInput distributor) {
        if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.GREEN)) {
            return new GreenStrategy();
        }

        if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.PRICE)) {
            return new PriceStrategy();
        }

        if (distributor.getProducerStrategy().equals(EnergyChoiceStrategyType.QUANTITY)) {
            return new QuantityStrategy();
        }
        return null;
    }

    /**
     * computes the price of a contract/offer
     * @param distributor
     */
    public void contractPrice(final DistributorInput distributor) {
        if (distributor.getNoClients() == 0) {
            distributor.setActualContractPrice(distributor.getInitialInfrastructureCost()
                    + distributor.getProductionCost()
                    + distributor.profit());
        } else {
            distributor.setActualContractPrice((int) Math.round(Math.floor(
                    distributor.getInitialInfrastructureCost()
                                    / distributor.getNoClients()))
            + distributor.getProductionCost() + distributor.profit());
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

                if (consumer.getActualC().getRemainedContractMonths() == 0) {
                    if (consumer.getActualD().equals(consumer.getNewD())) {
                        penalty += consumer.getNewC().getPrice();
                    }
                }
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
                    * distributor.getProductionCost());
            distributor.setInitialBudget(newBudget);
        } else {
            int newBudget = distributor.getInitialBudget()
                    - (distributor.getInitialInfrastructureCost()
                    + distributor.getNoClients()
                    * distributor.getProductionCost());
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

        if (monthlyUpdate.getDistributorChanges().size() != 0) {
            for (DistributorInput distributorChange : monthlyUpdate.getDistributorChanges()) {
                for (DistributorInput distributor : distributors) {
                    if (distributor.getId() == distributorChange.getId()) {
                        distributor.setInitialInfrastructureCost(distributorChange
                                .getInfrastructureCost());
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

    /**
     * updating producers
     * @param producers
     * @param monthlyUpdate
     */
    public void setUpdatesProducers(final List<ProducerInput> producers,
                                    final MonthlyUpdateInput monthlyUpdate) {
        if (monthlyUpdate.getProducerChanges().size() != 0) {
            for (ProducerChange producerChange : monthlyUpdate.getProducerChanges()) {
                for (ProducerInput producer : producers) {
                    if (producerChange.getId() == producer.getId()) {
                        producer.setEnergyPerDistributor(producerChange.getEnergyPerDistributor());
                    }
                }
            }
        }
    }

    /**
     * method for duties of consumers and distributors at the beginning of the month
     * @param distributors
     * @param consumers
     */
    public void beginningOfMonthDuties(List<DistributorInput> distributors,
                                       List<ConsumerInput> consumers) {
        /* getting the minimum contract cost distributor */
        DistributorInput minDistributor = distributors
                .stream().filter(distributor -> !distributor.getIsBankrupt()).collect(Collectors
                        .toList()).stream()
                .min(Comparator.comparing(DistributorInput::getActualContractPrice))
                .orElseThrow(NoSuchElementException::new);

        /* choosing contracts for consumers and paying the debt */
        for (ConsumerInput consumer : consumers) {
            if (!consumer.getIsBankrupt()) {
                consumer.totalBudget();
                chooseContract(consumer, minDistributor);
                payDebt(consumer);
            }
        }

        /* paying costs of distributors */
        for (DistributorInput distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                payCostsDistributor(distributor);
            }
        }
    }

    /**
     * method to chose the producers for distributor
     * @param prod
     * @param distributor
     */
    public void chooseProducers(List<ProducerInput> prod, DistributorInput distributor) {
        int totalEnergy = 0;

        /*assigning producers to distributor */
        for (ProducerInput producer : prod) {
            if (producer.getMaxDistributors() != producer.getProdDistributors().size()) {
                totalEnergy += producer.getEnergyPerDistributor();
                producer.getProdDistributors().add(distributor);
                producer.getDistributorIds().add(distributor.getId());
                producer.addObserver(distributor);
                distributor.getChosenProducers().add(producer);

                /* check if the amount of energy is enough for the distributor */
                if (totalEnergy > distributor.getEnergyNeededKW()) {
                    break;
                }
            }
        }
    }

    /**
     * the actions for the first month
     * @param distributors
     * @param consumers
     * @param producers
     */
    public void firstRound(final List<DistributorInput> distributors,
                           final List<ConsumerInput> consumers,
                           final List<ProducerInput> producers) {
        EnergyChoiceStrategy strategy;

        for (DistributorInput distributor : distributors) {

            /* setting the field for changing producer false for all distributors */
            distributor.setToChangeProducer(false);

            /* choosing strategy and producers */
            if (distributor.getChosenProducers().size() == 0) {
                strategy = chooseEnergyType(distributor);
                chooseProducers(strategy.chooseStrategy(producers), distributor);
                computeProductionCost(distributor);
            }
             contractPrice(distributor);
        }
        beginningOfMonthDuties(distributors, consumers);
    }

    /**
     * the actual simulation
     * @param distributors
     * @param consumers
     * @param producers
     * @param monthlyUpdates
     * @param numberOfTurns
     */
    public void simulate(final List<DistributorInput> distributors,
                         final List<ConsumerInput> consumers,
                         final List<ProducerInput> producers,
                         final List<MonthlyUpdateInput> monthlyUpdates,
                         int numberOfTurns) {

        EnergyChoiceStrategy strategy;

        /* start of the simulation */
        for (int i = 0; i < numberOfTurns + 1; ++i) {

            if (i == 0) {
                firstRound(distributors, consumers, producers);
            } else {

                /* setting updates */
                setUpdates(consumers, monthlyUpdates.get(i - 1), distributors);

                /* computing the price for every distributor*/
                for (DistributorInput distributor : distributors) {
                    if (!distributor.getIsBankrupt()) {
                        contractPrice(distributor);
                    }
                }

                beginningOfMonthDuties(distributors, consumers);

                /* setting updates for producers */
                setUpdatesProducers(producers, monthlyUpdates.get(i - 1));

                /* choosing producers for distributors that need to change */
                for (DistributorInput distributor : distributors) {
                    if (!distributor.getIsBankrupt() && distributor.getToChangeProducer()) {
                        /* removing the distributor from the producer's lists */
                        for (ProducerInput producer : distributor.getChosenProducers()) {
                            producer.getDistributorIds().remove((Integer) distributor.getId());
                            producer.getProdDistributors().remove(distributor);
                            producer.deleteObserver(distributor);
                        }
                        distributor.getChosenProducers().clear();
                        strategy = chooseEnergyType(distributor);
                        chooseProducers(strategy.chooseStrategy(producers), distributor);
                        distributor.setToChangeProducer(false);
                        computeProductionCost(distributor);
                    }
                }

                /* updating monthly stats for producers */
                for (ProducerInput producer : producers) {
                    Collections.sort(producer.getDistributorIds());
                    producer.getMonthlyStats().add(new MonthlyStat(i,
                            producer.getDistributorIds()));
                }
            }
        }
    }
}
