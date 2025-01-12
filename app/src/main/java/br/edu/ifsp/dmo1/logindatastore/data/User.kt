package br.edu.ifsp.dmo1.logindatastore.data

object User {
    private const val email = "admin@email.com"
    private const val passwd = 123456L

    fun autenticate(email: String, password: Long) = this.email == email && this.passwd == password
}