package thundersmotch.sophia.tools;

import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage {

    public ModEnergyStorage(int capacity, int maxReceive){
        super(capacity, maxReceive, 0);
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void consumePower(int energy){
        this.energy -= energy;
        if (this.energy < 0)
            this.energy = 0;
    }
}
