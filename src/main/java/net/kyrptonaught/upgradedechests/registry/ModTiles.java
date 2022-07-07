package net.kyrptonaught.upgradedechests.registry;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.block.tile.RiftEnderChestTile;
import net.kyrptonaught.upgradedechests.block.tile.SpatialEnderChestTile;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTiles {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, UpgradedEnderChests.MOD_ID);

    public static final RegistryObject<BlockEntityType<SpatialEnderChestTile>> SPATIAL_ENDER_CHEST = TILES.register("spatial_ender_chest_tile",
            () -> BlockEntityType.Builder.of(SpatialEnderChestTile::new, ModBlocks.SPATIAL_ENDER_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<RiftEnderChestTile>> RIFT_ENDER_CHEST = TILES.register("rift_ender_chest_tile",
            () -> BlockEntityType.Builder.of(RiftEnderChestTile::new, ModBlocks.RIFT_ENDER_CHEST.get()).build(null));
}
