package ru.yurannnzzz.moreredcccompat;

import commoble.morered.api.MoreRedAPI;
import commoble.morered.api.WireConnector;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.yurannnzzz.moreredcccompat.cc.MoreRedBundledRedstoneProvider;
import ru.yurannnzzz.moreredcccompat.morered.ComputerChanneledPowerCapability;
import ru.yurannnzzz.moreredcccompat.morered.ComputerWireConnector;

import java.util.Map;

@Mod(MoreRedCCCompatMod.MOD_ID)
public class MoreRedCCCompatMod {
    public static final String MOD_ID = "moreredxcctcompat";

    public MoreRedCCCompatMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerConnectors);

        MinecraftForge.EVENT_BUS.register(this);

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

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof AbstractComputerBlockEntity) {
            event.addCapability(ComputerChanneledPowerCapability.LOCATION, new ComputerChanneledPowerCapability.Provider());
        }
    }
}
