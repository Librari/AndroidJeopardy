package com.doctoredapps.androidjeopardy.model;

import android.database.Observable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MattDupree on 10/26/14.
 */
public class Round extends Observable<Round.OnRoundEndedListener> {

    private final ArrayList<Category> mCategories;
    private final HashMap<Category, Answer[]> mAnswers;
    private final HashMap<Category, List<Answer>> mFinishedAnswers;
    private Answer mCurrentAnswer;

    private Round(ArrayList<Category> categories, HashMap<Category, Answer[]> answers) {
        mCategories = categories;
        mAnswers = answers;
        mFinishedAnswers = new HashMap<Category, List<Answer>>(mAnswers.size());
        initFinishedAnswersContainer(mFinishedAnswers);
    }

    private void initFinishedAnswersContainer(HashMap<Category, List<Answer>> finishedAnswers) {

        for (Category category : mCategories) {
            int capacity = mAnswers.get(category).length;
            finishedAnswers.put(category, new ArrayList<Answer>(capacity));
        }

    }

    static Round fromJSON(JSONObject json) {




        return null;
    }

    List<Category> getCategories() {
        return mCategories;
    }

    List<Answer> getCategoryAnswers(Category category) {
        return null;
    }

    void setCurrentAnswer(Answer currentAnswer) {
        this.mCurrentAnswer = currentAnswer;
    }

    Answer getCurrentAnswer() {
        return mCurrentAnswer;
    }

    void finishCurrentAnswer() {

        List<Answer> answers = mFinishedAnswers.get(mCurrentAnswer.getCategory());
        answers.add(mCurrentAnswer);

        if (isRoundFinished()) {
            notifyRoundEnded();
        }

    }

    private void notifyRoundEnded() {
        for (OnRoundEndedListener listener : mObservers) {
            listener.onRoundEnded();
        }
    }

    private boolean isRoundFinished() {
        return false;
    }

    /**
     * @author MattDupree on 10/26/14.
     */
    static interface OnRoundEndedListener {
        public void onRoundEnded();
    }
}
