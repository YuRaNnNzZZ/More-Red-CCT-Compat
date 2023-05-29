package ru.yurannnzzz.moreredcccompat.morered;

import commoble.morered.api.ChanneledPowerSupplier;
import commoble.morered.api.MoreRedAPI;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.yurannnzzz.moreredcccompat.MoreRedCCCompatMod;

import java.util.EnumMap;
import java.util.Map;

public class ComputerChanneledPowerCapability implements ChanneledPowerSupplier {
    public static final ResourceLocation LOCATION = new ResourceLocation(MoreRedCCCompatMod.MOD_ID, "bundled_redstone");

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

    public static class Provider implements ICapabilityProvider {
        private final Map<Direction, LazyOptional<ChanneledPowerSupplier>> sidedPowerSuppliers = Util.make(new EnumMap<>(Direction.class), map -> {
            for (Direction side : Direction.values()) {
                map.put(side, LazyOptional.of(() -> new ComputerChanneledPowerCapability(side)));
            }
        });

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == MoreRedAPI.CHANNELED_POWER_CAPABILITY && side != null) {
                return sidedPowerSuppliers.get(side).cast();
            }

            return LazyOptional.empty();
        }
    }
}
