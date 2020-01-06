package safe.com.gamehuber.test

interface Printer{
    fun turnOn()
    fun isOn(): Boolean
    fun printCopy(image: Image)
}