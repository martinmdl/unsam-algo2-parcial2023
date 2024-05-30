@file:Suppress("SpellCheckingInspection")

class Canal(val grilla: MutableList<Programa>) {

    private val revision: MutableSet<Programa> = mutableSetOf()
    private var accion: Accion = Nula()
    private val observadores: MutableList<RecomendacionObserver> = mutableListOf()

    fun setAccion(accionRequerida: Accion) {
        this.accion = accionRequerida
    }

    fun accionar() {
        accion.execute()
    }

    fun verificarRestricciones() {

    }

    fun agregarARevision(programas: MutableSet<Programa>) {
        revision.union(programas)
    }

    fun agregarObserver(observer: RecomendacionObserver) { observadores.add(observer) }
    fun sacarObserver(observer: RecomendacionObserver) { observadores.remove(observer) }

}

//pueda definir una serie de restricciones a cumplir
//, y las acciones que deben ocurrir en caso de que la restricción no se cumpla.
// Por ejemplo, si un programa no tiene más de 5 puntos de rating debe mover el programa al día martes.

//pueda agregar una serie de programas para "revisión"
