package net.kyrptonaught.upgradedechests.mixin;

import net.kyrptonaught.upgradedechests.block.SpatialEnderChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {
    @Inject(method = "getBlockExplosionResistance", at = @At("HEAD"), cancellable = true)
    public void craftRift(Explosion explosion, BlockGetter getter, BlockPos pos, BlockState state, FluidState fluidState, CallbackInfoReturnable<Optional<Float>> cir) {
        if (explosion.getExploder() instanceof Creeper creeper && creeper.isPowered() && state.getBlock() instanceof SpatialEnderChest)
            cir.setReturnValue(Optional.of(0f));
    }
}
