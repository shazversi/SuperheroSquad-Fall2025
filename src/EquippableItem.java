/**
 * EquippableItem.java
 * Subclass for items that modify player stats (damage or defense/reduction) when equipped (FR7).
 */
public class EquippableItem extends Item {

    private final int damageBoost;
    private final int damageReduction;

    public EquippableItem(String name, String description, int damageBoost, int damageReduction) {
        super(name, description, "Equippable");
        this.damageBoost = damageBoost;
        this.damageReduction = damageReduction;
    }

    // --- Specific Getters ---
    public int getDamageBoost() {
        return damageBoost;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    // --- Core Interaction Implementations ---

    @Override
    public boolean equip(Player player) {
        // FR7: Equip an item (logic handled by Player class)
         player.equipItem(getName());
         return true;
    }

    @Override
    public boolean consume(Player player) {
        System.out.println(getName() + " cannot be consumed. Try 'equip'.");
        return false;
    }

    @Override
    public boolean use(Player player) {
        // FR9 logic for Equippable is synonymous with equipping it.
        return equip(player);
    }
}