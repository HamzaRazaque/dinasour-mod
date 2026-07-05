package com.dinomod.entity.client;

import com.dinomod.DinoMod;
import com.dinomod.entity.DinosaurEntity;
import com.dinomod.registry.ModItems;
import com.dinomod.registry.ModSounds;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;

public class DinosaurRenderer extends MobEntityRenderer<DinosaurEntity, DinosaurModel<DinosaurEntity>> {

    private static final Identifier TEXTURE =
        Identifier.of(DinoMod.MOD_ID, "textures/entity/dinosaur.png");

    private static final Identifier TAMED_TEXTURE =
        Identifier.of(DinoMod.MOD_ID, "textures/entity/dinosaur_tamed.png");

    public DinosaurRenderer(EntityRendererFactory.Context context) {
        super(context, new DinosaurModel<>(
            DinosaurModel.getTexturedModelData().createModel()
        ), 0.7f);
    }

    @Override
    public Identifier getTexture(DinosaurEntity entity) {
        return entity.isTamed() ? TAMED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(DinosaurEntity entity,
                       float yaw, float tickDelta,
                       net.minecraft.client.util.math.MatrixStack matrices,
                       net.minecraft.client.render.VertexConsumerProvider vertexConsumers,
                       int light) {

        // Check for nearby jukebox playing our disc
        checkNearbyJukebox(entity);

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void checkNearbyJukebox(DinosaurEntity entity) {
        World world = entity.getWorld();
        BlockPos entityPos = entity.getBlockPos();
        boolean jukeboxPlaying = false;

        // Scan a 5-block radius for jukeboxes
        for (BlockPos pos : BlockPos.iterate(
            entityPos.add(-5, -3, -5),
            entityPos.add(5, 3, 5))) {

            if (world.getBlockState(pos).isOf(Blocks.JUKEBOX)) {
                if (world.getBlockEntity(pos) instanceof JukeboxBlockEntity jukebox) {
                    ItemStack disc = jukebox.getStack(0);
                    if (disc.isOf(ModItems.DINO_MUSIC_DISC) && entity.isTamed()) {
                        jukeboxPlaying = true;
                        break;
                    }
                }
            }
        }

        if (jukeboxPlaying != entity.isDancing()) {
            entity.setDancing(jukeboxPlaying);
            if (jukeboxPlaying) {
                entity.playSound(ModSounds.DINO_DANCE, 1.0f, 1.0f);
            }
        }
    }
}
