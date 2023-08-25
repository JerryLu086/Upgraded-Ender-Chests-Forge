package net.kyrptonaught.upgradedechests.client;

import net.kyrptonaught.upgradedechests.UpgradedEnderChests;
import net.kyrptonaught.upgradedechests.block.blockEntities.CustomChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomChestRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T> {
   public static final ResourceLocation SPATIAL_RL = new ResourceLocation(UpgradedEnderChests.MOD_ID, "blocks/spatial_ender_chest");
   public static final ResourceLocation RIFT_RL = new ResourceLocation(UpgradedEnderChests.MOD_ID, "blocks/rift_ender_chest");

   public CustomChestRenderer(BlockEntityRendererProvider.Context context) {
      super(context);
   }

   @Override
   protected Material getMaterial(T tile, ChestType type) {
      return tile instanceof CustomChestBlockEntity chest ? chest.getChestMaterial() : null;
   }

   public static Material createChestMaterial(ResourceLocation rl) {
      return new Material(Sheets.CHEST_SHEET, rl);
   }
}