/**
package net.kyrptonaught.upgradedechests.compat.jei.category;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
 **/

// Rip-off'd from Tinkers Construct 3
// Link: https://github.com/SlimeKnights/TinkersConstruct/blob/1.18.2/src/main/java/slimeknights/tconstruct/plugin/jei/entity/EntityIngredientRenderer.java

/**
public class CreeperJEIRenderer implements IIngredientRenderer<Creeper> {
    @Override
    public void render(PoseStack matrixStack, @Nullable Creeper creeper) {
        Level level = Minecraft.getInstance().level;
        if (level == null)
            return;
        PoseStack modelView = RenderSystem.getModelViewStack();
        modelView.pushPose();
        modelView.mulPoseMatrix(matrixStack.last().pose());
        InventoryScreen.renderEntityInInventory(this.getWidth() / 2, this.getHeight(), 30, 0, 10, creeper);
        modelView.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    public List<Component> getTooltip(Creeper creeper, TooltipFlag flag) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(EntityType.CREEPER.getDescription());
        if (flag.isAdvanced()) {
            tooltip.add((new TextComponent(Objects.requireNonNull(EntityType.CREEPER.getRegistryName()).toString())).withStyle(ChatFormatting.DARK_GRAY));
        }
        return tooltip;
    }
}
 **/