# MobMoneyLite

Lightweight Paper plugin that rewards players with Vault economy money when they kill mobs.

## Features

- Economy rewards on mob kills
- Optional hostile-only rewards
- Per-mob reward override
- Optional random reward range
- Configurable message format and decimal formatting

## Requirements

- Java 21
- Paper 1.21+
- Vault
- A Vault-compatible economy plugin (for example EssentialsX Economy)

## Build

```bash
mvn clean package
```

Output jar: `target/mobmoneylite-1.0-SNAPSHOT.jar`

## Install

1. Build the plugin jar.
2. Put the jar into your server `plugins/` folder.
3. Make sure Vault and an economy plugin are installed.
4. Start/restart the server.
5. Edit `plugins/MobMoneyLite/config.yml` as needed.

## Configuration (`config.yml`)

- `enabled`: enable/disable rewards
- `hostile-only`: reward only hostile mobs when `true`
- `default-money`: fallback reward amount
- `use-random`: enable random reward mode
- `random-range.min` / `random-range.max`: random reward bounds
- `message.enabled`: enable reward messages
- `message.format`: message template (`%mob%`, `%money%`)
- `mobs`: per-mob fixed reward table (for example `ZOMBIE: 8.0`)
- `decimal-format`: Java `DecimalFormat` pattern for money display

## Notes

- Plugin disables itself if Vault or an economy provider is not available.
- Mob names in messages are Bukkit enum names (for example `ZOMBIE`).
