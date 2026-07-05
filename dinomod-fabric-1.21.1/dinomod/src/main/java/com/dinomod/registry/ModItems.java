package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.JukeboxPlayableComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.Optional;

public class ModItems {
    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {

        // Dango — poisons player if eaten
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

        // Green music disc — uses JukeboxPlayableComponent so it works in jukeboxes
        RegistryKey<net.minecraft.sound.SoundEvent> soundKey =
            RegistryKey.of(RegistryKeys.SOUND_EVENT,
                Identifier.of(DinoMod.MOD_ID, "dino_disc"));

        DINO_MUSIC_DISC = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "dino_music_disc"),
            new Item(new Item.Settings()
                .maxCount(1)
                .component(
                    DataComponentTypes.JUKEBOX_PLAYABLE,
                    new JukeboxPlayableComponent(
                        Registries.SOUND_EVENT.getEntry(
                            Identifier.of(DinoMod.MOD_ID, "dino_disc")
                        ).orElseThrow(),
                        true
                    )
                )
            )
        );
    }
}
