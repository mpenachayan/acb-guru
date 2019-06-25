package gal.mpch.acbguru.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import gal.mpch.acbguru.Config;
import gal.mpch.acbguru.R;
import gal.mpch.acbguru.model.ResultItem;
import gal.mpch.acbguru.util.Utility;

class ResultAdapter extends ArrayAdapter<ResultItem> {

    ResultAdapter(Context context, List<ResultItem> objects) {
        super(context, R.layout.fragment_results, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ResultItem resultItem = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_results, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (resultItem != null) {
            // Populate the data into the template view using the data object
            holder.localTextView.setText(resultItem.getLocalTeam());
            holder.visitorTextView.setText(resultItem.getVisitorTeam());
            Picasso.with(getContext()).load(resultItem.getLocalImageUrl()).into(holder.localImage);
            Picasso.with(getContext()).load(resultItem.getVisitorImageUrl()).into(holder.visitorImage);
            String scoreText = resultItem.getLocalPoints() + holder.pointsSeparator + resultItem.getVisitorPoints();
            holder.scoreTextView.setText(scoreText);
            if (Config.MATCH_STATUS_FINISHED.equalsIgnoreCase(resultItem.getStatus())) {
                holder.matchDateTextView.setText(getContext().getString(R.string.match_finished));
            } else if (Config.MATCH_STATUS_PENDING.equalsIgnoreCase(resultItem.getStatus())) {
                Locale locale = Locale.getDefault();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locale = getContext().getResources().getConfiguration().getLocales().get(0);
                }
                holder.matchDateTextView.setText(Utility.formatDateWithText(resultItem.getMatchDate(), locale));
            } else if (Config.MATCH_STATUS_PLAYING.equalsIgnoreCase(resultItem.getStatus())) {
                holder.matchDateTextView.setText(Utility.formatPlayingTime(resultItem.getLiveClock()));
            }
        }
        // Return the completed view to render on screen
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.text_result_local_name)
        TextView localTextView;

        @BindView(R.id.text_result_visitor_name)
        TextView visitorTextView;

        @BindView(R.id.text_result_match_time)
        TextView matchDateTextView;

        @BindView(R.id.text_result_score)
        TextView scoreTextView;

        @BindView(R.id.image_result_local)
        ImageView localImage;

        @BindView(R.id.image_result_visitor)
        ImageView visitorImage;

        @BindString(R.string.points_separator)
        String pointsSeparator;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
