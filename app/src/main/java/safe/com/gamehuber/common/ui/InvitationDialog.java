package safe.com.gamehuber.common.ui;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import safe.com.gamehuber.R;


public class InvitationDialog extends Dialog {
    private Context context;
    //private String title;
    private ClickListenerInterface clickListenerInterface;

    public InvitationDialog(Context context) {
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
        View view = inflater.inflate(R.layout.dialog_invitation, null);
        setContentView(view);
        TextView btImageText = view.findViewById(R.id.bt_image_text);
        TextView btVideo = view.findViewById(R.id.bt_video);


        btImageText.setOnClickListener(new clickListener());
        btVideo.setOnClickListener(new clickListener());

//		Window dialogWindow = getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//		dialogWindow.setAttributes(lp);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        setCanceledOnTouchOutside(true);
    }

    public interface ClickListenerInterface {
        void doImageText();
        void doVideo();
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.bt_image_text://拍照
                    // 用户已经同意该权限
                    clickListenerInterface.doImageText();
                    dismiss();
                    break;
                case R.id.bt_video: //本地相册
                    // 用户已经同意该权限
                    clickListenerInterface.doVideo();
                    dismiss();
                    break;
            }
        }
    }
}
