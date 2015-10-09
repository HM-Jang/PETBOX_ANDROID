package com.petbox.shop.Delegate;

/**
 * Created by petbox on 2015-09-22.
 */
public interface LoginManagerDelegate {
    public void prevRunningLogin();
    public void runningLogin();
    public void afterRunningLogin(int responseCode);
}
