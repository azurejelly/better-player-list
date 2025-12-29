# Better Player List ![Downloads](https://img.shields.io/modrinth/dt/better-player-list?logo=modrinth&color=00AF5C) ![Game version](https://img.shields.io/modrinth/game-versions/better-player-list?logo=modrinth&color=00AF5C)
Various Minecraft player list enhancements, such as numeric ping instead of connection bars.

## Features
This mod allows you to:
- Replace the default connection bars with the exact latency each player has
- Change between hold/toggle for the Player List keybind
- Disable server headers and footers
- Disable player heads
- Force player heads on offline mode servers

## Requirements
This mod depends on [Fabric API](https://modrinth.com/mod/fabricapi) and [Mod Menu](https://modrinth.com/mod/modmenu). The latter is technically optional, but it is required to access the settings screen.

## Building
To build BetterPlayerList, you'll need:
- Git
- JDK 21

Clone the repository:
```shell
$ git clone https://github.com/azurejelly/better-player-list
$ cd better-player-list/
```

And run:
```shell
$ ./gradlew build
```

Distributable JARs will be available under `build/libs/`.

## Credits
In some ways, this mod is inspired by [Perspective Enhancements](https://modrinth.com/mod/perspective-enhancements). You should check it out.
