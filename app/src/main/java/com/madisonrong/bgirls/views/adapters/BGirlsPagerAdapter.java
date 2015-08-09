package com.madisonrong.bgirls.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by MadisonRong on 15/8/7.
 */
public class BGirlsPagerAdapter extends PagerAdapter implements List<View> {

    private final Object lock = new Object();
    private final List<View> list;

    public BGirlsPagerAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public boolean isViewFromObject(android.view.View view, Object object) {
        return view == object;
    }

    @Override
    public void add(int location, View object) {
        synchronized (lock) {
            list.add(location, object);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean add(View object) {
        synchronized (lock) {
            if (list.add(object)) {
                int position = list.indexOf(object);
                notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addAll(Collection<? extends View> collection) {
        synchronized (lock) {
            int lastIndex = list.size();
            if (list.addAll(collection)) {
                notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addAll(int location, Collection<? extends View> collection) {
        synchronized (lock) {
            if (list.addAll(location, collection)) {
                notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            int size = list.size();
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean contains(Object object) {
        return list.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.contains(collection);
    }

    @Override
    public View get(int location) {
        return list.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return list.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<View> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return list.lastIndexOf(object);
    }

    @NonNull
    @Override
    public ListIterator<View> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<View> listIterator(int location) {
        return list.listIterator(location);
    }

    @Override
    public View remove(int location) {
        synchronized (lock) {
            View item = list.remove(location);
            notifyDataSetChanged();
            return item;
        }
    }

    @Override
    public boolean remove(Object object) {
        boolean modified = false;
        synchronized (lock) {
            if (list.contains(object)) {
                int position = list.indexOf(object);
                list.remove(position);
                notifyDataSetChanged();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        synchronized (lock) {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (list.contains(object)){
                    int position = list.indexOf(object);
                    list.remove(position);
                    notifyDataSetChanged();
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        synchronized (lock) {
            Iterator<View> iterator = list.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (!collection.contains(object)) {
                    int position = list.indexOf(object);
                    list.remove(position);
                    notifyDataSetChanged();
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public View set(int location, View object) {
        synchronized (lock) {
            View item = list.set(location, object);
            notifyDataSetChanged();
            return item;
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @NonNull
    @Override
    public List<View> subList(int start, int end) {
        return list.subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <View1> View1[] toArray(View1[] array) {
        return list.toArray(array);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof List && list.equals(o);
    }
}
