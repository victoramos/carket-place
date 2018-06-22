package com.example.victor.carketplace.view.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private final GridLayoutManager mManager;
    private int mCurrentPage = 0;
    private int mCurrentItems;
    private int mTotalItems;
    private int mScrollOutItems;
    private boolean mIsScrolling;

    private Subject<Integer> onNextPageSubject = PublishSubject.create();

    public EndlessScrollListener(GridLayoutManager manager){
        mManager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mCurrentItems = mManager.getChildCount();
        mTotalItems = mManager.getItemCount();
        mScrollOutItems = mManager.findFirstVisibleItemPosition();

        if(mIsScrolling && mCurrentItems + mScrollOutItems == mTotalItems){
            mCurrentPage++;
            mIsScrolling = false;

            onNextPageSubject.onNext(mCurrentPage);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            mIsScrolling = true;
        }
    }

    public Observable<Integer> asObservable(){
        return onNextPageSubject;
    }
}
