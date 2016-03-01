package com.alorma.travis.ui.view;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.alorma.travis.R;

public class BottomSheetDialogView {

  /**
   * remember to call setLocalNightMode for dialog
   *
   * @param dayNightMode current day night mode
   */
  public BottomSheetDialogView(Context context, int dayNightMode, RecyclerView.Adapter adapter) {
    BottomSheetDialog dialog = new BottomSheetDialog(context);
    dialog.getDelegate().setLocalNightMode(dayNightMode);

    View view =
        LayoutInflater.from(context).inflate(R.layout.bottom_sheet_recycler_view, null);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.setAdapter(adapter);

    dialog.setContentView(view);
    dialog.show();
  }

  public static void show(Context context, int dayNightMode) {
    new BottomSheetDialogView(context, dayNightMode, null);
  }
}