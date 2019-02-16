package thundersmotch.sophia;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;
import thundersmotch.sophia.block.generator.DamageTracker;
import thundersmotch.sophia.item.ModItems;
import thundersmotch.sophia.proxy.CommonProxy;

@Mod(modid = Sophia.MODID, name = Sophia.NAME, version = Sophia.VERSION)
public class Sophia
{
    //Constants
    public static final String MODID = "sophia";
    public static final String NAME = "Sophia";
    public static final String VERSION = "0.1";

    @SidedProxy(clientSide = "thundersmotch.sophia.proxy.ClientProxy", serverSide = "thundersmotch.sophia.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Sophia instance;

    public static Logger logger; //For logging things

    public static CreativeTabs creativeTab = new CreativeTabs("sophia") {
        @Override
        public ItemStack createIcon() {return new ItemStack(ModItems.itemEncyclopedia);}
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event){
        DamageTracker.instance.reset();
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event){
        DamageTracker.instance.reset();
    }
}
