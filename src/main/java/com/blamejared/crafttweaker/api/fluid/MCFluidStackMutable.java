package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

public class MCFluidStackMutable implements IFluidStack {

    private final SimpleFluidStack stack;

    public MCFluidStackMutable(SimpleFluidStack stack) {

        this.stack = stack;
    }


    @Override
    public IFluidStack setAmount(long amount) {

        return multiply(amount);
    }

    @Override
    public IFluidStack multiply(long amount) {

        getInternal().amount(amount);
        return this;
    }

    @Override
    public IFluidStack mutable() {

        return this;
    }

    @Override
    public IFluidStack asImmutable() {

        return new MCFluidStack(getInternal().copy());
    }

    @Override
    public boolean isImmutable() {

        return false;
    }

    @Override
    public Fluid getFluid() {

        return getInternal().fluid();
    }

    @Override
    public IFluidStack withTag(MapData tag) {

        if(tag != null) {
            tag = new MapData(tag.asMap());
            getInternal().tag(tag.getInternal());
        } else {
            getInternal().tag(null);
        }

        return this;
    }

    @Override
    public MapData getTag() {

        return TagToDataConverter.convertCompound(getInternal().tag());
    }

    @Override
    public IFluidStack copy() {

        return new MCFluidStackMutable(getInternal().copy());
    }

    @Override
    public SimpleFluidStack getInternal() {

        return stack;
    }

    @Override
    public SimpleFluidStack getImmutableInternal() {

        return stack.copy();
    }

    @Override
    public String getCommandString() {

        final Fluid fluid = getInternal().fluid();

        final StringBuilder stringBuilder = new StringBuilder("<fluid:");
        stringBuilder.append(Registry.FLUID.getKey(fluid));
        stringBuilder.append(">");

        if(getInternal().hasTag()) {
            MapData data = TagToDataConverter.convertCompound(getInternal().tag()).copyInternal();
            if(!data.isEmpty()) {
                stringBuilder.append(".withTag(");
                stringBuilder.append(data.asString());
                stringBuilder.append(")");
            }
        }

        if(!isEmpty()) {
            if(getInternal().amount() != 1) {
                stringBuilder.append(" * ").append(getInternal().amount());
            }
        }

        return stringBuilder.toString();
    }

    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {

        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        final SimpleFluidStack thatStack = ((MCFluidStackMutable) o).getInternal();
        final SimpleFluidStack thisStack = getInternal();

        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }

        if(thisStack.amount() != thatStack.amount()) {
            return false;
        }

        if(!Objects.equals(thisStack.fluid(), thatStack.fluid())) {
            return false;
        }

        return Objects.equals(thisStack.tag(), thatStack.tag());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getInternal().amount(), getInternal().fluid(), getInternal().tag());
    }

}