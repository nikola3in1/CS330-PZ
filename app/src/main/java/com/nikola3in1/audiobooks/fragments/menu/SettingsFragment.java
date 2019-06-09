package com.nikola3in1.audiobooks.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.model.UserData;
import com.nikola3in1.audiobooks.model.UserPreferences;

public class SettingsFragment extends Fragment {

    private UserPreferences userPreferences;
    private Context ctx;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_settings, null, false);
        ctx = getActivity().getApplicationContext();
        userPreferences = UserData.getUserPreferences();
        // If there is no UserPreferences, init UserPreferences
        if (userPreferences == null) {
            userPreferences = new UserPreferences();
            UserData.setUserPreferences(ctx,userPreferences);
        }
        radioGroup = contentView.findViewById(R.id.settings_radio_btn_group);
        initSettings();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.settings_skip_5:
                    userPreferences.setMediaSkipValue(5 * 1000);
                    break;
                case R.id.settings_skip_10:
                    userPreferences.setMediaSkipValue(10 * 1000);
                    break;
                case R.id.settings_skip_20:
                    userPreferences.setMediaSkipValue(20 * 1000);
                    break;
                case R.id.settings_skip_30:
                    userPreferences.setMediaSkipValue(30 * 1000);
                    break;
            }
            System.out.println("SKIP VALUE IS: " + userPreferences.getMediaSkipValue());
            saveUserPreferences();
        });

        return contentView;
    }

    private void initSettings() {

        if (userPreferences != null) {
            Integer skipMediaValue = userPreferences.getMediaSkipValue();
            switch (skipMediaValue) {
                case 5000:
                    radioGroup.check(R.id.settings_skip_5);
                    break;
                case 10000:
                    radioGroup.check(R.id.settings_skip_10);
                    break;
                case 20000:
                    radioGroup.check(R.id.settings_skip_20);
                    break;
                case 30000:
                    radioGroup.check(R.id.settings_skip_30);
                    break;
            }
        }
    }

    private void saveUserPreferences() {
        UserData.setUserPreferences(ctx, userPreferences);
        Toast.makeText(ctx, "In order for changes to take effect, please restart the app.", Toast.LENGTH_LONG).show();
    }
}
