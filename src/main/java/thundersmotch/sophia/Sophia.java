package thundersmotch.sophia;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import thundersmotch.sophia.proxy.CommonProxy;

@Mod(modid = Sophia.MODID, name = Sophia.NAME, version = Sophia.VERSION)
public class Sophia
{
    public static final String MODID = "sophia";
    public static final String NAME = "Sophia";
    public static final String VERSION = "0.1";

    @SidedProxy(clientSide = "thundersmotch.sophia.proxy.ClientProxy", serverSide = "thundersmotch.sophia.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Sophia instance;

    public static Logger logger; //For logging things



    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event) {
    }
}
