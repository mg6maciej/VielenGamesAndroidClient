package com.vielengames.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    protected List<ItemAdapter> items = new ArrayList<ItemAdapter>();

    protected AbstractBaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public final int getCount() {
        return items.size();
    }

    @Override
    public final Object getItem(int position) {
        return items.get(position).getItem();
    }

    @Override
    public final long getItemId(int position) {
        return items.get(position).getItemId();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layoutId = items.get(position).getLayoutId();
            convertView = inflater.inflate(layoutId, parent, false);
        }
        items.get(position).bindView(convertView);
        return convertView;
    }

    @Override
    public final boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public final boolean isEnabled(int position) {
        return items.get(position).isEnabled();
    }

    @Override
    public final int getViewTypeCount() {
        return 128;
    }

    @Override
    public final int getItemViewType(int position) {
        return items.get(position).getItemViewType();
    }
}
