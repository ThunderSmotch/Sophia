package thundersmotch.sophia.block.generator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thundersmotch.sophia.block.iron_furnace.ConfigIronFurnace;
import thundersmotch.sophia.block.iron_furnace.TileIronFurnace;
import thundersmotch.sophia.network.Messages;
import thundersmotch.sophia.network.PacketSyncMachine;
import thundersmotch.sophia.tools.IMachineStateContainer;

public class ContainerGenerator extends Container implements IMachineStateContainer {

    private TileGenerator te;

    public ContainerGenerator(IInventory playerInventory, TileGenerator te){
        this.te = te;

        //Defines the te's slots and the player slots
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        //Slots for the main inventory
        for (int row = 0; row < 3; row++){
            for (int col= 0; col < 9; col++){
                int x = 10 + col*18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col+row*9 + 9, x, y));
            }
        }

        //Add hotbar slots
        for (int row = 0; row < 9; row++){
            int x = 10 + row*18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return te.canInteractWith(entityPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        /*
        if(te.getEnergy() != te.getClientEnergy() || te.getProgress() != te.getClientProgress()){

            te.setClientEnergy(te.getEnergy());
            te.setClientProgress(te.getProgress());

            for (IContainerListener listener: listeners){
                if (listener instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    int pct = 100 - te.getProgress() * 100 / ConfigIronFurnace.MAX_PROGRESS;
                    Messages.INSTANCE.sendTo(new PacketSyncMachine(te.getEnergy(), pct), player);
                }
            }
        }*/
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void sync(int energy, int progress) {
        // te.setClientEnergy(energy);
        // te.setClientProgress(progress);
    }
}
