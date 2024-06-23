package com.example.wowapp.screen.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wowapp.R;
import com.example.wowapp.screen.model.Notification;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class InboxFragment extends Fragment  {
private  Context context = null;
private  final String TAG = "InboxActivity";
private DatabaseReference  mDatabase = null;
private FirebaseUser user;
private ListView lvNotifications;
private ArrayList<Notification>notifications;

    public InboxFragment() {
        // Required empty public constructor
    }
  public static  InboxFragment  newInstance(String strArg) {
      InboxFragment fragment = new InboxFragment();
      Bundle args = new Bundle();
      args.putString("name", strArg);
      fragment.setArguments(args);
      return fragment;
  }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }
}