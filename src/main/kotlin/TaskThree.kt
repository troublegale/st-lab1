package itmo.tg

/*
Возможно, ее сообщение привлекло бы больше внимания, если бы было известно,
что люди были третьими по уровню интеллекта существами на планете Земля,
а не (как полагало большинство независимых обозревателей) вторыми.
 */

enum class Intelligence {
    NOT_SO_SMART, SMART, THE_SMARTEST
}

interface IntelligentBeing {
    companion object {
        var information: String? = null
    }
    fun observe(being: IntelligentBeing): Intelligence
    fun hear(message: String) {
        information = message
    }
    fun speak() = information
}

class Man : IntelligentBeing {
    override fun observe(being: IntelligentBeing): Intelligence {
        return when (being) {
            is Man -> Intelligence.SMART
            is Woman -> Intelligence.NOT_SO_SMART
            is God -> Intelligence.THE_SMARTEST
            else -> throw IllegalArgumentException(
                "Can only observe men, women and gods")
        }
    }
}

class Woman : IntelligentBeing {
    override fun observe(being: IntelligentBeing): Intelligence {
        return when (being) {
            is Man -> Intelligence.NOT_SO_SMART
            is Woman -> Intelligence.SMART
            is God -> Intelligence.THE_SMARTEST
            else -> throw IllegalArgumentException(
                "Can only observe men, women and gods")
        }
    }
    fun bringMessage(message: String?): String {
        return if (message == null) "I have nothing to say."
        else "I bring a very important message: $message"
    }
}

class God : IntelligentBeing {
    override fun observe(being: IntelligentBeing): Intelligence {
        return when (being) {
            is Man -> Intelligence.NOT_SO_SMART
            is Woman -> Intelligence.NOT_SO_SMART
            is God -> Intelligence.SMART
            else -> throw IllegalArgumentException(
                "Can only observe men, women and gods")
        }
    }
}

class Monkey : IntelligentBeing {
    override fun observe(being: IntelligentBeing): Intelligence {
        return when (being) {
            is Man -> Intelligence.NOT_SO_SMART
            is Woman -> Intelligence.NOT_SO_SMART
            is God -> Intelligence.NOT_SO_SMART
            else -> Intelligence.THE_SMARTEST
        }
    }
}

fun getObservers(): List<IntelligentBeing> {
    return listOf(Man(), Man(), Man(),
        Woman(), Woman(), God(), Monkey())
}