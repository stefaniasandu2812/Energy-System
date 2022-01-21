package strategies;

import input.ProducerInput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class GreenStrategy implements EnergyChoiceStrategy {
    @Override
    public List<ProducerInput> chooseStrategy(List<ProducerInput> producers) {

        /* setting variable for each producer
        * to see who can provide renewable energy */
        for (ProducerInput producer : producers) {
            producer.setHasRenewable();
        }

        return producers.stream().sorted(Comparator.comparing(
                ProducerInput::isHasRenewableEnergy, Comparator.reverseOrder())
                .thenComparing(ProducerInput::getPriceKW)
                .thenComparing(ProducerInput::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(ProducerInput::getId))
                .collect(Collectors.toList());
    }
}
