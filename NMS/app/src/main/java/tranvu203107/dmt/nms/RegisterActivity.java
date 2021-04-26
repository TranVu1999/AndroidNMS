package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    String DATABASE_NAME="myDB.sqlite";
    SQLiteDatabase database = null;
    Button btnRegister;
    EditText txtEmail;
    EditText txtName;
    EditText txtPassword;
    EditText txtConfirmPassword;
    TextInputLayout emailContainer, passwordContainer, confirmPasswordContainer, nameContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        addEvents();
    }

    public void changeStatusBarColor(){
        // for changing  status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

        }
    }
    // Xu ly login
    private void addControls() {
        btnRegister = (Button) findViewById(R.id.btnChange);
        txtEmail = (EditText) findViewById(R.id.txtEmailChange);
        txtName = (EditText) findViewById(R.id.txtNameChange) ;
        txtPassword = (EditText)findViewById((R.id.txtPasswordRegister));
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        // container
        emailContainer =  (TextInputLayout) findViewById(R.id.emailContainer);
        passwordContainer =  (TextInputLayout) findViewById(R.id.passwordContainer);
        confirmPasswordContainer =  (TextInputLayout) findViewById(R.id.comfirmPasswordContainer);
        nameContainer =  (TextInputLayout) findViewById(R.id.nameContainer);
    }

    private void addEvents() {
        addControls();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addControls();

                Boolean flag = true;
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String name = txtName.getText().toString();
                String confirmPassword = txtConfirmPassword.getText().toString();

                // Check name
                if(name.isEmpty()){
                    flag = false;
                    try {
                        TextView nameNotify = findViewById(R.id.nameNotify);
                        ((ViewGroup) nameNotify.getParent()).removeView(nameNotify);
                    }catch (Exception e){}


                    TextView txtNameErr = new TextView(RegisterActivity.this, null, 0, R.style.notifyWarning);
                    txtNameErr.setId(R.id.nameNotify);
                    txtNameErr.setText("You must not leave name blank");
                    nameContainer.addView(txtNameErr);
                }

                // Check email
                if(email.isEmpty()){
                    flag = false;
                    try {
                        TextView emailNotify = findViewById(R.id.emailNotify);
                        ((ViewGroup) emailNotify.getParent()).removeView(emailNotify);
                    }catch (Exception e){}


                    TextView txtEmailErr = new TextView(RegisterActivity.this, null, 0, R.style.notifyWarning);
                    txtEmailErr.setId(R.id.emailNotify);
                    txtEmailErr.setText("You must not leave email blank");
                    emailContainer.addView(txtEmailErr);
                }

                // Check password
                if(password.isEmpty()){
                    flag = false;
                    try {
                        TextView passwordNotify = findViewById(R.id.passowrdNotify);
                        ((ViewGroup) passwordNotify.getParent()).removeView(passwordNotify);
                    }catch (Exception e){}


                    TextView txtPasswordErr = new TextView(RegisterActivity.this, null, 0, R.style.notifyWarning);
                    txtPasswordErr.setId(R.id.passowrdNotify);
                    txtPasswordErr.setText("You must not leave password blank");
                    passwordContainer.addView(txtPasswordErr);
                }

                // Check confirm password
                if(confirmPassword.isEmpty()){
                    flag = false;
                    try {
                        TextView confrimPasswordNotify = findViewById(R.id.confirmPassowrdNotify);
                        ((ViewGroup) confrimPasswordNotify.getParent()).removeView(confrimPasswordNotify);
                    }catch (Exception e){}


                    TextView txtConfirmPasswordErr = new TextView(RegisterActivity.this, null, 0, R.style.notifyWarning);
                    txtConfirmPasswordErr.setId(R.id.confirmPassowrdNotify);
                    txtConfirmPasswordErr.setText("You must not leave confirm password blank");
                    confirmPasswordContainer.addView(txtConfirmPasswordErr);
                }


                if(flag){
                    database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
                    ContentValues values = new ContentValues();
                    values.put("name",txtName.getText().toString());
                    values.put("email",txtEmail.getText().toString());
                    values.put("password",txtPassword.getText().toString());

                    long kq = database.insert("USER",null, values);
                    database.close();
                    if(kq>0) {
                        Toast.makeText(RegisterActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    }
                    else
                        Toast.makeText(RegisterActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
                }}
        });
    }
    public void onLoginClick(View v){
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }
}