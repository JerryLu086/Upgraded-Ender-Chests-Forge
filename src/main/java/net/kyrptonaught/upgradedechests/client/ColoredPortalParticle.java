package net.kyrptonaught.upgradedechests.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColoredPortalParticle extends PortalParticle {
    protected ColoredPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
    }

    @OnlyIn(Dist.CLIENT)
    public static class GreenFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public GreenFactory(SpriteSet p_107570_) {
            this.sprite = p_107570_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PortalParticle portalParticle = new ColoredPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            portalParticle.pickSprite(this.sprite);
            portalParticle.setColor(47 / 255f, 159 / 255f, 14 / 255f);
            return portalParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class BlueFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public BlueFactory(SpriteSet p_107570_) {
            this.sprite = p_107570_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PortalParticle portalParticle = new ColoredPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            portalParticle.pickSprite(this.sprite);
            portalParticle.setColor(81 / 255f, 255 / 255f, 249 / 255f);
            return portalParticle;
        }
    }
}
