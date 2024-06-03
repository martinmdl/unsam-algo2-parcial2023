interface MailSender { fun sendMail(mail: Mail) }
data class Mail(val from: String, val to: String, val subject: String, val message: String)
interface SMSSender { fun sendMessage(sms: SMS) }
data class SMS(val number: String, val contact: String, val message: String)

interface ObservadorNuevosProgramas {
    fun programaCreado(programa: Programa)
}


class MailConductores(private val mailSender: MailSender) : ObservadorNuevosProgramas {

    override fun programaCreado(programa: Programa) {

        val destinatarios = programa.conductores.joinToString(", ") { it.email }

        mailSender.sendMail(Mail(
            from = "system@mailer.com",
            to = destinatarios,
            subject = "Oportunidad!",
            message = "Fuiste seleccionado para conducir ${programa.titulo}!Ponete en contacto con la gerencia."
        ))
    }
}

class MailNecesidadSponsor(private val messageSender: SMSSender) : ObservadorNuevosProgramas {

    companion object { private const val PRESUPUESTO_LIMITE = 100000 }

    override fun programaCreado(programa: Programa) {
        if (programa.presupuestoBase > PRESUPUESTO_LIMITE) {
            messageSender.sendMessage(
                SMS(
                    number = "0800-666-4949",
                    contact = "Cliowin",
                    message = "${programa.presupuestoBase} - ${programa.titulo} - CONSEGUIR SPONSOR URGENTE!"
                )
            )
        }
    }
}

class SacarDeRevision(private val canal: Canal) : ObservadorNuevosProgramas {

    override fun programaCreado(programa: Programa) {
        canal.sacarDeRevision()
    }

}