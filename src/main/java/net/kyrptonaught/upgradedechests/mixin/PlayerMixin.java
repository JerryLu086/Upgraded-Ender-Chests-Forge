package net.kyrptonaught.upgradedechests.mixin;

import net.kyrptonaught.upgradedechests.container.ISpatialUser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements ISpatialUser {
    @Unique
    public SimpleContainer spatialContainer = new SimpleContainer(27);

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readSpatial(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("SpatialItems", 9)) {
            this.spatialContainer.fromTag(tag.getList("SpatialItems", 10));
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void writeSpatial(CompoundTag tag, CallbackInfo ci) {
        tag.put("SpatialItems", this.spatialContainer.createTag());
    }

    @Override
    public SimpleContainer getInventory() {
        return this.spatialContainer;
    }

    @Override
    public void setSpatialInventory(SimpleContainer container) {
        this.spatialContainer = container;
    }
}
