package com.doctoredapps.androidjeopardy.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.doctoredapps.androidjeopardy.model.Player;

import java.util.HashMap;

/**
 * Created by MattDupree on 11/8/14.
 */
public class PlayersView extends LinearLayout {
    private HashMap<String, Player> players;
    private String activePlayer;

    public PlayersView(Context context) {
        super(context);
    }

    public PlayersView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setActivePlayer(@Nullable String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }


}
