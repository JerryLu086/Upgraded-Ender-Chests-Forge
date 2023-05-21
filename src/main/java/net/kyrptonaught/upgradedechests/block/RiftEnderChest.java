package net.kyrptonaught.upgradedechests.block;

import net.kyrptonaught.upgradedechests.Utils;
import net.kyrptonaught.upgradedechests.block.tile.CustomChestTileBase;
import net.kyrptonaught.upgradedechests.block.tile.RiftEnderChestTile;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Random;

public class RiftEnderChest extends CustomChestBase {
    public RiftEnderChest(Properties builder) {
        super(builder,  ModTiles.RIFT_ENDER_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RiftEnderChestTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? createTickerHelper(type, ModTiles.RIFT_ENDER_CHEST.get(), CustomChestTileBase::lidAnimateTick) : null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        Utils.addPortalLikeParticle(ModParticles.BLUE_PORTAL.get(), level, pos, random);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide)
            if (placer instanceof Player player) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof RiftEnderChestTile chest) {
                    chest.setStoredPlayer(player);
                }
            }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof RiftEnderChestTile chest) {
                if (!chest.hasStoredPlayer())
                    player.displayClientMessage(new TranslatableComponent("block.upgradedechests.rift_ender_chest.invalid_1"), true);
                if (chest.getContainer() == null)
                    player.displayClientMessage(new TranslatableComponent("block.upgradedechests.rift_ender_chest.invalid_2"), true);
                else {
                    player.openMenu(!ChestBlock.isChestBlockedAt(level, pos) ? new SimpleMenuProvider((i, playerInventory, playerEntity) -> ChestMenu.threeRows(i, playerInventory, chest),
                            new TranslatableComponent("block.upgradedechests.rift_ender_chest.title", chest.getPlayerName())) : null);
                    player.awardStat(Stats.CUSTOM.get(Stats.OPEN_ENDERCHEST));
                    PiglinAi.angerNearbyPiglins(player, true);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}