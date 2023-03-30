# Labelling Containers

A mod that makes easy labeling chests and containers without the use of signs.

## How to use

This mod adds a "Label printer" which is an item that can set a name and an icon to a container. To use it, simply right-click the item while holding it and it will open a menu where you can set your icon and label. Then when you're ready, shift-right-click a chest or compatible container to set it.

## For developers

If you want to add compatibility to one of your blocks simply extend the "Taggable" interface on your block's block entity and implement the methods. If you intend to use it on a block that can be double (like a double chest) you can follow my approach on how to implement it using the "TaggableChest" interface.
