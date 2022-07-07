package net.kyrptonaught.upgradedechests.block.tile;

import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CustomChestTileBase extends BlockEntity implements LidBlockEntity {
    public CustomChestTileBase(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
        super(tile, pos, state);
    }

    private final ChestLidController chestLidController = new ChestLidController();
    protected final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        public void onOpen(Level level, BlockPos pos, BlockState state) {
            level.playSound((Player)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }

        @Override
        public void onClose(Level level, BlockPos pos, BlockState state) {
            level.playSound((Player)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.ENDER_CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }

        @Override
        public void openerCountChanged(Level level, BlockPos pos, BlockState state, int prev, int pNew) {
            level.blockEvent(CustomChestTileBase.this.worldPosition, state.getBlock(), 1, pNew);
        }

        @Override
        public boolean isOwnContainer(Player player) {
            return CustomChestTileBase.this.isOwnContainer(player);
        }
    };

    public abstract boolean isOwnContainer(Player player);

    public abstract Material getChestMaterial();

    public static void lidAnimateTick(Level level, BlockPos pos, BlockState state, CustomChestTileBase tile) {
        tile.chestLidController.tickLid();
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }
}