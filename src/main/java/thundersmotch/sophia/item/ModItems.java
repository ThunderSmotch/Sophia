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

    @GameRegistry.ObjectHolder("sophia:copper_ingot")
    public static  ItemCopperIngot itemCopperIngot;

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockIronFurnace).setRegistryName(ModBlocks.blockIronFurnace.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockGenerator).setRegistryName(ModBlocks.blockGenerator.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockCopperOre){
            @Override
            public int getMetadata(int damage) {
                return damage;
            }
        }.setRegistryName(ModBlocks.blockCopperOre.getRegistryName()).setHasSubtypes(true));

        event.getRegistry().register(new ItemEncyclopedia());
        event.getRegistry().register(new ItemCopperIngot());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        itemEncyclopedia.initModel();
        itemCopperIngot.initModel();
    }
}
