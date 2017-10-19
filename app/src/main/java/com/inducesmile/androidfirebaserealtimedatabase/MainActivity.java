package com.inducesmile.androidfirebaserealtimedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SubscriptionHandling;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText addTaskBox;
    private List<Task> allTask;
    ParseLiveQueryClient liveQueryClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Parse.enableLocalDatastore(this);
        //initialise Parse server (serv info in manifest metadata):
        //10.0.2.2 to adres kompa widzialny z emulatora
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("parseTest")
                .clientKey("master")
                .server("http://10.0.2.2:1337/parse/")
                .build()
        );
        ParseUser.enableRevocableSessionInBackground();


        setContentView(R.layout.activity_main);

        allTask = new ArrayList<Task>();
        addTaskBox = (EditText)findViewById(R.id.add_task_box);
        recyclerView = (RecyclerView)findViewById(R.id.task_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        liveQueryClient = ParseLiveQueryClient.Factory.getClient();

        getAllTasks();
//        subscribeToUserTask();

        Button addTaskButton = (Button)findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredTask = addTaskBox.getText().toString();
                if(TextUtils.isEmpty(enteredTask)){
                    Toast.makeText(MainActivity.this, "You must enter a task first", Toast.LENGTH_LONG).show();
                    return;
                }
                if(enteredTask.length() < 6){
                    Toast.makeText(MainActivity.this, "Task count must be more than 6", Toast.LENGTH_LONG).show();
                    return;
                }

                //send key-value to parse cloud
                Task taskObject = new Task(enteredTask);
                ParseObject userTask = new ParseObject("UserTask");
                userTask.put( "task", taskObject.getTask() );
                userTask.saveInBackground();

                addTaskBox.setText("");
                getAllTasks();
            }

        });
    }
//FIXME: za chuj jasny nie wiem jak to działa
//    private void subscribeToUserTask(){
//        ParseQuery<ParseObject> query = new ParseQuery<>("UserTask");
//
//        SubscriptionHandling<ParseObject> subscriptionHandling = liveQueryClient.subscribe(query);
//        subscriptionHandling.handleEvents(new SubscriptionHandling.HandleEventsCallback<ParseObject>() {
//            @Override
//            public void onEvents(ParseQuery<ParseObject> query, SubscriptionHandling.Event event, ParseObject object) {
//                // HANDLING all events
//                getAllTasks();
//            }
//        });
//    }
//FIXME: W PISDU

    private void getAllTasks(){
        allTask.clear();
        ParseQuery<ParseObject> query = new ParseQuery<>("UserTask");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    for( ParseObject po : markers ){
                        String taskTitle = po.getString("task");
                        allTask.add(new Task(taskTitle));
                        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, allTask);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                } else {
                    // handle Parse Exception here
                }
            }
        });
    }
    //fixme: pozostałości po fajerbejzie
//    private void taskDeletion(DataSnapshot dataSnapshot){
//        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//            String taskTitle = singleSnapshot.getValue(String.class);
//            for(int i = 0; i < allTask.size(); i++){
//                if(allTask.get(i).getTask().equals(taskTitle)){
//                    allTask.remove(i);
//                }
//            }
//            Log.d(TAG, "Task tile " + taskTitle);
//            recyclerViewAdapter.notifyDataSetChanged();
//            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, allTask);
//            recyclerView.setAdapter(recyclerViewAdapter);
//        }
//    }
}
