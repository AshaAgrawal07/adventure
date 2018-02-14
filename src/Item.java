import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.getDamage(), getDamage()) == 0 &&
                Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDamage());
    }

    public String toString() {
        return "Item: " + this.getName() + ", Damage: " + this.getDamage();
    }
}
