package com.example.myapp_natalygiron.tslyrics_api;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp_natalygiron.LyricsListAdapter;
import com.example.myapp_natalygiron.R;
import com.example.myapp_natalygiron.RetrofitClient;
import com.example.myapp_natalygiron.model.Lyrics;
import com.example.myapp_natalygiron.model.LyricsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LyricsFragment extends Fragment  {

    private Retrofit retrofit;
    private static final String TAG = "LYRICS";
    private RecyclerView recyclerView;
    private LyricsListAdapter lyricsListAdapter;
    LinearLayoutManager horizontalLayout;
    Spinner spinner;
    String getalbum;
    TSLyricsService service;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyrics_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        lyricsListAdapter = new LyricsListAdapter();
        recyclerView.setAdapter(lyricsListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        spinner = (Spinner) view.findViewById(R.id.spinner_album);

        // Para llenar el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,getAlbums());
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Para horizontal scroll
        horizontalLayout = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayout);



        // Para usar Grid Layout
        // recyclerView.setLayoutManager(layoutManager);

//        TSLyricsService service = RetrofitClient.getRetrofitInstance().create(TSLyricsService.class);
        service = RetrofitClient.getRetrofitInstance().create(TSLyricsService.class);
        Call<List<Lyrics>> call = service.getLyricsList("folklore");

        call.enqueue(new Callback<List<Lyrics>>() {
            @Override
            public void onResponse(Call<List<Lyrics>> call, Response<List<Lyrics>> response) {
                Log.e(TAG, "onResponse: " + response.code() + "\n"+
                        " message: " + response.message() + " body: " + response.body());

                if(response.isSuccessful()){
                    List<Lyrics> data = response.body();
                    lyricsListAdapter.addLyricsList(data);
                    for (Lyrics dt : data ) {
                        Log.e(TAG, "onResponse: quote:" + dt.getQuote());
                    }
                } else {
                    Log.e(TAG,"onResponse: No successful: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Lyrics>> call, Throwable t) {
                Log.e(TAG,"onFailure: "+ t.getMessage());
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!getSelectedAlbum().equals("")) {
                    lyricsListAdapter.removeData();
                    Call<List<Lyrics>> call = service.getLyricsList(getalbum);

                    call.enqueue(new Callback<List<Lyrics>>() {
                        @Override
                        public void onResponse(Call<List<Lyrics>> call, Response<List<Lyrics>> response) {
                            Log.e(TAG, "onResponse: " + response.code() + "\n" +
                                    " message: " + response.message() + " body: " + response.body());

                            if (response.isSuccessful()) {
                                List<Lyrics> newdata = response.body();
                                lyricsListAdapter.addLyricsList(newdata);
                                for (Lyrics dt : newdata) {
                                    Log.e(TAG, "onResponse: quote:" + dt.getQuote());
                                }
                            } else {
                                Log.e(TAG, "onResponse: No successful: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Lyrics>> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getalbum = "evermore";
            }
        });


        return view;
    }

    public ArrayList<String> getAlbums(){
        ArrayList<String> albumList = new ArrayList<>();
        albumList.add("evermore");
        albumList.add("folklore");
        albumList.add("lover");
        albumList.add("reputation");
        albumList.add("1989");
        albumList.add("red");
        albumList.add("speak now");
        albumList.add("fearless");

        return albumList;
    }

    private String getSelectedAlbum() {
        getalbum = spinner.getSelectedItem().toString();
        return getalbum;
    }


}
