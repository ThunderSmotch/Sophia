package thundersmotch.sophia.block.iron_furnace;

import net.minecraftforge.common.config.Config;

public class ConfigIronFurnace {

    @Config.LangKey("config.sophia.iron_furnace.max_progress")
    public static int MAX_PROGRESS = 40;

    @Config.LangKey("config.sophia.iron_furnace.max_power")
    public static int MAX_POWER = 100000;

    @Config.LangKey("config.sophia.iron_furnace.max_rf_input")
    public static int MAX_RF_INPUT = 100;

    @Config.LangKey("config.sophia.iron_furnace.rf_per_tick")
    public static int RF_PER_TICK = 20;


}