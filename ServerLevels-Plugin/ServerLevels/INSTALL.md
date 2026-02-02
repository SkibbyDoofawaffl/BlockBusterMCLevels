# Quick Installation Guide

## Step 1: Build the Plugin

You have two options:

### Option A: Use Maven (Recommended)
```bash
cd ServerLevels
mvn clean package
```
The compiled JAR will be at: `target/ServerLevels-1.0.0.jar`

### Option B: Use an IDE
1. Open the `ServerLevels` folder in IntelliJ IDEA or Eclipse
2. Let Maven download dependencies
3. Run Maven build: `clean package`
4. Find JAR in `target/` folder

## Step 2: Install Dependencies

Make sure your server has:
- ✅ Vault plugin
- ✅ LuckPerms plugin
- ✅ Essentials (for permission commands)
- ⚠️ Votifier (optional, for voting rewards)

## Step 3: Upload Plugin

1. Stop your server
2. Upload `ServerLevels-1.0.0.jar` to `plugins/` folder
3. Start your server
4. Config file will be generated at `plugins/ServerLevels/config.yml`

## Step 4: Configure (Optional)

Edit `plugins/ServerLevels/config.yml` to adjust:
- XP sources (playtime, voting, quests)
- XP curve difficulty
- Level rewards
- Messages

## Step 5: Setup Daily Quests (if using)

Edit `QuestListener.java` to integrate with your quest plugin before building.

See README.md for examples.

## Step 6: Restart and Test

```bash
/levels        # Check your level
/leveladmin give <player> 100   # Test giving XP (requires admin)
```

## Common Issues

**"Vault not found"**
→ Install Vault plugin

**"LuckPerms not found"**
→ Install LuckPerms plugin

**Quests not giving XP**
→ Edit QuestListener.java and rebuild

**Want to adjust difficulty**
→ Edit config.yml, change `multiplier` value

## Need Help?

See README.md for full documentation.
