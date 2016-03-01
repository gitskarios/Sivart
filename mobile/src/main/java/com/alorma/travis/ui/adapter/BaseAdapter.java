package com.alorma.travis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<K, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

  private List<K> itemsList;

  public BaseAdapter() {
    itemsList = new ArrayList<>();
  }

  @Override
  public int getItemCount() {
    return itemsList.size();
  }

  public K get(int position) {
    return itemsList.get(position);
  }

  public void add(K k) {
    itemsList.add(k);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends K> items) {
    itemsList.addAll(items);
    notifyDataSetChanged();
  }

  public void clear() {
    itemsList.clear();
    notifyDataSetChanged();
  }
}
