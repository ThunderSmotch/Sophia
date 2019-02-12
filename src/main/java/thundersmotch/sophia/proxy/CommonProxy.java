package thundersmotch.sophia.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.block.ModBlocks;
import thundersmotch.sophia.gui.GuiHandler;
import thundersmotch.sophia.item.ModItems;
import thundersmotch.sophia.tile.TileIronFurnace;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Sophia.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event);
        GameRegistry.registerTileEntity(TileIronFurnace.class, Sophia.MODID + "_iron_furnace");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.registerItems(event);
    }
}