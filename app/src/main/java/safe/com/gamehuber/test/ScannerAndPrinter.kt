package safe.com.gamehuber.test

class ScannerAndPrinter(scanner: Scan,printer: Printer): Scan by scanner, Printer by printer {
    override fun isOn(): Boolean = (this as Scan).isOn()//两个单例的方法一样,需要显示的覆写
    override fun turnOn() = (this as Scan).turnOn()//两个单例的方法一样,需要显示的覆写
    fun scanAndPrint(imageName: String) = printCopy(scan(imageName))
}
