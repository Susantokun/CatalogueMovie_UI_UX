package com.susantokun.project2_cataloguemovie_ui_ux.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.susantokun.project2_cataloguemovie_ui_ux.R;
import com.susantokun.project2_cataloguemovie_ui_ux.adapter.MovieAdapter;
import com.susantokun.project2_cataloguemovie_ui_ux.model.Response;
import com.susantokun.project2_cataloguemovie_ui_ux.model.ResultMovie;
import com.susantokun.project2_cataloguemovie_ui_ux.network.ConfigRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.susantokun.project2_cataloguemovie_ui_ux.BuildConfig.MOVIE_API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    MovieAdapter mAdapter;
    Context mContext;
    RecyclerView mRecyclerView;
    View mView;

    private ArrayList<ResultMovie> listMovie;
    private final String language = "en-US";

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_up_coming, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_up_coming);
        listMovie = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MovieAdapter(getActivity(), listMovie);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (savedInstanceState != null) {
            ArrayList<ResultMovie> list;
            list = savedInstanceState.getParcelableArrayList("up_coming");
            mAdapter = new MovieAdapter(getActivity(), list);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            loadMovie();
        }

        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("up_coming", new ArrayList<>(listMovie));
    }

    private void loadMovie() {
        final ProgressDialog dialog = ProgressDialog.show(getContext(), "", getString(R.string.loading), false);

        ConfigRetrofit.service.getUpComingMovie(MOVIE_API_KEY, language).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    listMovie = (ArrayList<ResultMovie>) response.body().getResults();

                    mRecyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                    mAdapter.notifyDataSetChanged();

                    Log.d("Status", "status" + response.body().getResults());
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), R.string.failed_get_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                Log.d(" Error", t.getMessage());
            }
        });
    }


}
