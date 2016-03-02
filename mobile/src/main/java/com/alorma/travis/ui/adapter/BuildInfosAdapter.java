package com.alorma.travis.ui.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.alorma.travis.R;

public class BuildInfosAdapter extends BaseAdapter<BuildInfo, BuildInfosAdapter.InfoHolder> {

  private static final int JOB_VIEW_TYPE = 1;
  private static final int INFO_VIEW_TYPE = 0;

  @Override
  public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    int layout = android.R.layout.simple_list_item_1;
    if (viewType == JOB_VIEW_TYPE) {
      layout = R.layout.row_job_build_info;
    }

    View v = inflater.inflate(layout, parent, false);
    return new InfoHolder(v);
  }

  @Override
  public void onBindViewHolder(InfoHolder holder, int position) {
    BuildInfo info = get(position);
    int dimension =
        holder.text.getResources().getDimensionPixelOffset(R.dimen.materialize_baseline_grid);
    holder.text.setCompoundDrawablePadding(dimension);
    holder.text.setCompoundDrawables(info.getDrawableHolder().get(), null, null, null);

    SpannableString spannableString = getSpannableString(info);

    holder.text.setText(spannableString);
  }

  private SpannableString getSpannableString(BuildInfo info) {
    if (info instanceof JobBuildInfo) {
      return getJobBuildInfoSpannableString((JobBuildInfo) info);
    }
    return getBuildInfoSpannableString(info);
  }

  @NonNull
  private SpannableString getBuildInfoSpannableString(BuildInfo info) {
    String text = info.getTitle().get() +
        ": " +
        info.getValue().get();

    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0,
        1 + info.getTitle().get().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1 + info.getTitle().get().length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(Color.GRAY),
        text.indexOf(info.getValue().get()), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    return spannableString;
  }

  @NonNull
  private SpannableString getJobBuildInfoSpannableString(JobBuildInfo info) {
    String text = info.getTitle().get();

    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0,
        info.getTitle().get().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    return spannableString;
  }

  @Override
  public int getItemViewType(int position) {
    if (get(position) instanceof JobBuildInfo) {
      return JOB_VIEW_TYPE;
    }
    return INFO_VIEW_TYPE;
  }

  public class InfoHolder extends RecyclerView.ViewHolder {
    private final TextView text;

    public InfoHolder(View itemView) {
      super(itemView);
      text = (TextView) itemView.findViewById(android.R.id.text1);
      text.setOnClickListener(v -> {

      });
    }
  }
}
