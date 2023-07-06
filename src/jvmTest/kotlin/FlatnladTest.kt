import io.kotest.core.spec.style.StringSpec

class FlatnladTest : StringSpec({
    "init 3 x 3 Flatland" {
        val flatland = Flatland(3, 3, initProbability = 500)
        repeat(3) {
            flatland.update()
        }
//        println(flatland.toString())
    }

    "10 x 10 Flatland, update 3 times" {

        val flatland = Flatland(10, 10, initProbability = 500)
        repeat(10) {
            flatland.update()
        }
    }
})