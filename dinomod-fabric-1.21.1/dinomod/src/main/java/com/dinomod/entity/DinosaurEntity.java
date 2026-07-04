package com.dinomod.entity;

import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
// Include your other existing imports below...

public class DinosaurEntity extends TameableEntity {

    public DinosaurEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    // This method MUST look exactly like this to satisfy the compiler
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        // Replace ModEntities.DINOSAUR with the actual name of your EntityType registration
        return ModEntities.DINOSAUR.create(world);
    }
}
