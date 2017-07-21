# Nukkit-WorldProtect
## Describe:

Nukkit WorldProtect Plugin

--------

## How to use?

- **Install plugin**
  1. Download 'WorldProtect' plugin.
  2. Put the plugin in the plugins folder.
  3. Restart your server.

- **Add world protection**
  1.In the console input /wp add <world_name>.
  
- **Remove world protection**
  1.In the console input /wp remove <world_name>.

### Of course,you can use [/wp help] for query help

## Config:
```
    worlds: '' #Protect the world list
    createmode: 'false' #Whether to restrict the creation mode player
```

--------

## Command:
|    Command    |              Args              |                  Describe                   |                     Permission                     |
|:-------------:|:-------------------------------|:-------------------------------------------:|:-------------------------------------------------:|
|       wp      |      <help/add/remove/sc>      |          WorldProtect main command          |  world.protect.command.worldprotectcommand   |

## Command Permission:
|             PermissionName                |               Describe               |    Default owner     |
|:-----------------------------------------:|:------------------------------------:|:--------------------:|
| world.protect.command.worldprotectcommand | WorldProtect main command permission | OP(including console |

--------

## API

- Add to protect the world `WorldProtect.getPlugin().addWorld(world_name);`
- Remove the world of protection `WorldProtect.getPlugin().removeWorld(world_name);`
- Whether the world is protected `WorldProtect.getPlugin().ifProtect(world_name);`
- For the players whether the world is protected `WorldProtect.getPlugin().ifProtectToPlayer(player,world_name);`
- Read the configuration, create mode whether it is restricted `WorldProtect.getPlugin().ifCreatemode();`

--------

### Open source:

- [GitHub(WorldProtect)](https://github.com/WetABQ/Nukkit-WorldProtect)

### Author:

- [WetABQ](https://github.com/WetABQ)
