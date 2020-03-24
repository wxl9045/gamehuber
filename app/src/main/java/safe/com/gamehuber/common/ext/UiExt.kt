package safe.com.gamehuber.common.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * 监听editext变化 扩展
 */
fun EditText.addTextChangedListenerEx(onChanged: () -> Unit = {}) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChanged.invoke()
        }
    })
}

