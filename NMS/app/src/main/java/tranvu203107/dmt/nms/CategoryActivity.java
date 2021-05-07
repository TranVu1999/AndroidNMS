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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    String DATABASE_NAME="myDB.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database = null;
    int Id;
    TextView txtNameUser;
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

    String cateChosed = "All";

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
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("cateChosed",cateChosed);
                startActivity(intent);
            }
        });
        cvHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Homework";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("cateChosed",cateChosed);
                startActivity(intent);
            }
        });
        cvMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Meeting";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("cateChosed",cateChosed);
                startActivity(intent);
            }
        });
        cvEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="Entertainment";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("cateChosed",cateChosed);
                startActivity(intent);
            }
        });
        cvMyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateChosed="My Job";
                Intent intent = new Intent(CategoryActivity.this,ListNoteActivity.class).putExtra("cateChosed",cateChosed);
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
}