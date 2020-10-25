package ru.mail.park.homework1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class ListFragment extends Fragment {
    private static final String ARGS_STRINGS = "args:strings";
    private String fragmentState;

    public static ListFragment newInstance(ArrayList<String> strings) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARGS_STRINGS, strings);
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        Log.d("D", "created list with fragments");

        return listFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,
                container, false);

        final RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.my_list);

        // tries for orientation task
        final int columns = getResources().getInteger(R.integer.numOfCols);
        GridLayoutManager layout = new GridLayoutManager(getContext(), columns);
        recyclerView.setLayoutManager(layout);


        ArrayList<String> strings;
        if (getArguments() != null) {
            strings = getArguments().getStringArrayList(ARGS_STRINGS);
        } else {
            strings = new ArrayList<>();
        }


        if (savedInstanceState != null) {
            fragmentState = savedInstanceState.getString("key");
        } else {
            fragmentState = "init";
        }

        toLog(fragmentState);

        final MyAdapter myAdapter = new MyAdapter(strings);
        recyclerView.setAdapter(myAdapter);

        Button button = view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextNum = (myAdapter.getItemCount() + 1) + "";
                myAdapter.addItem(nextNum);
                fragmentState = "clicked";
                toLog(fragmentState);
            }
        });

        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", fragmentState);
    }

    void toLog(String currentState) {
        Log.d("D", "Changed fragment state " + currentState);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.number);
        }

    }

    class MyAdapter extends RecyclerView.Adapter<ListFragment.MyViewHolder> {
        private ArrayList<String> mData;

        MyAdapter(ArrayList<String> data) {
            mData = data;
        }

        @NonNull
        @Override
        public ListFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_element, viewGroup, false);
            return new ListFragment.MyViewHolder(v);
        }


        @Override
        public void onBindViewHolder(@NonNull final ListFragment.MyViewHolder myViewHolder, int i) {
            String str = mData.get(i);
            myViewHolder.mTextView.setText(str);


            if (i % 2 == 0) {
                myViewHolder.mTextView.setTextColor(getResources().getColor(R.color.colorBlue));
            } else {
                myViewHolder.mTextView.setTextColor(getResources().getColor(R.color.colorRed));
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get number and color of clicked element
                    CharSequence number = myViewHolder.mTextView.getText();
                    int color = myViewHolder.mTextView.getCurrentTextColor();

                    NumberFragment numberFragment = NumberFragment.newInstance(number, color);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_frame, numberFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        void addItem(String newItem) {
            mData.add(newItem);
            notifyItemInserted(getItemCount());
        }
    }
}
