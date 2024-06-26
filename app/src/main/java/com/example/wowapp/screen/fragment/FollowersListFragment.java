package com.example.wowapp.screen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wowapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowersListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowersListFragment newInstance(String param1, String param2) {
        FollowersListFragment fragment = new FollowersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList <String>  userIdArrayList=new ArrayList<String>();
    ArrayList <String> userNameArrayList=new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> followingList = new ArrayList<String>();
    ArrayList<String> followingUserNameList = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ArrayList <String> test=new ArrayList<String>();

        //get mang ve
        test.add("abc");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers_list, container, false);
    }
}