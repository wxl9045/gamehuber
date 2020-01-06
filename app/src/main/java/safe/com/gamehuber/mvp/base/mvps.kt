package safe.com.gamehuber.mvp.base

interface IPresenter<out View: IMvpView<IPresenter<View>>>: ILifecycle {
    val view: View
}

interface IMvpView<out Presenter: IPresenter<IMvpView<Presenter>>>: ILifecycle {
    val presenter: Presenter
}