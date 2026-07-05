package com.dinomod.entity.client;

import com.dinomod.DinoMod;
import com.dinomod.entity.DinosaurEntity;
import com.dinomod.registry.ModItems;
import com.dinomod.registry.ModSounds;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DinosaurRenderer extends MobEntityRenderer<DinosaurEntity, DinosaurModel<DinosaurEntity>> {

    private static final Identifier TEXTURE =
        Identifier.of(DinoMod.MOD_ID, "textures/entity/dinosaur.png");
    private static final Identifier TAMED_TEXTURE =
        Identifier.of(DinoMod.MOD_ID, "textures/entity/dinosaur_tamed.png");
    private static final Identifier BABY_TEXTURE =
        Identifier.of(DinoMod.MOD_ID, "textures/entity/dinosaur_baby.png");

    public DinosaurRenderer(EntityRendererFactory.Context context) {
        super(context, new DinosaurModel<>(
            DinosaurModel.getTexturedModelData().createModel()
        ), 1.2f);
    }

    @Override
    public Identifier getTexture(DinosaurEntity entity) {
        if (entity.isBaby()) return BABY_TEXTURE;
        return entity.isTamed() ? TAMED_TEXTURE : TEXTURE;
    }

    @Override
    public void render(DinosaurEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        float scale = entity.getCurrentScale();
        matrices.push();
        matrices.scale(scale, scale, scale);

        if (!entity.isBaby()) {
            checkNearbyJukebox(entity);
        }

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }

    private void checkNearbyJukebox(DinosaurEntity entity) {
        World world = entity.getWorld();
        BlockPos entityPos = entity.getBlockPos();
        boolean jukeboxPlaying = false;

        for (BlockPos pos : BlockPos.iterate(
            entityPos.add(-8, -3, -8),
            entityPos.add(8, 3, 8))) {
            if (world.getBlockState(pos).isOf(Blocks.JUKEBOX)) {
                if (world.getBlockEntity(pos) instanceof JukeboxBlockEntity jukebox) {
                    ItemStack disc = jukebox.getStack(0);
                    if (!disc.isEmpty() && disc.getItem() == ModItems.DINO_MUSIC_DISC && entity.isTamed()) {
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
