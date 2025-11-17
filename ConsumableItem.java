/**
 * ConsumableItem.java
 * Subclass for single-use items that are deleted upon successful use (FR8).
 */
public class ConsumableItem extends Item {

    private final String effectType;
    private final int effectValue;

    public ConsumableItem(String name, String description, String effectType, int effectValue) {
        super(name, description, "Consumable");
        this.effectType = effectType;
        this.effectValue = effectValue;
    }

    // --- Core Interaction Implementations ---

    @Override
    public boolean equip(Player player) {
        System.out.println(getName() + " cannot be equipped.");
        return false;
    }

    @Override
    public boolean consume(Player player) {
        // FR8: Consume an item (apply effect and delete from inventory)
        System.out.println(player.getName() + " consumes the " + getName() + ".");

        boolean success = false;

        switch (this.effectType.toUpperCase()) {
            case "HEAL":
                player.heal(effectValue);
                System.out.println("Restored " + effectValue + " HP.");
                success = true;
                break;
            case "INVISIBILITY":
                player.applyInvisibility(effectValue);
                System.out.println("Gained invisibility for " + effectValue + " turns.");
                success = true;
                break;
            case "BYPASS_PUZZLE":
                player.markPuzzleBypassAvailable();
                System.out.println("Puzzle bypass ability is now active.");
                success = true;
                break;
            default:
                System.out.println("Error: Unknown effect type for " + getName() + ".");
                return false;
        }

        if (success) {
            // Fulfills FR8: Item deleted after successful consumption.
            player.getInventory().removeItem(this);
        }
        return success;
    }

    @Override
    public boolean use(Player player) {
        // The 'use' command for consumables is synonymous with 'consume'.
        return consume(player);
    }
}