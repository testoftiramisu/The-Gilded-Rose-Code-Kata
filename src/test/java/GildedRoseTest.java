import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GildedRoseTest {

    public static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String CONJURED = "Conjured Mana Cake";
    private GildedRose gildedRose;

    private void oneDayPassed() {
        gildedRose.updateQuality();
    }

    private ItemBuilder anItem() {
        return new ItemBuilder();
    }

    @Test
    public void itemNameShouldNotChanged() {
        // Given
        Item currentItem = anItem().createItem();
        given(currentItem);
        String expectedName = currentItem.name;

        // When
        oneDayPassed();

        // Then
        assertThat(expectedName).isEqualTo(currentItem.name);
    }

    @Test
    public void qualityShouldDegradeTwiceAfterSellDateHasPassed() {
        Item currentItem = anItem().withSellInValue(0).createItem();
        int expectedQuality = currentItem.quality - 2;
        given(currentItem);

        oneDayPassed();

        assertThat(expectedQuality).isEqualTo(currentItem.quality);
    }

    @Test
    public void qualityShouldNeverBeNegative() {
        Item currentItem = anItem().withQuality(0).createItem();
        given(currentItem);

        oneDayPassed();

        assertThat(currentItem.quality).isNotNegative();
    }

    @Test
    public void agedBrieShouldIncreaseTheQuality() {
        Item currentItem = anItem().withName(AGED_BRIE).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality + 1;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void agedBrieShouldIncreaseTheQualityTwiceFastAfterSellInDate() {
        Item currentItem = anItem().withName(AGED_BRIE).withSellInValue(0).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality + 2;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void agedBrieShouldNotIncreaseTheQualityOver50() {
        Item currentItem = anItem().withName(AGED_BRIE).withQuality(50).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void qualityOfAnItemIsNeverMoreThenFifty() {
        final int max_quality = 50;
        Item currentItem = anItem().withName(AGED_BRIE).withQuality(max_quality).createItem();
        given(currentItem);

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(max_quality);
    }

    @Test
    public void sulfurasNeverDecreasedByQuality() {
        Item currentItem = anItem().withName(SULFURAS).withQuality(80).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedByTwoWhereTenDaysLeft() {
        Item currentItem = anItem().withName(BACKSTAGE_PASS).withSellInValue(10).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality + 2;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedByThreeWhereFiveDaysLeft() {
        Item currentItem = anItem().withName(BACKSTAGE_PASS).withSellInValue(5).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality + 3;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedNormallyWhenMoreThanTenDaysLeft() {
        Item currentItem = anItem().withName(BACKSTAGE_PASS).withSellInValue(11).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality + 1;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIsZeroAfterTheConcert() {
        Item currentItem = anItem().withName(BACKSTAGE_PASS).withSellInValue(0).createItem();
        given(currentItem);
        int expectedQuality = 0;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }


    @Ignore
    @Test
    public void conjuredItemsShouldDegradeTwiceAsFast() {
        Item currentItem = anItem().withName(CONJURED).createItem();
        given(currentItem);
        int expectedQuality = currentItem.quality - 2;

        oneDayPassed();

        assertThat(currentItem.quality).isEqualTo(expectedQuality);
    }


    private Item given(String name, int sellInValue, int quality) {
        Item[] items = new Item[]{anItem().withName(name).withSellInValue(sellInValue).withQuality(quality).createItem()};
        this.gildedRose = new GildedRose(items);
        return this.gildedRose.items[0];
    }

    private GildedRose given(Item item) {
        Item[] items = new Item[]{item};
        gildedRose = new GildedRose(items);
        return gildedRose;
    }

}
