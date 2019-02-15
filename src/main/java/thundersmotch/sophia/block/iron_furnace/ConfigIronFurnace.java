package thundersmotch.sophia.block.iron_furnace;

import net.minecraftforge.common.config.Config;

public class ConfigIronFurnace {

    @Config.Name((char)0xE000 + "MAX_PROGRESS")
    @Config.LangKey("config.sophia.blockIronFurnace.max_progress")
    public static int MAX_PROGRESS = 40;

    @Config.Name((char)0xE001 + "MAX_POWER")
    @Config.LangKey("config.sophia.blockIronFurnace.max_power")
    public static int MAX_POWER = 100000;

    @Config.Name((char)0xE002 + "MAX_RF_INPUT")
    @Config.LangKey("config.sophia.blockIronFurnace.max_rf_input")
    public static int MAX_RF_INPUT = 100;

    @Config.Name((char)0xE003 + "RF_PER_TICK")
    @Config.LangKey("config.sophia.blockIronFurnace.rf_per_tick")
    public static int RF_PER_TICK = 20;


}