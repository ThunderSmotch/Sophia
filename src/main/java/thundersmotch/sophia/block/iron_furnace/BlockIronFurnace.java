package thundersmotch.sophia.block.iron_furnace;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import thundersmotch.sophia.Sophia;
import mcp.mobius.waila.api.IWailaDataAccessor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class BlockIronFurnace extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<FurnaceState> STATE = PropertyEnum.create("state", FurnaceState.class);

    public BlockIronFurnace() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(Sophia.MODID, "iron_furnace"));
        setTranslationKey(Sophia.MODID + ".iron_furnace");
        setHardness(5.0F);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if(!(te instanceof TileIronFurnace))
            return false;

        playerIn.openGui(Sophia.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileIronFurnace){
            NBTTagCompound compound = stack.getTagCompound();
            if (compound != null){
                ((TileIronFurnace) te).readRestorableFromNBT(compound);
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TileIronFurnace){
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound compound = new NBTTagCompound();
            ((TileIronFurnace) tileEntity).writeRestorableToNBT(compound);

            stack.setTagCompound(compound);
            drops.add(stack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest){
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound != null){
            int energy = compound.getInteger("energy");
            int sizeIn = getItemCount(compound, "itemsIn");
            int sizeOut = getItemCount(compound, "itemsOut");

            String translated = I18n.format("message.sophia.iron_furnace", energy, sizeIn, sizeOut);
            translated = COMPILE.matcher(translated).replaceAll("\u00a7");
            Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
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
