# Changelog

## Version 1.0.0 - Initial Release

### Changes from Original DeluxeMenus System

#### XP System
- **NERFED**: Exponential XP curve instead of linear
  - Level 1: 10 XP → 50 XP (5x harder)
  - Level 24: 10,000 XP → 50,168 XP (5x harder)
  - Total XP needed: ~37,000 → ~241,760 (6.5x harder)
- **KEPT**: All XP sources remain
  - Playtime: 5 minutes = 1 XP ✓
  - Voting: 1 vote = 10 XP ✓
  - Daily Quests: 1 quest = 5 XP ✓

#### Rewards
- **CHANGED**: Max level reward
  - OLD: Knight rank at level 24
  - NEW: /fly permission at level 24
- **CHANGED**: /fly permission
  - OLD: Level 22 (6,500 XP)
  - NEW: Level 24 (50,168 XP) - Much harder to obtain
- **ADJUSTED**: All rewards redistributed across 24 levels
- **INCREASED**: Money rewards (higher amounts for harder levels)

#### Technical Improvements
- **NEW**: Standalone plugin (no longer requires DeluxeMenus)
- **NEW**: Direct data storage (YAML files per player)
- **NEW**: LuckPerms metadata integration
- **NEW**: Admin commands for management
- **NEW**: Better performance (no GUI updates needed)
- **NEW**: Configurable XP curve
- **NEW**: Automatic data saving

#### Commands
- **NEW**: `/levels` - View progress in chat
- **NEW**: `/leveladmin` - Admin management tools
- **REMOVED**: GUI menu system (can be re-added if needed)

#### Permissions
- All original permissions kept
- Same LuckPerms track system
- Same command permissions granted

### Difficulty Comparison

**Example: Reaching Level 15**

Original System:
- Required: 1,460 XP
- Playtime only: ~121.7 hours
- With voting: ~73 days (2 votes/day)

New System:
- Required: 16,302 XP (11x harder)
- Playtime only: ~1,358.5 hours
- With voting: ~815 days (2 votes/day)

**Example: Reaching Max Level (24)**

Original System:
- Required: 10,000 XP total
- Playtime only: ~833 hours
- With voting: ~500 days

New System:
- Required: 241,760 XP total (24x harder)
- Playtime only: ~20,147 hours
- With voting: ~12,088 days
- **Realistically requires heavy voting + quests + playtime**

### Notes

This new system makes leveling a **long-term achievement** rather than something that can be rushed. Players will need to:

1. Vote consistently
2. Complete daily quests
3. Spend significant playtime
4. Combine all XP sources

The max level /fly reward is now truly prestigious and will take dedicated players months to achieve through normal gameplay.
