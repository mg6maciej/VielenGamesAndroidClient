package com.elpassion.vielengames.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.Player;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.ui.common.ItemAdapter;
import com.elpassion.vielengames.utils.Circlifier;
import com.elpassion.vielengames.utils.ViewUtils;
import com.elpassion.vielengames.utils.kuridor.KuridorGameStateDrawer;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class GameItemAdapter implements ItemAdapter {

    private final Game game;
    private final Player me;

    @Override
    public Game getItem() {
        return game;
    }

    @Override
    public long getItemId() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.game_item;
    }

    @Override
    public void bindView(View itemView) {
        Context context = itemView.getContext();
        KuridorGame item = (KuridorGame) getItem();
        CharSequence text = formatPlayerNames(item, context);
        ViewUtils.setText(text, itemView, R.id.game_item_player_names);
        int bitmapSize = context.getResources().getDimensionPixelSize(R.dimen.common_image_size);
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFFFFFFF);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        KuridorGameStateDrawer.Settings settings = new KuridorGameStateDrawer.Settings()
                .width(bitmapSize)
                .height(bitmapSize)
                .paint(paint)
                .dotsRadius(1.0f)
                .wallWidth(2.0f)
                .wallPadding(2.0f)
                .pawnPadding(2.0f)
                .team1Color(context.getResources().getColor(R.color.green_normal))
                .team2Color(context.getResources().getColor(R.color.blue_normal));
        KuridorGameStateDrawer.draw(item.getCurrentState(), canvas, settings);
        final int circleColor = 0xFFEEEEEE;
        final float circleWidth = context.getResources().getDimension(R.dimen.common_circle_width);
        final Bitmap circlified = Circlifier.circlify(bitmap, circleColor, circleWidth);
        ViewUtils.setImage(circlified, itemView, R.id.game_item_preview);
    }

    private CharSequence formatPlayerNames(KuridorGame item, Context context) {
        List<Player> players = item.getPlayers();
        Player team1Player = "team_1".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        Player team2Player = "team_2".equals(players.get(0).getTeam()) ? players.get(0) : players.get(1);
        String name1 = me.equals(team1Player) ? "You" : team1Player.getName();
        String name2 = me.equals(team2Player) ? "You" : team2Player.getName();
        SpannableString spannableString = new SpannableString(name1 + " vs " + name2);
        int greenTextColor = context.getResources().getColor(R.color.text_green_color);
        spannableString.setSpan(
                new ForegroundColorSpan(greenTextColor),
                name1.length(),
                name1.length() + 4,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getItemViewType() {
        return 0;
    }
}
