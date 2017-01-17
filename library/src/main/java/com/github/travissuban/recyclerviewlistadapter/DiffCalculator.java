package com.github.travissuban.recyclerviewlistadapter;

import android.support.v7.util.DiffUtil;

import java.util.List;

public interface DiffCalculator<T> {
    DiffUtil.DiffResult getDiffResult(List<T> oldList, List<T> newList);
}
