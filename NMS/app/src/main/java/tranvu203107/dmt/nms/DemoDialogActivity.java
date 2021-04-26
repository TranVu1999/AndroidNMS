package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DemoDialogActivity extends AppCompatActivity {

    Button btnAddCate, btnAddNote;
    Dialog dialog;

    Spinner spinnerCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_dialog);

        btnAddCate = (Button)findViewById(R.id.btnAddCate);
        btnAddNote = (Button)findViewById(R.id.btnAddNote);

        dialog = new Dialog(this);
        spinnerCate = (Spinner)findViewById(R.id.spinnerCate);

        btnAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCategoryDialog();
            }
        });
//        btnAddNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAddNoteDialog();
//            }
//        });
//
//
//
//        // Create list option
//        ArrayList<String> arr = new ArrayList<String>();
//        arr.add("Tran Le Anh Vu");
//        arr.add("Pham Duc Huy");
//        arr.add("Nguyen Hoang Toan");
//        arr.add("Tran Minh Thinh");
//        arr.add("Huynh Ngoc Quoc");
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item  );
//        spinnerCate.setAdapter(arrayAdapter);



    }

    private void openLoseDialog() {

    }

    private void openAddCategoryDialog() {
//        dialog.setContentView(R.layout.win_dialog);
        dialog.setContentView(R.layout.layout_add_cate_form);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);
        Button btnSaveData = dialog.findViewById(R.id.btnSaveData);

        dialog.show();
    }

    private void openAddNoteDialog() {
        dialog.setContentView(R.layout.layout_add_note);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);
        Button btnSaveData = dialog.findViewById(R.id.btnSaveData);

        dialog.show();
    }

}