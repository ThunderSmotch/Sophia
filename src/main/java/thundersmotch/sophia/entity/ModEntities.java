package thundersmotch.sophia.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.entity.guard.EntityGuard;
import thundersmotch.sophia.entity.guard.RenderGuard;

public class ModEntities {

    public static void init(){
        int id = 1;

        EntityRegistry.registerModEntity(new ResourceLocation(Sophia.MODID, "sophia_guard"), EntityGuard.class, "sophia_guard", id++, Sophia.instance, 64,
                3,true, 0x222222, 0x555555);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels(){
        RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, RenderGuard.FACTORY);
    }

}
