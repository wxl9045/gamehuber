package safe.com.gamehuber.test

object IaserPrinter : Printer{
    var  working = false
    override fun turnOn() {
        working = true
    }

    override fun isOn(): Boolean = working

    override fun printCopy(image: Image) {
        println("printed ${image.name}")
    }

}