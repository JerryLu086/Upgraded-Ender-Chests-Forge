package net.kyrptonaught.upgradedechests;

import net.kyrptonaught.upgradedechests.registry.ModBlocks;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModTiles;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UpgradedEnderChests.MOD_ID)
public class UpgradedEnderChests {
    public static final String MOD_ID = "upgradedechests";
    public static final Logger LOGGER = LogManager.getLogger();

    public UpgradedEnderChests() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModTiles.TILES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        modEventBus.addListener(this::addToTab);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RIFT_ENDER_CHEST.get());
            event.accept(ModItems.SPATIAL_ENDER_CHEST.get());
        }
    }
}
