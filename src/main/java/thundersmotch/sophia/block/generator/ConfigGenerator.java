package thundersmotch.sophia.block.generator;

import net.minecraftforge.common.config.Config;

public class ConfigGenerator {

    @Config.Name((char)0xE000 + "MAX_POWER")
    @Config.LangKey("config.sophia.blockGenerator.max_power")
    public static int MAX_POWER = 100000;

}