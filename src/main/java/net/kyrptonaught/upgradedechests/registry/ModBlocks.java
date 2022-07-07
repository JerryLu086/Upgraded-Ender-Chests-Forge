package net.kyrptonaught.upgradedechests.registry;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.block.RiftEnderChest;
import net.kyrptonaught.upgradedechests.block.SpatialEnderChest;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UpgradedEnderChests.MOD_ID);

    public static final RegistryObject<Block> SPATIAL_ENDER_CHEST = BLOCKS.register("spatial_ender_chest",
            () -> new SpatialEnderChest(BlockBehaviour.Properties.copy(Blocks.ENDER_CHEST)));
    public static final RegistryObject<Block> RIFT_ENDER_CHEST = BLOCKS.register("rift_ender_chest",
            () -> new RiftEnderChest(BlockBehaviour.Properties.copy(Blocks.ENDER_CHEST)));
}
