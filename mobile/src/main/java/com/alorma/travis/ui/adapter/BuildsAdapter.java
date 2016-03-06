package com.alorma.travis.ui.adapter;

import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.alorma.travisdk.bean.utils.StateColor;

public class BuildsAdapter extends BaseAdapter<TravisBuildResponse, BuildsAdapter.RepoHolder> {

  private BuildAdapterCallback buildAdapterCallback;

  public BuildsAdapter() {
    super();
  }

  @Override
  public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RepoHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_build, parent, false));
  }

  @Override
  public void onBindViewHolder(RepoHolder holder, int position) {
    TravisBuildResponse travisBuild = get(position);

    @ColorRes int color = getColorFromBuild(travisBuild);

    holder.statusLine.setBackgroundColor(getColor(holder.statusLine, color));

    holder.buildTitle.setText(
        "#".concat(String.valueOf(travisBuild.getNumber())));
  }

  private int getColor(View view, int color) {
    return ContextCompat.getColor(view.getContext(), color);
  }

  private int getColorFromBuild(TravisBuildResponse build) {
    StateColor stateColor = StateColor.fromState(build.getState());

    @ColorRes int color;
    switch (stateColor) {
      default:
      case GRAY:
        color = R.color.md_grey_700;
        break;
      case GREEN:
        color = R.color.md_green_700;
        break;
      case YELLOW:
        color = R.color.md_yellow_700;
        break;
      case RED:
        color = R.color.md_red_700;
        break;
    }
    return color;
  }

  public void setBuildAdapterCallback(BuildAdapterCallback buildAdapterCallback) {
    this.buildAdapterCallback = buildAdapterCallback;
  }

  public class RepoHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.status_line) View statusLine;
    @Bind(R.id.build_title) TextView buildTitle;

    public RepoHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(v -> {
        if (buildAdapterCallback != null) {
          buildAdapterCallback.onBuildSelected(get(getAdapterPosition()));
        }
      });
    }
  }

  public interface BuildAdapterCallback {
    void onBuildSelected(TravisBuildResponse travisBuild);
  }
}
