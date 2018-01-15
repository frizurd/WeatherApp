package com.frizkykramer.weatherapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.view.activity.SignupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by frizurd on 15/01/2018.
 */

public class PhoneVerificationDialog extends Dialog {

    public PhoneVerificationDialog(@NonNull Context context) {
        super(context);
        ButterKnife.bind(this);

        setContentView(R.layout.dialog_smsverification);
        setTitle("Phone number verification");

        show();
    }

    @OnClick(R.id.dialogSms_btnCancel)
    public void closeDialog() {
        dismiss();
    }
}
