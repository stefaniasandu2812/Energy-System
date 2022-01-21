package input;

import java.util.List;

public final class Input {
    private int numberOfTurns;
    private InitialData initialData;
    private List<MonthlyUpdateInput> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdateInput> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdateInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
