package thundersmotch.sophia.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import thundersmotch.sophia.block.ModBlocks;

public class ModItems {

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockIronFurnace).setRegistryName(ModBlocks.blockIronFurnace.getRegistryName()));
    }
}
