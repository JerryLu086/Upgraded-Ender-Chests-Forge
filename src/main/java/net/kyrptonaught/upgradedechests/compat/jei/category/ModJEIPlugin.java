package net.kyrptonaught.upgradedechests.compat.jei.category;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.registry.ModItems;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UpgradedEnderChests.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.RIFT_ENDER_CHEST.get()), VanillaTypes.ITEM_STACK, new TranslatableComponent("jei." + UpgradedEnderChests.MOD_ID + ".rift_ender_chest.info"));
    }
}
