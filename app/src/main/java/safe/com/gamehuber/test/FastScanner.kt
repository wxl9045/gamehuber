package safe.com.gamehuber.test

object FastScanner: Scan {//扫描仪接口实现单例-快速扫描仪
    var working = false
    override fun isOn(): Boolean = working
    override fun turnOn() {
        working = true
    }
    override fun scan(name: String): Image = Image(name)
}
