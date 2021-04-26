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

import tranvu203107.dmt.nms.model.Priority;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder> {
    Context context;
    ArrayList<Priority> listPriority;

    public PriorityAdapter(Context context, ArrayList<Priority> listPriority) {
        this.context = context;
        this.listPriority = listPriority;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_priority_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêuk
        Priority Priority = listPriority.get(position);

        holder.textPriority.setText(Priority.getPriority());
        holder.textCreatedDate.setText(Priority.getCreatedDate());
    }

    @Override
    public int getItemCount() {
        return listPriority.size(); // trả item tại vị trí postion
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textPriority, textCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            textPriority = itemView.findViewById(R.id.txtView_Priority);
            textCreatedDate = itemView.findViewById(R.id.txtView_CreatedDate);

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