package net.kyrptonaught.upgradedechests.container;

import net.minecraft.world.SimpleContainer;

public interface ISpatialUser {
    SimpleContainer getInventory();
    void setSpatialInventory(SimpleContainer spatialInventory);
}
