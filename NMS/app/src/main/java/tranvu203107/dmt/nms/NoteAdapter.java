package tranvu203107.dmt.nms;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        holder.txtStatus.setText(note.getStatus());
        holder.txtName.setText(note.getName());
        holder.txtCategory.setText(note.getCategory());
        holder.txtPriority.setText(note.getPriority());
        holder.txtPlanDate.setText(note.getPlanDate());
        holder.txtCreatedDate.setText(note.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return listNote.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView txtStatus, txtName, txtPlanDate, txtCreatedDate,txtCategory,txtPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            txtName = itemView.findViewById(R.id.txtName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPlanDate = itemView.findViewById(R.id.txtPlanDate);
            txtCreatedDate = itemView.findViewById(R.id.txtCreatedDate);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtPriority =itemView.findViewById(R.id.txtPriority);

            //
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Chọn thao tác");
            menu.add(getAdapterPosition(), 0, 0, "Sửa");//groupId, itemId, order, title
            menu.add(getAdapterPosition(), 1, 0, "Xóa");

        }
    }
}