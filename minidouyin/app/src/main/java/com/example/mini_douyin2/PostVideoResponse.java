package com.example.mini_douyin2;

import com.google.gson.annotations.SerializedName;

public class PostVideoResponse {
    @SerializedName("url") private String url;
    @SerializedName("success") private boolean success;
    @SerializedName("item") private Item item;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override public String toString() {
        return "PostVideoResponse{" +
                "playUrl='" + url + '\'' +
                ", success=" + success +
                '}';
    }

    public static class Item {
        @SerializedName("student_id")
        private String student_id;

        @SerializedName("user_name")
        private String user_name;

        @SerializedName("image_url")
        private String image_url;

        @SerializedName("video_url")
        private String video_url;
    }
}
