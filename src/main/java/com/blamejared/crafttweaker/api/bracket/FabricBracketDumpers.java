package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.fluid.SimpleFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.FabricBracketDumpers")
@Document("forge/api/ForgeBracketDumpers")
public class FabricBracketDumpers {
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("fluid")
    public static Collection<String> getFluidStackDump() {

        return Registry.FLUID.stream()
                .map(fluid -> new MCFluidStack(new SimpleFluidStack(fluid, 1)).getCommandString())
                .collect(Collectors.toList());
    }
}
