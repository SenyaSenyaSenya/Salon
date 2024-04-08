//import java.util.*
//import javax.mail.*
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
//class EmailSender(private val username: String, private val password: String) {
//    fun sendEmail(toEmail: String, subject: String, message: String) {
//        val properties = Properties().apply {
//            put("mail.smtp.auth", "true")
//            put("mail.smtp.starttls.enable", "true")
//            put("mail.smtp.host", "smtp.gmail.com") //SMTP-хост
//            put("mail.smtp.port", "587") // Порт для SMTP-сервера
//        }
//
//        val session = Session.getInstance(properties, object : Authenticator() {
//            override fun getPasswordAuthentication(): PasswordAuthentication {
//                return PasswordAuthentication(username, password)
//            }
//        })
//
//        try {
//            val mimeMessage = MimeMessage(session).apply {
//                setFrom(InternetAddress(username))
//                addRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
//                setSubject(subject)
//                setText(message)
//            }
//
//            Transport.send(mimeMessage)
//        } catch (e: MessagingException) {
//            e.printStackTrace()
//        }
//    }
//}