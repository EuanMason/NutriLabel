package masonator117.com.nutrilabel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.brkckr.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Euan on 19/07/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<NutritionLabel> nl = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<NutritionLabel> nlabel) {
        nl = nlabel;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Log.d(TAG, "Item count = " + getItemCount());
//        holder.sugarProgBar.setProgressValue(50);
    }

    @Override
    public int getItemCount() {
        return nl.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircularProgressBar energyProgBar;
        CircularProgressBar fatProgBar;
        CircularProgressBar saturatesProgBar;
        CircularProgressBar sugarProgBar;
        CircularProgressBar saltProgBar;

        TextView energyPercent;
        TextView fatPercent;
        TextView saturatesPercent;
        TextView sugarPercent;
        TextView saltPercent;

        TextView timelineEnergy;
        TextView timelineFat;
        TextView timelineSaturates;
        TextView timelineSugar;
        TextView timelineSalt;

        RelativeLayout parent;
        RelativeLayout secondParent;
        LinearLayout linLayout;
        Space space;

        public ViewHolder(View itemView) {
            super(itemView);
            energyProgBar = itemView.findViewById(R.id.energyProgressBar);
            fatProgBar = itemView.findViewById(R.id.fatProgressBar);
            saturatesProgBar = itemView.findViewById(R.id.saturatesProgressBar);
            sugarProgBar = itemView.findViewById(R.id.sugarsProgressBar);
            saltProgBar = itemView.findViewById(R.id.saltProgressBar);

            energyPercent = itemView.findViewById(R.id.energyPercent);
            fatPercent = itemView.findViewById(R.id.fatPercent);
            saturatesPercent = itemView.findViewById(R.id.saturatesPercent);
            sugarPercent = itemView.findViewById(R.id.sugarPercent);
            saltPercent = itemView.findViewById(R.id.saltPercent);

            timelineEnergy = itemView.findViewById(R.id.timelineenergyText);
            timelineFat = itemView.findViewById(R.id.timelinefatText);
            timelineSaturates = itemView.findViewById(R.id.timelinesaturatesText);
            timelineSugar = itemView.findViewById(R.id.timelinesugarText);
            timelineSalt = itemView.findViewById(R.id.timelinesaltText);

            parent = itemView.findViewById(R.id.parent_Layout);
            secondParent = itemView.findViewById(R.id.progressBars);
            linLayout = itemView.findViewById(R.id.progressText);

            space = itemView.findViewById(R.id.space);

        }
    }
}
