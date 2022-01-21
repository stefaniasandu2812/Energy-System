package output;

import java.util.ArrayList;
import java.util.List;

public final class OutputData {
    private List<ConsumerOutput> consumers = new ArrayList<>();
    private List<DistributorOutput> distributors = new ArrayList<>();
    private List<ProducerOutput> energyProducers = new ArrayList<>();

    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerOutput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorOutput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerOutput> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(List<ProducerOutput> energyProducers) {
        this.energyProducers = energyProducers;
    }
}
