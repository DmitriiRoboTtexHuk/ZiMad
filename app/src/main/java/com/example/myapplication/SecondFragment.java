package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class SecondFragment extends Fragment {
    List<String> DataCat = new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    View view = null;
    Parcelable savedRecyclerLayoutState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.secondfragmentlayout, container, false);
        assert view != null;
        this.setRetainInstance(true);

        // передаем в MyTask ссылку на текущее MainActivity
        if(view == null) {
            // Если представления нет, создаем его
            view = inflater.inflate(R.layout.secondfragmentlayout, container, false);
        } else {
            // Если представление есть, удаляем его из разметки,
            // иначе возникнет ошибка при его добавлении
            ((ViewGroup) view).removeView(view);
        }

        recyclerView = view.findViewById(R.id.rvCat);

        CatTask catTask = new CatTask();
        catTask.execute();
        return view;
    }

    class CatTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            GetData();

        }

        @Override
        protected Void doInBackground(String... params) {

                return null;
            }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }


    private void GetData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kot3.com/xim/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessagesApi service = retrofit.create(MessagesApi.class);

        Call<CountModel> call = service.request("cat");
        call.enqueue(new Callback<CountModel>() {
            @Override
            public void onResponse(Call<CountModel> call, Response<CountModel> response) {
                if (response.isSuccessful()) {

                    String JsonString=new Gson().toJson(response.body().Animals);

                    try {
                        // выводим целиком полученную json-строку
                        JSONArray jsonObj = new JSONArray(JsonString);

                        for (int i = 0; i <jsonObj.length(); i++) {
                            JSONObject jsonobject = jsonObj.getJSONObject(i);
                            String title = jsonobject.getString("title");
                            String url = jsonobject.getString("url");
                            DataCat.add(title+"img:"+url);
                        }
                        SetAdapter();
                    }
                    catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

            }
            @Override
            public void onFailure(Call<CountModel> call, Throwable t) {

            }
        });

    }
    private void SetAdapter(){
        if(SecondFragment.this.getActivity()!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(SecondFragment.this.getActivity()));
            adapter = new MyRecyclerViewAdapter(SecondFragment.this.getActivity(), DataCat);
            recyclerView.setAdapter(adapter);

            adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    RelativeLayout parent = (RelativeLayout) view;
                    TextView t = (TextView) parent.findViewById(R.id.title_name_text_view);
                    TextView u = (TextView) parent.findViewById(R.id.url);
                    AnimalView(t.getText().toString(),u.getText().toString());
                }
            });
        }
    }

    private void AnimalView(String Title, String Url){
        Intent intent = new Intent(SecondFragment.this.getActivity(), AnimalView.class);
        intent.putExtra("Title", Title);
        intent.putExtra("Url", Url);
        startActivity(intent);
    }


   @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }

}