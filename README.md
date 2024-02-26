![](https://img.shields.io/badge/Modloaders-Fabric,%20Forge,%20Neoforge-brightgreen) ![](https://img.shields.io/badge/Environment-Client%20%2F%20Server-yellow) ![](https://img.shields.io/badge/License-MIT-blue)
<br/>
[![](https://img.shields.io/curseforge/dt/844270?logo=curseforge&logoColor=f16436&label=%20Curseforge&color=2d2d2d)](https://www.curseforge.com/minecraft/mc-mods/labelling-containers) [![](https://img.shields.io/modrinth/dt/b2T42hfY?logo=modrinth&logoColor=1bd96a&label=%20Modrinth&color=2d2d2d)](https://modrinth.com/mod/labelling-containers)

<a href='https://ko-fi.com/infinituum' target='_blank'><img height=35 src='https://uploads-ssl.webflow.com/5c14e387dab576fe667689cf/61e11d430afb112ea33c3aa5_Button-1-p-500.png' alt='Buy Me a Coffee at ko-fi.com' /></a>

![](https://cdn.modrinth.com/data/b2T42hfY/images/cc27b05693aa6dae46db0000dd8506a6f09af542.png)

### A mod that makes easy labelling *chests*, *shulker boxes*, *barrels* and other containers without the use of signs.

# How to use

This mod adds a **Label printer** which is an item that can set a label and an icon to a container.

![](https://cdn.modrinth.com/data/b2T42hfY/images/b181c7a15b26931249f4b2e23eec84110416de0b.png)

## Printing labels:

To use the **Label Printer**, _right-click_ while holding it, and it'll open a menu where you can set your icon and
label. Finally, have **paper** in your inventory and _shift-right-click_ a chest or compatible container to set them.

# For developers

To add compatibility with your mod follow these steps:

1. Install curse-maven: https://www.cursemaven.com/

2. Add Labelling Containers to your project:<br/>
   > Using Architectury / Fabric / Fabric-likes:
   > ```gradle
   > dependencies {
   >     modImplementation "curse.maven:labelling-containers-844270:<version> "
   > }
   > ```
   > Using Forge / NeoForge:
   > ```gradle
   > dependencies {
   >     implementation fg.deobf("curse.maven:labelling-containers-844270:<version> ")
   > }
   > ```

3. Implement the `Taggable` interface in your BlockEntity class.<br/>
   > Check out
   some [examples](https://github.com/Infinituum17/LabellingContainers/tree/main/common/src/main/java/infinituum/labellingcontainers/mixin/minecraft).<br/>
   > <br/>***NOTE:***<br/>
   > The `Chest` mixins (`ChestBlockMixin` and `ChestBlockEntityMixin`) are different from all other mixins:
   > - `ChestBlockMixin` modifies the `onPlaced` method so that it can know when a chest is placed next to another
       chest.
   > - `ChestBlockEntityMixin` implements `TaggableChest` (not `Taggable`) because the chest could be double, so it has
       to check for the presence of another linked container.

4. Add the tooltip in your `Block`/`BlockItem`/`Item` class:
    ```java
    class MyBlock extends Block {
        // ...
        @Override
        public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
            // ...
            tooltip.add(TaggableTooltip.get());
        }
    }
    ```