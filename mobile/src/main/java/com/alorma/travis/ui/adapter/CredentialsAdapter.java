package com.alorma.travis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alorma.travis.R;
import com.alorma.travisdk.bean.utils.Credential;
import com.bumptech.glide.Glide;

public class CredentialsAdapter extends BaseAdapter<Credential, CredentialsAdapter.CredentialHolder> {

  private CredentialsAdapterCallback credentialsAdapterCallback;

  public CredentialsAdapter() {
    super();
  }

  @Override
  public CredentialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_credential, parent, false);
    return new CredentialHolder(v);
  }

  @Override
  public void onBindViewHolder(CredentialHolder holder, int position) {
    Credential credential = get(position);
    holder.textView.setText(credential.getName());

    Glide.with(holder.imageView.getContext()).load(credential.getAvatar()).into(holder.imageView);
  }

  public void setCredentialsAdapterCallback(CredentialsAdapterCallback credentialsAdapterCallback) {
    this.credentialsAdapterCallback = credentialsAdapterCallback;
  }

  public class CredentialHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView;
    private final TextView textView;

    public CredentialHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(android.R.id.text1);
      imageView = (ImageView) itemView.findViewById(android.R.id.icon);

      itemView.setOnClickListener(v -> {
        if (credentialsAdapterCallback != null) {
          Credential credential = get(getAdapterPosition());
          credentialsAdapterCallback.onCredentialSelected(credential);
        }
      });
    }
  }

  public interface CredentialsAdapterCallback {
    void onCredentialSelected(Credential credential);
  }
}
