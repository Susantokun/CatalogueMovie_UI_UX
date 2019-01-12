package com.susantokun.project2_cataloguemovie_ui_ux.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.susantokun.project2_cataloguemovie_ui_ux.R;
import com.susantokun.project2_cataloguemovie_ui_ux.adapter.MovieAdapter;
import com.susantokun.project2_cataloguemovie_ui_ux.model.Response;
import com.susantokun.project2_cataloguemovie_ui_ux.model.ResultMovie;
import com.susantokun.project2_cataloguemovie_ui_ux.network.ConfigRetrofit;

import java.util.ArrayList;

import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;
import static com.susantokun.project2_cataloguemovie_ui_ux.BuildConfig.MOVIE_API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    ImageView ivPoster;
    EditText etTitle;
    Button btnSearch;
    MovieAdapter mAdapter;
    RecyclerView mRecyclerView;
    Context mContext;
    View mView;

    private ArrayList<ResultMovie> listMovie;
    String query;
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false);

        btnSearch = mView.findViewById(R.id.btn_cari_film);
        etTitle = mView.findViewById(R.id.et_cari_film);
        ivPoster = mView.findViewById(R.id.iv_poster);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        listMovie = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MovieAdapter(getActivity(), listMovie);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnSearch.setOnClickListener(this);

        loadMovie();

        return mView;
    }

    private void loadMovie() {
        ConfigRetrofit.service.getAllMovies(MOVIE_API_KEY, language, sort_by, include_adult, include_video, page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                listMovie = (ArrayList<ResultMovie>) response.body().getResults();

                mRecyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                mAdapter.notifyDataSetChanged();

                Log.d("Status", "status" + response.body().getResults());

            }

            @Override
            public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                Log.d(" Error", t.getMessage());
                Toast.makeText(getContext(), R.string.failed_get_data, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_cari_film) {
            query = etTitle.getText().toString();
            if (query.isEmpty()) {
                Toast.makeText(getActivity(), R.string.not_empty_title, Toast.LENGTH_SHORT).show();

            } else {
                Log.e(TAG, "Data = " + query);
                getSearchMovie(query);
            }

        }
    }

    private void getSearchMovie(String query) {

        ConfigRetrofit.service.searchMovie(MOVIE_API_KEY, language, query).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                listMovie = (ArrayList<ResultMovie>) response.body().getResults();
                mRecyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                mAdapter.notifyDataSetChanged();
                Log.d("Status", "status" + response.body().getResults());

            }

            @Override
            public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                Log.d(" Error", t.getMessage());
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
