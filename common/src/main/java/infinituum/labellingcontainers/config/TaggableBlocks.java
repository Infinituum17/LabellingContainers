package infinituum.labellingcontainers.config;

import com.google.common.collect.ImmutableSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Stream;


public class TaggableBlocks {
    /**
     * '{@code true}' if only certain blocks can be tagged, '{@code false}' otherwise<br/>
     * <b>Default</b>: {@code true}
     */
    private boolean hasTagsLimit = true;
    /**
     * If {@link TaggableBlocks#hasTagsLimit} is '{@code true}', then only this variable's ids are taggable
     */
    private Set<String> taggableIds = ImmutableSet.<String>builder()
            // Minecraft
            .add("minecraft:furnace")
            .add("minecraft:beacon")
            .add("minecraft:smoker")
            .add("minecraft:blast_furnace")
            .add("minecraft:dispenser")
            .add("minecraft:dropper")
            .add("minecraft:conduit")
            .add("minecraft:brewing_stand")
            .add("minecraft:cauldron")
            .add("minecraft:lectern")
            .add("minecraft:hopper")
            .add("minecraft:daylight_detector")
            .add("minecraft:beehive")
            // Echo Chest
            .add("echochest:echo_chest")
            // Nether Chested
            .add("netherchested:nether_chest")
            // More Chest Variants
            .add("lolmcv:crimson_chest")
            .add("lolmcv:bamboo_chest")
            .add("lolmcv:cherry_chest")
            .add("lolmcv:mangrove_chest")
            .add("lolmcv:dark_oak_chest")
            .add("lolmcv:acacia_chest")
            .add("lolmcv:jungle_chest")
            .add("lolmcv:birch_chest")
            .add("lolmcv:spruce_chest")
            .add("lolmcv:warped_chest")
            .add("lolmcv:warped_trapped_chest")
            .add("lolmcv:crimson_trapped_chest")
            .add("lolmcv:mangrove_trapped_chest")
            .add("lolmcv:dark_oak_trapped_chest")
            .add("lolmcv:acacia_trapped_chest")
            .add("lolmcv:jungle_trapped_chest")
            .add("lolmcv:birch_trapped_chest")
            .add("lolmcv:spruce_trapped_chest")
            .add("lolmcv:oak_trapped_chest")
            .add("lolmcv:oak_chest")
            // Supplementaries
            .add("supplementaries:jar")
            .add("supplementaries:sack")
            .add("supplementaries:safe")
            .add("supplementaries:pedestal")
            .add("supplementaries:planter")
            .add("supplementaries:urn")
            .add("supplementaries:lock_block")
            // Iron Chest
            .add("ironchest:iron_chest")
            .add("ironchest:gold_chest")
            .add("ironchest:diamond_chest")
            .add("ironchest:copper_chest")
            .add("ironchest:crystal_chest")
            .add("ironchest:obsidian_chest")
            .add("ironchest:dirt_chest")
            .add("ironchest:trapped_iron_chest")
            .add("ironchest:trapped_gold_chest")
            .add("ironchest:trapped_diamond_chest")
            .add("ironchest:trapped_copper_chest")
            .add("ironchest:trapped_crystal_chest")
            .add("ironchest:trapped_obsidian_chest")
            .add("ironchest:trapped_dirt_chest")
            // Colossal Chests
            .add("colossalchests:chest_wall_wood")
            .add("colossalchests:chest_wall_copper")
            .add("colossalchests:chest_wall_iron")
            .add("colossalchests:chest_wall_silver")
            .add("colossalchests:chest_wall_gold")
            .add("colossalchests:chest_wall_diamond")
            .add("colossalchests:chest_wall_obsidian")
            .add("colossalchests:colossal_chest_wood")
            .add("colossalchests:colossal_chest_copper")
            .add("colossalchests:colossal_chest_iron")
            .add("colossalchests:colossal_chest_silver")
            .add("colossalchests:colossal_chest_gold")
            .add("colossalchests:colossal_chest_diamond")
            .add("colossalchests:colossal_chest_obsidian")
            .add("colossalchests:interface_wood")
            .add("colossalchests:interface_copper")
            .add("colossalchests:interface_iron")
            .add("colossalchests:interface_silver")
            .add("colossalchests:interface_gold")
            .add("colossalchests:interface_diamond")
            .add("colossalchests:interface_obsidian")
            .add("colossalchests:uncolossal_chest")
            // Sophisticated Storage
            .add("sophisticatedstorage:limited_barrel_1")
            .add("sophisticatedstorage:limited_iron_barrel_1")
            .add("sophisticatedstorage:limited_copper_barrel_1")
            .add("sophisticatedstorage:limited_gold_barrel_1")
            .add("sophisticatedstorage:limited_diamond_barrel_1")
            .add("sophisticatedstorage:limited_netherite_barrel_1")
            .add("sophisticatedstorage:limited_barrel_2")
            .add("sophisticatedstorage:limited_copper_barrel_2")
            .add("sophisticatedstorage:limited_iron_barrel_2")
            .add("sophisticatedstorage:limited_gold_barrel_2")
            .add("sophisticatedstorage:limited_diamond_barrel_2")
            .add("sophisticatedstorage:limited_netherite_barrel_2")
            .add("sophisticatedstorage:limited_barrel_3")
            .add("sophisticatedstorage:limited_copper_barrel_3")
            .add("sophisticatedstorage:limited_iron_barrel_3")
            .add("sophisticatedstorage:limited_gold_barrel_3")
            .add("sophisticatedstorage:limited_diamond_barrel_3")
            .add("sophisticatedstorage:limited_netherite_barrel_3")
            .add("sophisticatedstorage:limited_barrel_4")
            .add("sophisticatedstorage:limited_copper_barrel_4")
            .add("sophisticatedstorage:limited_iron_barrel_4")
            .add("sophisticatedstorage:limited_gold_barrel_4")
            .add("sophisticatedstorage:limited_diamond_barrel_4")
            .add("sophisticatedstorage:limited_netherite_barrel_4")
            .add("sophisticatedstorage:shulker_box")
            .add("sophisticatedstorage:copper_shulker_box")
            .add("sophisticatedstorage:iron_shulker_box")
            .add("sophisticatedstorage:gold_shulker_box")
            .add("sophisticatedstorage:diamond_shulker_box")
            .add("sophisticatedstorage:netherite_shulker_box")
            // Farmer's Delight
            .add("farmersdelight:acacia_cabinet")
            .add("farmersdelight:birch_cabinet")
            .add("farmersdelight:crimson_cabinet")
            .add("farmersdelight:dark_oak_cabinet")
            .add("farmersdelight:jungle_cabinet")
            .add("farmersdelight:mangrove_cabinet")
            .add("farmersdelight:oak_cabinet")
            .add("farmersdelight:spruce_cabinet")
            .add("farmersdelight:warped_cabinet")
            .add("farmersdelight:basket")
            .build();
    /**
     * If {@link TaggableBlocks#hasTagsLimit} is '{@code true}', then only this variable's tags are taggable
     */
    private Set<String> taggableBlockTags = ImmutableSet.<String>builder()
            // Vanilla
            .add("minecraft:shulker_boxes")
            // Fabric
            .add("c:chests")
            .add("c:shulker_boxes")
            .add("c:barrels")
            .add("c:barrel")
            .add("c:wooden_barrels")
            // Forge
            .add("forge:chests")
            .add("forge:barrels")
            // Ironchests
            .add("ironchests:chests")
            .add("ironchests:barrels")
            // More Chests
            .add("more_chests:upgradeable_chests")
            // Compact Storage
            .add("compact_storage:compact_chest")
            .add("compact_storage:compact_barrel")
            // Supplementaries
            .add("supplementaries:presents")
            .add("supplementaries:trapped_presents")
            .build();

    public boolean isLimited() {
        return hasTagsLimit;
    }

    public boolean has(String key) {
        return taggableIds.contains(key) || taggableBlockTags.contains(key);
    }

    public boolean hasAnyTag(Stream<TagKey<Block>> tags) {
        return tags.anyMatch(blockTagKey -> taggableBlockTags.contains(blockTagKey.location().toString()));
    }

    public void addId(String id) {
        taggableIds.add(id);
    }

    public void addTag(String tag) {
        taggableBlockTags.add(tag);
    }

    public void removeId(String id) {
        taggableIds.remove(id);
    }

    public void removeTag(String tag) {
        taggableBlockTags.remove(tag);
    }

    public Set<String> getIds() {
        return this.taggableIds;
    }

    public Set<String> getTags() {
        return this.taggableBlockTags;
    }

    public void setIds(Set<String> set) {
        this.taggableIds = set;
    }

    public void setTags(Set<String> set) {
        this.taggableBlockTags = set;
    }
}
