private void checkNearbyJukebox(DinosaurEntity entity) {
    World world = entity.getWorld();
    BlockPos entityPos = entity.getBlockPos();
    boolean jukeboxPlaying = false;

    for (BlockPos pos : BlockPos.iterate(
        entityPos.add(-8, -3, -8),
        entityPos.add(8, 3, 8))) {
        if (world.getBlockState(pos).isOf(Blocks.JUKEBOX)) {
            if (world.getBlockEntity(pos) instanceof JukeboxBlockEntity jukebox) {
                ItemStack disc = jukebox.getStack(0);
                // Check by item identity
                if (!disc.isEmpty() && disc.getItem() == ModItems.DINO_MUSIC_DISC && entity.isTamed()) {
                    jukeboxPlaying = true;
                    break;
                }
            }
        }
    }

    if (jukeboxPlaying != entity.isDancing()) {
        entity.setDancing(jukeboxPlaying);
        if (jukeboxPlaying) {
            entity.playSound(ModSounds.DINO_DANCE, 1.0f, 1.0f);
        }
    }
}
