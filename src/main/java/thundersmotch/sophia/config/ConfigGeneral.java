package thundersmotch.sophia.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.block.iron_furnace.ConfigIronFurnace;
import thundersmotch.sophia.block.ore.ConfigOres;

@Config(modid = Sophia.MODID, name = "Sophia/" + Sophia.MODID)
@Mod.EventBusSubscriber(modid = Sophia.MODID)
public class ConfigGeneral {

    //CATEGORIES
    @Config.LangKey("config.sophia.category.blocks")
    @Config.Name((char)0xE000 + "Blocks")
    public static Blocks blocks;

    public static class Blocks{

        @Config.LangKey("config.sophia.category.ores")
        @Config.Name((char)0xE000 + "Ores")
        public static ConfigOres ore;

        @Config.LangKey("config.sophia.iron_furnace")
        @Config.Name((char)0xE001 + "Iron Furnace")
        public static ConfigIronFurnace ironFurnace;


    }

    //CONFIG RELOADING
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Sophia.MODID)) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE); // Sync config values
        }
    }

}