package thundersmotch.sophia.config;

import net.minecraftforge.common.config.Config;
import thundersmotch.sophia.Sophia;

@Config(modid = Sophia.MODID)
public class ConfigGeneral {

    @Config.LangKey("config.config_enabled")
    @Config.Name("Is Config Enabled?")
    @Config.Comment("Set to false if you don't like this config")
    public static boolean configEnabled = true;

    @Config.LangKey("config.player_name")
    @Config.Name("Player Name")
    @Config.Comment("Set your player name here")
    public static String playerName = "ThunderSmotch";

}
