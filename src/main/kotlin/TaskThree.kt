package itmo.tg

import kotlin.reflect.KClass

/*
Возможно, ее сообщение привлекло бы больше внимания, если бы было известно,
что люди были третьими по уровню интеллекта существами на планете Земля,
а не (как полагало большинство независимых обозревателей) вторыми.
 */

class Message(
    private val content: String? = null,
    var attentionLevel: Int = 0
) {

    fun getContent(): String {
        if (content.isNullOrBlank()) return "Nothing to say."
        return content
    }

    fun calculateAndSetAttentionLevel(reviewers: List<Reviewer>) {
        val observerBeliefs = reviewers.map { it.review(HumanBeing()) }
        var totalAttention = 0
        observerBeliefs.forEach {
            totalAttention += when (it) {
                IntelligenceLevel.FIRST -> 5
                IntelligenceLevel.SECOND -> 10
                IntelligenceLevel.THIRD -> 20
            }
        }
        attentionLevel = totalAttention
    }

}

enum class IntelligenceLevel {
    FIRST, SECOND, THIRD
}

interface Being {
    fun getIntelligenceLevel(): IntelligenceLevel
}

class HumanBeing(private val message: Message? = null) : Being {

    fun getMessage(): Message {
        if (message == null) return Message("*silence*")
        return message
    }

    private val intelligence: IntelligenceLevel = IntelligenceLevel.THIRD

    override fun getIntelligenceLevel(): IntelligenceLevel {
        return intelligence
    }

}

class Cat : Being {

    private val intelligence: IntelligenceLevel = IntelligenceLevel.SECOND

    override fun getIntelligenceLevel(): IntelligenceLevel {
        return intelligence
    }

}

class CelestialBeing : Being {

    private val intelligence: IntelligenceLevel = IntelligenceLevel.FIRST

    override fun getIntelligenceLevel(): IntelligenceLevel {
        return intelligence
    }

}

class Reviewer(private val beliefs: Map<KClass<out Being>, IntelligenceLevel>) {

    fun review(being: Being): IntelligenceLevel {
        return beliefs[being::class]!!
    }

    companion object {
        private val defaultReviewer = Reviewer(
            mapOf(
                Pair(Cat::class, IntelligenceLevel.THIRD),
                Pair(HumanBeing::class, IntelligenceLevel.SECOND),
                Pair(CelestialBeing::class, IntelligenceLevel.FIRST)
            )
        )

        private val enlightenedReviewer = Reviewer(
            mapOf(
                Pair(HumanBeing::class, IntelligenceLevel.THIRD),
                Pair(Cat::class, IntelligenceLevel.SECOND),
                Pair(CelestialBeing::class, IntelligenceLevel.FIRST)
            )
        )

        private val crazyReviewer = Reviewer(
            mapOf(
                Pair(Cat::class, IntelligenceLevel.THIRD),
                Pair(CelestialBeing::class, IntelligenceLevel.SECOND),
                Pair(HumanBeing::class, IntelligenceLevel.FIRST)
            )
        )

        fun getIndependentReviewers(): List<Reviewer> {
            return listOf(
                defaultReviewer,
                defaultReviewer,
                defaultReviewer,
                enlightenedReviewer,
                crazyReviewer
            )
        }

        fun getTheoreticalReviewers(): List<Reviewer> {
            return listOf(
                defaultReviewer,
                enlightenedReviewer,
                enlightenedReviewer,
                enlightenedReviewer,
                crazyReviewer
            )
        }
    }

}
