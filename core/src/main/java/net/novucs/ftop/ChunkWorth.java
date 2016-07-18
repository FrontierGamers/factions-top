package net.novucs.ftop;

import java.util.EnumMap;
import java.util.Objects;

public class ChunkWorth {

    private final EnumMap<WorthType, Double> worth;
    private double totalWorth = 0;
    private long nextRecalculation;

    public ChunkWorth() {
        this(new EnumMap<>(WorthType.class));
    }

    public ChunkWorth(EnumMap<WorthType, Double> worth) {
        this.worth = worth;
        worth.forEach((k, v) -> this.totalWorth += k == WorthType.LIQUID ? 0d : v);
    }

    public double getWorth(WorthType worthType) {
        return worth.getOrDefault(worthType, 0d);
    }

    public void setWorth(WorthType worthType, double worth) {
        if (worthType == WorthType.LIQUID) {
            throw new IllegalArgumentException("Liquid worth cannot be associated with chunks!");
        }

        worth = Math.max(0, worth);
        Double prev = this.worth.put(worthType, worth);
        totalWorth += worth - (prev == null ? 0 : prev);
    }

    public void addWorth(WorthType worthType, double worth) {
        setWorth(worthType, getWorth(worthType) + worth);
    }

    public double getTotalWorth() {
        return totalWorth;
    }

    public long getNextRecalculation() {
        return nextRecalculation;
    }

    public void setNextRecalculation(long nextRecalculation) {
        this.nextRecalculation = nextRecalculation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkWorth that = (ChunkWorth) o;
        return Double.compare(that.totalWorth, totalWorth) == 0 &&
                nextRecalculation == that.nextRecalculation &&
                Objects.equals(worth, that.worth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worth, totalWorth, nextRecalculation);
    }

    @Override
    public String toString() {
        return "ChunkWorth{" +
                "worth=" + worth +
                ", totalWorth=" + totalWorth +
                ", nextRecalculation=" + nextRecalculation +
                '}';
    }
}