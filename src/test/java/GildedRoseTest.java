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
        Item item = anItem().create();
        given(item);
        String expectedName = item.name;

        // When
        oneDayPassed();

        // Then
        assertThat(expectedName).isEqualTo(item.name);
    }

    @Test
    public void qualityShouldDegradeTwiceAfterSellDateHasPassed() {
        Item item = anItem().withSellInValue(0).create();
        int expectedQuality = item.quality - 2;
        given(item);

        oneDayPassed();

        assertThat(expectedQuality).isEqualTo(item.quality);
    }

    @Test
    public void qualityShouldNeverBeNegative() {
        Item item = anItem().withQuality(0).create();
        given(item);

        oneDayPassed();

        assertThat(item.quality).isNotNegative();
    }

    @Test
    public void agedBrieShouldIncreaseTheQuality() {
        Item item = anItem().withName(AGED_BRIE).create();
        given(item);
        int expectedQuality = item.quality + 1;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void agedBrieShouldIncreaseTheQualityTwiceFastAfterSellInDate() {
        Item item = anItem().withName(AGED_BRIE).withSellInValue(0).create();
        given(item);
        int expectedQuality = item.quality + 2;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void agedBrieShouldNotIncreaseTheQualityOver50() {
        Item item = anItem().withName(AGED_BRIE).withQuality(50).create();
        given(item);
        int expectedQuality = item.quality;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void qualityOfAnItemIsNeverMoreThenFifty() {
        final int max_quality = 50;
        Item item = anItem().withName(AGED_BRIE).withQuality(max_quality).create();
        given(item);

        oneDayPassed();

        assertThat(item.quality).isEqualTo(max_quality);
    }

    @Test
    public void sulfurasNeverDecreasedByQuality() {
        Item item = anItem().withName(SULFURAS).withQuality(80).create();
        given(item);
        int expectedQuality = item.quality;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedByTwoWhereTenDaysLeft() {
        Item item = anItem().withName(BACKSTAGE_PASS).withSellInValue(10).create();
        given(item);
        int expectedQuality = item.quality + 2;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedByThreeWhereFiveDaysLeft() {
        Item item = anItem().withName(BACKSTAGE_PASS).withSellInValue(5).create();
        given(item);
        int expectedQuality = item.quality + 3;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIncreasedNormallyWhenMoreThanTenDaysLeft() {
        Item item = anItem().withName(BACKSTAGE_PASS).withSellInValue(11).create();
        given(item);
        int expectedQuality = item.quality + 1;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    @Test
    public void backStagePassedQualityIsZeroAfterTheConcert() {
        Item item = anItem().withName(BACKSTAGE_PASS).withSellInValue(0).create();
        given(item);
        int expectedQuality = 0;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }


    @Ignore
    @Test
    public void conjuredItemsShouldDegradeTwiceAsFast() {
        Item item = anItem().withName(CONJURED).create();
        given(item);
        int expectedQuality = item.quality - 2;

        oneDayPassed();

        assertThat(item.quality).isEqualTo(expectedQuality);
    }

    private GildedRose given(Item item) {
        Item[] items = new Item[]{item};
        gildedRose = new GildedRose(items);
        return gildedRose;
    }

}
