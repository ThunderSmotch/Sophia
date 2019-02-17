package thundersmotch.sophia.entity.guard;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderGuard extends RenderLiving<EntityGuard> {

    private ResourceLocation mobTexture = new ResourceLocation("sophia:textures/entity/guard/guard.png");

    public RenderGuard(RenderManager renderManager){
        super(renderManager, new ModelGuard(), 0.8F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGuard entity) {
        return mobTexture;
    }

    public static final RenderGuard.Factory FACTORY = new RenderGuard.Factory();

    public static class Factory implements IRenderFactory<EntityGuard>{
        @Override
        public Render<? super EntityGuard> createRenderFor(RenderManager manager) {
            return new RenderGuard(manager);
        }
    }
}
