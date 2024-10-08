package com.example.fitmeup;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutHistory extends AppCompatActivity {

    private LinearLayout workoutListContainer;
    private WorkoutDao workoutDao;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enable edge-to-edge if needed
        setContentView(R.layout.activity_workout_history);  // Set the correct layout

        workoutListContainer = findViewById(R.id.workout_list_container);  // Initialize the container
        // Assume that userId is passed when the user logs in
        userId = getIntent().getIntExtra("userId", -1);  // Get userId from the intent or session
        if (userId == -1) {
            // Handle error: no userId
            return;
        }

        // Fetch userId from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
         userId = Integer.parseInt(sharedPref.getString("userId", null));


        // Initialize the database and DAO
        RegisterUserDatabase db = RegisterUserDatabase.getInstance(getApplicationContext());
        workoutDao = db.WorkoutDao();

        // Load workout history and save a new workout with the current date
        loadWorkoutHistory();


        Workout workout = new Workout();
        WorkoutApi workoutApi= ApiClient.getClient().create(WorkoutApi.class);
        Call<Workout> call = workoutApi.createWorkout(workout);
        call.enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {
                if (response.isSuccessful()){
                    Log.d(TAG,"created workout");
                }

            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {

                    Log.e(TAG,"created workout failed");
                    Log.e(TAG, t.toString());

            }
        });

    }



    private void loadWorkoutHistory() {
        // Observe the workout history LiveData for the current user
        workoutDao.getAllWorkoutsForUser(userId).observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workoutList) {
                // Clear the container before adding new data
                workoutListContainer.removeAllViews();

                // Check if the workout list is empty or null
                if (workoutList == null || workoutList.isEmpty()) {
                    TextView noWorkoutsTextView = new TextView(WorkoutHistory.this);
                    noWorkoutsTextView.setText("No workout history available.");
                    workoutListContainer.addView(noWorkoutsTextView);
                    return;
                }

                // Layout inflater to inflate workout cards dynamically
                LayoutInflater inflater = LayoutInflater.from(WorkoutHistory.this);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

                // Loop through each workout in the list
                for (Workout workout : workoutList) {
                    View workoutCard = inflater.inflate(R.layout.workout_card, workoutListContainer, false);

                    // Format the workout date
                    Date workoutDate = workout.getDate();
                    String formattedDate = dateFormat.format(workoutDate);

                    // Set the date view (use correct TextView ID from your layout)
                    TextView workoutDateView = workoutCard.findViewById(R.id.workout_type);
                    workoutDateView.setText(formattedDate);

                    // Set the calories burned
                    TextView caloriesInfo = workoutCard.findViewById(R.id.calories_info);
                    caloriesInfo.setText(String.format(Locale.getDefault(), "%d Cal", workout.getCaloriesBurned()));

                    // Set the workout icon
                    ImageView workoutIcon = workoutCard.findViewById(R.id.workout_icon);
                    workoutIcon.setImageResource(getWorkoutIcon(workout.getWorkoutType()));

                    // Set up the delete button
                    ImageButton deleteButton = workoutCard.findViewById(R.id.delete_workout_button);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteWorkout(workout);
                        }
                    });

                    // Add the workout card to the container
                    workoutListContainer.addView(workoutCard);
                }
            }
        });
    }
    private void deleteWorkout(Workout workout) {
        // Use an executor to run database operations off the main thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Delete the workout from the database
            workoutDao.delete(workout);

            // Optional: Run on UI thread to show a toast message
            runOnUiThread(() -> Toast.makeText(WorkoutHistory.this, "Workout deleted", Toast.LENGTH_SHORT).show());
        });
    }




    private void saveWorkoutWithCurrentDate() {

        // Use an executor to run database operations off the main thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Get the current date
            Date currentDate = new Date();

            // Example workout details - you can replace these with actual user inputs
            String workoutType = "running";  // Change as needed
            int caloriesBurned = 300;        // Example value
            String icon = "running";         // Match the workout type with the correct icon

            // Create a new Workout object with the correct userId
            Workout newWorkout = new Workout(workoutType, currentDate, caloriesBurned, icon,0,userId);

            // Insert the workout into the database using the DAO
            workoutDao.insert(newWorkout);
        });
    }

    // Helper method to get the corresponding workout icon based on the workout type
    private int getWorkoutIcon(String workoutType) {
        // Normalize the workoutType string to avoid mismatches
        if (workoutType == null) {
            return R.drawable.run; // Default icon
        }
        workoutType = workoutType.trim().toLowerCase();

        // Switch case to return the correct icon based on the workout type
        switch (workoutType) {
            case "running":
                return R.drawable.run;
            case "core training":
                return R.drawable.core;
            case "pool swim":
                return R.drawable.swim;
            case "martial arts":
                return R.drawable.art;
            case "yoga":
                return R.drawable.yoga;
            case "cycling":
                return R.drawable.bike;
            default:
                return R.drawable.run;  // Fallback to the running icon if type is unknown
        }
    }
}
