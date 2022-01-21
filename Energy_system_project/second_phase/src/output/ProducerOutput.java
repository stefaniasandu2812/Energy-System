package output;

import entities.EnergyType;
import input.MonthlyStat;
import input.ProducerInput;
import java.util.List;

public final class ProducerOutput implements EntityOutput {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private EnergyType energyType;
    private int energyPerDistributor;
    private List<MonthlyStat> monthlyStats;

    public ProducerOutput(ProducerInput producer) {
        this.id = producer.getId();
        this.maxDistributors = producer.getMaxDistributors();
        this.priceKW = producer.getPriceKW();
        this.energyType = producer.getEnergyType();
        this.energyPerDistributor = producer.getEnergyPerDistributor();
        this.monthlyStats = producer.getMonthlyStats();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
