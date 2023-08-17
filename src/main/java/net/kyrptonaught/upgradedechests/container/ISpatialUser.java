package net.kyrptonaught.upgradedechests.container;

import net.minecraft.world.SimpleContainer;

public interface ISpatialUser {
    SimpleContainer getSpatialSlots();
    void setSpatialSlots(SimpleContainer targetContainer);
}
