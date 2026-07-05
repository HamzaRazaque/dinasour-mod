package com.dinomod.entity;

import com.dinomod.registry.ModItems;
import com.dinomod.registry.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DinosaurEntity extends TameableEntity {

    private static final TrackedData<Boolean> DANCING =
        DataTracker.registerData(DinosaurEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BABY =
        DataTracker.registerData(DinosaurEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> GROWTH =
        DataTracker.registerData(DinosaurEntity.class, TrackedDataHandlerRegistry.INTEGER);

    // How many dangos needed to grow from baby to adult
    private static final int MAX_GROWTH = 5;

    private int danceTickTimer = 0;

    public DinosaurEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createDinosaurAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.3);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DinosaurEntity baby = new DinosaurEntity(getType(), world);
        baby.setBaby(true);
        baby.setGrowth(0);
        return baby;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DANCING, false);
        builder.add(BABY, false);
        builder.add(GROWTH, 0);
    }

    public boolean isBaby() {
        return this.dataTracker.get(BABY);
    }

    public void setBaby(boolean baby) {
        this.dataTracker.set(BABY, baby);
    }

    public int getGrowth() {
        return this.dataTracker.get(GROWTH);
    }

    public void setGrowth(int growth) {
        this.dataTracker.set(GROWTH, growth);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true) {
            @Override
            public boolean canStart() {
                return !DinosaurEntity.this.isTamed() && !DinosaurEntity.this.isBaby() && super.canStart();
            }
        });
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        // Feed Momotaro Dango
        if (heldItem.isOf(ModItems.MOMOTARO_DANGO)) {

            // Taming wild adult dino
            if (!this.isTamed() && !this.isBaby()) {
                if (!this.getWorld().isClient()) {
                    if (!player.getAbilities().creativeMode) heldItem.decrement(1);
                    if (this.getRandom().nextInt(3) == 0) {
                        this.setOwner(player);
                        this.setSitting(false);
                        this.setHealth(this.getMaxHealth());
                        spawnTameParticles(true);
                        this.getWorld().sendEntityStatus(this, (byte) 7);
                        this.playSound(ModSounds.DINO_TAME, 1.0f, 1.0f);
                        player.sendMessage(Text.literal("§aThe dinosaur has been tamed! 🦕❤"), true);
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.SUCCESS;
            }

            // Growing a baby dino
            if (this.isBaby() && this.isTamed()) {
                if (!this.getWorld().isClient()) {
                    if (!player.getAbilities().creativeMode) heldItem.decrement(1);

                    int newGrowth = this.getGrowth() + 1;
                    this.setGrowth(newGrowth);

                    // Spawn heart particles while feeding
                    spawnTameParticles(true);

                    int remaining = MAX_GROWTH - newGrowth;
                    if (remaining > 0) {
                        player.sendMessage(
                            Text.literal("§e🍡 Your baby dino is growing! Feed it §6" + remaining + " more §edango to make it an adult!"),
                            true
                        );
                    }

                    // Fully grown!
                    if (newGrowth >= MAX_GROWTH) {
                        this.setBaby(false);
                        this.setGrowth(0);
                        this.setHealth(this.getMaxHealth());

                        // Big particle burst
                        for (int i = 0; i < 20; i++) {
                            double dx = this.getRandom().nextGaussian() * 0.1;
                            double dy = this.getRandom().nextGaussian() * 0.1;
                            double dz = this.getRandom().nextGaussian() * 0.1;
                            this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER,
                                this.getX(), this.getY() + 1, this.getZ(), dx, dy, dz);
                        }
                        player.sendMessage(Text.literal("§a🦕 Your baby dinosaur has grown into a full adult!"), true);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }

        // Owner controls tamed adult
        if (this.isTamed() && !this.isBaby() && player.getUuid().equals(this.getOwnerUuid())) {
            if (!this.getWorld().isClient()) {
                if (player.isSneaking()) {
                    this.setSitting(!this.isSitting());
                    player.sendMessage(
                        this.isSitting()
                            ? Text.literal("§eDinosaur is sitting.")
                            : Text.literal("§eDinosaur is standing."),
                        true
                    );
                } else {
                    this.setSitting(false);
                    player.startRiding(this);
                }
            }
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof PlayerEntity player) {
            if (this.isTamed() && player.getUuid().equals(this.getOwnerUuid())) {
                return player;
            }
        }
        return null;
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() != null;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            this.setYaw(player.getYaw());
            this.setPitch(player.getPitch() * 0.5f);
            this.setBodyYaw(this.getYaw());
            this.setHeadYaw(this.getYaw());
            float forward = player.forwardSpeed;
            float strafe = player.sidewaysSpeed;
            super.travel(new Vec3d(strafe, movementInput.y, forward));
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public void updatePassengerPosition(net.minecraft.entity.Entity passenger) {
        if (this.hasPassenger(passenger)) {
            passenger.setPosition(this.getX(), this.getY() + this.getHeight() * 0.9, this.getZ());
        }
    }

    // Scale baby to be smaller
    @Override
    public float getScaleFactor() {
        if (isBaby()) {
            // Scale grows from 0.3 to 1.0 based on growth progress
            float progress = (float) getGrowth() / MAX_GROWTH;
            return 0.3f + (progress * 0.7f);
        }
        return 1.0f;
    }

    public void setDancing(boolean dancing) {
        this.dataTracker.set(DANCING, dancing);
        if (dancing) danceTickTimer = 0;
    }

    public boolean isDancing() {
        return this.dataTracker.get(DANCING);
    }

    @Override
    public void tick() {
        super.tick();
        if (isDancing()) {
            danceTickTimer++;
            if (danceTickTimer > 100) {
                setDancing(false);
                danceTickTimer = 0;
            }
        }
        if (!this.getWorld().isClient() && this.age % 1200 == 0 && !this.isTamed()) {
            this.playSound(ModSounds.BACKGROUND_MUSIC, 0.5f, 1.0f);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsBaby", this.isBaby());
        nbt.putInt("Growth", this.getGrowth());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBaby(nbt.getBoolean("IsBaby"));
        this.setGrowth(nbt.getInt("Growth"));
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == (byte) 7) spawnTameParticles(true);
        else if (status == (byte) 6) spawnTameParticles(false);
    }

    private void spawnTameParticles(boolean success) {
        for (int i = 0; i < 7; i++) {
            double dx = this.getRandom().nextGaussian() * 0.02;
            double dy = this.getRandom().nextGaussian() * 0.02;
            double dz = this.getRandom().nextGaussian() * 0.02;
            this.getWorld().addParticle(
                success ? ParticleTypes.HEART : ParticleTypes.SMOKE,
                this.getX() + this.getRandom().nextFloat() * this.getWidth() * 2.0f - this.getWidth(),
                this.getY() + 0.5 + this.getRandom().nextFloat() * this.getHeight(),
                this.getZ() + this.getRandom().nextFloat() * this.getWidth() * 2.0f - this.getWidth(),
                dx, dy, dz
            );
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.MOMOTARO_DANGO);
    }

    @Override
    protected SoundEvent getAmbientSound() { return ModSounds.DINO_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.DINO_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return ModSounds.DINO_DEATH; }

    public boolean canBeLeashedBy(PlayerEntity player) { return this.isTamed(); }
}
