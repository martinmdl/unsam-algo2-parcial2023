object RegistroGlobal {

    private val registro: MutableMap<Dj, Int> = mutableMapOf()

    fun registrar(dj: Dj) { registro[dj] = getRegistro(dj) + 1 }

    fun getRegistro(dj: Dj): Int = registro.getOrPut(dj) { 0 }
}