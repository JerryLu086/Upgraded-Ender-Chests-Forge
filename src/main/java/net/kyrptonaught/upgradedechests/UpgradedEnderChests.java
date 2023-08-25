package net.kyrptonaught.upgradedechests;

import net.kyrptonaught.upgradedechests.container.ISpatialUser;
import net.kyrptonaught.upgradedechests.registry.ModBlocks;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(UpgradedEnderChests.MOD_ID)
public class UpgradedEnderChests {
    public static final String MOD_ID = "upgradedechests";
    public static final Logger LOGGER = LogManager.getLogger();

    public UpgradedEnderChests() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        modEventBus.addListener(this::addToTab);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addToTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RIFT_ENDER_CHEST.get());
            event.accept(ModItems.SPATIAL_ENDER_CHEST.get());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player old = event.getOriginal();
        Player _new = event.getEntity();
        if (old instanceof ISpatialUser) {
            ((ISpatialUser) _new).setSpatialSlots(((ISpatialUser) old).getSpatialSlots());
        }
    }
}
