package bluper.glowingdrops.client.mixin;

import bluper.glowingdrops.client.BeamRenderLayer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

  @Inject(at = @At("HEAD"),
      method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
  public void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack,
                     VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
    if (itemEntity.isOnGround() || itemEntity.isInFluid()) {
      matrixStack.push();
      MatrixStack.Entry entry = matrixStack.peek();
      ItemStack itemStack = itemEntity.getStack();
      int rarityColor = itemStack.getRarity().getFormatting().getColorValue()
          | ((int) ((float) itemStack.getCount() / (itemStack.getItem().getMaxCount()) * 0xC0 + 0x3f) << 24);
      VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BeamRenderLayer.INSTANCE);
      float rad = 0.05f, base = 0.3f, top = base + 1f, offset = 0.03125f;
      beamQuad(vertexConsumer, entry, -rad, top, rad, base, false, offset, rarityColor);
      beamQuad(vertexConsumer, entry, rad, top, -rad, base, false, -offset, rarityColor);
      beamQuad(vertexConsumer, entry, -rad, top, rad, base, true, -offset, rarityColor);
      beamQuad(vertexConsumer, entry, rad, top, -rad, base, true, offset, rarityColor);
      matrixStack.pop();
    }
  }

  @Unique
  private static void beamQuad(VertexConsumer buf, MatrixStack.Entry entry, float xz1, float y1, float xz2, float y2,
                               boolean z, float offset, int color) {
    float x1, x2, z1, z2;
    if (z) {
      z1 = xz1;
      z2 = xz2;
      x1 = offset;
      x2 = offset;
    } else {
      x1 = xz1;
      x2 = xz2;
      z1 = offset;
      z2 = offset;
    }
    buf.vertex(entry, x1, y1, z1).color(color).texture(0, 0).next();
    buf.vertex(entry, x1, y2, z1).color(color).texture(0, 1).next();
    buf.vertex(entry, x2, y2, z2).color(color).texture(1, 1).next();
    buf.vertex(entry, x2, y1, z2).color(color).texture(1, 0).next();
  }
}
