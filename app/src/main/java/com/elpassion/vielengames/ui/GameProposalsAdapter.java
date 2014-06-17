package com.elpassion.vielengames.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.GameProposal;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public final class GameProposalsAdapter extends BaseAdapter {

    private Context context;
    private final LayoutInflater inflater;
    private final List<GameProposal> proposals;

    public GameProposalsAdapter(Context context, List<GameProposal> proposals) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.proposals = proposals;
    }

    @Override
    public int getCount() {
        return proposals.size();
    }

    @Override
    public GameProposal getItem(int position) {
        return proposals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.game_proposal_item, parent, false);
        }
        GameProposal item = getItem(position);
        Player player = item.getAwaitingPlayers().get(0);
        ViewUtils.setText(player.getName(), convertView, R.id.game_proposal_name);
        ImageView profileIcon = ViewUtils.findView(convertView, R.id.game_proposal_profile_icon);
        Picasso.with(context).load(player.getAvatarUrl()).into(profileIcon);
        return convertView;
    }
}
