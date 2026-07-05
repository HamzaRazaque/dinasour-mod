package com.dinomod.registry;

import com.dinomod.DinoMod;
import com.dinomod.entity.DinosaurEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<DinosaurEntity> DINOSAUR;

    public static void register() {
        DINOSAUR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DinoMod.MOD_ID, "dinosaur"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DinosaurEntity::new)
                .dimensions(EntityDimensions.fixed(3.0f, 6.0f))
                .build()
        );
    }
}
