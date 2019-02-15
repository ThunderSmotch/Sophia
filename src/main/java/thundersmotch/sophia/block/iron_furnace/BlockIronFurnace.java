package thundersmotch.sophia.block.iron_furnace;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import thundersmotch.sophia.Sophia;
import mcp.mobius.waila.api.IWailaDataAccessor;
import thundersmotch.sophia.block.BlockGeneric;

import javax.annotation.Nullable;
import java.util.List;

public class BlockIronFurnace extends BlockGeneric implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<FurnaceState> STATE = PropertyEnum.create("state", FurnaceState.class);

    public BlockIronFurnace() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(Sophia.MODID, "iron_furnace"));
        setTranslationKey(Sophia.MODID + ".iron_furnace");
        setHardness(5.0F);
        setHarvestLevel("pickaxe", 2);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK):world.getTileEntity(pos);
        if (te instanceof TileIronFurnace){
            return state.withProperty(STATE, ((TileIronFurnace) te).getState());
        }
        return super.getActualState(state,world,pos);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileIronFurnace();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound != null){
            int energy = compound.getInteger("energy");
            int sizeIn = getItemCount(compound, "itemsIn");
            int sizeOut = getItemCount(compound, "itemsOut");

            addInformationLocalized(tooltip, "message.sophia.blockIronFurnace", energy, sizeIn, sizeOut);
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private int getItemCount(NBTTagCompound compound, String key){
        int sizeIn = 0;
        NBTTagCompound tag = (NBTTagCompound) compound.getTag(key);
        NBTTagList items = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); i++){
            NBTTagCompound itemTag = items.getCompoundTagAt(i);
            if (!new ItemStack(itemTag).isEmpty()){
                sizeIn++;
            }
        }
        return sizeIn;
    }

    //TODO pass this to BlockGeneric
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
            tooltip.add(TextFormatting.YELLOW + "Energy: " + "TODO" + "/" + ConfigIronFurnace.MAX_POWER + " FE");
            tooltip.add("A faster furnace!");
        }
        return tooltip;
    }
}
