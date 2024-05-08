import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class AutorSpec : DescribeSpec({

    val autor1 = Autor()

    it("hola") {
        autor1.num shouldBe 4
    }
})