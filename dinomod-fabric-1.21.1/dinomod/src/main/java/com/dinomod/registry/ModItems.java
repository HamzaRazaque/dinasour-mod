package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.Optional;

public class ModItems {
    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {

        // Dango — poisons the player if eaten
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

        // Green music disc
        DINO_MUSIC_DISC = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "dino_music_disc"),
            new Item(new Item.Settings()
                .maxCount(1)
                .component(
                    net.minecraft.component.DataComponentTypes.JUKEBOX_PLAYABLE,
                    new net.minecraft.component.type.JukeboxPlayableComponent(
                        net.minecraft.registry.entry.RegistryEntry.of(ModSounds.DINO_DISC_MUSIC),
                        true
                    )
                )
            )
        );
    }
}
