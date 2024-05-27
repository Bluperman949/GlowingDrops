package bluper.glowingdrops.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class BeamRenderLayer {
  public static final RenderLayer INSTANCE = getRenderLayer(new Identifier("glowingdrops", "textures/beam.png"));

  protected static RenderLayer getRenderLayer(Identifier texture) {
    RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
      .program(RenderPhase.BEACON_BEAM_PROGRAM)
      .texture(new RenderPhase.Texture(texture, false, false))
      .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
      .writeMaskState(RenderPhase.COLOR_MASK)
      .build(false);

    return RenderLayer.of("lootbeam", VertexFormats.POSITION_COLOR_TEXTURE,
      VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
  }
}
