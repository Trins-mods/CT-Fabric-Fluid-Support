package com.blamejared.crafttweaker.api.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class SimpleFluidStack {
    static SimpleFluidStack EMPTY = new SimpleFluidStack(Fluids.EMPTY, 0);
    private final Fluid fluid;
    private long amount;
    private CompoundTag tag;

    public SimpleFluidStack(Fluid fluid, long amount) {

        this(fluid, amount, null);
    }

    public SimpleFluidStack(Fluid fluid, long amount, @Nullable CompoundTag tag) {

        this.fluid = fluid;
        this.amount = amount;
        this.tag = tag;
    }

    public SimpleFluidStack copy() {

        return new SimpleFluidStack(fluid(), amount(), tag());
    }

    public boolean isEmpty() {

        return fluid == Fluids.EMPTY || amount() <= 0;
    }

    public Fluid fluid() {

        return fluid;
    }

    public long amount() {

        return amount;
    }

    public CompoundTag tag() {

        return tag;
    }

    public boolean hasTag(){
        return tag != null;
    }

    public void amount(long amount) {

        this.amount = amount;
    }

    public void tag(CompoundTag tag) {

        this.tag = tag;
    }

    public boolean containsOther(SimpleFluidStack other){
        return fluid == other.fluid && tag == other.tag && amount >= other.amount;
    }
}
