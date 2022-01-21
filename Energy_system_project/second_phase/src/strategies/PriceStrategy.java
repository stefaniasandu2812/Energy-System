package strategies;

import input.ProducerInput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class PriceStrategy implements EnergyChoiceStrategy {
    @Override
    public List<ProducerInput> chooseStrategy(List<ProducerInput> producers) {

        return producers.stream().sorted(Comparator.comparing(ProducerInput::getPriceKW)
                .thenComparing(ProducerInput::getPriceKW)
                .thenComparing(ProducerInput::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(ProducerInput::getId))
                .collect(Collectors.toList());
    }
}
