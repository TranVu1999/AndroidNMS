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
import android.text.Editable;
import android.text.TextWatcher;
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
    int Id;

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
        Id = getIntent().getIntExtra("Id", 2);


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

                    Intent intent = new Intent(ListStatusActivity.this, ChangeProfileActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(ListStatusActivity.this, ChangePasswordActivity.class).putExtra("Id", Id);

                    startActivity(intent);
                }
            }
        });

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
    public void displayAlertDialog(int flag, int StatusId) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_edit_status_dialog, null);
        EditText txtStatus= alertLayout.findViewById(R.id.editText_statusName);
        TextView textView_validateStatus = alertLayout.findViewById(R.id.textView_validateStatus);

        //Đổ dữ liệu lên dialog khi sửa Status
        if(flag == 1){ //Xử lý sửa
            Status Status = getStatusById(StatusId);
            txtStatus.setText(Status.getStatus());
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

        //Xác thực đầu vào
        txtStatus.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String Status = txtStatus.getText().toString();
                if (isStatusAlive(Status) == true){
                    textView_validateStatus.setText("Status với tên này đã tồn tại!");
                }
                else if(Status.equals(""))
                    textView_validateStatus.setText("Không được để trống");
                else
                    textView_validateStatus.setText("");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        if(flag == 0)  //Xử lý thêm
        {
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String Status = txtStatus.getText().toString();
                    String CreatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
                    if (textView_validateStatus.getText().toString().equals("") && !Status.equals("")){
                        addStatus(new Status(0, Status, CreatedDate, Id));
                        //textView_validateStatus.setText("");
                        LoadStatusList();
                        Toast.makeText(getBaseContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String Status = txtStatus.getText().toString();
                    if(textView_validateStatus.getText().toString().equals("") && !Status.equals("")){
                        editStatus(new Status(StatusId, txtStatus.getText().toString(), "", Id));
                        LoadStatusList();
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
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
        Cursor cursor = database.rawQuery("select * from Status where UserId ='" + Id + "' and IsDeleted = 0",null);
        //aP.clear();
        ArrayList<Status> arrStatus = new ArrayList<Status>();
        //Toast.makeText(ListStatusActivity.this,"Da load",Toast.LENGTH_LONG).show();
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String StatusName = cursor.getString(1);
            String createdDate = cursor.getString(2);
            Status Status = new Status(id, StatusName, createdDate, Id);
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
        values.put("UserId",Status.getUserId());
        database.insert("Status",null,values);
    }

    //Lấy Status theo id
    private Status getStatusById(int id){
        Cursor cursor = database.rawQuery("select * from Status where id ='" + id + "'",null);
        cursor.moveToFirst();
        id = cursor.getInt(0);
        String StatusName = cursor.getString(1);
        String createdDate = cursor.getString(2);
        Status Status = new Status(id, StatusName, createdDate, Id);
        return Status;
    }

    //Sửa Status
    private void editStatus(Status Status){

        //database.execSQL("Update Status Set Status = '" + Status.getStatus() + "' Where id = '" + Status.getId() + "'",null);
        ContentValues values = new ContentValues();
        values.put("Status",Status.getStatus());

        // updating row
        database.update("Status", values, "id" + " = ?",
                new String[]{String.valueOf(Status.getId())});
        Toast.makeText(ListStatusActivity.this, "Đã lưu",Toast.LENGTH_LONG).show();
    }

    //Xóa Status
    private  void deleteStatus(int id){
        ContentValues values = new ContentValues();
        values.put("IsDeleted",1);

        // updating row
        database.update("Status", values, "id" + " = ?",
                new String[]{String.valueOf(id)});
        LoadStatusList();
        Toast.makeText(ListStatusActivity.this, "Đã xóa",Toast.LENGTH_LONG).show();

//        database.delete("Status", "id" + " = ?",
//                new String[] { String.valueOf(id) });
//        LoadStatusList();
//        Toast.makeText(ListStatusActivity.this, "Đã xóa",Toast.LENGTH_LONG).show();
    }

    //Kiểm tra Status đã tồn tại hay chưa
    private boolean isStatusAlive(String s){
        String query = "select * from Status where Status = '" + s +"' and IsDeleted = 0";
        Cursor cursor   = database.rawQuery(query,null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            String string = cursor.getString(1) ;
            cursor.close();
            return true;
        }
        return false;
    }
}

