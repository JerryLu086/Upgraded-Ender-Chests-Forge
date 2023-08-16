package net.kyrptonaught.upgradedechests.container;

import net.kyrptonaught.upgradedechests.block.tile.SpatialEnderChestTile;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpatialContainer extends SimpleContainer {
    public SpatialEnderChestTile activeChest;
    private final Player playerEntity;

    public SpatialContainer(Player playerEntity) {
        this.playerEntity = playerEntity;
    }

    @Override
    public int getContainerSize() {
        return 54;
    }

    @Override
    public ItemStack getItem(int index) {
        if (index < 27)
            return playerEntity.getEnderChestInventory().getItem(index);
        return ((ISpatialUser)playerEntity).getInventory().getItem(index - 27);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if (index < 27)
            return playerEntity.getEnderChestInventory().removeItem(index, count);
        return ((ISpatialUser) playerEntity).getInventory().removeItem(index - 27, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index < 27)
            return playerEntity.getEnderChestInventory().removeItemNoUpdate(index);
        return ((ISpatialUser) playerEntity).getInventory().removeItemNoUpdate(index - 27);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index < 27)
            playerEntity.getEnderChestInventory().setItem(index, stack);
        else
            ((ISpatialUser) playerEntity).getInventory().setItem(index - 27, stack);
    }

    @Override
    public void setChanged() {
        playerEntity.getEnderChestInventory().setChanged();
        ((ISpatialUser) playerEntity).getInventory().setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return this.activeChest != null && !this.activeChest.stillValid(player) ? false : super.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return playerEntity.getEnderChestInventory().isEmpty() && ((ISpatialUser) playerEntity).getInventory().isEmpty();
    }

    @Override
    public void clearContent() {
        ((ISpatialUser) playerEntity).getInventory().clearContent();
        this.setChanged();
    }

    @Override
    public void startOpen(Player player) {
        if (this.activeChest != null) {
            this.activeChest.startOpen(player);
        }
        super.startOpen(player);
    }

    @Override
    public void stopOpen(Player player) {
        if (this.activeChest != null) {
            this.activeChest.stopOpen(player);
        }

        /*player.getEnderChestInventory().stopOpen(player);
        ((ISpatialUser) playerEntity).getInventory().stopOpen(player);*/

        super.stopOpen(player);
        this.activeChest = null;
    }
}
