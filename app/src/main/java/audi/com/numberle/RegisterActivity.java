package audi.com.numberle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Audi on 21/03/17.
 */

public class RegisterActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private TextView tvEmail, tvPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        findViewById(R.id.bRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWithFireBase();
            }
        });
        }

    private void registerWithFireBase() {
        mAuth.createUserWithEmailAndPassword(tvEmail.getText().toString(), tvPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(findViewById(R.id.llParent), task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    }
                });
    }

    private void init() {
        TextView tvName = (TextView) findViewById(R.id.tvNickName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPassword = (TextView) findViewById(R.id.tvPassword);

        mAuth = FirebaseAuth.getInstance();
    }
}
