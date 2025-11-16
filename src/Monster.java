public class Monster {

    // Basic monster stats
    private String name;
    private String description;
    private String droppedItem;
    private int roomLocation;
    private int attack;
    private int maxHP;
    private int currentHP;

    // Special combat traits
    private boolean invisible = false;    // for monsters like Transparent Shrimp
    private int turnCounter = 0;          // track ability cycle
    private int invisibilityStartTurn = 2; // For the Shrimp, default disabled
    private int invisibilityDuration = 1;
    private int invisibilityCooldown = 2;

    // Whether monster is removed after defeat
    private boolean defeated = false;

    public Monster(String name, String description, String droppedItem,
                   int roomLocation, int attack, int hp) {
        this.name = name;
        this.description = description;
        this.droppedItem = droppedItem;
        this.roomLocation = roomLocation;
        this.attack = attack;
        this.maxHP = hp;
        this.currentHP = hp;
    }

    // Enable special invisibility trait (for Transparent Shrimp)
    public void enableInvisibilityCycle(int startTurn, int duration, int cooldown) {
        this.invisibilityStartTurn = startTurn;
        this.invisibilityDuration = duration;
        this.invisibilityCooldown = cooldown;
    }

    /** Called each turn to update special abilities */
    public void updateTurn() {
        turnCounter++;

        // Only monsters with this trait should use it
        if (invisibilityStartTurn < 0) return;

        // Activate invisibility every cycle
        if (turnCounter >= invisibilityStartTurn &&
                (turnCounter - invisibilityStartTurn) % invisibilityCooldown == 0) {
            invisible = true;
        }

        // End invisibility after duration
        if (invisible && (turnCounter % invisibilityCooldown) == invisibilityDuration) {
            invisible = false;
        }
    }

    /** Player attack → returns damage dealt */
    public int takePlayerAttack(int playerDamage) {
        if (invisible) return 0; // cannot be hit when invisible

        int dmg = Math.max(1, playerDamage);
        currentHP = Math.max(0, currentHP - dmg);

        if (currentHP == 0) defeated = true;
        return dmg;
    }

    /** Monster counterattack → returns damage dealt */
    public int attackPlayer(boolean playerDefending, int defendReduction) {
        if (defeated) return 0;

        int damage = attack;

        if (playerDefending) {
            damage = Math.max(0, damage - defendReduction);
        }

        return damage;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public String getName() { return name; }
    public int getAttack() { return attack; }
    public int getHP() { return currentHP; }
    public int getRoom() { return roomLocation; }
    public String getDroppedItem() { return droppedItem; }
    public String getDescription() { return description; }

    // Serialize to single line for saving
    @Override
    public String toString() {
        return name + "|" + description + "|" + droppedItem + "|" +
                roomLocation + "|" + attack + "|" + maxHP + "|" +
                currentHP + "|" + turnCounter + "|" + invisible + "|" +
                invisibilityStartTurn + "|" + invisibilityDuration + "|" + invisibilityCooldown;
    }

    // Deserialize from saved line
    public static Monster fromString(String s) {
        String[] p = s.split("\\|");

        Monster m = new Monster(
                p[0],  // name
                p[1],  // description
                p[2],  // dropped item
                Integer.parseInt(p[3]), // room
                Integer.parseInt(p[4]), // attack
                Integer.parseInt(p[5])  // maxHP
        );

        m.currentHP = Integer.parseInt(p[6]);
        m.turnCounter = Integer.parseInt(p[7]);
        m.invisible = Boolean.parseBoolean(p[8]);
        m.invisibilityStartTurn = Integer.parseInt(p[9]);
        m.invisibilityDuration = Integer.parseInt(p[10]);
        m.invisibilityCooldown = Integer.parseInt(p[11]);

        return m;
    }
}