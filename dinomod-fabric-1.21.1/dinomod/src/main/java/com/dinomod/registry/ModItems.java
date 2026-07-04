package com.dinomod.registry;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
// Include your other existing imports below...

public class ModItems {

    // Example of a correct 1.21+ Food Item registration
    public static final Item DINO_MEAT = Registry.register(
        Registries.ITEM,
        Identifier.of("dinomod", "dino_meat"),
        new Item(new Item.Settings().food(
            new FoodComponent.Builder()
                .nutrition(4)
                .saturationModifier(0.3f)
                .build()
        ))
    );

    // Example of a correct 1.21+ Music Disc registration
    // Note: 1.21+ requires passing the sound event, settings, length in seconds, and comparator output
    public static final Item DINO_MUSIC_DISC = Registry.register(
        Registries.ITEM,
        Identifier.of("dinomod", "dino_music_disc"),
        new MusicDiscItem(7, ModSounds.DINO_MUSIC, new Item.Settings().maxCount(1), 120)
    );

    public static void registerModItems() {
        // Your initialization logic
    }
}
