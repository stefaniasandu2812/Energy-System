import utils.EnergySystem;
import input.ConsumerInput;
import input.DistributorInput;
import input.Input;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import input.MonthlyUpdateInput;
import output.ConsumerOutput;
import output.DistributorOutput;
import output.EntityFactory;
import output.OutputData;

public final class Main {

    private Main() {
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        /* reading from JSON file */
        ObjectMapper objectMapperInput = new ObjectMapper();
        Input input = objectMapperInput.readValue(new File(args[0]), Input.class);

        int numberOfTurns = input.getNumberOfTurns();
        List<ConsumerInput> consumers = input.getInitialData().getConsumers();
        List<DistributorInput> distributors = input.getInitialData().getDistributors();
        List<MonthlyUpdateInput> monthlyUpdates = input.getMonthlyUpdates();

        /* start of the simulation */
        EnergySystem energySystem = EnergySystem.getInstance();

        for (int i = 0; i < numberOfTurns + 1; ++i) {
            /* setting updates */
            if (i != 0) {
                energySystem.setUpdates(consumers, monthlyUpdates.get(i - 1), distributors);
            }

            /* computing the price for every distributor*/
            for (DistributorInput distributor : distributors) {
                if (!distributor.getIsBankrupt()) {
                    energySystem.contractPrice(distributor);
                }
            }

            /* getting the distributor with the minimum price */
            DistributorInput minDistributor = distributors
                    .stream().filter(distributor -> !distributor.getIsBankrupt()).collect(Collectors
                        .toList()).stream()
                    .min(Comparator.comparing(DistributorInput::getActualContractPrice))
                    .orElseThrow(NoSuchElementException::new);

            /* choosing contracts for consumers and paying the debt */
            for (ConsumerInput consumer : consumers) {
                if (!consumer.getIsBankrupt()) {
                    consumer.totalBudget();
                    energySystem.chooseContract(consumer, minDistributor);
                    energySystem.payDebt(consumer);
                }
            }

            /* paying the costs of distributors */
            for (DistributorInput distributor : distributors) {
                if (!distributor.getIsBankrupt()) {
                    energySystem.payCostsDistributor(distributor);
                }
            }
        }

        /* writing to JSON file */
        OutputData outputData = new OutputData();
        EntityFactory entityFactory = new EntityFactory();

        for (ConsumerInput consumer : consumers) {
            outputData.getConsumers().add((ConsumerOutput) entityFactory
                    .createEntity("consumer", consumer));
        }
        for (DistributorInput distributor : distributors) {
            outputData.getDistributors().add((DistributorOutput) entityFactory
            .createEntity("distributor", distributor));
        }

        ObjectMapper objectMapperOutput = new ObjectMapper();
        objectMapperOutput.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]),
                outputData);
    }
}
