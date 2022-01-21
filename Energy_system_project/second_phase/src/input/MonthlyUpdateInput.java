package input;

import java.util.List;

public final class MonthlyUpdateInput {
    private List<ConsumerInput> newConsumers;
    private List<DistributorInput> distributorChanges;
    private List<ProducerChange> producerChanges;

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorInput> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<DistributorInput> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChange> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(List<ProducerChange> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
