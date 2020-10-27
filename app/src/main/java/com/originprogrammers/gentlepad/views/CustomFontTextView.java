package com.originprogrammers.gentlepad.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.originprogrammers.gentlepad.R;


/**
 * The type Custom font text view.
 */
public class CustomFontTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * Instantiates a new Custom font text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Instantiates a new Custom font text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    /**
     * Instantiates a new Custom font text view.
     *
     * @param context the context
     */
    public CustomFontTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            String fontName = a.getString(R.styleable.CustomFontTextView_font_text);
            String fontAwesomeName = a.getString(R.styleable.CustomFontTextView_font_awesome_text);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
                if (fontAwesomeName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontAwesomeName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }


}