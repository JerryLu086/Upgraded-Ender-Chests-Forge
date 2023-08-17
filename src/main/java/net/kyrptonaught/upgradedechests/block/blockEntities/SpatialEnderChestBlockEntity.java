package net.kyrptonaught.upgradedechests.block.blockEntities;

import net.kyrptonaught.upgradedechests.client.CustomChestRenderer;
import net.kyrptonaught.upgradedechests.container.SpatialContainer;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.state.BlockState;

public class SpatialEnderChestBlockEntity extends CustomChestBlockEntity {
    public SpatialEnderChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPATIAL_ENDER_CHEST.get(), pos, state);
    }

    @Override
    public boolean isOwnContainer(Player player) {
        if (player.containerMenu instanceof ChestMenu menu && menu.getContainer() instanceof SpatialContainer container) {
            return container.activeChest == SpatialEnderChestBlockEntity.this;
        } else {
            return false;
        }
    }

    @Override
    public Material getChestMaterial() {
        return CustomChestRenderer.createChestMaterial(CustomChestRenderer.SPATIAL_RL);
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }
}
