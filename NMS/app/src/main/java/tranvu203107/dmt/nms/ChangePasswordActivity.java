package tranvu203107.dmt.nms;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    int Id;
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    EditText txtOldPass;
    EditText txtNewPassword, txtConfirmNewPass;
    Button btnChange;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Id = getIntent().getIntExtra("Id", 0);
        addEvents();
    }
    // Xu ly
    private void addControls() {
        btnChange = (Button) findViewById(R.id.btnSaveChangePassword);
        txtOldPass = (EditText) findViewById(R.id.txtOldPassword);
        txtNewPassword = (EditText)findViewById((R.id.txtNewPassword));
        txtConfirmNewPass = (EditText)findViewById((R.id.txtConfirmNewPass));
    }
    private void addEvents() {
        addControls();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String query = "select * from USER where Id = " + Id ;
        Cursor cursor   = database.rawQuery(query,null);
        cursor.moveToFirst();
       // txtName.setText(cursor.getString(1)) ;
        //txtEmail.setText(cursor.getString(2));
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
                ContentValues values = new ContentValues();
                values.put("password",txtNewPassword.getText().toString());
                if(txtOldPass.getText().toString().isEmpty()||txtNewPassword.getText().toString().isEmpty()||txtConfirmNewPass.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "All field must be fill!", Toast.LENGTH_LONG).show();
                }
                else if(txtNewPassword.getText().toString().equals(txtConfirmNewPass.getText().toString())== false){
                    Toast.makeText(ChangePasswordActivity.this, "NewPassword and ConfirmNewPassword must match!", Toast.LENGTH_LONG).show();
                }
                else {
                    if(txtOldPass.getText().toString().equals(cursor.getString(3)) == true){
                        int kq = database.update("USER",values,"Id=?", new String[]{String.valueOf(Id)});
                        if(kq>0)
                            Toast.makeText(ChangePasswordActivity.this,"Password has changed!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ChangePasswordActivity.this,"Fail to change password!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "Incorrect old password!", Toast.LENGTH_LONG).show();
                    }

                    // Intent intent = new Intent(ChangeProfileActivity.this,HomeActivity.class).putExtra("Id", Id);
                    //startActivity(intent);
                }
                cursor.close();
            }
        });
    }
    public void onHomeClick(View v) {
        startActivity(new Intent(this, HomeActivity.class).putExtra("Id", Id));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }
}

