package tranvu203107.dmt.nms;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> listNote;

    public NoteAdapter(Context context, ArrayList<Note> listNote) {
        this.context = context;
        this.listNote = listNote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêuk
        Note note = listNote.get(position);

        holder.textStatus.setText(note.getStatus());
        holder.textCategory.setText(note.getCategory());
        holder.textName.setText(note.getName());
        holder.textPriority.setText(note.getPriority());
        holder.textPlanDate.setText(note.getPlanDate());
        holder.textCreateDate.setText(note.getCreateDate());

    }

    @Override
    public int getItemCount() {
        return listNote.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textStatus, textName, textPlanDate, textCreateDate,textCategory,textPriority;
        CardView noteCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            textStatus = itemView.findViewById(R.id.textStatus);
            textName = itemView.findViewById(R.id.textCategory);
            textCategory=itemView.findViewById(R.id.textNoteName);
            textPriority=itemView.findViewById(R.id.textPriority);
            textPlanDate = itemView.findViewById(R.id.textPlanDate);
            textCreateDate = itemView.findViewById(R.id.textCreateDate);

            noteCardView = itemView.findViewById(R.id.noteCardView);
            noteCardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),121,0,"Sửa");
            menu.add(this.getAdapterPosition(),122,1,"Xóa");
        }

    }
    public Note edit(int position){
        //xử lý thêm
        Note selectedNote = listNote.get(position); //lấy được thông tin note đang chọn
        //Toast.makeText(context.getApplicationContext(), selectedNote.getId(), Toast.LENGTH_LONG).show();
        return selectedNote;
    }

    public Note delete(int position){
        //xử lý xóa
        Note selectedNote = listNote.get(position); //lấy được thông tin note đang chọn
        return selectedNote;
    }
}