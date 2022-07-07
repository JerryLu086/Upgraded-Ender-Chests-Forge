package net.kyrptonaught.upgradedechests.registry;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, UpgradedEnderChests.MOD_ID);

    public static final RegistryObject<SimpleParticleType> BLUE_PORTAL = PARTICLES.register("blue_portal",
            () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREEN_PORTAL = PARTICLES.register("green_portal",
            () -> new SimpleParticleType(false));
}
