package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryActivity extends AppCompatActivity {

    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    int Id;
    TextView txtNameUser, listNoteTitle, listNoteNotify;
    ImageView listNoteBanner;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ListView listView;
    ListView listViewAccount;
    CardView cvExcercise,cvHomeWork,cvMeeting,cvEntertainment,cvMyJob;
    TextView txtExcercise,txtHomeWork,txtMeeting,txtEntertainment,txtMyJob;
    ArrayList<ItemMenu> arrList;
    ArrayList<ItemMenu> arrListAccount;
    MenuAdapter menuAdapter;

    String cateChosed = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addControls();
        addEvents();

        txtNameUser = (TextView) findViewById(R.id.txtNameUser);
        Id = getIntent().getIntExtra("Id", 2);
        txtNameUser.setText("Hi, "+ getName() + " !");

        // map
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.listView);
        listViewAccount = (ListView) findViewById(R.id.listViewAccount);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

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
                    Intent intent = new Intent(CategoryActivity.this, HomeActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(CategoryActivity.this, CategoryActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 2) {
                    Intent intent = new Intent(CategoryActivity.this, ListPriorityActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 3) {
                    Intent intent = new Intent(CategoryActivity.this, ListStatusActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 4) {
                    Intent intent = new Intent(CategoryActivity.this, ListNoteActivity.class).putExtra("Id", Id);
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

                    Intent intent = new Intent(CategoryActivity.this, ChangeProfileActivity.class).putExtra("Id", Id);
                    startActivity(intent);
                }
                if(position == 1) {
                    Intent intent = new Intent(CategoryActivity.this, ChangePasswordActivity.class).putExtra("Id", Id);

                    startActivity(intent);
                }
            }
        });


        showStatistical();
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

    private void addEvents() {
        cvExcercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Exercise";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("Id", Id).putExtra("cateChosed", cateChosed);
                startActivity(intent);
            }
        });
        cvHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Homework";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("Id", Id).putExtra("cateChosed", cateChosed);
                startActivity(intent);
            }
        });
        cvMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Meeting";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("Id", Id).putExtra("cateChosed", cateChosed);
                startActivity(intent);
            }
        });
        cvEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Entertainment";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("Id", Id).putExtra("cateChosed", cateChosed);
                startActivity(intent);
            }
        });
        cvMyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="My Job";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("Id", Id).putExtra("cateChosed", cateChosed);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        cvExcercise = (CardView) findViewById(R.id.cvExercise);
        cvHomeWork= (CardView)findViewById(R.id.cvHomeWork);
        cvMeeting = (CardView)findViewById(R.id.cvMeeting);
        cvEntertainment = (CardView)findViewById(R.id.cvEntertainment);
        cvMyJob = (CardView)findViewById(R.id.cvMyJob);
        txtExcercise =(TextView)findViewById(R.id.txtExercise);
        txtHomeWork = (TextView)findViewById(R.id.txtHomeWork);
        txtMeeting = (TextView)findViewById(R.id.txtMeeting);
        txtEntertainment = (TextView)findViewById(R.id.txtEntertainment);
        txtMyJob = (TextView)findViewById(R.id.txtMyJob);
    }

    private void showStatistical() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ArrayList<Note>arrNote = new ArrayList<Note>();

        Cursor cursor = database.rawQuery("SELECT USER.Id, Status.status,CATEGORY.name,NOTE.Name,PRIORITY.Priority,NOTE.PlanDate,NOTE.CreatedDate,NOTE.Id\n" +
                "FROM NOTE\n" +
                "INNER JOIN CATEGORY ON NOTE.CateId =CATEGORY.Id\n" +
                "INNER JOIN Priority ON NOTE.PriorityId = Priority.Id\n" +
                "INNER JOIN Status ON NOTE.StatusId=Status.id\n" +
                "INNER JOIN USER ON NOTE.UserId = USER.Id\n" +
                "WHERE USER.Id = '" + Id + "'", null);
        //adapter.clear();
        //Toast.makeText(MainActivity.this,"Da vao",Toast.LENGTH_LONG).show();
        while (cursor.moveToNext()) {
            String status = cursor.getString(1);
            String cateName = cursor.getString(2);
            String noteName = cursor.getString(3);
            String priorityName = cursor.getString(4);
            String planDate = cursor.getString(5);
            String createdDate = cursor.getString(6);
            String iD = cursor.getString(7);
            arrNote.add(new Note(status, cateName, noteName, priorityName, planDate, createdDate,iD));
        }
        cursor.close();

        int amountExercise = countLateDeadline(arrNote, "Exercise");
        int amountHomework = countLateDeadline(arrNote, "Homework");
        int amountMeeting = countLateDeadline(arrNote, "Meeting");
        int amountEntertainment = countLateDeadline(arrNote, "Entertainment");
        int amountMyBob = countLateDeadline(arrNote, "My Job");

        // check max
        int maxAmountDeadline = amountMyBob;
        String categoryDeadline = "My Job";


        // check amountExercise is max
        if(amountExercise >= amountHomework &&
                amountExercise >= amountMeeting &&
                amountExercise >= amountEntertainment &&
                amountExercise >= amountMyBob
        ){
            categoryDeadline = "Exercise";
            maxAmountDeadline = amountExercise;
        }

        // check amountHomework is max
        if(amountHomework >= amountExercise &&
                amountHomework >= amountMeeting &&
                amountHomework >= amountEntertainment &&
                amountHomework >= amountMyBob
        ){
            categoryDeadline = "Homework";
            maxAmountDeadline = amountHomework;
        }

        // check amountMeeting is max
        if(amountMeeting >= amountExercise &&
                amountMeeting >= amountHomework &&
                amountMeeting >= amountEntertainment &&
                amountMeeting >= amountMyBob
        ){
            categoryDeadline = "Meeting";
            maxAmountDeadline = amountMeeting;
        }

        // check amountEntertainment is max
        if(amountEntertainment >= amountExercise &&
                amountEntertainment >= amountHomework &&
                amountEntertainment >= amountMeeting &&
                amountEntertainment >= amountMyBob
        ){
            categoryDeadline = "Entertainment";
            maxAmountDeadline = amountEntertainment;
        }



        String strNotify = "You have " + maxAmountDeadline + " missions that\nneed to be completed";
        listNoteTitle.setText(categoryDeadline);
        listNoteNotify.setText(strNotify);


        if(categoryDeadline == "Exercise"){
            listNoteBanner.setImageResource(R.drawable.ic_action_exercise);
        }else if(categoryDeadline == "Homework"){
            listNoteBanner.setImageResource(R.drawable.ic_action_homework);
        }else if(categoryDeadline == "Meeting"){
            listNoteBanner.setImageResource(R.drawable.ic_action_meeting);
        }else if(categoryDeadline == "Entertainment"){
            listNoteBanner.setImageResource(R.drawable.ic_action_entertainment);
        }else if(categoryDeadline == "My Job"){
            listNoteBanner.setImageResource(R.drawable.ic_action_job);

        }
    }

    private int countLateDeadline (ArrayList<Note>arrNote, String category){
        int count = 0;
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        int lengthNote = arrNote.size();

        for(int i = 0; i < lengthNote; i++){
            if(arrNote.get(i).getCategory().equals(category)){
                String nowStr = simpleDateFormat.format(new Date());
                String[] arrTimeNow = nowStr.split("/");

                String []arrTimePlaneDate = arrNote.get(i).getPlanDate().split("/");

                Boolean flag = false;
                if(Integer.parseInt(arrTimePlaneDate[2]) < Integer.parseInt(arrTimeNow[2])){
                    flag = true;
                }else if (Integer.parseInt(arrTimePlaneDate[1]) < Integer.parseInt(arrTimeNow[1])){
                    flag = true;
                }else if (Integer.parseInt(arrTimePlaneDate[0]) < Integer.parseInt(arrTimeNow[0])){
                    flag = true;
                }

                if(flag){
                    count += 1;
                }
            }

        }

        return count;
    }
}

