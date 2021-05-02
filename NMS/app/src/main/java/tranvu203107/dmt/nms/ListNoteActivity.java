package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ListNoteActivity extends AppCompatActivity {

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

    ArrayList<ItemMenu> arrList;
    ArrayList<ItemMenu> arrListAccount;
    MenuAdapter menuAdapter;

    ArrayList<Note> arrNote;
    RecyclerView noteListRecycler;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
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


        // list note
        arrNote = new ArrayList<Note>();
        arrNote.add(new Note(
                "Football",
                "Exercise",
                "Slow",
                "Processing",
                "10-01-1999",
                "10-01-2021"
        ));
        arrNote.add(new Note(
                "Android Project",
                "Homework",
                "High",
                "Processing",
                "10-01-1999",
                "10-01-2021"
        ));

        noteAdapter =new NoteAdapter(getApplicationContext(), arrNote);
        noteListRecycler.setAdapter(noteAdapter);

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