package net.kyrptonaught.upgradedechests.block.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kyrptonaught.upgradedechests.block.CustomChestBase;
import net.kyrptonaught.upgradedechests.block.blockEntities.RiftEnderChestBlockEntity;
import net.kyrptonaught.upgradedechests.block.blockEntities.SpatialEnderChestBlockEntity;
import net.kyrptonaught.upgradedechests.registry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ChestItemWithCustomRenderer extends BlockItem {
    public ChestItemWithCustomRenderer(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                Minecraft mc = Minecraft.getInstance();
                return new BlockEntityWithoutLevelRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels()) {
                    SpatialEnderChestBlockEntity SPATIAL_TILE = new SpatialEnderChestBlockEntity(BlockPos.ZERO, ModBlocks.SPATIAL_ENDER_CHEST.get().defaultBlockState());
                    RiftEnderChestBlockEntity RIFT_TILE = new RiftEnderChestBlockEntity(BlockPos.ZERO, ModBlocks.RIFT_ENDER_CHEST.get().defaultBlockState());
                    @Override
                    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
                        if (getBlock() instanceof CustomChestBase block)
                            mc.getBlockEntityRenderDispatcher().renderItem(block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()), poseStack, buffer, combinedLight, combinedOverlay);
                    }
                };
            }
        });
    }
}
