package com.nikola3in1.audiobooks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.nikola3in1.audiobooks.R;
import com.nikola3in1.audiobooks.activities.HomeActivity;

public class PlayerFragment extends Fragment {
    private String TITLE;
    private Context ctx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        //Setting the title of the category
        if (args != null && args.get("book") != null) {
            this.TITLE = (String) args.get("title");
            getActivity().setTitle(TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_player, container, false);
        ctx = getActivity().getApplicationContext();
        initBook(contentView);

        System.out.println("FRAGMENT IS CREATED");

        return contentView;
    }

    private void initBook(View contentView) {
        Button playBtn = contentView.findViewById(R.id.player_play_btn);
        playBtn.setOnClickListener(v -> {
            HomeActivity.playerService.play();
        });


    }
}
