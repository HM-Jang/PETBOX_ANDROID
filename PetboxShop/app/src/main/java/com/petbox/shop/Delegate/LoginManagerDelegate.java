package com.petbox.shop.Delegate;

/**
 * Created by petbox on 2015-09-22.
 */
public interface LoginManagerDelegate {
    public void prevRunning();
    public void running();
    public void afterRunning(int responseCode);
}
