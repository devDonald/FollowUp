package com.godlife.followup.reports;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.godlife.followup.R;

public class RecyclerAdapter extends RecyclerView.ViewHolder{
    View mView;

    public RecyclerAdapter(View itemView) {
        super(itemView);
        mView=itemView;

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());

            }
        });
    }
    public void setTitle(String title){
        TextView tv_title = (TextView)mView.findViewById(R.id.tv_title);
        tv_title.setText(title);
    }
    public void setReporter(String reporter){
        TextView tv_reporter =(TextView)mView.findViewById(R.id.tv_reporter);
        tv_reporter.setText(reporter);
    }
    public void setDate(String date){
        TextView tv_date=(TextView)mView.findViewById(R.id.tv_date);
        tv_date.setText(date);
    }

    private RecyclerAdapter.ClickListener mClickListener;

    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(RecyclerAdapter.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
