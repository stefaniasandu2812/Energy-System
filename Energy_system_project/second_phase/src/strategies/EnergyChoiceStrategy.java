package strategies;

import input.ProducerInput;
import java.util.List;

public interface EnergyChoiceStrategy {
    /**
     *
     * @param producers all producers
     * @return list of sorted producers based on the chosen strategy
     */
    List<ProducerInput> chooseStrategy(List<ProducerInput> producers);
}
