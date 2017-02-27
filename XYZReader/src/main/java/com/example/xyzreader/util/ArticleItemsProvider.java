package com.example.xyzreader.util;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.ui.model.ArticleItemViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lars on 15.02.17.
 */

public class ArticleItemsProvider {

    public static List<ArticleItemViewModel> fromCursor(Context context, Cursor cursor) {
        if (context == null) {
            return Collections.emptyList();
        }
        List<ArticleItemViewModel> items = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ArticleItemViewModel item = new ArticleItemViewModel(cursor.getLong(ArticleLoader.Query._ID));
                    item.setTitle(cursor.getString(ArticleLoader.Query.TITLE));
                    item.setDate(cursor.getLong(ArticleLoader.Query.PUBLISHED_DATE));
                    item.setAuthor(cursor.getString(ArticleLoader.Query.AUTHOR));
                    item.setThumbImageUrl(cursor.getString(ArticleLoader.Query.THUMB_URL));
                    item.setBody(cursor.getString(ArticleLoader.Query.BODY));
                    item.setInfo(String.format(context.getString(R.string.date_by_author),
                            DateUtils.getRelativeTimeSpanString(
                                    cursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                                    DateUtils.FORMAT_ABBREV_ALL)
                                    .toString(), cursor.getString(ArticleLoader.Query.AUTHOR)));
                    item.setPhotoUrl(cursor.getString(ArticleLoader.Query.PHOTO_URL));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        }
        return items;
    }

}
