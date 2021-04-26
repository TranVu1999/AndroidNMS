package tranvu203107.dmt.nms;

import android.content.Context;
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

        holder.textStatus.setText(note.getStatus());
        holder.textName.setText(note.getName());
        holder.textPlanDate.setText(note.getPlanDate());
        holder.textCreateDate.setText(note.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return listNote.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStatus, textName, textPlanDate, textCreateDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            textName = itemView.findViewById(R.id.textName);
            textStatus = itemView.findViewById(R.id.textStatus);
            textPlanDate = itemView.findViewById(R.id.textPlanDate);
            textCreateDate = itemView.findViewById(R.id.textCreateDate);
        }
    }
}