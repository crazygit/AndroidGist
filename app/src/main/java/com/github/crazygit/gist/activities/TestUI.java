package com.github.crazygit.gist.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityTestUIBinding;
import com.github.crazygit.gist.helper.SharedPreferenceEntry;
import com.github.crazygit.gist.helper.SharedPreferencesHelper;
import com.github.crazygit.gist.utils.EmailValidator;

import java.util.Calendar;

// 参考
// https://medium.com/mindorks/learn-unit-testing-in-android-by-building-a-sample-application-23ec2f6340e8
//
// 依赖
//    testImplementation 'junit:junit:4.12'
//    testImplementation "org.mockito:mockito-core:2.28.2"
public class TestUI extends BaseActivity<ActivityTestUIBinding> {
    // Logger for this class.
    private static final String TAG = "MainActivity";
    // The helper that manages writing to SharedPreferences.
    private SharedPreferencesHelper mSharedPreferencesHelper;
    // The validator for the email input field.
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityTestUIBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        mEmailValidator = new EmailValidator();
        dataBinding.emailInput.addTextChangedListener(mEmailValidator);
        // Instantiate a SharedPreferencesHelper.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        // Fill input fields from data retrieved from the SharedPreferences.
        populateUi();
    }

    /**
     * Initialize all fields from the personal info saved in the SharedPreferences.
     */
    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        dataBinding.userNameInput.setText(sharedPreferenceEntry.getName());
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();
        dataBinding.dateOfBirthInput.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
                dateOfBirth.get(Calendar.DAY_OF_MONTH), null);
        dataBinding.emailInput.setText(sharedPreferenceEntry.getEmail());
    }

    public void onRevertClick(View view) {
        populateUi();
        Toast.makeText(this, "Personal information reverted", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Personal information reverted");
    }

    public void onSaveClick(View view) {
        // Don't save if the fields do not validate.
        // Don't save if the fields do not validate.
        if (!mEmailValidator.isValid()) {
            dataBinding.emailInput.setError("Invalid email");
            Log.w(TAG, "Not saving personal information: Invalid email");
            return;
        }
        // Get the text from the input fields.
        String name = dataBinding.userNameInput.getText().toString();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(dataBinding.dateOfBirthInput.getYear(), dataBinding.dateOfBirthInput.getMonth(), dataBinding.dateOfBirthInput.getDayOfMonth());
        String email = dataBinding.emailInput.getText().toString();
        // Create a Setting model class to persist.
        SharedPreferenceEntry sharedPreferenceEntry =
                new SharedPreferenceEntry(name, dateOfBirth, email);
        // Persist the personal information.
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if (isSuccess) {
            Toast.makeText(this, "Personal information saved", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Personal information saved");
        } else {
            Log.e(TAG, "Failed to write personal information to SharedPreferences");
        }
    }


}
