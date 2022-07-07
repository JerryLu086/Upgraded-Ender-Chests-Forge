package net.kyrptonaught.upgradedechests.block;

import net.kyrptonaught.upgradedechests.Util;
import net.kyrptonaught.upgradedechests.block.tile.CustomChestTileBase;
import net.kyrptonaught.upgradedechests.block.tile.SpatialEnderChestTile;
import net.kyrptonaught.upgradedechests.block.container.SpatialContainer;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
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
import java.util.Random;

public class SpatialEnderChest extends CustomChestBase {
    public static TranslatableComponent SPATIAL_CHEST_TITLE = new TranslatableComponent("block.upgradedechests.spatial_ender_chest");
    public SpatialEnderChest(BlockBehaviour.Properties builder) {
        super(builder, ModTiles.SPATIAL_ENDER_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpatialEnderChestTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? createTickerHelper(type, ModTiles.SPATIAL_ENDER_CHEST.get(), CustomChestTileBase::lidAnimateTick) : null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        Util.addPortalLikeParticle(ModParticles.GREEN_PORTAL.get(), level, pos, random);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof SpatialEnderChestTile chest) {
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
