package audi.com.numberle;

import android.content.Intent;
import android.widget.RemoteViewsService;

import audi.com.numberle.adapter.AppointmentProvider;

/**
 * Created by Audi on 20/04/17.
 */

public class NumberLeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppointmentProvider(this, intent);
    }
}
