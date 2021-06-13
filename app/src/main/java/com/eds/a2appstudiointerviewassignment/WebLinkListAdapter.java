package com.eds.a2appstudiointerviewassignment;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.StreamEncoder;
import com.caverock.androidsvg.SVG;
import com.eds.a2appstudiointerviewassignment.glide.svg.SvgDecoder;
import com.eds.a2appstudiointerviewassignment.glide.svg.SvgDrawableTranscoder;
import com.eds.a2appstudiointerviewassignment.glide.svg.SvgSoftwareLayerSetter;
import com.eds.a2appstudiointerviewassignment.model.WebLink;

import java.io.InputStream;

class WebLinkListAdapter extends ListAdapter<WebLink, WebLinkListAdapter.ViewHolder> {

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(WebLink webLink);
    }

    public static final DiffUtil.ItemCallback<WebLink> diffUtil = new DiffUtil.ItemCallback<WebLink>() {
        @Override
        public boolean areItemsTheSame(@NonNull WebLink oldItem, @NonNull WebLink newItem) {
            return oldItem.getWebLink().equals(newItem.getWebLink());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WebLink oldItem, @NonNull WebLink newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected WebLinkListAdapter(OnClickListener onClickListener) {
        super(diffUtil);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_weblink, parent, false);
        return new ViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView urlTextView;
        private final TextView titleTextView;
        private final ImageView imageViewLogo;

        private final RequestBuilder requestBuilder;
        private WebLink webLink;

        public ViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.textViewUrl);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            imageViewLogo = itemView.findViewById(R.id.imageViewLogo);

            itemView.setOnClickListener(v -> {
                onClickListener.onClick(webLink);
            });

            requestBuilder =
                    Glide.with(itemView.getContext())
                            .as(PictureDrawable.class)
                            .listener(new SvgSoftwareLayerSetter());
        }

        private void bind(WebLink webLink) {
            this.webLink = webLink;

            urlTextView.setText(webLink.getWebLink());
            titleTextView.setText(webLink.getTitle());

            if (webLink.imageUrl.endsWith(".svg")) {
                requestBuilder.load(webLink.imageUrl).into(imageViewLogo);
            } else {
                Glide.with(itemView.getContext()).load(webLink.imageUrl).into(imageViewLogo);
            }
        }
    }
}
