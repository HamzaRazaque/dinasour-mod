package com.dinomod;

import com.dinomod.registry.ModEntities;
import com.dinomod.registry.ModItems;
import com.dinomod.registry.ModSounds;
import com.dinomod.entity.DinosaurEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.event.JukeboxSong;
import net.minecraft.util.Identifier;
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
