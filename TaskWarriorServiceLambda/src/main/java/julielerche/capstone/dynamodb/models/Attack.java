package julielerche.capstone.dynamodb.models;

public class Attack {
    private Integer attackPower;
    private Integer staminaNeeded;
    private Integer target;

    public Attack(Integer attackPower, Integer staminaNeeded, Integer target) {
        this.attackPower = attackPower;
        this.staminaNeeded = staminaNeeded;
        this.target = target;
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public Integer getStaminaNeeded() {
        return staminaNeeded;
    }

    public void setStaminaNeeded(Integer staminaNeeded) {
        this.staminaNeeded = staminaNeeded;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
