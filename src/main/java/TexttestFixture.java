public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = new Item[]{
                new ItemBuilder().withName("+5 Dexterity Vest").withSellInValue(10).withQuality(20).create(),
                new ItemBuilder().withName("Aged Brie").withSellInValue(2).withQuality(0).create(),
                new ItemBuilder().withName("Elixir of the Mongoose").withSellInValue(5).withQuality(7).create(),
                new ItemBuilder().withName("Sulfuras, Hand of Ragnaros").withSellInValue(0).withQuality(80).create(),
                new ItemBuilder().withName("Sulfuras, Hand of Ragnaros").withSellInValue(-1).withQuality(80).create(),
                new ItemBuilder().withName("Backstage passes to a TAFKAL80ETC concert").withSellInValue(15).withQuality(20).create(),
                new ItemBuilder().withName("Backstage passes to a TAFKAL80ETC concert").withSellInValue(10).withQuality(49).create(),
                new ItemBuilder().withName("Backstage passes to a TAFKAL80ETC concert").withSellInValue(5).withQuality(49).create(),
                // this conjured item does not work properly yet
                new ItemBuilder().withName("Conjured Mana Cake").withSellInValue(3).withQuality(6).create()};

        GildedRose app = new GildedRose(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
