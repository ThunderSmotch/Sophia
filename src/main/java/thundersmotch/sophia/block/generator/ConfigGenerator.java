package thundersmotch.sophia.block.generator;

import net.minecraftforge.common.config.Config;

public class ConfigGenerator {

    @Config.Name((char)0xE000 + "MAX_POWER")
    @Config.LangKey("config.sophia.generator.max_power")
    public static int MAX_POWER = 100000;

    @Config.Name((char)0xE001 + "POWER_DAMAGE_FACTOR")
    @Config.LangKey("config.sophia.generator.power_damage_factor")
    public static float POWER_DAMAGE_FACTOR = 5.0f;

}