package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {
        MOMOTARO_DANGO = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "momotaro_dango"),
            new Item(new Item.Settings()
                .food(new FoodComponent(6, 0.8f, false, 1.6f, null, java.util.List.of()))
                .maxCount(16))
        );

        DINO_MUSIC_DISC = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "dino_music_disc"),
            new Item(new Item.Settings().maxCount(1))
        );
    }
}
