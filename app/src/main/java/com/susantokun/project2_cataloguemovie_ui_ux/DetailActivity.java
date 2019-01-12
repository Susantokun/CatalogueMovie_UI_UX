package com.susantokun.project2_cataloguemovie_ui_ux;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.susantokun.project2_cataloguemovie_ui_ux.helper.MovieHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.title_detail)
    TextView tvTitle;

    @BindView(R.id.synopsis_detail)
    TextView tvOverview;

    @BindView(R.id.release_date_detail)
    TextView tvReleaseDate;

    @BindView(R.id.poster_detail)
    ImageView imgPoster;


    String title, overview, poster_backdrop, poster_jpg, release_date;
    private int id_movie;
    MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setToolbar();
        setData();
        backButton();
        loadData();
    }

    private void loadData() {
        movieHelper = new MovieHelper(this);
    }

    private void setData() {
        id_movie = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        overview = getIntent().getStringExtra("overview");
        poster_backdrop = getIntent().getStringExtra("backdrop_path");
        release_date = getIntent().getStringExtra("release_date");
        poster_jpg = getIntent().getStringExtra("poster_path");

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTitle.setText(title);
        tvOverview.setText(overview);
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/" + poster_backdrop).into(imgPoster);
    }

    private void setToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    public void backButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }
}