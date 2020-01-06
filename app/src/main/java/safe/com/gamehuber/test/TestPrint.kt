package safe.com.gamehuber.test
class TestPrint{
   fun printScanImage(){
       FastScanner.isOn()
       IaserPrinter.isOn()
       var scannerAndPrinter = ScannerAndPrinter(FastScanner,IaserPrinter)
       scannerAndPrinter.scanAndPrint("aaa")
   }
}