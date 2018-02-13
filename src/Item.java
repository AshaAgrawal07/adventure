public class Item {
    private String name;
    private double damage;

    public Item(String itemName, double damageValue) {
        name = itemName;
        damage = damageValue;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }
}
