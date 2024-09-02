package ru.yurannnzzz.moreredcccompat.morered;

import commoble.morered.api.ChanneledPowerSupplier;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComputerChanneledPowerCapability implements ChanneledPowerSupplier {
    private final Direction side;

    public ComputerChanneledPowerCapability(Direction side) {
        this.side = side;
    }

    @Override
    public int getPowerOnChannel(@NotNull Level world, @NotNull BlockPos wirePos, @NotNull BlockState wireState, @Nullable Direction wireFace, int channel) {
        if (wireFace == null) return 0;
        if (side == null) return 0;

        BlockPos targetPos = wirePos.relative(side.getOpposite());
        int output = ComputerCraftAPI.getBundledRedstoneOutput(world, targetPos, side);
        if (output < 0) return 0;

        int expected = 1 << channel;
        return ((output & expected) == expected) ? 31 : 0;
    }
}
