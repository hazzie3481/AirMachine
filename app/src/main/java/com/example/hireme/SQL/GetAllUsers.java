package com.example.hireme.SQL;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.hireme.model.UserListData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class GetAllUsers {
    String str;
    AppCompatActivity mParentActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private dbHelper databaseHelper;
    String trendingMoviesUrl = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3923b0956e8e11766fc5ef9de9726cde";
    public GetAllUsers(){

    }
    public GetAllUsers(RecyclerView recyclerView, RecyclerView.Adapter adapter, AppCompatActivity activity){
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mParentActivity = activity;
        databaseHelper = new dbHelper(activity);
    }
    public  ArrayList<UserListData> GetListOfUsers(){
         ArrayList<UserListData> userList = new ArrayList<UserListData>();
        userList.add(new UserListData("Mike","mike@gmail.com"));
        userList.add(new UserListData("Brady","brady@gmail.com"));
        userList.add(new UserListData("Roger","Roger@gmail.com"));
        return userList;
    }

}
