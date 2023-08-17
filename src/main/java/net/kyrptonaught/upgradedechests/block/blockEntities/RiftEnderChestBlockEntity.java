package net.kyrptonaught.upgradedechests.block.blockEntities;

import net.kyrptonaught.upgradedechests.client.CustomChestRenderer;
import net.kyrptonaught.upgradedechests.registry.ModBlockEntities;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class RiftEnderChestBlockEntity extends CustomChestBlockEntity implements Container {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.empty();
    public UUID storedPlayer;

    public RiftEnderChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RIFT_ENDER_CHEST.get(), pos, state);
    }

    @Override
    public boolean isOwnContainer(Player player) {
        if (player.containerMenu instanceof ChestMenu menu) {
            Container container = menu.getContainer();
            return container == RiftEnderChestBlockEntity.this;
        } else {
            return false;
        }
    }

    @Override
    public Material getChestMaterial() {
        return CustomChestRenderer.createChestMaterial(CustomChestRenderer.RIFT_RL);
    }

    public void setStoredPlayer(Player player) {
        this.storedPlayer = player.getUUID();
    }

    public boolean hasStoredPlayer() {
        return storedPlayer != null;
    }

    public Component getPlayerName() {
        Player player = level.getPlayerByUUID(storedPlayer);
        return player != null ? player.getName() : null;
    }

    // ------------------------------ Container ------------------------------

    public PlayerEnderChestContainer getContainer() {
        return this.hasStoredPlayer() ? level.getPlayerByUUID(storedPlayer).getEnderChestInventory() : null;
    }

    @Override
    public int getContainerSize() {
        return this.getContainer().getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return this.getContainer().isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return this.getContainer().getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return this.getContainer().removeItem(index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return this.getContainer().removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.getContainer().setItem(index, stack);
    }

    @Override
    public void setChanged() {
        this.getContainer().setChanged();
        super.setChanged();
    };

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
        Container.super.startOpen(player);
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
        Container.super.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        this.getContainer().clearContent();
    }

    // -----------------------------------------------------------------------

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("storedplayer"))
            storedPlayer = tag.getUUID("storedplayer");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (storedPlayer != null)
            tag.putUUID("storedplayer", storedPlayer);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        super.saveAdditional(tag);
        if (storedPlayer != null)
            tag.putUUID("storedplayer", storedPlayer);
        return tag;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (itemHandler != null) {
            itemHandler.invalidate();
            itemHandler = null;
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            itemHandler.invalidate();
            itemHandler = null;
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(() -> new InvWrapper(getContainer()));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (this.itemHandler == null)
                this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new InvWrapper(getContainer()));
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }
}
