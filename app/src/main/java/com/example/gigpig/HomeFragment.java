package com.example.gigpig;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, ValueEventListener {

    private ArrayList<Job> jobsList;

    private RecyclerView recyclerView;
    private JobAdapter mAdapter;

    private Spinner sortSelection;

    private SortingStrategy sortingStrategy;

    public int test;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        this.jobsList = new ArrayList<Job>();
        this.sortingStrategy = new SortByAlphabeticalOrder();

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("jobs");

        dataRef.addValueEventListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sortSelection = getView().findViewById(R.id.sortSelection);

        List<String> sortMethods = new ArrayList<>();
        sortMethods.add("Sort by my tags");
        sortMethods.add("Sort alphabetically");
        sortMethods.add("Sort by date");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sortMethods);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortSelection.setAdapter(dataAdapter);
        sortSelection.setSelection(1);

        sortSelection.setOnItemSelectedListener(this);

        recyclerView = getView().findViewById(R.id.recycler_view);
        mAdapter = new JobAdapter(jobsList);
        mAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get Job object and use the values to update the UI
        this.jobsList.clear();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Job job = snapshot.getValue(Job.class);
            this.jobsList.add(job);
        }
        this.mAdapter.updateContents(this.sortingStrategy.sort(this.jobsList));
        this.mAdapter.notifyDataSetChanged();

        // ...
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        // ...
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        // placeholder
        ArrayList<String> tags = new ArrayList<>();

        // tags to sort by!
        tags.add("wash");
        tags.add("clean");
        tags.add("lost");


        switch (item) {
            case "Sort by my tags":
                this.sortingStrategy = new SortByTagsStrategy(tags);
                break;
            case "Sort alphabetically":
                this.sortingStrategy = new SortByAlphabeticalOrder();
                break;
            case "Sort by date":
                this.sortingStrategy = new SortByDateStrategy();
                break;
        }

        ArrayList<Job> sortList = this.sortingStrategy.sort(this.jobsList);

        mAdapter.updateContents(sortList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void preparePostings() {

        // placeholder
        User user = new User();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("goat");
        tags.add("clean");

        Job job = new Job("Goats",
                "Need someone who is experienced with goat care to work one day cleaning stalls" +
                        ", feeding etc.",
                50, user, tags, null);

        this.jobsList.add(job);

        tags = new ArrayList<>();
        tags.add("line");
        tags.add("long");

        job = new Job("Long job",
                "This is to text a long job with multiple lines\n" +
                        "Here's another line\n" +
                        "another one\n" +
                        "the idea is that the cells will adjust their size automatically based on the" +
                        "size of the job posting",
                12, user, tags, null);

        this.jobsList.add(job);

        tags = new ArrayList<>();
        tags.add("find");
        tags.add("cats");
        tags.add("lost");

        job = new Job("Lost cat",
                "REWARD: Need someone to find my cat. Lost somewhere in monument creek." +
                        "He responds to Jimmy",
                100, user, tags, null);

        this.jobsList.add(job);

        tags = new ArrayList<>();
        tags.add("an");
        tags.add("nothing");

        job = new Job("A nothing thing",
                "Test sort alphabetically",
                0, user, tags, null);

        this.jobsList.add(job);

        tags = new ArrayList<>();
        tags.add("clean");
        tags.add("windex");

        job = new Job("Clean with my cleaning supplies",
                "Need someone with a lot of cleaning experience to clean my kitchen floor" +
                        "\n I will supply cleaning materials",
                20, user, tags, null);

        this.jobsList.add(job);


        mAdapter.notifyDataSetChanged();
    }

}
