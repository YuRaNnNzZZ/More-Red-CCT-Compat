package ru.yurannnzzz.moreredcccompat.morered;

import commoble.morered.api.WireConnector;
import dan200.computercraft.shared.common.IBundledRedstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ComputerWireConnector implements WireConnector {
    @Override
    public boolean canConnectToAdjacentWire(@NotNull BlockGetter world, @NotNull BlockPos thisPos, @NotNull BlockState thisState, @NotNull BlockPos wirePos, @NotNull BlockState wireState, @NotNull Direction wireFace, @NotNull Direction directionToWire) {
        return world.getBlockState(thisPos).getBlock() instanceof IBundledRedstoneBlock;
    }
}
