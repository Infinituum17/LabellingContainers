package infinituum.labellingcontainers.registration.component_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

// TODO: See if ItemStack.CODEC can become Codec.STRING (just store the item name)
public record LabelProperties(String text, ItemStack displayItem, int mode) {
    public static final Codec<LabelProperties> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.STRING
                            .fieldOf("text")
                            .forGetter(LabelProperties::text),
                    ItemStack.CODEC
                            .fieldOf("displayItem")
                            .forGetter(LabelProperties::displayItem),
                    Codec.INT
                            .fieldOf("mode")
                            .forGetter(LabelProperties::mode)
            ).apply(builder, LabelProperties::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, LabelProperties> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            LabelProperties::text,
            ItemStack.STREAM_CODEC,
            LabelProperties::displayItem,
            ByteBufCodecs.INT,
            LabelProperties::mode,
            LabelProperties::new
    );
}
