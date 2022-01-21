package input;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyStat {
    private int month;
    private List<Integer> distributorsIds;

    public MonthlyStat(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = new ArrayList<>(distributorsIds);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
