package tranvu203107.dmt.nms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranvu203107.dmt.nms.model.Status;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    Context context;
    ArrayList<Status> listStatus;

    public StatusAdapter(Context context, ArrayList<Status> listStatus) {
        this.context = context;
        this.listStatus = listStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_status_item, parent, false);
        return new StatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        // Gán dữ liêuk
        Status status = listStatus.get(position);

        holder.textStatus.setText(status.getStatus());
        holder.textCreatedDate.setText(status.getCreatedDate());
    }

    @Override
    public int getItemCount() {
        return listStatus.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStatus, textCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            textStatus = itemView.findViewById(R.id.txtView_Status);
            textCreatedDate = itemView.findViewById(R.id.txtView_CreatedDate);
        }
    }
}
