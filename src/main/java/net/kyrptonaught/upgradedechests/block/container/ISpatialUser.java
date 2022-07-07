package net.kyrptonaught.upgradedechests.block.container;

import net.minecraft.world.SimpleContainer;

public interface ISpatialUser {
    SimpleContainer getInventory();
    void setSpatialInventory(SimpleContainer spatialInventory);
}
