package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {
        // Momotaro Dango — the taming food
        MOMOTARO_DANGO = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "momotaro_dango"),
            new Item(new Item.Settings()
                .food(new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(0.8f)
                    .build())
                .maxCount(16))
        );

        // Special green dino music disc
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
