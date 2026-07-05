package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.Optional;

public class ModItems {
    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {

        MOMOTARO_DANGO = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "momotaro_dango"),
            new Item(new Item.Settings()
                .food(new FoodComponent(
                    2,
                    0.1f,
                    false,
                    1.6f,
                    Optional.empty(),
                    List.of(new FoodComponent.StatusEffectEntry(
                        new StatusEffectInstance(StatusEffects.POISON, 300, 1),
                        1.0f
                    ))
                ))
                .maxCount(16))
        );

        // Register jukebox song first
        RegistryKey<net.minecraft.sound.SoundEvent> songKey =
            RegistryKey.of(RegistryKeys.SOUND_EVENT,
                Identifier.of(DinoMod.MOD_ID, "dino_disc"));

        DINO_MUSIC_DISC = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "dino_music_disc"),
            new MusicDiscItem(
                15,
                ModSounds.DINO_DISC_MUSIC,
                new Item.Settings().maxCount(1),
                180
            )
        );
    }
}
