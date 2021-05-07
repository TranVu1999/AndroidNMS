package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static tranvu203107.dmt.nms.CategoryActivity.cateChosed;

public class ListNoteActivity extends AppCompatActivity {

    int Id;
    TextView txtNameUser, listNoteTitle, listNoteNotify;
    ImageView listNoteBanner;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ListView listView;
    ListView listViewAccount;
    EditText textAddNote;
    EditText editNoteName;
    Button btnCloseDialogs;

    ArrayList<ItemMenu> arrList;
    ArrayList<ItemMenu> arrListAccount;
    MenuAdapter menuAdapter;

    ArrayList<Note> arrNote;
    RecyclerView noteListRecycler;
    NoteAdapter noteAdapter;

    FloatingActionButton btnAddNote;
    Dialog dialog;
    Dialog planDateDialog;

    Spinner spinnerCate, spinnerPriority, spinnerStatus;

    public static String statusName;
    public static String cateName;
    public static String priorityName;
    public static String planDate;
    public static int cateIndex;
    public static int priIndex;
    public static int stIndex;

    public static String DATABASE_NAME="myDB.sqlite";
    public static String DB_PATH_SUFFIX="/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        addControls();
        processCopy();

        txtNameUser = (TextView) findViewById(R.id.txtNameUser);
        Id = getIntent().getIntExtra("Id", 2);
        txtNameUser.setText("Hi, "+ getName() + " !");

        //truyen id
        txtNameUser = (TextView) findViewById(R.id.txtNameUser);
        Id = getIntent().getIntExtra("Id", 2);
        txtNameUser.setText("Hi, "+ getName() + " !");

        // map
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.listView);
        listViewAccount = (ListView) findViewById(R.id.listViewAccount);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        noteListRecycler = findViewById(R.id.noteList);
        listNoteBanner = findViewById(R.id.listNoteBanner);
        listNoteTitle = findViewById(R.id.listNoteTitle);
        listNoteNotify = findViewById(R.id.listNoteNotify);

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(ListNoteActivity.this, HomeActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(ListNoteActivity.this, CategoryActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 2) {
                    Intent intent = new Intent(ListNoteActivity.this, ListPriorityActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 3) {
                    Intent intent = new Intent(ListNoteActivity.this, ListStatusActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 4) {
                    Intent intent = new Intent(ListNoteActivity.this, ListNoteActivity.class).putExtra("Id", Id);
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

                    Intent intent = new Intent(ListNoteActivity.this, ChangeProfileActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(ListNoteActivity.this, ChangePasswordActivity.class).putExtra("Id", Id);

                    startActivity(intent);
                }
            }
        });

        showListNote();

    }

    private void addControls() {
        btnAddNote = (FloatingActionButton) findViewById(R.id.btn_add_note);

        dialog = new Dialog(this);
        planDateDialog = new Dialog(this);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });
    }
    private void openAddNoteDialog() {
        dialog.setContentView(R.layout.layout_add_note);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        editNoteName=(EditText) dialog.findViewById(R.id.editNoteName);
        btnCloseDialogs=(Button)dialog.findViewById(R.id.btnCloseDialog);
        Button btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);
        Button btnSaveData = dialog.findViewById(R.id.btnSaveDataAdd);
        Button btnSelectPlanDate = dialog.findViewById(R.id.btnSelectPlanDate);
        btnSelectPlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectDateDialog();
            }
        });

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);

        OptionAdapter optionsAdapter;
        spinnerCate = dialog.findViewById(R.id.spinnerCate);
        spinnerPriority = dialog.findViewById(R.id.spinnerPrioroty);
        spinnerStatus = dialog.findViewById(R.id.spinnerStatus);
        ArrayList<Option> arrOptionCate;
        arrOptionCate = new ArrayList<Option>();
        // action cate
        Cursor cursor = database.rawQuery("SELECT Id,NAME\n" +
                "FROM CATEGORY",null);
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String cateName = cursor.getString(1);
            arrOptionCate.add(new Option(cateName,id+""));

        }
        cursor.close();

        ArrayList<Option> arrOptionPriority;
        // action Priority
        arrOptionPriority = new ArrayList<Option>();

        Cursor cursorPriority = database.rawQuery("SELECT Id,Priority\n" +
                "FROM PRIORITY",null);
        while(cursorPriority.moveToNext())
        {
            int id=cursorPriority.getInt(0);
            String priorityName = cursorPriority.getString(1);
            arrOptionPriority.add(new Option(priorityName,id+""));

        }
        cursorPriority.close();


        ArrayList<Option> arrOptionStatus;
        // action Status
        arrOptionStatus = new ArrayList<Option>();
        Cursor cursorStatus = database.rawQuery("SELECT Id,Status \n" +
                "FROM STATUS",null);
        while(cursorStatus.moveToNext())
        {
            int id=cursorStatus.getInt(0);
            String statusName = cursorStatus.getString(1);
            arrOptionStatus.add(new Option(statusName,id+""));

        }
        cursorPriority.close();

        optionsAdapter = new OptionAdapter(this, R.layout.layout_option_item, arrOptionCate);
        spinnerCate.setAdapter(optionsAdapter);

        optionsAdapter = new OptionAdapter(this, R.layout.layout_option_item, arrOptionPriority);
        spinnerPriority.setAdapter(optionsAdapter);

        optionsAdapter = new OptionAdapter(this, R.layout.layout_option_item, arrOptionStatus);
        spinnerStatus.setAdapter(optionsAdapter);

        spinnerCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                cateIndex=position+1;
                cateName= arrOptionCate.get(position).title.toString();
                Toast.makeText(getApplicationContext(), "You pick category "+ cateName , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                priIndex=position+1;
                priorityName = arrOptionPriority.get(position).title.toString();
                Toast.makeText(getApplicationContext(), "You pick priority " + priorityName, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                stIndex=position+1;
                statusName = arrOptionStatus.get(position).title.toString();
                Toast.makeText(getApplicationContext(), "You pick status " + statusName, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        dialog.show();
    }
    private void openSelectDateDialog(){
        //mở layout chọn ngày lên
        planDateDialog.setContentView(R.layout.layout_select_plandate);
        planDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Button btnOk, btnCancel;
        btnOk = planDateDialog.findViewById(R.id.btnOk);
        btnCancel = planDateDialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planDateDialog.dismiss();
            }
        });

        CalendarView calendarView;
        calendarView = planDateDialog.findViewById(R.id.cldPlanDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String  curDate = String.valueOf(dayOfMonth);
                String  Year = String.valueOf(year);
                String  Month = String.valueOf(month);

                planDate = curDate+'/'+Month+'/'+Year;
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView lblPlanDate = dialog.findViewById(R.id.lblPlanDate);
                lblPlanDate.setText(planDate);
                planDateDialog.dismiss();
            }
        });

        planDateDialog.show();
    }

    private void showStatistical(String strNotify){
        listNoteTitle.setText(cateChosed);
        listNoteNotify.setText(strNotify);


        if(cateChosed == "Exercise"){
            listNoteBanner.setImageResource(R.drawable.ic_action_exercise);
        }else if(cateChosed == "Homework"){
            listNoteBanner.setImageResource(R.drawable.ic_action_homework);
        }else if(cateChosed == "Meeting"){
            listNoteBanner.setImageResource(R.drawable.ic_action_meeting);
        }else if(cateChosed == "Entertainment"){
            listNoteBanner.setImageResource(R.drawable.ic_action_entertainment);
        }else if(cateChosed == "My Job"){
            listNoteBanner.setImageResource(R.drawable.ic_action_job);

        }
    }

    private void showListNote() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        arrNote = new ArrayList<Note>();
        Cursor cursor = database.rawQuery("SELECT USER.Id, Status.status,CATEGORY.name,NOTE.Name,PRIORITY.Priority,NOTE.PlanDate,NOTE.CreatedDate\n" +
                "FROM NOTE\n" +
                "INNER JOIN CATEGORY ON NOTE.CateId =CATEGORY.Id\n" +
                "INNER JOIN Priority ON NOTE.PriorityId = Priority.Id\n" +
                "INNER JOIN Status ON NOTE.StatusId=Status.id\n" +
                "INNER JOIN USER ON NOTE.UserId = USER.Id\n" +
                "WHERE CATEGORY.Name = '"+cateChosed+"' AND USER.Id = '" + Id + "'", null);
        //adapter.clear();
        //Toast.makeText(MainActivity.this,"Da vao",Toast.LENGTH_LONG).show();
        while (cursor.moveToNext()) {
            String status = cursor.getString(1);
            String cateName = cursor.getString(2);
            String noteName = cursor.getString(3);
            String priorityName = cursor.getString(4);
            String planDate = cursor.getString(5);
            String createdDate = cursor.getString(6);
            arrNote.add(new Note(status, cateName, noteName, priorityName, planDate, createdDate));
        }
        cursor.close();
        noteAdapter = new NoteAdapter(getApplicationContext(), arrNote);
        noteListRecycler.setAdapter(noteAdapter);


        String strNotify = "You have 2 missions that\nneed to be completed";

        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String now = simpleDateFormat.format(new Date());
        String date = simpleDateFormat.format(new Date("2021/01/10"));

        Toast.makeText(ListNoteActivity.this, date.toString(), Toast.LENGTH_LONG).show();

        showStatistical(strNotify);
    }

    private void processCopy()
    {
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                copyDatabaseFromAsset();
                Toast.makeText(ListNoteActivity.this, "Sao chép thành công", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(ListNoteActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
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
    public void SaveDataAdd(View view) {
        String noteName =editNoteName.getText().toString();
        ContentValues newValues = new ContentValues();
        // newValues.put("Id",8);
        newValues.put("Name",noteName);
        newValues.put("UserId",Id);
        newValues.put("CateId",cateIndex);
        newValues.put("PriorityId",priIndex);
        newValues.put("StatusId",stIndex);
        newValues.put("PlanDate",planDate);
        newValues.put("CreatedDate",java.time.LocalDate.now().toString());

        long kq =ListNoteActivity.database.insert("NOTE",null,newValues);
        if(kq>0) {
            Toast.makeText(ListNoteActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            showListNote();
        }
        else
            Toast.makeText(ListNoteActivity.this,"Thêm thất bại",Toast.LENGTH_LONG).show();
    }
    public void eCloseAddNote(View view)
    {
        dialog.cancel();
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showListNote();
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