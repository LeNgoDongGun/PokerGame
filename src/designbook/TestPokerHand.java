package designbook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class TestPokerHand {

    /**
     * Helper method: Sử dụng Java Reflection để gọi phương thức private isStraightFlush.
     */
    private static boolean invokeIsStraightFlush(PokerHand pHand) {
        try {
            Method method = PokerHand.class.getDeclaredMethod("isStraightFlush");
            method.setAccessible(true);
            return (boolean) method.invoke(pHand);
        } catch (Exception e) {
            throw new RuntimeException("Không thể gọi phương thức private isStraightFlush", e);
        }
    }

    /**
     * Test case 1 (từ Exercise 8):
     * Bộ bài sảnh thùng hợp lệ (A-2-3-4-5 SPADES) -> trả về TRUE.
     * Bao phủ nhánh: while (hasNext) = true/false, cả hai điều kiện của if đều false.
     */
    @Test
    public void testIsStraightFlush_ReturnsTrue() {
        PokerHand hand = new PokerHand(
            new Card(Rank.ACE, Suit.SPADES),
            new Card(Rank.TWO, Suit.SPADES),
            new Card(Rank.THREE, Suit.SPADES),
            new Card(Rank.FOUR, Suit.SPADES),
            new Card(Rank.FIVE, Suit.SPADES)
        );

        boolean result = invokeIsStraightFlush(hand);
        assertTrue(result);
    }

    /**
     * Test case 2 (từ Exercise 9):
     * Bộ bài KHÁC CHẤT (lá thứ hai là 2 HEARTS thay vì 2 SPADES) -> trả về FALSE.
     * Bao phủ nhánh: 'card.getSuit() != suit' = TRUE (ngắn mạch dừng kiểm tra rank).
     */
    @Test
    public void testIsStraightFlush_DifferentSuit_ReturnsFalse() {
        PokerHand hand = new PokerHand(
            new Card(Rank.ACE, Suit.SPADES),
            new Card(Rank.TWO, Suit.HEARTS),
            new Card(Rank.THREE, Suit.SPADES),
            new Card(Rank.FOUR, Suit.SPADES),
            new Card(Rank.FIVE, Suit.SPADES)
        );

        boolean result = invokeIsStraightFlush(hand);
        assertFalse(result);
    }

    /**
     * Test case 3 (bổ sung cho Exercise 10):
     * Bộ bài CÙNG CHẤT nhưng KHÔNG LIÊN TIẾP (A-2-3-4-6 SPADES, khuyết lá 5) -> trả về FALSE.
     * Bao phủ nhánh: 'card.getSuit() != suit' = FALSE và 'rank diff != 1' = TRUE.
     */
    @Test
    public void testIsStraightFlush_SameSuitNonConsecutive_ReturnsFalse() {
        PokerHand hand = new PokerHand(
            new Card(Rank.ACE, Suit.SPADES),
            new Card(Rank.TWO, Suit.SPADES),
            new Card(Rank.THREE, Suit.SPADES),
            new Card(Rank.FOUR, Suit.SPADES),
            new Card(Rank.SIX, Suit.SPADES)
        );

        boolean result = invokeIsStraightFlush(hand);
        assertFalse(result);
    }
}