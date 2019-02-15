package thundersmotch.sophia.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.block.generator.BlockGenerator;
import thundersmotch.sophia.block.generator.TileGenerator;
import thundersmotch.sophia.block.iron_furnace.BlockIronFurnace;
import thundersmotch.sophia.block.iron_furnace.TileIronFurnace;
import thundersmotch.sophia.block.ore.BlockCopperOre;

@GameRegistry.ObjectHolder("sophia")
public class ModBlocks {

    @GameRegistry.ObjectHolder("sophia:iron_furnace")
    public static BlockIronFurnace blockIronFurnace;

    @GameRegistry.ObjectHolder("sophia:copper_ore")
    public static BlockCopperOre blockCopperOre;

    @GameRegistry.ObjectHolder("sophia:generator")
    public static BlockGenerator blockGenerator;


    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().register(new BlockIronFurnace());
        event.getRegistry().register(new BlockCopperOre());
        event.getRegistry().register(new BlockGenerator());

        GameRegistry.registerTileEntity(TileIronFurnace.class, new ResourceLocation(Sophia.MODID, "iron_furnace"));
        GameRegistry.registerTileEntity(TileGenerator.class, new ResourceLocation(Sophia.MODID, "generator"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        blockIronFurnace.initModel();
        blockCopperOre.initModel();
        blockGenerator.initModel();
    }

}
