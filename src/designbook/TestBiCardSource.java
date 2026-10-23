package designbook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestBiCardSource {

    /**
     * Lớp Stub thay thế cho CardSource để cô lập hành vi kiểm thử (Test Isolation).
     * Cho phép cấu hình trước giá trị trả về của isEmpty() và draw().
     */
    private static class StubCardSource implements CardSource {
        private final boolean aIsEmpty;
        private final Card aCard;

        public StubCardSource(boolean pIsEmpty, Card pCard) {
            this.aIsEmpty = pIsEmpty;
            this.aCard = pCard;
        }

        public StubCardSource(boolean pIsEmpty) {
            this(pIsEmpty, null);
        }

        @Override
        public Card draw() {
            return aCard;
        }

        @Override
        public boolean isEmpty() {
            return aIsEmpty;
        }
    }

    // =========================================================================
    // 1. KIỂM THỬ NHÁNH CHO PHƯƠNG THỨC draw()
    // =========================================================================

    /**
     * Nhánh 1: !aSource1.isEmpty() = TRUE (Source 1 không rỗng).
     * Kỳ vọng: Thực thi nhánh if, gọi và trả về kết quả từ aSource2.draw().
     */
    @Test
    public void testDraw_Source1NotEmpty_DrawsFromSource2() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.KING, Suit.HEARTS);

        CardSource source1 = new StubCardSource(false, card1);
        CardSource source2 = new StubCardSource(false, card2);
        BiCardSource biSource = new BiCardSource(source1, source2);

        Card drawnCard = biSource.draw();
        assertSame(card2, drawnCard);
    }

    /**
     * Nhánh 2: !aSource1.isEmpty() = FALSE (Source 1 rỗng).
     * Kỳ vọng: Thực thi nhánh else, gọi và trả về kết quả từ aSource1.draw().
     */
    @Test
    public void testDraw_Source1Empty_DrawsFromSource1() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.KING, Suit.HEARTS);

        CardSource source1 = new StubCardSource(true, card1);
        CardSource source2 = new StubCardSource(false, card2);
        BiCardSource biSource = new BiCardSource(source1, source2);

        Card drawnCard = biSource.draw();
        assertSame(card1, drawnCard);
    }

    // =========================================================================
    // 2. KIỂM THỬ NHÁNH CHO PHƯƠNG THỨC isEmpty()
    // Biểu thức: aSource1.isEmpty() && aSource2.isEmpty()
    // =========================================================================

    /**
     * Nhánh 3: aSource1.isEmpty() = FALSE.
     * Kỳ vọng: Toán tử && ngắn mạch (short-circuit), trả về FALSE mà không cần xét Source 2.
     */
    @Test
    public void testIsEmpty_Source1NotEmpty_ReturnsFalse() {
        CardSource source1 = new StubCardSource(false);
        CardSource source2 = new StubCardSource(true);
        BiCardSource biSource = new BiCardSource(source1, source2);

        assertFalse(biSource.isEmpty());
    }

    /**
     * Nhánh 4: aSource1.isEmpty() = TRUE, aSource2.isEmpty() = FALSE.
     * Kỳ vọng: Vượt qua Source 1, xét Source 2 là false -> trả về FALSE.
     */
    @Test
    public void testIsEmpty_Source1EmptyAndSource2NotEmpty_ReturnsFalse() {
        CardSource source1 = new StubCardSource(true);
        CardSource source2 = new StubCardSource(false);
        BiCardSource biSource = new BiCardSource(source1, source2);

        assertFalse(biSource.isEmpty());
    }

    /**
     * Nhánh 5: aSource1.isEmpty() = TRUE, aSource2.isEmpty() = TRUE.
     * Kỳ vọng: Cả hai nguồn đều rỗng -> trả về TRUE.
     */
    @Test
    public void testIsEmpty_BothSourcesEmpty_ReturnsTrue() {
        CardSource source1 = new StubCardSource(true);
        CardSource source2 = new StubCardSource(true);
        BiCardSource biSource = new BiCardSource(source1, source2);

        assertTrue(biSource.isEmpty());
    }
}