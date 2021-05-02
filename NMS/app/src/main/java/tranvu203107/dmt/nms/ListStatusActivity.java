package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tranvu203107.dmt.nms.model.Status;

public class ListStatusActivity extends AppCompatActivity {

    //Create database name and her path
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    int Id;
    TextView txtNameUser;

    FloatingActionButton btnAlertDialog_AddStatus;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ListView listView;
    ListView listViewAccount;

    ArrayList<ItemMenu> arrList;
    ArrayList<ItemMenu> arrListAccount;
    MenuAdapter menuAdapter;

    //Define RecyclerView and Adapter variable
    RecyclerView statusListRecycler;
    StatusAdapter statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_status);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        txtNameUser = (TextView) findViewById(R.id.txtNameUser);
        Id = getIntent().getIntExtra("Id", 2);
        txtNameUser.setText("Hi, "+ getName() + " !");

        //import myDB.sqlite to project
        processCopy();

        // map
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.listView);
        listViewAccount = (ListView) findViewById(R.id.listViewAccount);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        statusListRecycler = findViewById(R.id.statusList);

        // Config toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawerLayout.openDrawer(GravityCompat.START );
            }
        });

        // action menu
        arrList = new ArrayList<ItemMenu>();
        arrList.add(new ItemMenu("Home", R.drawable.ic_action_home));
        arrList.add(new ItemMenu("Category", R.drawable.ic_action_category));
        arrList.add(new ItemMenu("Priority", R.drawable.ic_action_priority));
        arrList.add(new ItemMenu("Status", R.drawable.ic_action_status));
        arrList.add(new ItemMenu("Note", R.drawable.ic_action_note));

        menuAdapter = new MenuAdapter(this, R.layout.item_row_menu, arrList);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 2) {
                    Intent intent = new Intent(getApplicationContext(), ListPriorityActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 3) {
                    Intent intent = new Intent(getApplicationContext(), ListStatusActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 4) {
                    Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
            }
        });


        // action menu account
        arrListAccount = new ArrayList<ItemMenu>();
        arrListAccount.add(new ItemMenu("Edit Profile", R.drawable.ic_action_edit));
        arrListAccount.add(new ItemMenu("Change Password", R.drawable.ic_action_change));

        menuAdapter = new MenuAdapter(this, R.layout.item_row_menu, arrListAccount);
        listViewAccount.setAdapter(menuAdapter);
        listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
            }
        });

        // event btn dialog
        btnAlertDialog_AddStatus = (FloatingActionButton) findViewById(R.id.btn_add_status);

        btnAlertDialog_AddStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayAlertDialog();
            }
        });

        //Get Status List for Adapter
        LoadStatusList();
    }

    //Get Status List for Adapter
    public void LoadStatusList(){
        statusAdapter = new StatusAdapter(this, statusList());

        statusListRecycler.setAdapter(statusAdapter);
    }
    //Display Dialog
    public void displayAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_edit_status_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
                //Toast.makeText(getBaseContext(), "Add clicked", Toast.LENGTH_SHORT).show();
                EditText txtStatus= alertLayout.findViewById(R.id.editText_statusName);
                String status = txtStatus.getText().toString();
                String CreatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
                addStatus(new Status(0, status, CreatedDate));
                LoadStatusList();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    //Import sqlite database to project
    private void processCopy()
    {
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                copyDatabaseFromAsset();
                Toast.makeText(ListStatusActivity.this, "Sao chép thành công", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(ListStatusActivity.this,"Khong sao chep",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(ListStatusActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
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

    //Hien thi status
    private ArrayList<Status> statusList() {
        //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from Status",null);
        //aP.clear();
        ArrayList<Status> arrStatus = new ArrayList<Status>();
        Toast.makeText(ListStatusActivity.this,"Da load",Toast.LENGTH_LONG).show();
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String statusName = cursor.getString(1);
            String createdDate = cursor.getString(2);
            Status status = new Status(id, statusName, createdDate);
            arrStatus.add(status);
        }
        cursor.close();
        return arrStatus;
    }

    //Thêm Status
    private void addStatus(Status status){
        ContentValues values=new ContentValues();
        values.put("status",status.getStatus());
        values.put("createdDate", status.getCreatedDate());
        database.insert("Status",null,values);
    }

    private String getName(){
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String query = "select * from USER where Id = " + Id ;
        Cursor cursor   = database.rawQuery(query,null);
        cursor.moveToFirst();
        String string = cursor.getString(1) ;
        cursor.close();
        return string;
    }
}