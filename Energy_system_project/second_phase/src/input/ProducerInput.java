package input;

import entities.EnergyType;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class ProducerInput extends Observable implements EntityInput {
    private int id;
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;
    private List<DistributorInput> prodDistributors = new ArrayList<>();
    private boolean hasRenewableEnergy;
    private List<MonthlyStat> monthlyStats = new ArrayList<>();
    private List<Integer> distributorIds = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
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

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     * setting the energy updated for producer
     * @param energyPerDistributor
     */
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        setChanged();
        notifyObservers();
    }

    public List<DistributorInput> getProdDistributors() {
        return prodDistributors;
    }

    public void setProdDistributors(List<DistributorInput> prodDistributors) {
        this.prodDistributors = prodDistributors;
    }

    public boolean isHasRenewableEnergy() {
        return hasRenewableEnergy;
    }

    /**
     * using an extra field for sorting the producers
     */
    public void setHasRenewable() {
        if (energyType.isRenewable()) {
            this.hasRenewableEnergy = true;
        }
    }

    public void setHasRenewableEnergy(boolean hasRenewableEnergy) {
        this.hasRenewableEnergy = hasRenewableEnergy;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public List<Integer> getDistributorIds() {
        return distributorIds;
    }

    public void setDistributorIds(List<Integer> distributorIds) {
        this.distributorIds = distributorIds;
    }
}
