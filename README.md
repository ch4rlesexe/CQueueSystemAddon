
---

# CQueueSystemAddon

**CQueueSystemAddon** is a lightweight Spigot-side bridge plugin for [`CQueueSystem`](https://github.com/ch4rlesexe/CQueueSystem), designed to let players interact with the BungeeCord-based queue system via commands, GUI items, or NPCs. It communicates through a plugin messaging channel (`CQueue`) and enables a seamless queueing experience from any connected server.

---

## 🌐 Overview

When used alongside `CQueueSystem`, this addon allows players to:

* Join or leave queues using `/queueaddon join <queue>` and `/queueaddon leave`
* Trigger queue joins/leaves via NPC interactions (e.g., Citizens plugin) or GUI server selectors (e.g., DeluxeHub or similar)
* Run queue commands without needing direct access to the BungeeCord proxy

---

## ⚙️ Requirements

* **Spigot / Paper 1.8.x** (Java 8)
* `CQueueSystem` running on the **BungeeCord proxy**
* A queue defined in the BungeeCord `config.yml`
* An outgoing plugin messaging channel named `"CQueue"` (registered by this addon)

---

## 📦 Installation

1. Download the `CQueueSystemAddon.jar` file.
2. Place it in the `/plugins` folder of your Spigot/Paper server (e.g., hub server).
3. Restart the server.
4. Ensure that the command `/queueaddon` is recognized and the log confirms registration of the plugin messaging channel.

---

## 📁 Configuration

After first run, the plugin generates a `config.yml`:

```yaml
messages:
  usage: "&eUsage: /queueaddon <join|leave> [queueName]"
  onlyPlayers: "&cOnly players can use this command."
  unknownSubcommand: "&cUnknown sub-command. Use join or leave."
  queued: "&aRequested to join the queue: &e{queue}"
  left: "&aYou have left your queue."
```

You can customize all messages with `&` color codes. These messages are shown when players interact with the queue system through this addon.

---

## 🧪 Example GUI Setup (DeluxeHub, ServerSelectorX, etc.)

```yaml
  pvp:
    material: DIAMOND_SWORD
    slot: 11
    display_name: "&cPvP Mode"
    lore:
      - "&7Fight against others!"
      - "&aClick to join!"
    actions:
      - "[CLOSE]"
      - "[MESSAGE] &7Sending you to PvP..."
      - "[COMMAND] queueaddon join pvp"
```

This sends the player to the `pvp` queue managed by the BungeeCord `CQueueSystem`.

---

## 🧵 Permissions

```yaml
permissions:
  queueaddon.use:
    description: Allows player to use /queueaddon
    default: true
```

Players need this permission to run `/queueaddon`.

---

## 🛠 Commands

| Command                    | Description             |
| -------------------------- | ----------------------- |
| `/queueaddon join <queue>` | Request to join a queue |
| `/queueaddon leave`        | Leave the current queue |

---

## 🔗 Related Projects

* **[CQueueSystem](https://github.com/ch4rlesexe/CQueueSystem)** – the BungeeCord plugin that manages all queue logic and Pterodactyl server startup/shutdown integration.

---

