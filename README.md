![](https://img.shields.io/badge/modloader-fabric-brightgreen) ![](https://img.shields.io/badge/environment-client%20%2F%20server-yellow) ![](https://img.shields.io/badge/license-MIT-blue)

<a href='https://ko-fi.com/infinituum' target='_blank'><img height=35 src='https://uploads-ssl.webflow.com/5c14e387dab576fe667689cf/61e11d430afb112ea33c3aa5_Button-1-p-500.png' alt='Buy Me a Coffee at ko-fi.com' /></a>

# Labelling Containers (1.19.4)

A mod that makes easy labeling chests, shulker boxes, barrels and other containers without the use of signs.

## How to use

This mod adds a "Label printer" which is an item that can set a name and an icon to a container. To use it, simply right-click the item while holding it, and it will open a menu where you can set your icon and label. Then, when you're ready, shift-right-click a chest or compatible container to set it.

## For developers

If you want to add compatibility to one of your blocks simply extend the "Taggable" interface on your block's block entity and implement the methods. If you intend to use it on a block that can be double (like a double chest) you can follow my approach on how to implement it using the "TaggableChest" interface.
