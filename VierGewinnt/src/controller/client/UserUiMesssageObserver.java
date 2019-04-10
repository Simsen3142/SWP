package controller.client;

import models.wifimessages.UserUIMessage;

public interface UserUiMesssageObserver {
    public void onMessageReceived(UserUIMessage message);
}
