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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.TakePhotoActivity
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.presenter.RichEditorPresenter


class RichEditorActivity : TakePhotoActivity<RichEditorPresenter>() {
    private var subsInsert: Disposable? = null
    private var gameId: String? = null
    private var name: String? = null
    private var postTypeEventBean: PostTypeEventBean? = null
    private var fileIds: ArrayList<String> = ArrayList()
    override fun getLayoutId(): Int = R.layout.activity_rich_editor

    override fun initView() {
        gameId = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        gameId.isNullOrEmpty().no {
            tvTitle.text = name
        }
        EventBus.getDefault().register(this)
        setMyClickListener(close, btPic, tvTitle, btCommit)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.close -> finish()
            R.id.btCommit -> commitData()
            R.id.btPic -> takePhoto.onPickFromGallery()
            R.id.tvTitle -> {
                gameId.isNullOrEmpty().yes {
                    startActivity<SelectGameActivity>()
                }.otherwise {
                    startActivity<PostTypeActivity>("id" to gameId, "name" to name)
                }
            }
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

    @Subscribe
    fun getPostTypeEventBean(eventBean: PostTypeEventBean) {
        if (eventBean != null) {
            postTypeEventBean = eventBean
            tvTitle.text = eventBean.topicName + "-" + eventBean.name
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    fun getFileData(fileIds: List<String>) {
        this.fileIds.clear()
        for (id in fileIds) {
            this.fileIds.add(id)
        }
        commitHtmlData()
    }

    private fun commitData() {
        (postTypeEventBean == null).yes {
            toast("请选择游戏和所属分区")
            return
        }
        (postTypeEventBean == null).yes {
            toast("请选择游戏和所属分区")
            return
        }
        edTitle.text.isNullOrEmpty().yes {
            toast("请输入标题")
            return
        }
        var data = et_new_content.buildEditData()
        (data.size in 0..1).yes {
            toast("请输入内容")
            return
        }
        var imageIds: ArrayList<String> = ArrayList()//获取所有相关图片路径
        for (i in 0 until data.size) {
            val imagUrl = data[i].imagePath
            if (null != imagUrl && "" != imagUrl) {
                imageIds.add(imagUrl)
            }
        }
        (imageIds.size > 0).yes {
            //先上传图片
            presenter.uploadFile(imageIds)
        }.otherwise {
            //没有图片直接提交
            commitHtmlData()
        }
    }

    private fun commitHtmlData() {
        //提交数据
        val map = mapOf(
                "topicId" to postTypeEventBean?.topicId,
                "title" to postTypeEventBean?.topicName,
                "typeId" to postTypeEventBean?.id,
                "htmlContent" to getHtmlData(),
                "type" to 2,
                "planTextContent" to "a")
        presenter.addPost(map as Map<String, Any>)
    }

    private fun getHtmlData(): String {
        var content = ""
        var data = et_new_content.buildEditData()
        for (i in 0 until data.size) {
            val imagUrl = data[i].imagePath//媒体文件的远程路径
            val inputStr = data[i].inputStr//输入的文本
            if (null != inputStr && "" != inputStr)
                content = "$content<p>$inputStr</p>"
            if (null != imagUrl && "" != imagUrl) {
                val html = """<p><img src = "${fileIds[i]}"/></p>"""
                content += html
            }
        }
        return content
    }

}