package com.derrick.simbisamulacheckout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.derrick.simbisamulacheckout.WebViewActivity.EXTRA_AMOUNT;
import static com.derrick.simbisamulacheckout.WebViewActivity.EXTRA_EMAIL;
import static com.derrick.simbisamulacheckout.WebViewActivity.EXTRA_FIRST_NAME;
import static com.derrick.simbisamulacheckout.WebViewActivity.EXTRA_PHONE_NUMBER;

public class MainActivity extends AppCompatActivity {
    private Button mPay;
    private TextInputEditText mPhoneNumberEdt;
    private TextInputEditText mFirstNameEdt;
    private TextInputEditText mEmailEdt;
    private TextInputEditText mAmountEdt;
    private TextInputLayout mPhoneNumberIl;
    private TextInputLayout mFirstNameIl;
    private TextInputLayout mEmailIl;
    private TextInputLayout mAmountIl;

    private String phone_number, first_name, email, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.mula_checkout);
        InitializeViews();
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateVariables();
                Intent i = getIntent();
                startActivity(i);
            }
        });
    }

    public Intent getIntent() {
        Intent i = new Intent(MainActivity.this, WebViewActivity.class);
        i.putExtra(EXTRA_PHONE_NUMBER, phone_number);
        i.putExtra(EXTRA_FIRST_NAME, first_name);
        i.putExtra(EXTRA_EMAIL, email);
        i.putExtra(EXTRA_AMOUNT, amount);
        return i;
    }

    private void InitializeViews() {
        mPay = findViewById(R.id.pay);
        mPhoneNumberEdt = findViewById(R.id.phone_number_et);
        mFirstNameEdt = findViewById(R.id.first_name_et);
        mEmailEdt = findViewById(R.id.email_et);
        mAmountEdt = findViewById(R.id.amount_et);
        mPhoneNumberIl = findViewById(R.id.phone_number_tl);
        mFirstNameIl = findViewById(R.id.first_name_tl);
        mEmailIl = findViewById(R.id.email_tl);
        mAmountIl = findViewById(R.id.amount_tl);
    }

    private void populateVariables() {
        phone_number = mPhoneNumberEdt.getText().toString();
        first_name = mFirstNameEdt.getText().toString();
        email = mEmailEdt.getText().toString();
        amount = mAmountEdt.getText().toString();
        if (TextUtils.isEmpty(phone_number)) {
            mPhoneNumberIl.setError(getString(R.string.empty_phone_number));
            return;
        }
        if (TextUtils.isEmpty(first_name)) {
            mFirstNameIl.setError(getString(R.string.empty_first_name));
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailIl.setError(getString(R.string.empty_email));
            return;
        }

        if (TextUtils.isEmpty(amount)) {
            mAmountIl.setError(getString(R.string.empty_amount));
            return;
        }

    }
}
