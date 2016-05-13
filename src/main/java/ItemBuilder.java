public class ItemBuilder {
    private String name = "Irrelevant Item";
    private int sellIn = 25;
    private int quality = 25;

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withSellInValue(int sellIn) {
        this.sellIn = sellIn;
        return this;
    }

    public ItemBuilder withQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Item create() {
        return new Item(name, sellIn, quality);
    }
}