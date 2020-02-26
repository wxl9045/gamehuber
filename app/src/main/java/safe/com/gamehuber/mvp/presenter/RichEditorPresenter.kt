package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.LoginModel
import safe.com.gamehuber.mvp.page.RichEditorActivity

class RichEditorPresenter : BasePresenter<RichEditorActivity>(){
    
    fun doCommit(userEmail:String, pwd: String){
        view.showDialog("提交中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = LoginModel().login(userEmail, pwd)
            if(result is MyResult.Success) {

            }else {  view.toast(result.getString()) }
            view.missDialog()
        }
    }
}