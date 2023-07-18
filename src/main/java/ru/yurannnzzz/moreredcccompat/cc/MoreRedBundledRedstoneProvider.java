package ru.yurannnzzz.moreredcccompat.cc;

import commoble.morered.api.ChanneledPowerSupplier;
import commoble.morered.api.MoreRedAPI;
import dan200.computercraft.api.redstone.BundledRedstoneProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class MoreRedBundledRedstoneProvider implements BundledRedstoneProvider {
    @Override
    public int getBundledRedstoneOutput(@NotNull Level world, @NotNull BlockPos pos, @NotNull Direction side) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity != null) {
            LazyOptional<ChanneledPowerSupplier> capability = blockEntity.getCapability(MoreRedAPI.CHANNELED_POWER_CAPABILITY, side);

            if (capability.isPresent()) {
                ChanneledPowerSupplier channeledPowerSupplier = capability.orElseThrow(NullPointerException::new);

                int result = 0;

                for (int i = 0; i < 16; i++) {
                    int power = channeledPowerSupplier.getPowerOnChannel(world, pos, world.getBlockState(pos), null, i);

                    if (power > 0) {
                        result += (1 << i);
                    }
                }

                return result;
            }
        }

        return 0;
    }
}
