package net.kyrptonaught.upgradedechests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class Utils {
    public static void addPortalLikeParticle(ParticleOptions particle, Level level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 3; ++i) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
            double e = (float) pos.getY() + random.nextFloat();
            double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
            double g = random.nextFloat() * (float) j;
            double h = ((double) random.nextFloat() - 0.5D) * 0.125D;
            double l = random.nextFloat() * (float) k;
            level.addParticle(particle, d, e, f, g, h, l);
        }
    }
}
