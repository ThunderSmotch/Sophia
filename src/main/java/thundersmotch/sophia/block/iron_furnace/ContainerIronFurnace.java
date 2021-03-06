package thundersmotch.sophia.block.iron_furnace;

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
import thundersmotch.sophia.network.Messages;
import thundersmotch.sophia.network.PacketSyncMachine;
import thundersmotch.sophia.tools.IMachineStateContainer;

public class ContainerIronFurnace extends Container implements IMachineStateContainer {

    private TileIronFurnace te;

    private static final int PROGRESS_ID = 0;

    public ContainerIronFurnace(IInventory playerInventory, TileIronFurnace te){
        this.te = te;

        //Defines the te's slots and the player slots
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 10;
        int y = 26;

        //Add furnace slots
        int slotIndex = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x+= 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x+= 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        x = 118;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x+= 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x+= 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
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
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()){
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            //Shift clicking from the te slots
            if(index < TileIronFurnace.SIZE){
                if (!this.mergeItemStack(itemStack1, TileIronFurnace.SIZE, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } //Shift clicking from the inventory
            else if (!this.mergeItemStack(itemStack1, 0, TileIronFurnace.SIZE, false)){
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemStack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return te.canInteractWith(entityPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (te.getWorld().isRemote){
            return;
        }

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
        }
    }

    @Override
    public void sync(int energy, int progress) {
        te.setClientEnergy(energy);
        te.setClientProgress(progress);
    }
}
