package com.dinomod.entity;

import net.minecraft.entity.ai.goal.Goal;

public class DinosaurRideableGoal extends Goal {
    private final DinosaurEntity dinosaur;

    public DinosaurRideableGoal(DinosaurEntity dinosaur) {
        this.dinosaur = dinosaur;
    }

    @Override
    public boolean canStart() {
        return dinosaur.getControllingPassenger() != null;
    }

    @Override
    public boolean shouldContinue() {
        return dinosaur.getControllingPassenger() != null;
    }
}
