package edu.neu.madcourse.mathters;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import mathters.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecordViewHolder> {
    private Context mCtx;
    private List<OneRecord> recordList;

    public HistoryAdapter(Context mCtx, List<OneRecord> recordList) {
        this.mCtx = mCtx;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_card, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        OneRecord record = recordList.get(position);
        holder.textViewTime.setText(record.time);
        holder.textViewCat.setText("Age group: " + record.category);
        holder.textViewLevel.setText("Level: " + record.level);
        holder.textViewScore.setText("Score: " + record.score);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView textViewScore, textViewCat, textViewLevel, textViewTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewScore = itemView.findViewById(R.id.record_score);
            textViewCat = itemView.findViewById(R.id.record_cat);
            textViewLevel = itemView.findViewById(R.id.record_level);
            textViewTime = itemView.findViewById(R.id.record_time);
        }
    }

}
