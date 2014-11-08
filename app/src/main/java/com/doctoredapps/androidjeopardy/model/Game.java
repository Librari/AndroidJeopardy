package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Game implements Round.OnRoundEndedListener{

    private final Round[] rounds;
    private final HashMap<String, Player> mPlayers;

    private Round mCurrentRound;
    private int currentRoundIndex;

    private Player playerWithControl;

    private GameStateObservable gameStateObservable;

    public void registerOnPlayerControlChangedListner(Player.OnPlayerControlChanged listener) {

    }

    public void registerOnGameStateChangedListner(OnGameStateChangedListner listener) {

    }

    public String getPlayerInLastPlace() {
        return null;
    }

    public Round getCurrentRound() {
        return mCurrentRound;
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GAME_STATE_NEW_ROUND, GAME_STATE_FINAL_JEOPARDY, GAME_STATE_END})
    public @interface GAME_STATE{}

    public static final int GAME_STATE_NEW_ROUND = 0;
    public static final int GAME_STATE_FINAL_JEOPARDY = 1;
    public static final int GAME_STATE_END = 2;


    Game(Round[] rounds, HashMap<String, Player> players) {
        this.rounds = rounds;
        this.mPlayers = players;
        this.mCurrentRound = rounds[currentRoundIndex];
        this.mCurrentRound.registerObserver(this);
        this.gameStateObservable = new GameStateObservable();
    }

    public void incrementPlayerScore(String playerId) {
        mPlayers.get(playerId).increaseScoreBy(mCurrentRound.getCurrentAnswer().getScoreValue());
    }

    public void decrementPlayerScore(String playerId) {
        mPlayers.get(playerId).decreaseScoreBy(mCurrentRound.getCurrentAnswer().getScoreValue());
    }

    public void givePlayerControl(String playerId) {
        playerWithControl.setIsInControl(false);

        Player player = mPlayers.get(playerId);
        playerWithControl = player;
        player.setIsInControl(true);
    }

    public Player getPlayerWithId(String id) {
        return mPlayers.get(id);
    }

    public HashMap<String, Player> getPlayers() {
        return mPlayers;
    }

    @Override
    public void onRoundEnded() {
        dispatchGameStateChangedNotification();
    }

    private void dispatchGameStateChangedNotification() {

        if (noMoreRounds()) {
            gameStateObservable.notifyGameStateChanged(GAME_STATE_END);
            return;
        }

        if (finalJeopardyIsNextRound()) {
            gameStateObservable.notifyGameStateChanged(GAME_STATE_FINAL_JEOPARDY);
        } else {
            gameStateObservable.notifyGameStateChanged(GAME_STATE_NEW_ROUND);
        }

        mCurrentRound = rounds[++currentRoundIndex];

    }

    private boolean finalJeopardyIsNextRound() {
        return currentRoundIndex < rounds.length - 1;
    }

    private boolean noMoreRounds() {
        return currentRoundIndex == rounds.length;
    }

    private static class GameStateObservable extends Observable<OnGameStateChangedListner>{
        public void notifyGameStateChanged(@GAME_STATE int newGameState) {
            for (OnGameStateChangedListner listner : mObservers) {
                listner.onGameStateChanged(newGameState);
            }
        }
    }


    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnGameStateChangedListner {


        public void onGameStateChanged(@GAME_STATE int newGameState);
    }
}
