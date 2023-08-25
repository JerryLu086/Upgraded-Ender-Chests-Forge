package net.kyrptonaught.upgradedechests.registry;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.block.blockEntities.RiftEnderChestBlockEntity;
import net.kyrptonaught.upgradedechests.block.blockEntities.SpatialEnderChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, UpgradedEnderChests.MOD_ID);

    public static final RegistryObject<BlockEntityType<SpatialEnderChestBlockEntity>> SPATIAL_ENDER_CHEST = TILES.register("spatial_ender_chest_tile",
            () -> BlockEntityType.Builder.of(SpatialEnderChestBlockEntity::new, ModBlocks.SPATIAL_ENDER_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<RiftEnderChestBlockEntity>> RIFT_ENDER_CHEST = TILES.register("rift_ender_chest_tile",
            () -> BlockEntityType.Builder.of(RiftEnderChestBlockEntity::new, ModBlocks.RIFT_ENDER_CHEST.get()).build(null));
}
