package com.doctoredapps.androidjeopardy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doctoredapps.androidjeopardy.R;
import com.doctoredapps.androidjeopardy.model.Game;
import com.doctoredapps.androidjeopardy.model.GameManager;
import com.doctoredapps.androidjeopardy.model.Player;
import com.doctoredapps.androidjeopardy.widgets.PlayersView;

import java.util.HashMap;


/**
 * @author Matt Dupree on 11/8/14.
 */
public class ModeratorFragment extends Fragment
                            implements Player.OnPlayerControlChanged,
                                        Game.OnGameStateChangedListner{


    private Game mCurrentGame;
    private PlayersView mPlayersView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mPlayersView = (PlayersView) inflater.inflate(R.layout.fragment_moderator, container, false);

        GameManager gameManager = GameManager.getInstance();
        mCurrentGame = gameManager.getCurrentGame();

        HashMap<String, Player> players = mCurrentGame.getPlayers();
        mPlayersView.setPlayers(players);



        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentGame.registerOnPlayerControlChangedListner(this);
        mCurrentGame.registerOnGameStateChangedListner(this);

    }

    @Override
    public void onPlayerControlChanged(String playerId) {
        mPlayersView.setActivePlayer(playerId);
    }

    @Override
    public void onGameStateChanged(@Game.GAME_STATE int newGameState) {
        switch (newGameState) {
            case Game.GAME_STATE_NEW_ROUND:
                //According to the rules, the player that's in last place gets control first whenever
                //a new round starts
                String playerInLastPlace = mCurrentGame.getPlayerInLastPlace();
                mPlayersView.setActivePlayer(playerInLastPlace);
                break;
            case Game.GAME_STATE_FINAL_JEOPARDY:
                mPlayersView.setActivePlayer(null);
                break;
            case Game.GAME_STATE_END:
                break;
        }
    }
}
