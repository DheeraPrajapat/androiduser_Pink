package com.marius.valeyou.util.databinding;//package com.bct.flexcheckauto.util.databinding;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.SuggestionsModel;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.util.AppUtils;

import androidx.databinding.BindingAdapter;

//
//import android.databinding.BindingAdapter;
//import android.databinding.BindingMethod;
//import android.databinding.BindingMethods;
//import android.databinding.InverseBindingAdapter;
//import android.databinding.InverseBindingListener;
//import android.databinding.adapters.ListenerUtil;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.text.InputType;
//import android.text.Spanned;
//import android.text.SpannableString;
//import android.text.SpannableStringBuilder;
//import android.text.TextWatcher;
//import android.text.method.DialerKeyListener;
//import android.text.method.DigitsKeyListener;
//import android.text.method.KeyListener;
//import android.text.method.PasswordTransformationMethod;
//import android.text.method.TextKeyListener;
//import android.util.Log;
//import android.util.TypedValue;
//import android.widget.TextView;
//
//import com.bct.flexcheckauto.R;
//
//@BindingMethods({
//        @BindingMethod(type = TextView.class, attribute = "android:autoLink", method = "setAutoLinkMask"),
//        @BindingMethod(type = TextView.class, attribute = "android:drawablePadding", method = "setCompoundDrawablePadding"),
//        @BindingMethod(type = TextView.class, attribute = "android:editorExtras", method = "setInputExtras"),
//        @BindingMethod(type = TextView.class, attribute = "android:inputType", method = "setRawInputType"),
//        @BindingMethod(type = TextView.class, attribute = "android:scrollHorizontally", method = "setHorizontallyScrolling"),
//        @BindingMethod(type = TextView.class, attribute = "android:textAllCaps", method = "setAllCaps"),
//        @BindingMethod(type = TextView.class, attribute = "android:textColorHighlight", method = "setHighlightColor"),
//        @BindingMethod(type = TextView.class, attribute = "android:textColorHint", method = "setHintTextColor"),
//        @BindingMethod(type = TextView.class, attribute = "android:textColorLink", method = "setLinkTextColor"),
//        @BindingMethod(type = TextView.class, attribute = "android:onEditorAction", method = "setOnEditorActionListener"),
//})
public class TextViewBindingAdapter {

    @BindingAdapter({"android:htmlText"})
    public static void setHtmlTextValue(TextView textView, String htmlText) {
        if (htmlText == null)
            return;
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(htmlText);
        }
        textView.setText(result);
    }

    @BindingAdapter({"android:time"})
    public static void setTime(TextView textView, String timestamp) {
        long timeLong = Long.parseLong(timestamp);
        String time = AppUtils.getDateTime(timeLong);
        textView.setText(time);
    }

    @BindingAdapter({"android:timestamp_date"})
    public static void setTimestampDate(TextView textView, String timestamp) {
        long timeLong = Long.parseLong(timestamp);
        String time = AppUtils.getDateTime(timeLong, "dd MMM yyyy");
        textView.setText(time);
    }

    @BindingAdapter(value = {"android:job_status", "type"}, requireAll = false)
    public static void setJobStatus(TextView textView, int status, int type) {
        String job_status = "";
        switch (status) {
            case 0:
                job_status = "Open";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.grey));
                break;
            case 1:
                if (type == 3) {
                    job_status = "Upcoming";
                } else if (type == 2) {
                    job_status = "Today";
                } else if (type == 1) {
                    job_status = "Now";
                } else {
                    job_status = "Now";
                }

                textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                job_status = "Cancelled";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.red_drak));
                break;
            case 3:
                job_status = "Arriving";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.color_orange));
                break;
            case 4:
                job_status = "Arrived";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.color_orange));
                break;

            case 6:
                job_status = "Verified";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.green));
                break;

            case 7:
                job_status = "Ongoing";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.yellow));
                break;

            case 8:
                job_status = "Completed";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.green));
                break;

            case 9:
                job_status = "Job Closed";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.grey));
                break;

            case 10:
                job_status = "No Access Charge";
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.red));
                break;
        }
        textView.setText(job_status);
    }


    @BindingAdapter(value = {"suggestions_text"})
    public static void setSuggestionText(TextView textView, SuggestionsModel model) {
        if (model.getSearch_type() == 0) {
            textView.setText(model.getFirstName() + " " + model.getLastName());
        } else {
            textView.setText(model.getName());
        }

    }


    @BindingAdapter(value = {"card_number"})
    public static void setImageUrl(TextView textView, String card_number) {
        String card = "";
        if (card_number != null) {
            int length = card_number.length();
            int s = length - 2;
            for (int i = 0; i < s; i++) {
                card = card + "*";
            }
            card = card + card_number.substring(s, length);
        }
        textView.setText(card);
    }


  /*  @BindingAdapter({"meetmetype"})
    public static void meetmetype(TextView textView, SingleInboxBean inboxBean) {
        if (inboxBean != null && inboxBean.getMeetme() != null) {
            switch (inboxBean.getMeetme().getRequest_status()) {
                case 1:
                    if (inboxBean.getUser_profile().getUser_id().equals(inboxBean.getMeetme().getUser_id()))
                        textView.setText(Html.fromHtml(String.format("You have accepted request for Meet Up sent by <b>%s %s</b>", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    else
                        textView.setText(Html.fromHtml(String.format("<b>%s %s</b> have accepted request for Meet Up", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    break;
                case 2:
                    if (inboxBean.getUser_profile().getUser_id().equals(inboxBean.getMeetme().getUser_id()))
                        textView.setText(Html.fromHtml(String.format("You have rejected request for Meet Up sent by  <b>%s %s</b>", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    else
                        textView.setText(Html.fromHtml(String.format("<b>%s %s</b> have rejected request for Meet Up", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    break;
                default:
                    if (inboxBean.getUser_profile().getUser_id().equals(inboxBean.getMeetme().getUser_id()))
                        textView.setText(Html.fromHtml(String.format("<b>%s %s</b> have sent you request for Meet Up", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    else
                        textView.setText(Html.fromHtml(String.format("You have send request to  <b>%s %s</b> for Meet Up", inboxBean.getUser_profile().getFirst_name(), inboxBean.getUser_profile().getLast_name())));
                    break;
            }


        } else
            textView.setText("");
    }*/


}
//    public static void setNumeric(TextView view, int numeric) {
//        view.setKeyListener(DigitsKeyListener.getInstance((numeric & SIGNED) != 0,
//                (numeric & DECIMAL) != 0));
//    }


//    private static final String TAG = "TextViewBindingAdapters";
//    public static final int INTEGER = 0x01;
//    public static final int SIGNED = 0x03;
//    public static final int DECIMAL = 0x05;
//    @BindingAdapter("android:text")
//    public static void setText(TextView view, CharSequence text) {
//        final CharSequence oldText = view.getText();
//        if (text == oldText || (text == null && oldText.length() == 0)) {
//            return;
//        }
//        if (text instanceof Spanned) {
//            if (text.equals(oldText)) {
//                return; // No change in the spans, so don't set anything.
//            }
//        } else if (!haveContentsChanged(text, oldText)) {
//            return; // No content changes, so don't set anything.
//        }
//        view.setText(text);
//    }
//    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
//    public static String getTextString(TextView view) {
//        return view.getText().toString();
//    }
//    @BindingAdapter({"android:autoText"})
//    public static void setAutoText(TextView view, boolean autoText) {
//        KeyListener listener = view.getKeyListener();
//        TextKeyListener.Capitalize capitalize = TextKeyListener.Capitalize.NONE;
//        int inputType = listener != null ? listener.getInputType() : 0;
//        if ((inputType & InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS) != 0) {
//            capitalize = TextKeyListener.Capitalize.CHARACTERS;
//        } else if ((inputType & InputType.TYPE_TEXT_FLAG_CAP_WORDS) != 0) {
//            capitalize = TextKeyListener.Capitalize.WORDS;
//        } else if ((inputType & InputType.TYPE_TEXT_FLAG_CAP_SENTENCES) != 0) {
//            capitalize = TextKeyListener.Capitalize.SENTENCES;
//        }
//        view.setKeyListener(TextKeyListener.getInstance(autoText, capitalize));
//    }
//    @BindingAdapter({"android:capitalize"})
//    public static void setCapitalize(TextView view, TextKeyListener.Capitalize capitalize) {
//        KeyListener listener = view.getKeyListener();
//        int inputType = listener.getInputType();
//        boolean autoText = (inputType & InputType.TYPE_TEXT_FLAG_AUTO_CORRECT) != 0;
//        view.setKeyListener(TextKeyListener.getInstance(autoText, capitalize));
//    }
//    @BindingAdapter({"android:bufferType"})
//    public static void setBufferType(TextView view, TextView.BufferType bufferType) {
//        view.setText(view.getText(), bufferType);
//    }
//    @BindingAdapter({"android:digits"})
//    public static void setDigits(TextView view, CharSequence digits) {
//        if (digits != null) {
//            view.setKeyListener(DigitsKeyListener.getInstance(digits.toString()));
//        } else if (view.getKeyListener() instanceof DigitsKeyListener) {
//            view.setKeyListener(null);
//        }
//    }
//    @BindingAdapter({"android:numeric"})
//    public static void setNumeric(TextView view, int numeric) {
//        view.setKeyListener(DigitsKeyListener.getInstance((numeric & SIGNED) != 0,
//                (numeric & DECIMAL) != 0));
//    }
//    @BindingAdapter({"android:phoneNumber"})
//    public static void setPhoneNumber(TextView view, boolean phoneNumber) {
//        if (phoneNumber) {
//            view.setKeyListener(DialerKeyListener.getInstance());
//        } else if (view.getKeyListener() instanceof DialerKeyListener) {
//            view.setKeyListener(null);
//        }
//    }
//    private static void setIntrinsicBounds(Drawable drawable) {
//        if (drawable != null) {
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        }
//    }
//    @BindingAdapter({"android:drawableBottom"})
//    public static void setDrawableBottom(TextView view, Drawable drawable) {
//        setIntrinsicBounds(drawable);
//        Drawable[] drawables = view.getCompoundDrawables();
//        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawable);
//    }
//    @BindingAdapter({"android:drawableLeft"})
//    public static void setDrawableLeft(TextView view, Drawable drawable) {
//        setIntrinsicBounds(drawable);
//        Drawable[] drawables = view.getCompoundDrawables();
//        view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
//    }
//    @BindingAdapter({"android:drawableRight"})
//    public static void setDrawableRight(TextView view, Drawable drawable) {
//        setIntrinsicBounds(drawable);
//        Drawable[] drawables = view.getCompoundDrawables();
//        view.setCompoundDrawables(drawables[0], drawables[1], drawable,
//                drawables[3]);
//    }
//    @BindingAdapter({"android:drawableTop"})
//    public static void setDrawableTop(TextView view, Drawable drawable) {
//        setIntrinsicBounds(drawable);
//        Drawable[] drawables = view.getCompoundDrawables();
//        view.setCompoundDrawables(drawables[0], drawable, drawables[2],
//                drawables[3]);
//    }
//    @BindingAdapter({"android:drawableStart"})
//    public static void setDrawableStart(TextView view, Drawable drawable) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            setDrawableLeft(view, drawable);
//        } else {
//            setIntrinsicBounds(drawable);
//            Drawable[] drawables = view.getCompoundDrawablesRelative();
//            view.setCompoundDrawablesRelative(drawable, drawables[1], drawables[2], drawables[3]);
//        }
//    }
//    @BindingAdapter({"android:drawableEnd"})
//    public static void setDrawableEnd(TextView view, Drawable drawable) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            setDrawableRight(view, drawable);
//        } else {
//            setIntrinsicBounds(drawable);
//            Drawable[] drawables = view.getCompoundDrawablesRelative();
//            view.setCompoundDrawablesRelative(drawables[0], drawables[1], drawable, drawables[3]);
//        }
//    }
//    @BindingAdapter({"android:imeActionLabel"})
//    public static void setImeActionLabel(TextView view, CharSequence value) {
//        view.setImeActionLabel(value, view.getImeActionId());
//    }
//    @BindingAdapter({"android:imeActionId"})
//    public static void setImeActionLabel(TextView view, int value) {
//        view.setImeActionLabel(view.getImeActionLabel(), value);
//    }
//    @BindingAdapter({"android:inputMethod"})
//    public static void setInputMethod(TextView view, CharSequence inputMethod) {
//        try {
//            Class<?> c = Class.forName(inputMethod.toString());
//            view.setKeyListener((KeyListener) c.newInstance());
//        } catch (ClassNotFoundException e) {
//            Log.d(TAG, "Could not create input method: " + inputMethod, e);
//        } catch (InstantiationException e) {
//            Log.d(TAG, "Could not create input method: " + inputMethod, e);
//        } catch (IllegalAccessException e) {
//            Log.d(TAG, "Could not create input method: " + inputMethod, e);
//        }
//    }
//    @BindingAdapter({"android:lineSpacingExtra"})
//    public static void setLineSpacingExtra(TextView view, float value) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            view.setLineSpacing(value, view.getLineSpacingMultiplier());
//        } else {
//            view.setLineSpacing(value, 1);
//        }
//    }
//    @BindingAdapter({"android:lineSpacingMultiplier"})
//    public static void setLineSpacingMultiplier(TextView view, float value) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            view.setLineSpacing(view.getLineSpacingExtra(), value);
//        } else {
//            view.setLineSpacing(0, value);
//        }
//    }
//    @BindingAdapter({"android:maxLength"})
//    public static void setMaxLength(TextView view, int value) {
//        InputFilter[] filters = view.getFilters();
//        if (filters == null) {
//            filters = new InputFilter[]{
//                    new InputFilter.LengthFilter(value)
//            };
//        } else {
//            boolean foundMaxLength = false;
//            for (int i = 0; i < filters.length; i++) {
//                InputFilter filter = filters[i];
//                if (filter instanceof InputFilter.LengthFilter) {
//                    foundMaxLength = true;
//                    boolean replace = true;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        replace = ((InputFilter.LengthFilter) filter).getMax() != value;
//                    }
//                    if (replace) {
//                        filters[i] = new InputFilter.LengthFilter(value);
//                    }
//                    break;
//                }
//            }
//            if (!foundMaxLength) {
//                // can't use Arrays.copyOf -- it shows up in API 9
//                InputFilter[] oldFilters = filters;
//                filters = new InputFilter[oldFilters.length + 1];
//                System.arraycopy(oldFilters, 0, filters, 0, oldFilters.length);
//                filters[filters.length - 1] = new InputFilter.LengthFilter(value);
//            }
//        }
//        view.setFilters(filters);
//    }
//    @BindingAdapter({"android:password"})
//    public static void setPassword(TextView view, boolean password) {
//        if (password) {
//            view.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        } else if (view.getTransformationMethod() instanceof PasswordTransformationMethod) {
//            view.setTransformationMethod(null);
//        }
//    }
//    @BindingAdapter({"android:shadowColor"})
//    public static void setShadowColor(TextView view, int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            float dx = view.getShadowDx();
//            float dy = view.getShadowDy();
//            float r = view.getShadowRadius();
//            view.setShadowLayer(r, dx, dy, color);
//        }
//    }
//    @BindingAdapter({"android:shadowDx"})
//    public static void setShadowDx(TextView view, float dx) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            int color = view.getShadowColor();
//            float dy = view.getShadowDy();
//            float r = view.getShadowRadius();
//            view.setShadowLayer(r, dx, dy, color);
//        }
//    }
//    @BindingAdapter({"android:shadowDy"})
//    public static void setShadowDy(TextView view, float dy) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            int color = view.getShadowColor();
//            float dx = view.getShadowDx();
//            float r = view.getShadowRadius();
//            view.setShadowLayer(r, dx, dy, color);
//        }
//    }
//    @BindingAdapter({"android:shadowRadius"})
//    public static void setShadowRadius(TextView view, float r) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            int color = view.getShadowColor();
//            float dx = view.getShadowDx();
//            float dy = view.getShadowDy();
//            view.setShadowLayer(r, dx, dy, color);
//        }
//    }
//    @BindingAdapter({"android:textSize"})
//    public static void setTextSize(TextView view, float size) {
//        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//    }
//    private static boolean haveContentsChanged(CharSequence str1, CharSequence str2) {
//        if ((str1 == null) != (str2 == null)) {
//            return true;
//        } else if (str1 == null) {
//            return false;
//        }
//        final int length = str1.length();
//        if (length != str2.length()) {
//            return true;
//        }
//        for (int i = 0; i < length; i++) {
//            if (str1.charAt(i) != str2.charAt(i)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    @BindingAdapter(value = {"android:beforeTextChanged", "android:onTextChanged",
//            "android:afterTextChanged", "android:textAttrChanged"}, requireAll = false)
//    public static void setTextWatcher(TextView view, final BeforeTextChanged before,
//                                      final OnTextChanged on, final AfterTextChanged after,
//                                      final InverseBindingListener textAttrChanged) {
//        final TextWatcher newValue;
//        if (before == null && after == null && on == null && textAttrChanged == null) {
//            newValue = null;
//        } else {
//            newValue = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    if (before != null) {
//                        before.beforeTextChanged(s, start, count, after);
//                    }
//                }
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (on != null) {
//                        on.onTextChanged(s, start, before, count);
//                    }
//                    if (textAttrChanged != null) {
//                        textAttrChanged.onChange();
//                    }
//                }
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (after != null) {
//                        after.afterTextChanged(s);
//                    }
//                }
//            };
//        }
//        final TextWatcher oldValue = ListenerUtil.trackListener(view, newValue, R.id.textWatcher);
//        if (oldValue != null) {
//            view.removeTextChangedListener(oldValue);
//        }
//        if (newValue != null) {
//            view.addTextChangedListener(newValue);
//        }
//    }
//    public interface AfterTextChanged {
//        void afterTextChanged(Editable s);
//    }
//    public interface BeforeTextChanged {
//        void beforeTextChanged(CharSequence s, int start, int count, int after);
//    }
//    public interface OnTextChanged {
//        void onTextChanged(CharSequence s, int start, int before, int count);
//    }
//}