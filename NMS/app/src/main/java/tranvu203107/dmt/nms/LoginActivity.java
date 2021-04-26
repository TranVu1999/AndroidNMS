package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    Button btnLogin;
    EditText txtEmail;
    EditText txtPassword;
    LinearLayout emailContainer, passwordContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load database
        processCopy();
        // Handling login
        // addControls();

        // for changing  status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        addEvents();
    }
    // Xu ly
    private void addControls() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmailChange);
        txtPassword = (EditText)findViewById((R.id.txtPasswordLogin));
        emailContainer = (LinearLayout)findViewById(R.id.emailContainer);
        passwordContainer = (LinearLayout)findViewById(R.id.passwordContainer);

    }
    private void addEvents() {
        addControls();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        //String query = "select * from USER where Email = '" + txtEmail.getText().toString() + "' and Password = '" + txtPassword.getText().toString() +"'";
       // String query = "select * from USER where Email = 'thinh' and Password = 'thinh'";
       //Cursor cursor = null;
        //cursor   = database.rawQuery(query,null);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flag = true;
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                // Check email
                if(email.isEmpty()){
                    flag = false;
                    try {
                        TextView emailNotify = findViewById(R.id.emailNotify);
                        ((ViewGroup) emailNotify.getParent()).removeView(emailNotify);
                    }catch (Exception e){}


                    TextView txtEmailErr = new TextView(LoginActivity.this, null, 0, R.style.notifyWarning);
                    txtEmailErr.setId(R.id.emailNotify);
                    txtEmailErr.setText("You must not leave email blank");
                    emailContainer.addView(txtEmailErr);
                }

                // Check password
                if(password.isEmpty()){
                    flag = false;
                    try {
                        TextView passowrdNotify = findViewById(R.id.passowrdNotify);
                        ((ViewGroup) passowrdNotify.getParent()).removeView(passowrdNotify);
                    }catch (Exception e){}

                    TextView txtPasswordErr = new TextView(LoginActivity.this, null, 0, R.style.notifyWarning);
                    txtPasswordErr.setId(R.id.passowrdNotify);
                    txtPasswordErr.setText("You must not leave password blank");
                    passwordContainer.addView(txtPasswordErr);
                }

                // All good
                if(false){
                    String query = "select * from USER where Email = '" + txtEmail.getText().toString() + "' and Password = '" + txtPassword.getText().toString() +"'";
                    Cursor cursor = null;
                    cursor = database.rawQuery(query,null);

                    if(cursor.getCount() == 1 ) {
                        int Id;
                        String Name;
                        //USER user =new USER();
                        // get id's user.
                        cursor.moveToFirst();
                        Id = cursor.getInt(0);
                        Name = cursor.getString(1);
                        Toast.makeText(LoginActivity.this, "Successfully login!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class).putExtra("Id", Id).putExtra("Name",Name);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid incredent!", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }

            }
        });
    }
    public void onLoginClick(View v){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
    // Dataabase
    private void processCopy()
    {
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                copyDatabaseFromAsset();
                Toast.makeText(LoginActivity.this, "Sao chép thành công", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(LoginActivity.this,"Khong sao chep",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(LoginActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
            Log.e("LOI",ex.toString());
        }
    }
    private String getDatabasePath()
    {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    private void copyDatabaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
            {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte []buffer = new byte[1024];
            int length;
            while((length = myInput.read(buffer))>0)
            {
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();


        }catch (Exception ex)
        {
            Log.e("LOI",ex.toString());
        }

    }
}