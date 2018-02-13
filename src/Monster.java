public class Monster {
    private String name;
    private double attack;
    private double defense;
    private double health;

    public Monster (String name, double attack, double defense, double health) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public Double getAttack() {
        return attack;
    }

    public Double getDefense() {
        return defense;
    }

    public Double getHealth() {
        return health;
    }

}
