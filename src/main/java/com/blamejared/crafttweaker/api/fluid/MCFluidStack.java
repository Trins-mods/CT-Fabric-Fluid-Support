package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

public class MCFluidStack implements IFluidStack {

    public static Supplier<MCFluidStack> EMPTY = () -> new MCFluidStack(SimpleFluidStack.EMPTY);
    private final SimpleFluidStack stack;

    public MCFluidStack(SimpleFluidStack fluidStack) {

        this.stack = fluidStack;
    }

    @Override
    public String getCommandString() {

        final Fluid fluid = getInternal().fluid();
        final StringBuilder builder = new StringBuilder().append("<fluid:")
                .append(Registry.FLUID.getKey(fluid))
                .append(">");

        if(getInternal().hasTag()) {
            MapData data = TagToDataConverter.convertCompound(getInternal().tag()).copyInternal();
            if(!data.isEmpty()) {
                builder.append(".withTag(");
                builder.append(data.asString());
                builder.append(")");
            }
        }

        if(!isEmpty()) {
            if(getInternal().amount() != 1) {
                builder.append(" * ").append(getInternal().amount());
            }
        }
        return builder.toString();
    }
    @Override
    public long getAmount() {
        return stack.amount();
    }

    @Override
    public IFluidStack setAmount(long amount) {

        final SimpleFluidStack copy = getInternal().copy();
        copy.amount(amount);
        return new MCFluidStack(copy);
    }

    @Override
    public IFluidStack multiply(long amount) {

        return setAmount(amount);
    }


    @Override
    public IFluidStack mutable() {

        return new MCFluidStackMutable(getInternal());
    }

    @Override
    public IFluidStack asImmutable() {

        return this;
    }

    @Override
    public boolean isImmutable() {

        return true;
    }

    @Override
    public IFluidStack copy() {
        //We have to copy, in case someone calls ".copy().mutable"
        return new MCFluidStack(getInternal().copy());
    }

    @Override
    public Fluid getFluid() {

        return getInternal().fluid();
    }


    @Override
    public IFluidStack withTag(@ZenCodeType.Nullable MapData tag) {

        final SimpleFluidStack copy = getInternal().copy();
        if(tag != null) {
            tag = new MapData(tag.asMap());
            copy.tag(tag.getInternal());
        } else {
            copy.tag(null);
        }

        return new MCFluidStack(copy);
    }

    @Override
    public MapData getTag() {

        return TagToDataConverter.convertCompound(getInternal().tag());
    }

    @Override
    public SimpleFluidStack getInternal() {

        return stack;
    }

    @Override
    public SimpleFluidStack getImmutableInternal() {

        return stack;
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

        final SimpleFluidStack thatStack = ((MCFluidStack) o).getInternal();
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
