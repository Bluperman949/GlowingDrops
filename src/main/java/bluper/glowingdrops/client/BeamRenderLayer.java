package bluper.glowingdrops.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class BeamRenderLayer extends RenderLayer {
  public static final RenderLayer INSTANCE = getRenderLayer(new Identifier("glowingdrops", "textures/beam.png"));

  protected static RenderLayer getRenderLayer(Identifier texture) {
    MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
        .program(RenderPhase.BEACON_BEAM_PROGRAM)
        .texture(new RenderPhase.Texture(texture, false, false))
        .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
        .writeMaskState(RenderPhase.COLOR_MASK)
        .build(false);

    return RenderLayer.of("lootbeam", VertexFormats.POSITION_COLOR_TEXTURE,
        VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
  }

  // ignored
  private BeamRenderLayer(String name, VertexFormat vertexFormat,
                          VertexFormat.DrawMode drawMode, int expectedBufferSize,
                          boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
    super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
  }
}
