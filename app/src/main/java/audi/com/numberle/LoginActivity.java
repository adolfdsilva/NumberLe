package audi.com.numberle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import studios.codelight.smartloginlibrary.SmartCustomLoginListener;
import studios.codelight.smartloginlibrary.SmartLoginBuilder;
import studios.codelight.smartloginlibrary.SmartLoginConfig;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

/**
 * Created by Audi on 09/03/17.
 */

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SmartCustomLoginListener loginListener = new SmartCustomLoginListener() {
            @Override
            public boolean customSignin(SmartUser smartUser) {
                return true;
            }

            @Override
            public boolean customSignup(SmartUser smartUser) {
                return true;
            }

            @Override
            public boolean customUserSignout(SmartUser smartUser) {
                return true;
            }
        };
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");

        SmartLoginBuilder loginBuilder = new SmartLoginBuilder();
        Intent intent = loginBuilder.with(this)
                .setAppLogo(R.mipmap.ic_launcher)
                .isFacebookLoginEnabled(true).withFacebookAppId(getString(R.string.facebook_app_id))
                .withFacebookPermissions(permissions)
                .isGoogleLoginEnabled(true)
                .isCustomLoginEnabled(true).setSmartCustomLoginHelper(loginListener)
                .build();
        startActivityForResult(intent, SmartLoginConfig.LOGIN_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Intent "data" contains the user object
        if (resultCode == SmartLoginConfig.FACEBOOK_LOGIN_REQUEST) {
            SmartFacebookUser user;
            try {
                user = data.getParcelableExtra(SmartLoginConfig.USER);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
        } else if (resultCode == SmartLoginConfig.GOOGLE_LOGIN_REQUEST) {
            SmartGoogleUser user;
            try {
                user = data.getParcelableExtra(SmartLoginConfig.USER);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }
        } else if (resultCode == SmartLoginConfig.CUSTOM_LOGIN_REQUEST) {
            SmartUser user = data.getParcelableExtra(SmartLoginConfig.USER);
        } else if (resultCode == RESULT_CANCELED) {
            //Login Failed
        }
    }
}
