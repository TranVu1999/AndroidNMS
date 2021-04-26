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

public class ChangeProfileActivity extends AppCompatActivity {
    int Id;
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    EditText txtName;
    EditText txtEmail;
    Button btnChange;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        Id = getIntent().getIntExtra("Id", 0);
        addEvents();
    }
    // Xu ly
    private void addControls() {
        btnChange = (Button) findViewById(R.id.btnSaveChangePassword);
        txtEmail = (EditText) findViewById(R.id.txtConfirmNewPass);
        txtName = (EditText)findViewById((R.id.txtOldPassword));
    }
    private void addEvents() {
        addControls();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String query = "select * from USER where Id = " + Id ;
        Cursor cursor   = database.rawQuery(query,null);
        cursor.moveToFirst();
        txtName.setText(cursor.getString(1)) ;
        txtEmail.setText(cursor.getString(2));
        btnChange.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
              // database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

               ContentValues values = new ContentValues();
               values.put("name",txtName.getText().toString());
               values.put("email",txtEmail.getText().toString());
                if(txtName.getText().toString().isEmpty()||txtEmail.getText().toString().isEmpty()){
                    Toast.makeText(ChangeProfileActivity.this, "All field must be fill!", Toast.LENGTH_LONG).show();
                }
                else {
                    int kq = database.update("USER",values,"Id=?", new String[]{String.valueOf(Id)});
                    if(kq>0)
                        Toast.makeText(ChangeProfileActivity.this,"Successfully Edit!",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ChangeProfileActivity.this,"Fail Edit!",Toast.LENGTH_LONG).show();
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

