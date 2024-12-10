package infinituum.labellingcontainers.network;

import infinituum.labellingcontainers.network.packets.c2s.LabelPrinterSavePacket;
import infinituum.labellingcontainers.network.packets.c2s.RequestTaggableBlocksConfig;
import infinituum.labellingcontainers.network.packets.s2c.*;

public final class Packets {
    public static void init() {
        registerS2C();
        registerC2S();
    }

    public static void registerS2C() {
        UpdatePreferencesConfigPacket.register();
        SyncConfigPacket.register();
        AddIdConfigPacket.register();
        AddTagConfigPacket.register();
        RemoveIdConfigPacket.register();
        RemoveTagConfigPacket.register();
    }

    public static void registerC2S() {
        LabelPrinterSavePacket.register();
        RequestTaggableBlocksConfig.register();
    }
}