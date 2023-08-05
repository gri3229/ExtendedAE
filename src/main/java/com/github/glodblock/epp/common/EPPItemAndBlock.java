package com.github.glodblock.epp.common;

import com.github.glodblock.epp.common.blocks.BlockExInterface;
import com.github.glodblock.epp.common.blocks.BlockExPatternProvider;
import com.github.glodblock.epp.common.items.*;
import com.github.glodblock.epp.common.tileentities.TileExInterface;
import com.github.glodblock.epp.common.tileentities.TileExPatternProvider;

public class EPPItemAndBlock {

    public static BlockExPatternProvider EX_PATTERN_PROVIDER;
    public static ItemPartExPatternProvider EX_PATTERN_PROVIDER_PART;
    public static ItemPatternProviderUpgrade PATTERN_PROVIDER_UPGRADE;
    public static ItemInterfaceUpgrade INTERFACE_UPGRADE;
    public static ItemIOBusUpgrade IO_BUS_UPGRADE;
    public static BlockExInterface EX_INTERFACE;
    public static ItemPartExInterface EX_INTERFACE_PART;
    public static InfinityCell INFINITY_CELL;
    public static ItemPartExExportBus EX_EXPORT_BUS;
    public static ItemPartExImportBus EX_IMPORT_BUS;
    public static ItemPartExPatternAccessTerminal EX_PATTERN_TERMINAL;

    public static void init(RegistryHandler regHandler) {
        EX_PATTERN_PROVIDER = new BlockExPatternProvider();
        EX_PATTERN_PROVIDER_PART = new ItemPartExPatternProvider();
        PATTERN_PROVIDER_UPGRADE = new ItemPatternProviderUpgrade();
        INTERFACE_UPGRADE = new ItemInterfaceUpgrade();
        EX_INTERFACE = new BlockExInterface();
        EX_INTERFACE_PART = new ItemPartExInterface();
        INFINITY_CELL = new InfinityCell();
        EX_EXPORT_BUS = new ItemPartExExportBus();
        EX_IMPORT_BUS = new ItemPartExImportBus();
        IO_BUS_UPGRADE = new ItemIOBusUpgrade();
        EX_PATTERN_TERMINAL = new ItemPartExPatternAccessTerminal();
        regHandler.block("ex_pattern_provider", EX_PATTERN_PROVIDER, TileExPatternProvider.class, TileExPatternProvider::new);
        regHandler.block("ex_interface", EX_INTERFACE, TileExInterface.class, TileExInterface::new);
        regHandler.item("ex_pattern_provider_part", EX_PATTERN_PROVIDER_PART);
        regHandler.item("ex_interface_part", EX_INTERFACE_PART);
        regHandler.item("pattern_provider_upgrade", PATTERN_PROVIDER_UPGRADE);
        regHandler.item("interface_upgrade", INTERFACE_UPGRADE);
        regHandler.item("io_bus_upgrade", IO_BUS_UPGRADE);
        regHandler.item("infinity_cell", INFINITY_CELL);
        regHandler.item("ex_export_bus_part", EX_EXPORT_BUS);
        regHandler.item("ex_import_bus_part", EX_IMPORT_BUS);
        regHandler.item("ex_pattern_access_part", EX_PATTERN_TERMINAL);
    }

}
