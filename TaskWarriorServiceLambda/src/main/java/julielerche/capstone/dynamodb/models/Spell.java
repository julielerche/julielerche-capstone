package julielerche.capstone.dynamodb.models;

public class Spell {
    private Integer attackPower;
    private Integer manaNeeded;

    /**
     * Constructs the spell object with values.
     * @param attackPower the amount of damage to do
     * @param manaNeeded the amount of mana the user needs to cast it
     */
    public Spell(Integer attackPower, Integer manaNeeded) {
        this.attackPower = attackPower;
        this.manaNeeded = manaNeeded;
    }

    /**
     * Constructs the spell object with no values.
     */
    public Spell() {
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public Integer getManaNeeded() {
        return manaNeeded;
    }

    public void setManaNeeded(Integer manaNeeded) {
        this.manaNeeded = manaNeeded;
    }
}
