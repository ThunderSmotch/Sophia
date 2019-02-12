package thundersmotch.sophia.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.Sophia;

public class BlockIronFurnace extends Block {

    public BlockIronFurnace() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(Sophia.MODID, "iron_furnace"));
        setUnlocalizedName(Sophia.MODID + ".iron_furnace");
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(Sophia.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
