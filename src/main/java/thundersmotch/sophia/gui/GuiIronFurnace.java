package thundersmotch.sophia.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import thundersmotch.sophia.Sophia;
import thundersmotch.sophia.container.ContainerIronFurnace;
import thundersmotch.sophia.tile.TileIronFurnace;

public class GuiIronFurnace extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    public static final ResourceLocation background = new ResourceLocation(Sophia.MODID, "textures/gui/iron_furnace.png");

    public GuiIronFurnace(TileIronFurnace tileEntity, ContainerIronFurnace container){
        super(container);

        xSize=WIDTH;
        ySize=HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
