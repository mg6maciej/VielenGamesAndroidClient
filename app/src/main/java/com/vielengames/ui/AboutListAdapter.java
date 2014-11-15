package com.vielengames.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.vielengames.R;
import com.vielengames.ui.common.AbstractBaseAdapter;
import com.vielengames.ui.common.ItemAdapter;
import com.vielengames.utils.Circlifier;
import com.vielengames.utils.ViewUtils;

import lombok.RequiredArgsConstructor;

public final class AboutListAdapter extends AbstractBaseAdapter {

    public AboutListAdapter(Context context) {
        super(context);
        items.add(new AboutHeaderItemAdapter("DESIGNER"));
        items.add(new AboutPersonItemAdapter("Natalia Berowska", R.drawable.about_image_natalia_b, "https://www.behance.net/natiwa"));
        items.add(new AboutHeaderItemAdapter("ANDROID DEVELOPER"));
        items.add(new AboutPersonItemAdapter("Maciej Górski", R.drawable.about_image_maciej_g, "https://github.com/mg6maciej"));
        items.add(new AboutHeaderItemAdapter("BACKEND DEVELOPER"));
        items.add(new AboutPersonItemAdapter("Michał Janeczek", R.drawable.about_image_michal_j, "http://mjaneczek.pl"));
    }

    @RequiredArgsConstructor
    private static final class AboutHeaderItemAdapter implements ItemAdapter {

        private final String title;

        @Override
        public Object getItem() {
            return null;
        }

        @Override
        public long getItemId() {
            return 0;
        }

        @Override
        public int getLayoutId() {
            return R.layout.about_header_item;
        }

        @Override
        public void bindView(View itemView) {
            TextView titleView = (TextView) itemView;
            titleView.setText(title);
        }

        @Override
        public boolean isEnabled() {
            return "ANDROID DEVELOPER".equals(title);
        }

        @Override
        public int getItemViewType() {
            return 0;
        }
    }

    @RequiredArgsConstructor
    private static final class AboutPersonItemAdapter implements ItemAdapter {

        private final String name;
        private final int image;
        private final String url;
        private Bitmap circlified;

        @Override
        public String getItem() {
            return url;
        }

        @Override
        public long getItemId() {
            return 0;
        }

        @Override
        public int getLayoutId() {
            return R.layout.about_person_item;
        }

        @Override
        public void bindView(View itemView) {
            if (circlified == null) {
                circlified = createCirclifiedBitmap(itemView.getResources());
            }
            ViewUtils.setImage(circlified, itemView, R.id.about_person_image);
            ViewUtils.setText(name, itemView, R.id.about_person_name);
            ViewUtils.setText(url, itemView, R.id.about_person_url);
        }

        private Bitmap createCirclifiedBitmap(Resources resources) {
            final Bitmap bitmap = BitmapFactory.decodeResource(resources, image);
            final int circleColor = 0xFFEEEEEE;
            final float circleWidth = resources.getDimension(R.dimen.common_circle_width);
            return Circlifier.circlify(bitmap, circleColor, circleWidth);
        }

        @Override
        public boolean isEnabled() {
            return url != null;
        }

        @Override
        public int getItemViewType() {
            return 1;
        }
    }
}
