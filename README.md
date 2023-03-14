# Nostalgia Project

The Nostalgia Project aims to create a scalable system for Minecraft servers based on the concept of "FullPvP" servers.
These servers had limited resources and were put together haphazardly. The project seeks to create a more organized
system that can handle multiple servers and players, mostly for Nostalgia reasons.

The project will involve a CLI tool that allows users to feed in a configuration file with each section/region. The
tool will automatically set up the server with the necessary worlds, and player data will be synced between servers in
real-time.

The configuration file will be in TOML format and will include settings for chat, regions, and more. Users can update
their configurations by running the CLI tool again with the updated configuration file.

The system will involve a central "Control" server that stores and manages the data. Velocity and Minecraft servers will
receive the configurations, and each region will be a world hosted on a separate server. Velocity will move players
between servers as necessary.

## Definitions

|      Name      | Description                                                                    |
|:--------------:|--------------------------------------------------------------------------------|
| Control Server | An independent server whose goal is to manage the different Minecraft servers. |
|     Shard      | A Minecraft server which hosts a small section of the "whole" server.          |
|    Roaming     | The action of moving a player from a shard to another.                         |

## Goals

- Create a scalable system for Minecraft servers that can handle multiple servers and players
- Develop a CLI tool that can set up servers automatically based on a configuration file
- Sync player data between servers in real-time
- Use a central server to manage data and distribute configurations
- Use Velocity to move players between servers as necessary

## Non-Goals

- Focus on aesthetics or design of the server worlds

## Projects

The following are projects that are to be developed to accomplish the goals mentioned previously:

- Libraries:
    - [ ] `nostalgia-rpc` - Minimal RPC framework
- Abstractions:
    - [ ] `nostalgia-economy` - In-game Economy system for the Nostalgia Project
    - [ ] `nostalgia-mines` - Automatically regenerating "Mine" system for the Nostalgia Project
- Tools:
    - [ ] `nostalgia-cli` - CLI tool for setting up servers based on configuration files
- Plugins:
    - [ ] `nostalgia-bukkit` - Bukkit plugin for the Nostalgia Project
    - [ ] `nostalgia-velocity` - Velocity plugin for the Nostalgia Project


