package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Player {

    private int mScore;
    private String mFinalJeopardyAnswer;
    private final String mId;
    private boolean mIsInControl;

    private PlayerControlObservable playerControlObservable;
    private PlayerScoreObservable playerScoreObservable;

    public Player(String id) {
        this.mId = id;
        playerControlObservable = new PlayerControlObservable();
        playerScoreObservable = new PlayerScoreObservable();
    }

    void increaseScoreBy(int scoreValue) {
        mScore += scoreValue;
        playerScoreObservable.notifyObserversScoreChanged(mId);
    }

    void decreaseScoreBy(int scoreValue) {
        mScore -= scoreValue;
        playerScoreObservable.notifyObserversScoreChanged(mId);
    }

    void setIsInControl(boolean mIsInControl) {
        this.mIsInControl = mIsInControl;
        playerControlObservable.notifyPlayerControlChanged(mId);
    }

    public void registerOnPlayerScoreChangedListener(OnPlayerScoreChangedListener listener) {
        playerScoreObservable.registerObserver(listener);
    }

    public void unRegisterOnPlayerScoreChangedListener(OnPlayerScoreChangedListener listener) {
        playerScoreObservable.unregisterObserver(listener);
    }


    public void registerOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlObservable.registerObserver(listener);
    }

    public void unRegisterOnPlayerControlChangedListner(OnPlayerControlChanged listener) {
        playerControlObservable.unregisterObserver(listener);
    }



    private static class PlayerControlObservable extends Observable<OnPlayerControlChanged>{
        private void notifyPlayerControlChanged(String playerId) {
            for (OnPlayerControlChanged listener : mObservers) {
                listener.onPlayerControlChanged(playerId);
            }
        }
    }

    private static class PlayerScoreObservable extends Observable<OnPlayerScoreChangedListener>{
        private void notifyObserversScoreChanged(String playerId) {
            for (OnPlayerScoreChangedListener listener : mObservers) {
                listener.onPlayerScoreChanged(playerId);
            }
        }
    }



    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerControlChanged {
        public void onPlayerControlChanged(String playerId);
    }


    /**
     * Created by MattDupree on 10/26/14.
     */
    public static interface OnPlayerScoreChangedListener {
        public void onPlayerScoreChanged(String playerId);
    }

}
