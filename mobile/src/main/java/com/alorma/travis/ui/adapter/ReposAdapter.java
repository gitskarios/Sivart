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
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.StateColor;

public class ReposAdapter extends BaseAdapter<RepositoryResponse, ReposAdapter.RepoHolder> {

  private ReposAdapterCallback reposAdapterCallback;

  public ReposAdapter() {
    super();
  }

  @Override
  public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RepoHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repository, parent, false));
  }

  @Override
  public void onBindViewHolder(RepoHolder holder, int position) {
    RepositoryResponse repository = get(position);

    @ColorRes int color = getColorFromRepository(repository);

    holder.statusLine.setBackgroundColor(getColor(holder.statusLine, color));

    holder.repoName.setText(repository.getRepo());
  }

  private int getColor(View view, int color) {
    return ContextCompat.getColor(view.getContext(), color);
  }

  private int getColorFromRepository(RepositoryResponse repository) {
    StateColor stateColor = StateColor.fromState(repository.getLastBuildState());

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

  public void setReposAdapterCallback(ReposAdapterCallback reposAdapterCallback) {
    this.reposAdapterCallback = reposAdapterCallback;
  }

  public class RepoHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.status_line) View statusLine;
    @Bind(R.id.repo_name) TextView repoName;

    public RepoHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(v -> {
        if (reposAdapterCallback != null) {
          reposAdapterCallback.onRepoSelected(get(getAdapterPosition()));
        }
      });
    }
  }

  public interface ReposAdapterCallback {
    void onRepoSelected(RepositoryResponse repositoryResponse);
  }
}
