package thundersmotch.sophia.proxy;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import jdk.nashorn.internal.ir.annotations.Immutable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.block.ModBlocks;
import thundersmotch.sophia.block.generator.DamageTracker;
import thundersmotch.sophia.entity.ModEntities;
import thundersmotch.sophia.gui.GuiHandler;
import thundersmotch.sophia.item.ModItems;
import thundersmotch.sophia.network.Messages;
import thundersmotch.sophia.worldgen.OreGenerator;
import thundersmotch.sophia.worldgen.WorldTickHandler;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Messages.registerMessages(Sophia.MODID);
        GameRegistry.registerWorldGenerator(OreGenerator.instance, 5);
        MinecraftForge.EVENT_BUS.register(OreGenerator.instance);

        ModEntities.init();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Sophia.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(DamageTracker.instance);
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
    }

    public void postInit(FMLPostInitializationEvent e) {
        //SMELTING
        GameRegistry.addSmelting(ModBlocks.blockCopperOre, new ItemStack(ModItems.itemCopperIngot, 1), 0.0f);

        //ORE DICT
        OreDictionary.registerOre("oreCopper", ModBlocks.blockCopperOre);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.registerItems(event);
    }

    @Nullable
    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters){
        return null;
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule){
        throw new IllegalStateException("This should only be called from the client side!");
    }

    public EntityPlayer getClientPlayer(){
        throw new IllegalStateException("This should only be called from the client side!");
    }
}