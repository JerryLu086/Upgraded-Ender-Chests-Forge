package net.kyrptonaught.upgradedechests;

import net.kyrptonaught.upgradedechests.registry.ModBlocks;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModTiles;
import net.minecraftforge.common.MinecraftForge;
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

        MinecraftForge.EVENT_BUS.register(this);
    }
}
