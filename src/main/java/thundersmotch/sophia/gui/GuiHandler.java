package thundersmotch.sophia.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import thundersmotch.sophia.block.iron_furnace.ContainerIronFurnace;
import thundersmotch.sophia.block.iron_furnace.GuiIronFurnace;
import thundersmotch.sophia.block.iron_furnace.TileIronFurnace;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileIronFurnace){
            return new ContainerIronFurnace(entityPlayer.inventory, (TileIronFurnace) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileIronFurnace){
            TileIronFurnace tileIronFurnace = (TileIronFurnace) te;
            return new GuiIronFurnace(tileIronFurnace, new ContainerIronFurnace(entityPlayer.inventory, tileIronFurnace));
        }
        return null;
    }
}
