package ru.yurannnzzz.moreredcccompat;

import commoble.morered.api.MoreRedAPI;
import commoble.morered.api.WireConnector;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.shared.ModRegistry;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import ru.yurannnzzz.moreredcccompat.cc.MoreRedBundledRedstoneProvider;
import ru.yurannnzzz.moreredcccompat.morered.ComputerChanneledPowerCapability;
import ru.yurannnzzz.moreredcccompat.morered.ComputerWireConnector;

import java.util.Map;

@Mod(MoreRedCCCompatMod.MOD_ID)
public class MoreRedCCCompatMod {
    public static final String MOD_ID = "moreredxcctcompat";

    public MoreRedCCCompatMod(IEventBus modEventBus) {
        modEventBus.addListener(this::registerConnectors);
        modEventBus.addListener(this::registerCapabilities);

        ComputerCraftAPI.registerBundledRedstoneProvider(new MoreRedBundledRedstoneProvider());
    }

    private void registerConnectors(final FMLCommonSetupEvent event) {
        ComputerWireConnector connector = new ComputerWireConnector();
        Map<Block, WireConnector> registry = MoreRedAPI.getCableConnectabilityRegistry();

        registry.put(ModRegistry.Blocks.COMPUTER_NORMAL.get(), connector);
        registry.put(ModRegistry.Blocks.COMPUTER_ADVANCED.get(), connector);
        registry.put(ModRegistry.Blocks.COMPUTER_COMMAND.get(), connector);
        registry.put(ModRegistry.Blocks.TURTLE_NORMAL.get(), connector);
        registry.put(ModRegistry.Blocks.TURTLE_ADVANCED.get(), connector);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, ModRegistry.BlockEntities.COMPUTER_NORMAL.get(), (be, side) -> new ComputerChanneledPowerCapability(side));
        event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, ModRegistry.BlockEntities.COMPUTER_ADVANCED.get(), (be, side) -> new ComputerChanneledPowerCapability(side));
        event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, ModRegistry.BlockEntities.COMPUTER_COMMAND.get(), (be, side) -> new ComputerChanneledPowerCapability(side));
        event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, ModRegistry.BlockEntities.TURTLE_NORMAL.get(), (be, side) -> new ComputerChanneledPowerCapability(side));
        event.registerBlockEntity(MoreRedAPI.CHANNELED_POWER_CAPABILITY, ModRegistry.BlockEntities.TURTLE_ADVANCED.get(), (be, side) -> new ComputerChanneledPowerCapability(side));
    }
}
