package thundersmotch.sophia.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.Sophia;

public class ItemEncyclopedia extends Item {
    public ItemEncyclopedia(){
        setRegistryName(new ResourceLocation(Sophia.MODID, "encyclopedia"));
        setTranslationKey(Sophia.MODID + ".encyclopedia");
        setCreativeTab(Sophia.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
