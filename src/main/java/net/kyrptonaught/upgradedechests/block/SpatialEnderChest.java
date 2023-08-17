package net.kyrptonaught.upgradedechests.block;

import net.kyrptonaught.upgradedechests.Utils;
import net.kyrptonaught.upgradedechests.block.blockEntities.CustomChestBlockEntity;
import net.kyrptonaught.upgradedechests.block.blockEntities.SpatialEnderChestBlockEntity;
import net.kyrptonaught.upgradedechests.container.SpatialContainer;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class SpatialEnderChest extends CustomChestBase {
    public static Component SPATIAL_CHEST_TITLE = Component.translatable("block.upgradedechests.spatial_ender_chest");
    public SpatialEnderChest(BlockBehaviour.Properties builder) {
        super(builder, ModBlockEntities.SPATIAL_ENDER_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpatialEnderChestBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? createTickerHelper(type, ModBlockEntities.SPATIAL_ENDER_CHEST.get(), CustomChestBlockEntity::lidAnimateTick) : null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Utils.addPortalLikeParticle(ModParticles.GREEN_PORTAL.get(), level, pos, random);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SpatialEnderChestBlockEntity chest) {
                SpatialContainer container = new SpatialContainer(player);
                container.activeChest = chest;
                player.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> ChestMenu.sixRows(i, playerInventory, container), SPATIAL_CHEST_TITLE));
                player.awardStat(Stats.CUSTOM.get(Stats.OPEN_ENDERCHEST));
                PiglinAi.angerNearbyPiglins(player, true);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter getter, BlockPos pos, Explosion explosion) {
        return explosion.getExploder() instanceof Creeper creeper && creeper.isPowered() && state.getBlock() instanceof SpatialEnderChest ? 0F : super.getExplosionResistance(state, getter, pos, explosion);
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (explosion.getExploder() instanceof Creeper creeper && creeper.isPowered())
            Block.popResource(level, pos, new ItemStack(ModItems.RIFT_ENDER_CHEST.get()));
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return (!(explosion.getExploder() instanceof Creeper creeper) || !creeper.isPowered()) && super.dropFromExplosion(explosion);
    }
}
