package com.petbox.shop.Delegate;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by petbox on 2015-10-08.
 */
public interface HttpPostDelegate {
    public void prevRunningHttpPost();
    public String getPostUrl();
    public List<NameValuePair> getNameValuePairs();
    public void runningHttpPost();
    public void afterRunningHttpPost(int responseCode);
}
