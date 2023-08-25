package net.kyrptonaught.upgradedechests;

import net.kyrptonaught.upgradedechests.client.ColoredPortalParticle;
import net.kyrptonaught.upgradedechests.client.CustomChestRenderer;
import net.kyrptonaught.upgradedechests.registry.ModParticles;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UpgradedEnderChests.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetups {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.GREEN_PORTAL.get(), ColoredPortalParticle.GreenProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.BLUE_PORTAL.get(), ColoredPortalParticle.BlueProvider::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(ModBlockEntities.SPATIAL_ENDER_CHEST.get(), CustomChestRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.RIFT_ENDER_CHEST.get(), CustomChestRenderer::new);
    }
}
