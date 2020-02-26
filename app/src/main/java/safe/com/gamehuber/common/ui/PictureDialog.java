package safe.com.gamehuber.common.ui;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import safe.com.gamehuber.R;


public class PictureDialog extends Dialog {
	private Context context;
	//private String title;
	private ClickListenerInterface clickListenerInterface;

	public PictureDialog(Context context) {
		super(context, R.style.PictureDialogStyle);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_picture, null);
		setContentView(view);
		TextView btCamera = view.findViewById(R.id.bt_camera);
		TextView btLocal =  view.findViewById(R.id.bt_local);


		btCamera.setOnClickListener(new clickListener());
		btLocal.setOnClickListener(new clickListener());

//		Window dialogWindow = getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//		dialogWindow.setAttributes(lp);
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		layoutParams.width =  context.getResources().getDisplayMetrics().widthPixels;
		view.setLayoutParams(layoutParams);
		getWindow().setGravity(Gravity.BOTTOM);
		getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
		setCanceledOnTouchOutside(true);
	}

	public interface ClickListenerInterface {
	    void doCamera();
		void doLocal();
	}

	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
		this.clickListenerInterface = clickListenerInterface;
	}

	private class clickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.bt_camera://拍照
				RxPermissions rxPermission = new RxPermissions((Activity) context);
				rxPermission
						.requestEach(Manifest.permission.CAMERA)
						.subscribe(new Consumer<Permission>() {
							@Override
							public void accept(Permission permission) throws Exception {
								if (permission.granted) {
									// 用户已经同意该权限
									clickListenerInterface.doCamera();
								}
							}
						});
				dismiss();
				break;
			case R.id.bt_local: //本地相册
				rxPermission = new RxPermissions((Activity) context);
				rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.READ_CALENDAR,
						Manifest.permission.READ_CONTACTS)
						.subscribe(new Consumer<Permission>() {
							@Override
							public void accept(Permission permission) throws Exception {
								if (permission.granted) {
									// 用户已经同意该权限
									clickListenerInterface.doLocal();
								}
							}
						});
				dismiss();
				break;
			}
		}
	};
}
