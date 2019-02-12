package thundersmotch.sophia.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.block.ModBlocks;

public class ModItems {

    @GameRegistry.ObjectHolder("sophia:encyclopedia")
    public static ItemEncyclopedia itemEncyclopedia;

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockIronFurnace).setRegistryName(ModBlocks.blockIronFurnace.getRegistryName()));
        event.getRegistry().register(new ItemEncyclopedia());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        itemEncyclopedia.initModel();
    }
}
