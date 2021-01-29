# BungeeCommandBridge

Simple plugin that adds a way to execute a BungeeCord command from Spigot as a player, mostly for use by plugins (ex. WorldGuard ExtraFlags).

- Command:
  - /bridge [bungeeCmd] - execute bungeeCmd as the player on the BungeeCord proxy.
- Perms:
  - cccmdbridge.cmd.bridge - run /bridge
- Config:
  - require-perm - `false` by default. Idk why you'd need it. Idk why I added perms in the first place. Enjoy.
