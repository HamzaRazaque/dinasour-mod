package com.dinomod;

import com.dinomod.entity.client.DinosaurRenderer;
import com.dinomod.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DinoModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.DINOSAUR, DinosaurRenderer::new);
    }
}
