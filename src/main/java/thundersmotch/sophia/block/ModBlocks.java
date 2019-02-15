package thundersmotch.sophia.block;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.block.iron_furnace.BlockIronFurnace;
import thundersmotch.sophia.block.ore.BlockCopperOre;

public class ModBlocks {

    @GameRegistry.ObjectHolder("sophia:iron_furnace")
    public static BlockIronFurnace blockIronFurnace;

    @GameRegistry.ObjectHolder("sophia:copper_ore")
    public static BlockCopperOre blockCopperOre;


    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().register(new BlockIronFurnace());
        event.getRegistry().register(new BlockCopperOre());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        blockIronFurnace.initModel();
        blockCopperOre.initModel();
    }

}
