package safe.com.gamehuber.mvp.page

import android.view.View
import com.jph.takephoto.model.TResult
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rich_editor.*
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.TakePhotoActivity
import safe.com.gamehuber.mvp.presenter.RichEditorPresenter


class RichEditorActivity : TakePhotoActivity<RichEditorPresenter>() {
    var  subsInsert: Disposable? = null
    override fun getLayoutId(): Int = R.layout.activity_rich_editor

    override fun initView() {
        setMyClickListener(close, btPic)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.close -> finish()
            R.id.btPic -> takePhoto.onPickFromGallery()
        }
    }

    /**
     * 选取照片成功
     */
    override fun takeSuccess(result: TResult) {
        var iconPath = result.image.originalPath
        Observable.create(ObservableOnSubscribe<String> { emitter ->
            try {
                et_new_content.measure(0, 0)
                // 可以同时插入多张图片
                //Log.e(TAG, "###imagePath="+imagePath);
                emitter.onNext(iconPath)
                emitter.onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }
        })
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        toast("图片插入失败:" + e.message)
                    }

                    override fun onSubscribe(d: Disposable) {
                        subsInsert = d
                    }

                    override fun onNext(imagePath: String) {
                        et_new_content.insertImage(imagePath)
                    }
                })
    }

    override fun onStop() {
        super.onStop()
    }
}