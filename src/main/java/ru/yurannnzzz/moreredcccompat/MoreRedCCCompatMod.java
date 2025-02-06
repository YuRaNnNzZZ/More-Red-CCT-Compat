package ru.yurannnzzz.moreredcccompat;

import commoble.morered.api.MoreRedAPI;
import commoble.morered.api.WireConnector;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.shared.common.IBundledRedstoneBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import ru.yurannnzzz.moreredcccompat.cc.MoreRedBundledRedstoneProvider;
import ru.yurannnzzz.moreredcccompat.morered.ComputerChanneledPowerCapability;
import ru.yurannnzzz.moreredcccompat.morered.ComputerWireConnector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod(MoreRedCCCompatMod.MOD_ID)
public class MoreRedCCCompatMod {
    public static final String MOD_ID = "moreredxcctcompat";

    public MoreRedCCCompatMod(IEventBus modEventBus) {
        modEventBus.addListener(this::registerConnectors);
        modEventBus.addListener(this::registerCapabilities);

        ComputerCraftAPI.registerBundledRedstoneProvider(new MoreRedBundledRedstoneProvider());
    }

    private static final Map<Block, BlockEntityType<?>> BLOCKS = new HashMap<>();

    private void registerConnectors(final FMLCommonSetupEvent event) {
        ComputerWireConnector connector = new ComputerWireConnector();
        Map<Block, WireConnector> registry = MoreRedAPI.getCableConnectabilityRegistry();

        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof IBundledRedstoneBlock) {
                Optional<BlockEntityType<?>> filter = BuiltInRegistries.BLOCK_ENTITY_TYPE.stream().filter(it -> it.isValid(block.defaultBlockState())).findFirst();

                filter.ifPresent(blockEntityType -> BLOCKS.put(block, blockEntityType));
            }
        }

        for (Map.Entry<Block, BlockEntityType<?>> entry : BLOCKS.entrySet()) {
            registry.put(entry.getKey(), connector);
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (Map.Entry<Block, BlockEntityType<?>> entry : BLOCKS.entrySet()) {
            event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, entry.getValue(), (be, side) -> new ComputerChanneledPowerCapability(side));
        }
    }
}
