package net.kyrptonaught.upgradedechests.registry;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.block.item.ChestItemWithCustomRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UpgradedEnderChests.MOD_ID);

    public static final RegistryObject<Item> SPATIAL_ENDER_CHEST = ITEMS.register("spatial_ender_chest",
            () -> new ChestItemWithCustomRenderer(ModBlocks.SPATIAL_ENDER_CHEST.get(), new BlockItem.Properties()));
    public static final RegistryObject<Item> RIFT_ENDER_CHEST = ITEMS.register("rift_ender_chest",
            () -> new ChestItemWithCustomRenderer(ModBlocks.RIFT_ENDER_CHEST.get(), new BlockItem.Properties()));
}
