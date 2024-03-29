package net.kyrptonaught.upgradedechests.block;

import net.kyrptonaught.upgradedechests.Utils;
import net.kyrptonaught.upgradedechests.block.blockEntities.CustomChestBlockEntity;
import net.kyrptonaught.upgradedechests.block.blockEntities.RiftEnderChestBlockEntity;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class RiftEnderChest extends CustomChestBase {
    public RiftEnderChest(BlockBehaviour.Properties builder) {
        super(builder,  ModBlockEntities.RIFT_ENDER_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RiftEnderChestBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? createTickerHelper(type, ModBlockEntities.RIFT_ENDER_CHEST.get(), CustomChestBlockEntity::lidAnimateTick) : null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
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
                if (blockEntity instanceof RiftEnderChestBlockEntity chest) {
                    chest.setStoredPlayer(player);
                }
            }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BlockPos above = pos.above();
        if (level.getBlockState(above).isRedstoneConductor(level, above)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (!level.isClientSide && blockEntity instanceof RiftEnderChestBlockEntity chest) {
            if (!chest.hasStoredPlayer()) {
                player.displayClientMessage(Component.translatable("block.upgradedechests.rift_ender_chest.invalid_1"), true);
            } else if (chest.getContainer() == null) {
                player.displayClientMessage(Component.translatable("block.upgradedechests.rift_ender_chest.invalid_2"), true);
            } else {
                player.openMenu(!ChestBlock.isChestBlockedAt(level, pos) ? new SimpleMenuProvider((i, playerInventory, playerEntity) -> ChestMenu.threeRows(i, playerInventory, chest),
                        Component.translatable("block.upgradedechests.rift_ender_chest.title", chest.getPlayerName())) : null);
                player.awardStat(Stats.CUSTOM.get(Stats.OPEN_ENDERCHEST));
                PiglinAi.angerNearbyPiglins(player, true);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}