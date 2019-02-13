package thundersmotch.sophia.block.iron_furnace;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import thundersmotch.sophia.Sophia;
import mcp.mobius.waila.api.IWailaDataAccessor;

import javax.annotation.Nullable;
import java.util.List;

public class BlockIronFurnace extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockIronFurnace() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(Sophia.MODID, "iron_furnace"));
        setUnlocalizedName(Sophia.MODID + ".iron_furnace");
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(Sophia.creativeTab);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileIronFurnace();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if(!(te instanceof TileIronFurnace))
            return false;

        playerIn.openGui(Sophia.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Optional.Method(modid = "waila")
    public List<String> getWailaBody(List<String> tooltip, IWailaDataAccessor accessor){
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileIronFurnace){
            TileIronFurnace furnace = (TileIronFurnace) te;

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                tooltip.add("SHIFTADO!");
            }

            tooltip.add(TextFormatting.GREEN + "Progress: " + furnace.getClientProgress() + "%");

            //TODO fix this when packets are implemented cuz client cannot read the current energy stored!
            tooltip.add(TextFormatting.YELLOW + "Energy: " + "TODO" + "/" + TileIronFurnace.MAX_POWER + " FE");
            tooltip.add("A faster furnace!");
        }
        return tooltip;
    }
}