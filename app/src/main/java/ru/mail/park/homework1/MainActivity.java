package ru.mail.park.homework1;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String LIST_KEY = "savedNumberList";
    private static final String CURRENT_FRAGMENT_KEY = "savedCurrentFragmentName";
    private ArrayList<String> numberList;
    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            numberList = new ArrayList<>();
            fillList(numberList);
        } else {
            numberList = savedInstanceState.getStringArrayList(LIST_KEY);
            currentFragment = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
        }

        if (currentFragment == null || currentFragment.equals("list")) {
            ListFragment listFragment;


            if (getSupportFragmentManager().findFragmentByTag("list") == null) {
                listFragment = ListFragment.newInstance(numberList);
            } else {
                listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("list");
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_frame, Objects.requireNonNull(listFragment), "list")
                    .commit();
        }
    }


    void fillList(List<String> toFill) {
        for (int i = 1; i <= 100; i++) {
            toFill.add(i + "");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_KEY, numberList);
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragment);
    }


    @Override
    protected void onPause() {

        if (Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("list")).isVisible())
            currentFragment = "list";
        else {
            currentFragment = "number";
        }
        super.onPause();
    }
}
