package thundersmotch.sophia.block;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("sophia:iron_furnace")
    public static BlockIronFurnace blockIronFurnace;


    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().register(new BlockIronFurnace());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        blockIronFurnace.initModel();
    }

}
