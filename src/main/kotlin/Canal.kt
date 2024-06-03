@file:Suppress("SpellCheckingInspection")

class Canal(val grilla: MutableList<Programa>) {

    private val revision: MutableSet<Programa> = mutableSetOf()

    fun agregarARevision(programas: MutableSet<Programa>) {
        revision.union(programas)
    }

    fun sacarDeRevision() {
        revision.removeAll { !grilla.contains(it) }
    }

    fun ejecutarRevision() {
        grilla.forEach { it.ejecutarRevision(this) }
    }

    fun agregarPrograma(programa: Programa) {
        grilla.add(programa)
    }


}
