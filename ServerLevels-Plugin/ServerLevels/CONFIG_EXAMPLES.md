# Configuration Examples

## Difficulty Presets

### Easy Mode (Casual Server)
```yaml
level-settings:
  max-level: 24
  base-xp: 25           # Half the default
  multiplier: 1.2       # Slower growth

xp-sources:
  playtime:
    minutes: 3          # More frequent
    xp-reward: 2        # Double reward
  voting:
    xp-reward: 20       # Double reward
  daily-quests:
    xp-reward: 10       # Double reward
```
**Time to max level**: ~2,000 hours playtime

---

### Medium Mode (Balanced)
```yaml
level-settings:
  max-level: 24
  base-xp: 35
  multiplier: 1.25

xp-sources:
  playtime:
    minutes: 5
    xp-reward: 1
  voting:
    xp-reward: 15
  daily-quests:
    xp-reward: 7
```
**Time to max level**: ~6,000 hours playtime

---

### Hard Mode (Default)
```yaml
level-settings:
  max-level: 24
  base-xp: 50
  multiplier: 1.35

xp-sources:
  playtime:
    minutes: 5
    xp-reward: 1
  voting:
    xp-reward: 10
  daily-quests:
    xp-reward: 5
```
**Time to max level**: ~20,000 hours playtime

---

### Extreme Mode (Hardcore)
```yaml
level-settings:
  max-level: 24
  base-xp: 100
  multiplier: 1.5

xp-sources:
  playtime:
    minutes: 10
    xp-reward: 1
  voting:
    xp-reward: 5
  daily-quests:
    xp-reward: 3
```
**Time to max level**: ~100,000+ hours playtime

---

## Custom Reward Examples

### Custom Level 10 Reward (Kit Access)
```yaml
level-rewards:
  10:
    commands:
      - "lp user %player% permission set essentials.kit.starter true"
      - "lp user %player% permission set essentials.kit.tools true"
      - "eco give %player% 50000"
    broadcast: "&8[&a!&8] &a%player% &7unlocked starter kits!"
```

### Custom Level 15 Reward (Custom Item)
```yaml
level-rewards:
  15:
    commands:
      - "give %player% diamond_sword{Enchantments:[{id:sharpness,lvl:5}]} 1"
      - "lp user %player% permission set essentials.feed true"
      - "eco give %player% 100000"
    broadcast: "&8[&6!&8] &6%player% &7received a legendary sword!"
```

### Custom Level 20 Reward (Multiple Perks)
```yaml
level-rewards:
  20:
    commands:
      - "lp user %player% permission set essentials.speed true"
      - "lp user %player% permission set essentials.speed.fly true"
      - "lp user %player% permission set essentials.heal true"
      - "lp user %player% permission set essentials.feed true"
      - "eco give %player% 200000"
      - "crates key give %player% legendary_key 3"
    broadcast: "&8[&b!&8] &b%player% &7unlocked ultimate perks!"
```

---

## XP Source Variations

### Vote-Heavy Server
```yaml
xp-sources:
  playtime:
    enabled: true
    minutes: 10
    xp-reward: 1
  voting:
    enabled: true
    xp-reward: 50        # Major reward for voting
  daily-quests:
    enabled: true
    xp-reward: 10
```

### Quest-Heavy Server
```yaml
xp-sources:
  playtime:
    enabled: true
    minutes: 5
    xp-reward: 1
  voting:
    enabled: true
    xp-reward: 10
  daily-quests:
    enabled: true
    xp-reward: 25        # Major reward for quests
```

### Playtime-Focused Server
```yaml
xp-sources:
  playtime:
    enabled: true
    minutes: 3           # More frequent
    xp-reward: 3         # Higher reward
  voting:
    enabled: true
    xp-reward: 5         # Reduced
  daily-quests:
    enabled: true
    xp-reward: 5         # Reduced
```

---

## Message Customization

### Custom Messages
```yaml
messages:
  prefix: "&8[&c&l★ LEVELS ★&8]"
  level-up: "&6&l⚡ LEVEL UP! &7You reached level &6&l%level%&7!"
  xp-gain: "&a+ %xp%XP &8(&7%source%&8)"
  not-enough-xp: "&c❌ Need &f%xp%XP &cfor level &f%level%&c!"
  max-level: "&6&l★ You're at MAX LEVEL! ★"
```

---

## Tips

1. **Balance Your XP Sources**: If voting gives too much, players won't play. If playtime gives too much, voting won't matter.

2. **Test Your Curve**: Use `/leveladmin give <player> <amount>` to test progression

3. **Adjust Gradually**: Don't make massive changes - tweak multiplier by 0.05-0.1 at a time

4. **Consider Your Player Base**: Casual servers need easier progression, competitive servers can be harder

5. **Rewards Matter**: Make sure higher levels have exciting rewards to maintain motivation
