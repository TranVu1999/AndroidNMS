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

import tranvu203107.dmt.nms.model.Priority;

public class ListPriorityActivity extends AppCompatActivity {

    //Create database name and her path
    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    int Id;

    FloatingActionButton btnAlertDialog_AddPriority;

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
    RecyclerView PriorityListRecycler;
    PriorityAdapter PriorityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_priority);
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
        PriorityListRecycler = findViewById(R.id.priorityList);

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

                    Intent intent = new Intent(ListPriorityActivity.this, ChangeProfileActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(ListPriorityActivity.this, ChangePasswordActivity.class).putExtra("Id", Id);

                    startActivity(intent);
                }
            }
        });

        // event btn dialog
        btnAlertDialog_AddPriority = (FloatingActionButton) findViewById(R.id.btn_add_priority);

        btnAlertDialog_AddPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayAlertDialog(0, 0);
            }
        });

        //Get Priority List for Adapter
        LoadPriorityList();
    }

    //Get Priority List for Adapter
    public void LoadPriorityList(){
        PriorityAdapter = new PriorityAdapter(this, PriorityList());
        PriorityListRecycler.setAdapter(PriorityAdapter);
        // Register the RecyclerView for Context menu
        registerForContextMenu(PriorityListRecycler);
    }

    //Display Dialog
    public void displayAlertDialog(int flag, int PriorityId) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_edit_priority_dialog, null);
        EditText txtPriority= alertLayout.findViewById(R.id.editText_priorityName);
        TextView textView_validatePriority = alertLayout.findViewById(R.id.textView_validatePriority);

        //Đổ dữ liệu lên dialog khi sửa Priority
        if(flag == 1){ //Xử lý sửa
            Priority Priority = getPriorityById(PriorityId);
            txtPriority.setText(Priority.getPriority());
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
        txtPriority.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String Priority = txtPriority.getText().toString();
                if (isPriorityAlive(Priority) == true){
                    textView_validatePriority.setText("Priority với tên này đã tồn tại!");
                }
                else if(Priority.equals(""))
                    textView_validatePriority.setText("Không được để trống");
                else
                    textView_validatePriority.setText("");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        if(flag == 0)  //Xử lý thêm
        {
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String Priority = txtPriority.getText().toString();
                    String CreatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
                    if (textView_validatePriority.getText().toString().equals("") && !Priority.equals("")){
                        addPriority(new Priority(0, Priority, CreatedDate, Id));
                        //textView_validatePriority.setText("");
                        LoadPriorityList();
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
                    String Priority = txtPriority.getText().toString();
                    if(textView_validatePriority.getText().toString().equals("") && !Priority.equals("")){
                        editPriority(new Priority(PriorityId, txtPriority.getText().toString(), "", Id));
                        LoadPriorityList();
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
            displayAlertDialog(1, PriorityList().get(item.getGroupId()).getId());
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            deletePriority(PriorityList().get(item.getGroupId()).getId());
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
                Toast.makeText(ListPriorityActivity.this, "Sao chép thành công", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(ListPriorityActivity.this,"Khong sao chep",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(ListPriorityActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
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

    //Hien thi Priority
    private ArrayList<Priority> PriorityList() {
        //database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from Priority where UserId ='" + Id + "' and IsDeleted = 0",null);
        //aP.clear();
        ArrayList<Priority> arrPriority = new ArrayList<Priority>();
        //Toast.makeText(ListPriorityActivity.this,"Da load",Toast.LENGTH_LONG).show();
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String PriorityName = cursor.getString(1);
            String createdDate = cursor.getString(2);
            Priority Priority = new Priority(id, PriorityName, createdDate, Id);
            arrPriority.add(Priority);
        }
        cursor.close();
        return arrPriority;
    }

    //Thêm Priority
    private void addPriority(Priority Priority){
        ContentValues values=new ContentValues();
        values.put("Priority",Priority.getPriority());
        values.put("createdDate", Priority.getCreatedDate());
        values.put("UserId",Priority.getUserId());
        database.insert("Priority",null,values);
    }

    //Lấy Priority theo id
    private Priority getPriorityById(int id){
        Cursor cursor = database.rawQuery("select * from Priority where id ='" + id + "'",null);
        cursor.moveToFirst();
        id = cursor.getInt(0);
        String PriorityName = cursor.getString(1);
        String createdDate = cursor.getString(2);
        Priority Priority = new Priority(id, PriorityName, createdDate, Id);
        return Priority;
    }

    //Sửa Priority
    private void editPriority(Priority Priority){

        //database.execSQL("Update Priority Set Priority = '" + Priority.getPriority() + "' Where id = '" + Priority.getId() + "'",null);
        ContentValues values = new ContentValues();
        values.put("Priority",Priority.getPriority());

        // updating row
        database.update("Priority", values, "id" + " = ?",
                new String[]{String.valueOf(Priority.getId())});
        Toast.makeText(ListPriorityActivity.this, "Đã lưu",Toast.LENGTH_LONG).show();
    }

    //Xóa Priority
    private  void deletePriority(int id){
        ContentValues values = new ContentValues();
        values.put("IsDeleted",1);

        // updating row
        database.update("Priority", values, "id" + " = ?",
                new String[]{String.valueOf(id)});
        LoadPriorityList();
        Toast.makeText(ListPriorityActivity.this, "Đã xóa",Toast.LENGTH_LONG).show();

//        database.delete("Priority", "id" + " = ?",
//                new String[] { String.valueOf(id) });
//        LoadPriorityList();
//        Toast.makeText(ListPriorityActivity.this, "Đã xóa",Toast.LENGTH_LONG).show();
    }

    //Kiểm tra Priority đã tồn tại hay chưa
    private boolean isPriorityAlive(String s){
        String query = "select * from Priority where Priority = '" + s +"' and IsDeleted = 0";
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

