package thundersmotch.sophia.block.ore;

import net.minecraftforge.common.config.Config;

public class ConfigOres {

    @Config.LangKey("config.sophia.ores.retrogen")
    @Config.Name((char)0xE000 + "RETROGEN")
    public static boolean RETROGEN = true;

    @Config.LangKey("config.sophia.ores.verbose")
    @Config.Name((char)0xE001 + "VERBOSE")
    public static boolean VERBOSE = true;

    //CATEGORIES
    @Config.LangKey("config.sophia.copper_ore")
    public static CopperOre copperOre;

    public static class CopperOre implements IConfigOre{

        @Config.Name((char)0xE000 + "gen_overworld")
        @Config.LangKey("config.sophia.copper_ore.gen_overworld")
        public static boolean GEN_OVERWORLD = true;

        @Config.Name((char)0xE001 + "gen_nether")
        @Config.LangKey("config.sophia.copper_ore.gen_nether")
        public static boolean GEN_NETHER = true;

        @Config.Name((char)0xE002 + "gen_end")
        @Config.LangKey("config.sophia.copper_ore.gen_end")
        public static boolean GEN_END = true;

        @Config.Name((char)0xE003 + "min_vein_size")
        @Config.LangKey("config.sophia.copper_ore.min_vein_size")
        public static int MIN_VEIN_SIZE = 4;

        @Config.Name((char)0xE004 + "max_vein_size")
        @Config.LangKey("config.sophia.copper_ore.max_vein_size")
        public static int MAX_VEIN_SIZE = 8;

        @Config.Name((char)0xE005 + "chances_to_spawn")
        @Config.LangKey("config.sophia.copper_ore.chances_to_spawn")
        public static int CHANCES_TO_SPAWN = 9;

        @Config.Name((char)0xE006 + "min_y")
        @Config.LangKey("config.sophia.copper_ore.min_y")
        public static int MIN_Y = 2;

        @Config.Name((char)0xE007 + "max_y")
        @Config.LangKey("config.sophia.copper_ore.max_y")
        public static int MAX_Y = 50;


    }
}
