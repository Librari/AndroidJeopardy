package com.doctoredapps.androidjeopardy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doctoredapps.androidjeopardy.R;
import com.doctoredapps.androidjeopardy.adapters.ScoreboardAdapter;
import com.doctoredapps.androidjeopardy.model.Game;
import com.doctoredapps.androidjeopardy.model.GameManager;
import com.doctoredapps.androidjeopardy.model.Player;
import com.doctoredapps.androidjeopardy.model.Round;

/**
 *
 *
 * @author MattDupree on 11/8/14.
 */
public class AnswersGridFragment extends Fragment implements Game.OnGameStateChangedListner, Player.OnPlayerControlChanged {

    private Game mCurrentGame;
    private RecyclerView mAnswersGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mCurrentGame = GameManager.getInstance().getCurrentGame();

        mAnswersGridView = (RecyclerView) inflater.inflate(R.layout.fragment_scoreboard, container, false);


        mAnswersGridView.setAdapter(new ScoreboardAdapter(mCurrentGame.getCurrentRound()));


        return mAnswersGridView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentGame.registerOnGameStateChangedListner(this);
        mCurrentGame.registerOnPlayerControlChangedListner(this);
    }

    @Override
    public void onGameStateChanged(@Game.GAME_STATE int newGameState) {
        switch (newGameState) {
            case Game.GAME_STATE_NEW_ROUND:
                Round currentRound = mCurrentGame.getCurrentRound();
                mAnswersGridView.setAdapter(new ScoreboardAdapter(currentRound));
                break;
            case Game.GAME_STATE_FINAL_JEOPARDY:
                break;
        }
    }

    @Override
    public void onPlayerControlChanged(String playerId) {

    }
}
