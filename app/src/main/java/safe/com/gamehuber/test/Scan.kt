package safe.com.gamehuber.test

interface Scan{
    fun turnOn()
    fun isOn(): Boolean
    fun scan(name: String): Image
}