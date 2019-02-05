package com.pagatodo.yaganaste.modules.management.singletons;

import com.pagatodo.yaganaste.modules.management.request.NotificationPayRequest;

public class NotificationSingleton {

    private static NotificationSingleton notificationSingleton;
    private NotificationPayRequest request;

    private NotificationSingleton() {
        request = new NotificationPayRequest();
    }

    public static synchronized NotificationSingleton getInstance() {
        if (notificationSingleton == null)
            notificationSingleton = new NotificationSingleton();
        return notificationSingleton;
    }

    public NotificationPayRequest getRequest() {
        return request;
    }
}
