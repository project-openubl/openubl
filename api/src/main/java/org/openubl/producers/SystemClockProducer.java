package org.openubl.producers;

import io.github.project.openubl.xmlbuilderlib.utils.SystemClock;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;
import java.util.TimeZone;

@ApplicationScoped
public class SystemClockProducer implements SystemClock {

    private TimeZone timeZone = TimeZone.getTimeZone("America/Lima");

    @Override
    public TimeZone getTimeZone() {
        return timeZone;
    }

    @Override
    public Calendar getCalendarInstance() {
        return Calendar.getInstance();
    }
}
