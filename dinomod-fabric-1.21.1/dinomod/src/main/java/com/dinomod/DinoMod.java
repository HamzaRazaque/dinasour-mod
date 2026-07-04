package com.dinomod;

import com.dinomod.registry.ModEntities;
import com.dinomod.registry.ModItems;
import com.dinomod.registry.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import com.dinomod.entity.DinosaurEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DinoMod implements ModInitializer {
    public static final String MOD_ID = "dinomod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModItems.register();
        ModEntities.register();

        FabricDefaultAttributeRegistry.register(
            ModEntities.DINOSAUR,
            DinosaurEntity.createDinosaurAttributes()
        );

        LOGGER.info("DinoMod initialized! Rawr!");
    }
}
