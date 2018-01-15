package com.frizkykramer.weatherapp.view.activity;

import android.accounts.Account;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frizkykramer.weatherapp.R;
import com.frizkykramer.weatherapp.application.SmsListener;
import com.frizkykramer.weatherapp.application.SmsReceiver;
import com.frizkykramer.weatherapp.libs.restrofit.RetrofitBuilder;
import com.frizkykramer.weatherapp.libs.restrofit.RetrofitBuilderOKHome;
import com.frizkykramer.weatherapp.model.AccountModel;
import com.frizkykramer.weatherapp.model.WeatherModel;
import com.frizkykramer.weatherapp.restclient.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.actSignUp_btnCheckPhone)     Button buttonCheckPhone;
    @BindView(R.id.actSignUp_btnSignUp)         Button buttonSignUp;

    @BindView(R.id.actSignUp_etName)            EditText editName;
    @BindView(R.id.actSignUp_etPhone)           EditText editPhone;

    @BindView(R.id.ActSignUp_tvResponse)        TextView responseText;

    private String phoneNumber;
    private String firstName;
    private boolean isPhoneVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        adaptDataAndViews();
    }

    private void adaptDataAndViews() {
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text",messageText);
                Toast.makeText(SignupActivity.this,"Message: "+messageText.substring(4),Toast.LENGTH_LONG).show();
            }
        });

        buttonCheckPhone.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                phoneNumber = editPhone.getText().toString().trim();
                firstName  = editName.getText().toString().trim();

                int nameLength = firstName.length();

                if(nameLength<4 && !TextUtils.isEmpty(editName.toString())) {
                    editName.setError("Your name has to be more than 4 characters.");
                } else {
                    if (isValidPhone(phoneNumber) && !TextUtils.isEmpty(editPhone.toString())) {
                        sendPost(phoneNumber);
                    } else {
                        editPhone.setError("Please enter a correct phone number.");
                    }
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {

                if (isPhoneVerified) {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void sendPost(final String phone) {

        ApiInterface apiService = RetrofitBuilderOKHome.getInstance().getRetrofit().create(ApiInterface.class);

        apiService.verifyPhone(phone, "Y").enqueue(new Callback<AccountModel>() {

            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {

                if(response.isSuccessful()) {

                    createPhoneVerificationDialog();

                    Toast.makeText(SignupActivity.this,
                            "Success", Toast.LENGTH_LONG).show();

                    showResponse(response.body().toString());
                    Log.i("OKHOME PHONE API", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                showResponse("Error: "+ t.toString() + call.toString());
                Log.e("OKHOME PHONE API", "Unable to submit post to API.");
            }
        });
    }

    private void createPhoneVerificationDialog() {
        final Dialog dialog = new Dialog(SignupActivity.this);
        dialog.setContentView(R.layout.dialog_smsverification);
        dialog.setTitle("Phone number verification");

        Button dialogCloseBtn = dialog.findViewById(R.id.dialogSms_btnCancel);
        dialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogResendBtn = dialog.findViewById(R.id.dialogSms_btnResend);
        dialogResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost(phoneNumber);
            }
        });

        Button dialogConfirmBtn = dialog.findViewById(R.id.dialogSms_btnConfirm);
        dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhoneVerified = true;
                dialog.hide();
            }
        });

        EditText dialogPhone = dialog.findViewById(R.id.dialogSms_etPhone);

        dialogPhone.setText(phoneNumber);
        dialogPhone.setKeyListener(null);

        dialog.show();
    }

    public void showResponse(String response) {
        if(responseText.getVisibility() == View.GONE) {
            responseText.setVisibility(View.VISIBLE);
        }
        responseText.setText(response);
    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }


}