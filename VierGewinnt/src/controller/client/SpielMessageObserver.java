package controller.client;

import models.wifimessages.SpielMessage;

public interface SpielMessageObserver {
    public void onMessageReceived(SpielMessage message);
}
