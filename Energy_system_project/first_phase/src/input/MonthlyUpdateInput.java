package input;

import java.util.List;

public final class MonthlyUpdateInput {
    private List<ConsumerInput> newConsumers;
    private List<CostInput> costsChanges;

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostInput> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostInput> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
