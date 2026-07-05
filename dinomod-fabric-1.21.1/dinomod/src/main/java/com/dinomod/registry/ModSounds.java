package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static SoundEvent DINO_AMBIENT;
    public static SoundEvent DINO_HURT;
    public static SoundEvent DINO_DEATH;
    public static SoundEvent DINO_ATTACK;
    public static SoundEvent DINO_TAME;
    public static SoundEvent DINO_DANCE;
    public static SoundEvent DINO_DISC_MUSIC;
    public static SoundEvent BACKGROUND_MUSIC;

    public static void register() {
        DINO_AMBIENT    = register("dino_ambient");
        DINO_HURT       = register("dino_hurt");
        DINO_DEATH      = register("dino_death");
        DINO_ATTACK     = register("dino_attack");
        DINO_TAME       = register("dino_tame");
        DINO_DANCE      = register("dino_dance");
        DINO_DISC_MUSIC = register("dino_disc");
        BACKGROUND_MUSIC= register("background_music");
    }

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of(DinoMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
