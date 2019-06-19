package com.beike.ctdialog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beike.ctdialog.R;
import com.beike.ctdialog.utils.DensityUtil;


public class ImageText extends LinearLayout {

	private Context context;
	public ImageView iconImageView;
	public TextView titleTextView;

	public ImageText(Context context) {
		this(context, null);
	}

	public ImageText(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		initAttrsView(attrs);
	}

	private void initAttrsView(AttributeSet attrs) {

		initBaseView();

		if (attrs == null)
			return;

		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageText);
		if (a.hasValue(R.styleable.ImageText_itSrc)) {
			Drawable drawable = a.getDrawable(R.styleable.ImageText_itSrc);
			this.iconImageView.setImageDrawable(drawable);
		}
		if (a.hasValue(R.styleable.ImageText_itText)) {
			String text = a.getString(R.styleable.ImageText_itText);
			this.titleTextView.setText(text);
		}

		if (a.hasValue(R.styleable.ImageText_imageSize)) {
			int imageSize = a.getDimensionPixelSize(R.styleable.ImageText_imageSize, 46);
			LayoutParams params = (LayoutParams) iconImageView.getLayoutParams();
			params.width = imageSize;
			params.height = imageSize;
			iconImageView.setLayoutParams(params);

//			LayoutParams textParams = (LayoutParams) titleTextView.getLayoutParams();
//			textParams.width = imageSize;
//			textParams.height = LayoutParams.WRAP_CONTENT;
//			titleTextView.setLayoutParams(textParams);
			titleTextView.setSingleLine(true);
			titleTextView.setEllipsize(TextUtils.TruncateAt.END);

		}

		if (a.hasValue(R.styleable.ImageText_textColor)) {
			titleTextView.setTextColor(a.getColorStateList(R.styleable.ImageText_textColor));
		}

		if (a.hasValue(R.styleable.ImageText_textSize)) {
			titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.ImageText_textSize, (int) titleTextView.getTextSize()));
		}

		a.recycle();

	}

	private void initBaseView() {
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

		this.iconImageView = new ImageView(getContext());
		LayoutParams ivParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		iconImageView.setLayoutParams(ivParams);
		iconImageView.setDuplicateParentStateEnabled(true);
		this.addView(iconImageView);

		this.titleTextView = new TextView(getContext());
		LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleTextView.setLayoutParams(tvParams);
		tvParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
		tvParams.topMargin = DensityUtil.dip2px(context, 5);
		this.titleTextView.setTextSize(13);
		this.titleTextView.setTextColor(Color.BLACK);
		this.titleTextView.setHighlightColor(Color.DKGRAY);
		titleTextView.setDuplicateParentStateEnabled(true);
		this.addView(titleTextView);

//		titleTextView.setIncludeFontPadding(false);
	}

	public void setImage(@DrawableRes int resId) {
		this.iconImageView.setImageResource(resId);
	}

	public void setImageView(@DrawableRes int imgId, int imgSize) {
		this.iconImageView.setImageResource(imgId);

		int size;
		if (imgSize > 0) {
			size = imgSize;
		} else {
			size = 46;
		}

		LayoutParams params = (LayoutParams) iconImageView.getLayoutParams();
		params.weight = DensityUtil.dip2px(context, size);
		params.height = DensityUtil.dip2px(context, size);
		iconImageView.setLayoutParams(params);
	}

	public void setImageView(Drawable drawable, int imgSize) {
		if (drawable != null) {
			this.iconImageView.setImageDrawable(drawable);
		}

		int size;
		if (imgSize > 0) {
			size = imgSize;
		} else {
			size = 46;
		}

		LayoutParams params = (LayoutParams) iconImageView.getLayoutParams();
		params.weight = DensityUtil.dip2px(context, size);
		params.height = DensityUtil.dip2px(context, size);
		iconImageView.setLayoutParams(params);
	}

	public void setText(CharSequence text) {
		titleTextView.setText(text);
	}

	public void setTextColor(int color) {
		this.titleTextView.setTextColor(color);
	}

	public void setTextSize(int size) {
		this.titleTextView.setTextSize(size);
	}

	public TextView getTextView() {
		return titleTextView;
	}

	public ImageView getImageView() {
		return iconImageView;
	}
}
