# 🦕 Dino Mod — Fabric 1.21.1

A Minecraft Fabric mod featuring tameable dinosaurs, Momotaro Dango food, a green dancing music disc, and background prehistoric music!

---

## ✨ Features

### 🦕 Dinosaur Mob
- Roams the world naturally
- **Attacks players** that come within ~5 blocks (when wild)
- Has **40 HP**, 8 attack damage, large size
- Has ambient roar sounds, hurt, and death sounds
- Once tamed: follows owner, can sit/stand (right-click), won't attack

### 🍡 Momotaro Dango
- Special food item added by the mod
- **Craft:** Stick → Pink Dye → Sugar → Green Dye (vertical column) → gives 3 Dango
- Hold it and **right-click the dinosaur** to feed it
- Each feeding has a **33% tame chance** (like vanilla wolves)
- Hearts appear on success, smoke on failure

### 💚 Green Music Disc
- Special **green-colored music disc** (Dino Dance)
- Place it in a **Jukebox** near a **tamed dinosaur**
- The dino will start **dancing** with full body animations!
- Dino stops dancing when disc is removed

### 🎵 Background Music
- Wild dinosaurs occasionally trigger prehistoric ambient music

---

## 🔨 Building the Mod

### Requirements
- Java 21+
- Gradle (wrapper included)

```bash
cd dinomod
./gradlew build
```

The compiled `.jar` will be in `build/libs/dinomod-1.0.0.jar`

### Installation
1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Drop `dinomod-1.0.0.jar` into your `mods/` folder

---

## 🔊 Adding Your Own Sounds

The mod expects `.ogg` sound files at:
```
src/main/resources/assets/dinomod/sounds/
  dino_ambient.ogg
  dino_hurt.ogg
  dino_death.ogg
  dino_attack.ogg
  dino_tame.ogg
  dino_dance.ogg
  dino_disc.ogg        ← music that plays from jukebox
  background_music.ogg ← ambient prehistoric music
```

Convert MP3 → OGG free at: https://cloudconvert.com/mp3-to-ogg

Without sound files the mod works fine — just no custom audio.

---

## 🎮 Commands (Spawn for Testing)
```
/summon dinomod:dinosaur
/give @s dinomod:momotaro_dango 5
/give @s dinomod:dino_music_disc
```

---

## 🗂 File Structure
```
dinomod/
├── src/main/java/com/dinomod/
│   ├── DinoMod.java               ← Main mod initializer
│   ├── DinoModClient.java         ← Client initializer (renderer registration)
│   ├── entity/
│   │   ├── DinosaurEntity.java    ← All AI, taming, dancing logic
│   │   └── client/
│   │       ├── DinosaurModel.java ← 3D model + dance animations
│   │       └── DinosaurRenderer.java ← Texture + jukebox detection
│   └── registry/
│       ├── ModEntities.java
│       ├── ModItems.java
│       └── ModSounds.java
└── src/main/resources/
    ├── fabric.mod.json
    ├── assets/dinomod/
    │   ├── sounds.json
    │   ├── lang/en_us.json
    │   ├── textures/
    │   │   ├── entity/dinosaur.png
    │   │   ├── entity/dinosaur_tamed.png
    │   │   ├── item/momotaro_dango.png
    │   │   └── item/dino_music_disc.png
    │   └── models/item/
    │       ├── momotaro_dango.json
    │       └── dino_music_disc.json
    └── data/dinomod/
        └── recipe/momotaro_dango.json
```
