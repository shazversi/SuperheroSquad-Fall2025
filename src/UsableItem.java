/**
 * UseableItem.java
 * Subclass for items that can be used an infinite number of times (FR9).
 */
class UseableItem extends Item {

    private final String useFunction;

    public UseableItem(String name, String description, String useFunction) {
        super(name, description, "Useable");
        this.useFunction = useFunction;
    }

    // --- Specific Getters ---
    public String getUseFunction() {
        return useFunction;
    }

    // --- Core Interaction Implementations ---

    @Override
    public boolean equip(Player player) {
        System.out.println(getName() + " cannot be equipped.");
        return false;
    }

    @Override
    public boolean consume(Player player) {
        System.out.println(getName() + " cannot be consumed.");
        return false;
    }

    @Override
    public boolean use(Player player) {
        // FR9: Use an item without removal
        System.out.println(player.getName() + " uses the " + getName() + ".");

        // The actual effect (e.g., unlocking a door, decoding a puzzle)
        // will be handled by the main Game/Room logic checking for this item.
        switch (this.useFunction.toUpperCase()) {
            case "UNLOCK_P2":
                System.out.println("The " + getName() + " pulses near the sealed portal. Ready to unlock.");
                break;
            case "DECODE_PUZZLE":
                System.out.println("You use the " + getName() + ". Hidden clues are revealed.");
                break;
            case "RESTORE_ATLANTIS":
                System.out.println("The " + getName() + " restores Atlantis! Game end condition met.");
                break;
            default:
                System.out.println("The " + getName() + " doesn't seem to have a purpose here.");
                return false;
        }
        return true;
    }
}