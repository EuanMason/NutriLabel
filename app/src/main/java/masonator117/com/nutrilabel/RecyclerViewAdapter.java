package masonator117.com.nutrilabel;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.brkckr.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

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
        NutritionLabel nlS = nl.get(position);
        Log.d(TAG, "Item count = " + getItemCount());

        holder.energyProgBar.setProgressValue(Float.parseFloat(nlS.getEnergy().replaceAll("%", "")));
        holder.energyPercent.setText(nlS.getEnergy());

        holder.fatProgBar.setProgressValue(Float.parseFloat(nlS.getFat().replaceAll("%", "")));
        holder.fatPercent.setText(nlS.getFat());


        holder.saturatesProgBar.setProgressValue(Float.parseFloat(nlS.getSaturates().replaceAll("%", "")));
        holder.saturatesPercent.setText(nlS.getSaturates());


        holder.sugarProgBar.setProgressValue(Float.parseFloat(nlS.getSugars().replaceAll("%", "")));
        holder.sugarPercent.setText(nlS.getSugars());


        holder.saltProgBar.setProgressValue(Float.parseFloat(nlS.getSalts().replaceAll("%", "")));
        holder.saltPercent.setText(nlS.getSalts());



        colourProgBars(holder);

//        holder.sugarProgBar.setProgressValue(50);
    }

    private void colourProgBars(ViewHolder holder){
        CircularProgressBar cpbEnergy = holder.energyProgBar;
        CircularProgressBar cpbFat = holder.fatProgBar;
        CircularProgressBar cpbSaturates = holder.saturatesProgBar;
        CircularProgressBar cpbSugars = holder.sugarProgBar;
        CircularProgressBar cpbSalts = holder.saltProgBar;



        CircularProgressBar[] circulararray = {cpbEnergy, cpbFat, cpbSaturates, cpbSugars, cpbSalts};
        for (int i=0;i<circulararray.length;i++){
            Log.e("ColourArray", "ColourArray length = " + circulararray.length);
            Log.e("Progress value", "Progress array length = " + circulararray.length + " i = " + i + " really?" + cpbSugars.getProgressValue());
            if (circulararray[i].getProgressValue() < 50){
//                Log.e("ColourArray", "ColourArray = " + circulararray[i].getProgressValue());

                circulararray[i].setProgressColor(Color.parseColor("#00e500"));
            } else if (circulararray[i].getProgressValue() < 100 ){
                circulararray[i].setProgressColor(Color.parseColor("#ffa500"));
            } else if (circulararray[i].getProgressValue() >= 100){
                Log.e("ProgressValue", "Progress value");
//                Toast.makeText(this, "You're over your daily limit for nutrition type", Toast.LENGTH_LONG).show();
                circulararray[i].setProgressColor(Color.parseColor("#e50000"));
            } else {

            }
            Log.e("ProgressValue", "Progress value = " + circulararray[i].getProgressValue());

        }
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
            energyProgBar = itemView.findViewById(R.id.energyProgressBarRecycler);
            fatProgBar = itemView.findViewById(R.id.fatProgressBarRecycler);
            saturatesProgBar = itemView.findViewById(R.id.saturatesProgressBarRecycler);
            sugarProgBar = itemView.findViewById(R.id.sugarProgressBarRecycler);
            saltProgBar = itemView.findViewById(R.id.saltProgressBarRecycler);

            energyPercent = itemView.findViewById(R.id.energyPercentRecycler);
            fatPercent = itemView.findViewById(R.id.fatPercentRecycler);
            saturatesPercent = itemView.findViewById(R.id.saturatesPercentRecycler);
            sugarPercent = itemView.findViewById(R.id.sugarPercentRecycler);
            saltPercent = itemView.findViewById(R.id.saltPercentRecycler);

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
