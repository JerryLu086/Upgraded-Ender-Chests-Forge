package net.kyrptonaught.upgradedechests.block.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kyrptonaught.upgradedechests.block.CustomChestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ChestItemWithCustomRenderer extends BlockItem {
    public ChestItemWithCustomRenderer(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                Minecraft mc = Minecraft.getInstance();
                return new BlockEntityWithoutLevelRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels()) {
                    @Override
                    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext context, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
                        if (getBlock() instanceof CustomChestBase block)
                            mc.getBlockEntityRenderDispatcher().renderItem(block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()), poseStack, buffer, combinedLight, combinedOverlay);
                    }
                };
            }
        });
    }
}
