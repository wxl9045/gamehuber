package safe.com.gamehuber.common.utils

import android.widget.EditText


fun showTips(view: EditText, tips: String){
    view.requestFocus()
    view.error = tips
}