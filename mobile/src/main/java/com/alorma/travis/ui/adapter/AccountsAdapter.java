package com.alorma.travis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alorma.github.sdk.bean.dto.response.Organization;
import com.alorma.travis.R;
import com.bumptech.glide.Glide;

public class AccountsAdapter extends BaseAdapter<Organization, AccountsAdapter.AccountHolder> {

  private AccountsCallback accountsCallback;

  public AccountsAdapter() {
    super();
  }

  @Override
  public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View v = inflater.inflate(R.layout.row_gh_organization, parent, false);
    return new AccountHolder(v);
  }

  @Override
  public void onBindViewHolder(AccountHolder holder, int position) {
    Organization organization = get(position);

    holder.textView.setText(organization.login);
    Glide.with(holder.imageView.getContext()).load(organization.avatar_url).into(holder.imageView);
  }

  public void setAccountsCallback(AccountsCallback accountsCallback) {
    this.accountsCallback = accountsCallback;
  }

  public class AccountHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView;
    private final TextView textView;

    public AccountHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(android.R.id.text1);
      imageView = (ImageView) itemView.findViewById(android.R.id.icon);

      itemView.setOnClickListener(v -> {
        if (accountsCallback != null) {
          accountsCallback.onAccountSelected(get(getAdapterPosition()));
        }
      });
    }
  }

  public interface AccountsCallback {
    void onAccountSelected(Organization organization);
  }
}
