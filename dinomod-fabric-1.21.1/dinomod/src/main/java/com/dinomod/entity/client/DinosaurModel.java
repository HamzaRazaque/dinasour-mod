package com.dinomod.entity.client;

import com.dinomod.entity.DinosaurEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class DinosaurModel<T extends DinosaurEntity> extends EntityModel<T> {

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart tail;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightBackLeg;

    private boolean isDancing = false;

    public DinosaurModel(ModelPart root) {
        this.body          = root.getChild("body");
        this.head          = root.getChild("head");
        this.neck          = root.getChild("neck");
        this.tail          = root.getChild("tail");
        this.leftFrontLeg  = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftBackLeg   = root.getChild("left_back_leg");
        this.rightBackLeg  = root.getChild("right_back_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        // Body — big central block
        root.addChild("body",
            ModelPartBuilder.create().uv(0, 0).cuboid(-6, -4, -8, 12, 10, 16),
            ModelTransform.pivot(0, 8, 0));

        // Neck
        root.addChild("neck",
            ModelPartBuilder.create().uv(0, 26).cuboid(-3, -8, -4, 6, 8, 6),
            ModelTransform.pivot(0, 4, -8));

        // Head
        root.addChild("head",
            ModelPartBuilder.create().uv(28, 26).cuboid(-4, -6, -8, 8, 6, 8),
            ModelTransform.pivot(0, -4, -10));

        // Tail
        root.addChild("tail",
            ModelPartBuilder.create().uv(0, 40).cuboid(-3, -2, 0, 6, 6, 14),
            ModelTransform.pivot(0, 6, 8));

        // Front legs
        root.addChild("left_front_leg",
            ModelPartBuilder.create().uv(44, 0).cuboid(-2, 0, -2, 4, 10, 4),
            ModelTransform.pivot(6, 12, -4));
        root.addChild("right_front_leg",
            ModelPartBuilder.create().uv(44, 0).cuboid(-2, 0, -2, 4, 10, 4),
            ModelTransform.pivot(-6, 12, -4));

        // Back legs (larger)
        root.addChild("left_back_leg",
            ModelPartBuilder.create().uv(56, 0).cuboid(-3, 0, -3, 6, 12, 6),
            ModelTransform.pivot(6, 12, 6));
        root.addChild("right_back_leg",
            ModelPartBuilder.create().uv(56, 0).cuboid(-3, 0, -3, 6, 12, 6),
            ModelTransform.pivot(-6, 12, 6));

        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        isDancing = entity.isDancing();

        float speed = isDancing ? animationProgress * 0.3f : limbAngle;
        float swing = isDancing ? 0.6f : limbDistance * 0.5f;

        // Head tracking
        this.head.yaw   = headYaw   * ((float) Math.PI / 180f);
        this.head.pitch = headPitch * ((float) Math.PI / 180f);
        this.neck.pitch = headPitch * ((float) Math.PI / 360f);

        // Leg animations
        this.leftFrontLeg.pitch  =  (float) Math.cos(speed * 0.6662f + Math.PI) * 1.4f * swing;
        this.rightFrontLeg.pitch =  (float) Math.cos(speed * 0.6662f) * 1.4f * swing;
        this.leftBackLeg.pitch   =  (float) Math.cos(speed * 0.6662f) * 1.4f * swing;
        this.rightBackLeg.pitch  =  (float) Math.cos(speed * 0.6662f + Math.PI) * 1.4f * swing;

        // Tail sway
        this.tail.yaw = (float) Math.sin(speed * 0.6662f) * 0.2f * swing;

        if (isDancing) {
            // Body bob while dancing
            this.body.pitch  = (float) Math.sin(animationProgress * 0.2f) * 0.15f;
            this.body.roll   = (float) Math.sin(animationProgress * 0.3f) * 0.1f;
            this.head.roll   = (float) Math.sin(animationProgress * 0.25f) * 0.2f;
            this.tail.yaw    = (float) Math.sin(animationProgress * 0.4f) * 0.5f;
        } else {
            this.body.pitch = 0;
            this.body.roll  = 0;
            this.head.roll  = 0;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer,
                       int light, int overlay, int color) {
        body.render(matrices, vertexConsumer, light, overlay, color);
        neck.render(matrices, vertexConsumer, light, overlay, color);
        head.render(matrices, vertexConsumer, light, overlay, color);
        tail.render(matrices, vertexConsumer, light, overlay, color);
        leftFrontLeg.render(matrices, vertexConsumer, light, overlay, color);
        rightFrontLeg.render(matrices, vertexConsumer, light, overlay, color);
        leftBackLeg.render(matrices, vertexConsumer, light, overlay, color);
        rightBackLeg.render(matrices, vertexConsumer, light, overlay, color);
    }
}
