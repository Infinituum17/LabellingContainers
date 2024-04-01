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

To use the **Label Printer**, _right-click_ while holding it, and it'll open a menu
where you can set your icon and
label. Finally, have **paper** in your inventory and _shift-right-click_ a chest or compatible container to set them.

## Copying labels from existing ones:

When holding the **Label Printer**, _shift-right-click_ to switch to *Copy mode*.
Then _shift-right-click_ on a container that has a label already set to copy its icon and text.

# Commands

## `/labelposition`

Sets the current player's HUD position.
Available positions are:

- `center-right` (default)
- `center-left`
- `left`
- `top`
- `top-left`

Examples:

- Set the current HUD position to `left`:
  ```minecraft
  /labelposition left
  ```

## `/setlabel`

Requires *admin* permissions. Sets the label / icon of the selected container.

Examples:

- Setting the label of a block at <x> <y> <z> coordinates:
  ```minecraft
  /setlabel <x> <y> <z> label I'm a dirt chest!
  ```
- Setting the icon of a block at <x> <y> <z> coordinates:
  ```minecraft
  /setlabel <x> <y> <z> icon minecraft:dirt
  ```

## `/labelconfig`

Requires *admin* permissions. Adds/removes blocks that can be labeled from the config.

> NOTE: All blocks that have a Block Entity associated with them can be labeled.
> <br/>To check if a block can be added to the config and used correctly try *shift-right-clicking* on it with the
*Label Printer* item,
> and if it displays something like "You can't tag this block", that block can be added to the config

Examples:

- Add the Bell block to the config:
  ```minecraft
  /labelconfig add-item minecraft:bell
  ```
- Add the Bell block to the config (while having the block in player's hand):
  ```minecraft
  /labelconfig add-hand
  ```
- Remove the Bell block from the config:
  ```minecraft
  /labelconfig remove-item minecraft:bell
  ```
- Remove the Bell block from the config (while having the block in player's hand):
  ```minecraft
  /labelconfig remove-hand
  ```
  