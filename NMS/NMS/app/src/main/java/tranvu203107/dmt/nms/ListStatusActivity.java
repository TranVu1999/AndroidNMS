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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;

import tranvu203107.dmt.nms.model.Status;

public class ListStatusActivity extends AppCompatActivity {

    //Create database name and her path
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;

    FloatingActionButton btnAlertDialog_AddStatus;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ListView listView;
    ListView listViewAccount;

    ArrayList<ItemMenu> arrList;
    ArrayList<ItemMenu> arrListAccount;
    MenuAdapter menuAdapter;

    //Define item index variable
    private static final int MENU_ITEM_EDIT = 0;
    private static final int MENU_ITEM_DELETE = 1;

    //Define RecyclerView and Adapter variable
    RecyclerView StatusListRecycler;
    StatusAdapter StatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_status);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        //import myDB.sqlite to project
        processCopy();

        // map
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.listView);
        listViewAccount = (ListView) findViewById(R.id.listViewAccount);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        StatusListRecycler = findViewById(R.id.statusList);

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
        arrList.add(new ItemMenu("Status", R.drawable.ic_action_status));
        arrList.add(new ItemMenu("Status", R.drawable.ic_action_status));
        arrList.add(new ItemMenu("Note", R.drawable.ic_action_note));

        menuAdapter = new MenuAdapter(this, R.layout.item_row_menu, arrList);
        listView.setAdapter(menuAdapter);

        // action menu account
        arrListAccount = new ArrayList<ItemMenu>();
        arrListAccount.add(new ItemMenu("Edit Profile", R.drawable.ic_action_edit));
        arrListAccount.add(new ItemMenu("Change Password", R.drawable.ic_action_change));

        menuAdapter = new MenuAdapter(this, R.layout.item_row_menu, arrListAccount);
        listViewAccount.setAdapter(menuAdapter);

        // event btn dialog
        btnAlertDialog_AddStatus = (FloatingActionButton) findViewById(R.id.btn_add_status);

        btnAlertDialog_AddStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayAlertDialog(0, 0);
            }
        });

        //Get Status List for Adapter
        LoadStatusList();
    }

    //Get Status List for Adapter
    public void LoadStatusList(){
        StatusAdapter = new StatusAdapter(this, StatusList());
        StatusListRecycler.setAdapter(StatusAdapter);
        // Register the RecyclerView for Context menu
        registerForContextMenu(StatusListRecycler);
    }

    //Display Dialog
    public void displayAlertDialog(int flag, int statusId) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_edit_status_dialog, null);
        EditText txtStatus= alertLayout.findViewById(R.id.editText_statusName);

        //Đổ dữ liệu lên dialog khi sửa Status
        if(flag == 1){
            Status status = getStatusById(statusId);
            txtStatus.setText(status.getStatus());
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if(flag == 0)
        {
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String Status = txtStatus.getText().toString();
                    String CreatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
                    addStatus(new Status(0, Status, CreatedDate));
                    LoadStatusList();
                }
            });
        }
        else {
            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editStatus(new Status(statusId, txtStatus.getText().toString(), ""));
                    LoadStatusList();
                }
            });
        }
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    //Xử lí click item trên Context menu
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == MENU_ITEM_EDIT){
            displayAlertDialog(1, StatusList().get(item.getGroupId()).getId());
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            deleteStatus(StatusList().get(item.getGroupId()).getId());
        }
        else {
            return false;
        }
        return true;
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

    //Hien thi Status
    private ArrayList<Status> StatusList() {
        //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from Status",null);
        //aP.clear();
        ArrayList<Status> arrStatus = new ArrayList<Status>();
        //Toast.makeText(ListStatusActivity.this,"Da load",Toast.LENGTH_LONG).show();
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String StatusName = cursor.getString(1);
            String createdDate = cursor.getString(2);
            Status Status = new Status(id, StatusName, createdDate);
            arrStatus.add(Status);
        }
        cursor.close();
        return arrStatus;
    }

    //Thêm Status
    private void addStatus(Status Status){
        ContentValues values=new ContentValues();
        values.put("Status",Status.getStatus());
        values.put("createdDate", Status.getCreatedDate());
        database.insert("Status",null,values);
    }

    //Lấy Satus theo id
    private Status getStatusById(int id){
        Cursor cursor = database.rawQuery("select * from Status where id ='" + id + "'",null);
        cursor.moveToFirst();
        id = cursor.getInt(0);
        String StatusName = cursor.getString(1);
        String createdDate = cursor.getString(2);
        Status Status = new Status(id, StatusName, createdDate);
        return Status;
    }

    //Sửa Status
    private void editStatus(Status status){

        //database.execSQL("Update Status Set status = '" + status.getStatus() + "' Where id = '" + status.getId() + "'",null);
        ContentValues values = new ContentValues();
        values.put("Status",status.getStatus());

        // updating row
        database.update("Status", values, "id" + " = ?",
                new String[]{String.valueOf(status.getId())});
        Toast.makeText(ListStatusActivity.this, "Đã lưu",Toast.LENGTH_LONG).show();
    }
    //Xóa Status
    private  void deleteStatus(int id){
        database.delete("Status", "id" + " = ?",
                new String[] { String.valueOf(id) });
        LoadStatusList();
        Toast.makeText(ListStatusActivity.this, "Đã xóa",Toast.LENGTH_LONG).show();
    }
}