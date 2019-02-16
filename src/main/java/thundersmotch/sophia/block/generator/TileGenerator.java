package thundersmotch.sophia.block.generator;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.tools.IGuiTile;
import thundersmotch.sophia.tools.IRestorableTileEntity;
import thundersmotch.sophia.tools.ModEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class TileGenerator extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile {

    private AxisAlignedBB trackingBox;
    private int trackCounter = 0;
    private int clientEnergy = -1;

    @Nullable
    private final IAnimationStateMachine asm;

    public TileGenerator(){
        asm = Sophia.proxy.load(new ResourceLocation(Sophia.MODID, "asms/block/generator.json"), ImmutableMap.of());
    }

    @Override
    public void update() {
        if (!world.isRemote){
            trackCounter--;
            if (trackCounter <= 0){
                trackCounter = 20;
                findEntities();
            }
        }

        sendEnergy();
    }

    private void sendEnergy(){
        if (energyStorage.getEnergyStored() > 0){
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity te = world.getTileEntity(pos.offset(facing));
                if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())){
                    IEnergyStorage handler = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive()){
                        int accepted = handler.receiveEnergy(energyStorage.getEnergyStored(), false);
                        energyStorage.consumePower(accepted);
                        if (energyStorage.getEnergyStored() <= 0){
                            break;
                        }
                    }
                }
            }
            markDirty();
        }
    }

    public int getClientEnergy(){
        return clientEnergy;
    }

    public void setClientEnergy(int energy){
        this.clientEnergy = energy;
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        DamageTracker.instance.remove(world.provider.getDimension(), pos);
    }

    private void findEntities(){
        int dim = world.provider.getDimension();
        DamageTracker.instance.clear(dim, pos);
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, getTrackingBox());
        for (EntityLivingBase entity: entities){
            DamageTracker.instance.register(dim, pos, entity.getUniqueID());
        }
    }

    public void senseDamage(EntityLivingBase entity, float amount){
        if (getTrackingBox().contains(entity.getPositionVector())){
            energyStorage.generatePower((int) (amount * ConfigGenerator.POWER_DAMAGE_FACTOR));
        }
    }

    private AxisAlignedBB getTrackingBox(){
        if (trackingBox == null){
            trackingBox = new AxisAlignedBB(pos.add(-5, -3, -5), pos.add(5,3,5));
        }
        return trackingBox;
    }


    // -------------------------------------------------

    private ModEnergyStorage energyStorage = new ModEnergyStorage(ConfigGenerator.MAX_POWER, 0);

    // -------------------------------------------------

    @Override
    public Container createContainer(EntityPlayer player) {
        return new ContainerGenerator(player.inventory, this);
    }

    @Override
    public GuiContainer createGui(EntityPlayer player) {
        return new GuiGenerator(this, new ContainerGenerator(player.inventory, this));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        energyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", energyStorage.getEnergyStored());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY){
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY){
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm);
        }
        return super.getCapability(capability, facing);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        //If we are too far away from the te then you cannot use it
        return !isInvalid() && (playerIn.getDistanceSq(pos.add(0.5D, 0.5D,0.5D)) <= 64D);
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
