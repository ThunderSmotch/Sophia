package thundersmotch.sophia.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
import thundersmotch.sophia.network.Messages;
import thundersmotch.sophia.block.iron_furnace.TileIronFurnace;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Messages.registerMessages(Sophia.MODID);
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Sophia.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event);
        GameRegistry.registerTileEntity(TileIronFurnace.class, new ResourceLocation(Sophia.MODID, "iron_furnace"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.registerItems(event);
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule){
        throw new IllegalStateException("This should only be called from the client side!");
    }

    public EntityPlayer getClientPlayer(){
        throw new IllegalStateException("This should only be called from the client side!");
    }
}