package ru.mail.park.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NumberFragment extends Fragment {
    private static final String ARGS_NUMBER = "args:number";
    private static final String ARGS_COLOR = "args:color";

    public static NumberFragment newInstance(CharSequence number, int color) {
        NumberFragment numberFragment = new NumberFragment();

        Bundle bundle = new Bundle();
        bundle.putCharSequence(ARGS_NUMBER, number);
        bundle.putInt(ARGS_COLOR, color);
        numberFragment.setArguments(bundle);

        return numberFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number,
                container, false);

        TextView textView = view.findViewById(R.id.selected_number);

        if (getArguments() != null) {
            CharSequence number = getArguments().getCharSequence(ARGS_NUMBER);
            int color = getArguments().getInt(ARGS_COLOR);
            textView.setText(number);
            textView.setTextColor(color);
        }

        return view;
    }

}