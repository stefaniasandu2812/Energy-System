import input.ConsumerInput;
import input.DistributorInput;
import input.Input;
import input.MonthlyUpdateInput;
import input.ProducerInput;
import output.ConsumerOutput;
import output.DistributorOutput;
import output.EntityFactory;
import output.OutputData;
import output.ProducerOutput;
import utils.EnergySystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        /* reading from JSON file */
        ObjectMapper objectMapperInput = new ObjectMapper();
        Input input = objectMapperInput.readValue(new File(args[0]), Input.class);

        int numberOfTurns = input.getNumberOfTurns();
        List<ConsumerInput> consumers = input.getInitialData().getConsumers();
        List<DistributorInput> distributors = input.getInitialData().getDistributors();
        List<MonthlyUpdateInput> monthlyUpdates = input.getMonthlyUpdates();
        List<ProducerInput> producers = input.getInitialData().getProducers();

        /* the simulation */
        EnergySystem energySystem = EnergySystem.getInstance();

        energySystem.simulate(distributors, consumers, producers, monthlyUpdates, numberOfTurns);

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
        for (ProducerInput producer : producers) {
            outputData.getEnergyProducers().add((ProducerOutput) entityFactory
                    .createEntity("producer", producer));
        }

        ObjectMapper objectMapperOutput = new ObjectMapper();
        objectMapperOutput.writerWithDefaultPrettyPrinter()
                .writeValue(new File(args[1]), outputData);
    }
}
