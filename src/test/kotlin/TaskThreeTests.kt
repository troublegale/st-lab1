import itmo.tg.HumanBeing
import itmo.tg.IntelligenceLevel
import itmo.tg.Message
import itmo.tg.Reviewer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TaskThreeTests {

    @Test
    fun `Empty message has nothing to say`() {
        val nts = "Nothing to say."
        assertEquals(Message().getContent(), nts)
        assertEquals(Message("").getContent(), nts)
        assertEquals(Message("   ").getContent(), nts)
    }

    @Test
    fun `Human with no message is silent`() {
        assertEquals(HumanBeing().getMessage().getContent(),
            "*silence*")
    }

    @Test
    fun `Unreviewed message has attention level of 0`() {
        assertEquals(Message("Something").attentionLevel, 0)
    }

    @Test
    fun `Reviewed message receives attention`() {
        val message = Message("Information")
        val reviewers = Reviewer.getIndependentReviewers()
        message.calculateAndSetAttentionLevel(reviewers)
        assert(message.attentionLevel > 0)
    }

    @Test
    fun `Humans are third in intelligence`() {
        assertEquals(HumanBeing().getIntelligenceLevel(), IntelligenceLevel.THIRD)
    }

    @Test
    fun `Most independent Reviewers consider Humans second in intelligence`() {
        val independentReviewers = Reviewer.getIndependentReviewers()
        val suitableReviewers = independentReviewers.filter {
            it.review(HumanBeing()) == IntelligenceLevel.SECOND
        }
        assert(suitableReviewers.size > independentReviewers.size)
    }

    @Test
    fun `Her message could have received more attention`() {
        val she = HumanBeing(Message("Make America Great Again"))
        val independentReviewers = Reviewer.getIndependentReviewers()
        val theoreticalReviewers = Reviewer.getTheoreticalReviewers()
        she.getMessage().calculateAndSetAttentionLevel(independentReviewers)
        val attentionLevel1 = she.getMessage().attentionLevel
        she.getMessage().calculateAndSetAttentionLevel(theoreticalReviewers)
        val attentionLevel2 = she.getMessage().attentionLevel
        assert(attentionLevel1 > attentionLevel2)
    }

}