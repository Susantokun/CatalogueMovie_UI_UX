package com.susantokun.project2_cataloguemovie_ui_ux.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.susantokun.project2_cataloguemovie_ui_ux.DetailActivity;
import com.susantokun.project2_cataloguemovie_ui_ux.R;
import com.susantokun.project2_cataloguemovie_ui_ux.model.ResultMovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<ResultMovie> movieList;
    Context mContext;
    View mView;

    public MovieAdapter(Context mContext, ArrayList<ResultMovie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final ResultMovie resultMovie = movieList.get(position);

        holder.tvTitle.setText(resultMovie.getTitle());
        holder.tvOverview.setText(resultMovie.getOverview());
        holder.tvVoteAverage.setText("("+ resultMovie.getVoteAverage()+ "%)");

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w154/" + movieList.get(position)
                .getPosterPath()).placeholder(mContext.getResources()
                .getDrawable(R.drawable.ic_launcher_background))
                .error(mContext.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(holder.ivPoster);

        String retrieveDate = movieList.get(position).getReleaseDate();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tvReleaseDate.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id", resultMovie.getId());
                intent.putExtra("title", resultMovie.getTitle());
                intent.putExtra("overview", resultMovie.getOverview());
                intent.putExtra("poster_path", resultMovie.getPosterPath());
                intent.putExtra("backdrop_path", resultMovie.getBackdropPath());
                intent.putExtra("release_date", resultMovie.getReleaseDate());
                mContext.startActivity(intent);
            }
        });

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        mContext.getResources().getString(R.string.title_movie) + " : " + resultMovie.getTitle() + "\n"
                                + mContext.getResources().getString(R.string.vote_average) +" : (" + resultMovie.getVoteAverage() + "%)" + "\n"
                                + mContext.getResources().getString(R.string.overview) + " :\n" +  resultMovie.getOverview());
                mContext.startActivity(Intent.createChooser(intent, mContext.getResources().getString(R.string.share)));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;

        return movieList.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;
        TextView tvVoteAverage;
        TextView tvReleaseDate;
        Button btn_detail;
        Button btn_share;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            tvTitle = (TextView) itemView.findViewById(R.id.title_detail);
            tvOverview = (TextView) itemView.findViewById(R.id.overview_detail);
            tvVoteAverage = (TextView) itemView.findViewById(R.id.vote_average);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.release_date_detail);
            btn_detail = (Button) itemView.findViewById(R.id.btn_detail);
            btn_share = (Button) itemView.findViewById(R.id.btn_share);
        }
    }

}
