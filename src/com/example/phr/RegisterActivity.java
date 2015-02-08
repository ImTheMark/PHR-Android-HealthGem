package com.example.phr;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileUserDao;
import com.example.phr.mobile.daoimpl.MobileUserDaoImpl;
import com.example.phr.service.UserService;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.tools.PasswordValidator;

public class RegisterActivity extends Activity {

	private ImageButton mBtnRegister;
	private String password;
	private String confirmPassword;
	private String username;
	private PasswordValidator passwordValidator;
	private EditText formUsername;
	private EditText formPassword;
	private EditText formConfirmPassword;
	private TextView mTextValid;
	private TextView textViewPasswordStrength;
	private UserService userService;
	private MobileUserDao userDao;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));

		userService = new UserServiceImpl();
		userDao = new MobileUserDaoImpl();

		mBtnRegister = (ImageButton) findViewById(R.id.btnRegister);
		mTextValid = (TextView) findViewById(R.id.valid);
		textViewPasswordStrength = (TextView) findViewById(R.id.textViewPasswordStrength);

		formUsername = (EditText) findViewById(R.id.txtUsernameReg);
		formPassword = (EditText) findViewById(R.id.txtPasswordReg);
		formConfirmPassword = (EditText) findViewById(R.id.confirmPasswordReg);
		passwordValidator = new PasswordValidator();

		formUsername.setText(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.REGISTER_USERNAME));

		formPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				boolean valid = passwordValidator.validate(formPassword
						.getText().toString());
				if (valid)
					textViewPasswordStrength
							.setText("Password Strength: Strong");
				else
					textViewPasswordStrength.setText("Password Strength: Weak");

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});

		mBtnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				username = formUsername.getText().toString();
				password = formPassword.getText().toString();
				confirmPassword = formConfirmPassword.getText().toString();

				boolean valid = passwordValidator.validate(password);

				try {
					if (password.equals(confirmPassword)) {
						Log.e("tama1", "tama2");
						if (password.length() > 7) {
							Log.e("USERNAME REG", username);
							if (!userService.usernameAlreadyExists(username)) {
								Intent intent = new Intent(
										getApplicationContext(),
										RegisterUserInformationActivity.class);
								HealthGem.getSharedPreferences()
										.savePreferences(
												SPreference.REGISTER_USERNAME,
												username);
								HealthGem.getSharedPreferences()
										.savePreferences(
												SPreference.REGISTER_PASSWORD,
												password);
								startActivity(intent);
							} else
								mTextValid.setText("Username already exists!");

						} else
							mTextValid
									.setText("password length must be at least 8 characters");
					} else {
						mTextValid.setText("passwords does not match");
						Log.e("mali1", "mali2");
					}
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					Toast.makeText(HealthGem.getContext(),
							"No Internet Connection !", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		userDao.clearRegisterInformation();
	}
}