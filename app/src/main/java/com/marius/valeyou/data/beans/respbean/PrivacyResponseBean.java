package com.marius.valeyou.data.beans.respbean;

import android.os.Parcel;
import android.os.Parcelable;

public class PrivacyResponseBean implements Parcelable {

    /**
     * id : 1
     * about : <p><strong style="font-family: &quot;Open Sans&quot;, Arial, sans-serif; text-align: justify;">Lorem Ipsum</strong><span style="font-family: &quot;Open Sans&quot;, Arial, sans-serif; text-align: justify;">&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</span><br></p>
     */

    private int id;
    private String term;
    private String privacy;
    private String about;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.term);
        dest.writeString(this.privacy);
        dest.writeString(this.about);
    }

    public PrivacyResponseBean() {
    }

    protected PrivacyResponseBean(Parcel in) {
        this.id = in.readInt();
        this.term = in.readString();
        this.privacy = in.readString();
        this.about = in.readString();
    }

    public static final Parcelable.Creator<PrivacyResponseBean> CREATOR = new Parcelable.Creator<PrivacyResponseBean>() {
        @Override
        public PrivacyResponseBean createFromParcel(Parcel source) {
            return new PrivacyResponseBean(source);
        }

        @Override
        public PrivacyResponseBean[] newArray(int size) {
            return new PrivacyResponseBean[size];
        }
    };
}
