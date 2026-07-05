package com.dinomod.registry;

import com.dinomod.DinoMod;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.List;

public class ModItems {
    public static Item MOMOTARO_DANGO;
    public static Item DINO_MUSIC_DISC;

    public static void register() {

        // Dango — edible but gives poison to player
        MOMOTARO_DANGO = Registry.register(
            Registries.ITEM,
            Identifier.of(DinoMod.MOD_ID, "momotaro_dango"),
            new Item(new Item.Settings()
                .food(new FoodComponent(
                    2,      // nutrition (low — it's dino food!)
                    0.1f,   // saturation
                    false,  // meat
                    1.6f,   // eat seconds
                    new StatusEffectInstance(StatusEffects.POISON, 300, 1), // 15s poison lvl 2
                    1.0f,   // 100% chance of poison
                    List.of()
                ))
                .maxCount(16))
        );

        // Green music disc — properly registered as MusicDiscItem
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
