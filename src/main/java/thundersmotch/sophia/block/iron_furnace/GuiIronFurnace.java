package thundersmotch.sophia.block.iron_furnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import thundersmotch.sophia.Sophia;

import java.util.Collections;

public class GuiIronFurnace extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    public static final ResourceLocation background = new ResourceLocation(Sophia.MODID, "textures/gui/iron_furnace.png");
    private TileIronFurnace furnace;

    public GuiIronFurnace(TileIronFurnace tileEntity, ContainerIronFurnace container){
        super(container);

        xSize=WIDTH;
        ySize=HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energy = furnace.getClientEnergy();
        drawEnergyBar(energy);

        if (furnace.getClientProgress() > 0){
            int percentage = 100 - furnace.getClientProgress()*100/TileIronFurnace.MAX_PROGRESS;
            drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft+10, guiTop+50, 0xffffff);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        if(mouseX > guiLeft+10 && mouseX < guiLeft+112 && mouseY >  guiTop + 5 && mouseY < guiTop + 15){
            drawHoveringText(Collections.singletonList("Energy: " + furnace.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }
    }

    //Draws the energy bar (alternating red and black bars)
    private void drawEnergyBar(int energy){
        drawRect(guiLeft+10, guiTop+5, guiLeft+112, guiTop+15, 0xff555555);
        int percentage = energy * 100 / TileIronFurnace.MAX_POWER;
        for (int i = 0; i < percentage; i++){
            drawVerticalLine(guiLeft+10+1+i, guiTop+5, guiTop+14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }
}
