package safe.com.gamehuber.mvp.page

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_rich_editor.close
import kotlinx.android.synthetic.main.activity_send_video.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.TakePhotoActivity
import safe.com.gamehuber.mvp.presenter.RichEditorPresenter


class SendVideoActivity : TakePhotoActivity<RichEditorPresenter>() {
    private var imagePath: String? = null
    private var videoPath: String? = null
    override fun getLayoutId(): Int = R.layout.activity_send_video

    override fun initView() {
        imagePath = intent.getStringExtra("imagePath")
        videoPath = intent.getStringExtra("videoPath")
        setMyClickListener(close)
        loadVideoImage()
    }

    private fun loadVideoImage() {
        Glide.with(this)
                .load(imagePath)
                .into(im_video)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.close -> finish()
        }
    }
}