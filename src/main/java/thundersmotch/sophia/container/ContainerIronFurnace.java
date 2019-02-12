package thundersmotch.sophia.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thundersmotch.sophia.tile.TileIronFurnace;

public class ContainerIronFurnace extends Container {

    private TileIronFurnace te;

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
                this.addSlotToContainer(new Slot(playerInventory, col+row*9 + 10, x, y));
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
}
