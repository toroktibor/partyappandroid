package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity implements
        hu.schonherz.y2014.partyappandroid.dialogs.DatePickerCommunicator {

    EditText nameEditText;
    EditText passwordEditText;
    EditText password2EditText;
    EditText emailEditText;
    EditText dateOfBirthEditText;
    Spinner spinnerSexSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Regisztráció").setLayout();

        setContentView(R.layout.activity_register);
        nameEditText = (EditText) findViewById(R.id.register_edittext_name);
        passwordEditText = (EditText) findViewById(R.id.register_edittext_password);
        password2EditText = (EditText) findViewById(R.id.register_edittext_password2);
        emailEditText = (EditText) findViewById(R.id.register_edittext_email);
        dateOfBirthEditText = (EditText) findViewById(R.id.register_edittext_dateofbirth);
        spinnerSexSpinner = (Spinner) findViewById(R.id.register_spinner_sex);
    }

    public void onClickHandler(View v) {
        if (v.getId() == R.id.register_button_register) {
            final String userName = nameEditText.getText().toString();
            final String userPassword = passwordEditText.getText().toString();
            String userPassword2 = password2EditText.getText().toString();
            final String userEmail = emailEditText.getText().toString();
            final String userBirthday = dateOfBirthEditText.getText().toString();
            String userSex = spinnerSexSpinner.getSelectedItem().toString();

            if (userName.isEmpty()) {
                new ErrorToast(this, "Nem adtál meg nevet!").show();
                return;
            }
            if (userPassword.isEmpty()) {
                new ErrorToast(this, "Nem adtál meg jelszót!").show();
                return;
            }
            if (!userPassword2.isEmpty()) {
                if (!userPassword.equals(userPassword2)) {
                    new ErrorToast(this, "Nem egyezik meg a két jelszó!").show();
                    return;
                }
            } else {
                new ErrorToast(this, "Nem ismételted meg a jelszót!").show();
                return;
            }
            if (userEmail.isEmpty()) {
                new ErrorToast(this, "Nem adtad meg az e-mail címed!").show();
                return;
            }
            if (userBirthday.isEmpty()) {
                new ErrorToast(this, "Nem adtad meg a születési dátumod!").show();
                return;
            }
            if (userSex.isEmpty()) {
                new ErrorToast(this, "Nem nem adtad meg a nemed!").show();
                return;
            }

            if (!RegisterActivity.isEmailValid(userEmail)) {
                new ErrorToast(this, "Érvénytelen email címet adtál meg!").show();
                return;
            }

            if (userName.length() < 3) {
                new ErrorToast(this, "Túl rövid nevet adtál meg").show();
                return;
            }

            final int userSexInt = userSex.equals("Férfi") ? 0 : 1;

            Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                    "Regisztráció folyamatban...", true, false);
            new NetThread(this, new Runnable() {

                @Override
                public void run() {
                    final User newUser = Session.getInstance().getActualCommunicationInterface()
                            .registerANewUser(userName, userPassword, userEmail, userSexInt, userBirthday);
                    
                    
                    if (newUser == null) {
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            
                            @Override
                            public void run() {
                                new ErrorToast(getApplicationContext(),
                                        "Ez a felhasználó név már foglalt! Kérlek válasz másikat!")
                                        .show();
                                Session.getInstance().progressDialog.dismiss(); 
                            }
                        });
                        return;
                    }
                    
                    LoginActivity.loginSynchronize(newUser, getApplicationContext());
                    
                    RegisterActivity.this.runOnUiThread(new Runnable() {                 
                        @Override
                        public void run() {
                            
                            
                            Intent newIntent = new Intent(RegisterActivity.this, ClubsActivity.class);
                            Session.getInstance().dismissProgressDialog();
                            if (LoginActivity.instance != null) {
                                LoginActivity.instance.finish();
                            }
                            new DoneToast(RegisterActivity.this, "Sikeres regisztráció!").show();
                            startActivity(newIntent);
                            Log.e("REGISTER FORM", "CLUBSACTIVITY STARTED");
                            finish();
                        }
                    });
                }
            }).start();

        }
        if (v.getId() == R.id.register_edittext_dateofbirth) {

            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getSupportFragmentManager(), "timePicker");

        }
    }

    @Override
    public void onDatePicked(String date) {
        dateOfBirthEditText.setText(date);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
