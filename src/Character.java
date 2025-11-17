/**
 * Character.java
 * Abstract base class for all characters in the game (Player and Monster).
 * Provides common attributes and behaviors such as name, health points (HP), and attack power.
 * Author: Triniese Thompson
 */


/**
 * The Character class serves as a foundation for entities that share combat-related attributes.
 * It is abstract because it does not represent a standalone entity in the game.
 */
public abstract class Character {
    /** The name of the character */
    protected String name;
    /** Health points of the character */
    protected int hp;
    /** Attack power of the character */
    protected int attack;

    /**
     * Constructs a Character with the specified name, HP, and attack power.
     * @param name The name of the character.
     * @param hp The health points of the character.
     * @param attack The attack power of the character.
     */
    public Character(String name, int hp, int attack) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    /** @return The name of the character */
    public String getName() { return name; }

    /** @return The current health points */
    public int getHp() { return hp; }

    /**
     * Sets the health points.
     * @param hp New health value.
     */
    public void setHp(int hp) { this.hp = hp; }

    /** @return The attack power */
    public int getAttack() { return attack; }

    /**
     * Sets the attack power.
     * @param attack New attack value.
     */
    public void setAttack(int attack) { this.attack = attack; }

    /**
     * Performs an attack on another character.
     * Reduces the target's HP by this character's attack value.
     * @param target The character being attacked.
     */
    public void attack(Character target) {
        if (target.getHp() > 0) {
            target.setHp(Math.max(0, target.getHp() - this.attack));
            System.out.println(this.name + " attacks " + target.getName() + " for " + this.attack + " damage.");
        }
    }
}
