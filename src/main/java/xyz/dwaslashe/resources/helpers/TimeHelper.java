package xyz.dwaslashe.resources.helpers;

import xyz.dwaslashe.lang.Lang;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ~WuShei
 */
public class TimeHelper {

    public static TimeHelper getTimeHelperByUTC(String utc){
        ZonedDateTime date = ZonedDateTime.now(ZoneId.of(utc));
        return new TimeHelper(date.toInstant().toEpochMilli());
    }

    public static Helper helper = new Helper();

    private long time;

    public TimeHelper(){

    }

    public TimeHelper(long time) {
        this.time = time;
    }

    public TimeHelper(long time, long subtract){
        this.time = time - subtract;
    }

    public String simpleDate(){
        return new SimpleDateFormat("hh:mm:ss dd/MM/yy").format(time);
    }

    public String toString(Lang lang){
        StringBuilder builder = new StringBuilder();

        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        days = TimeUnit.MILLISECONDS.toDays(time);
        if(days > 0){
            builder.append(days).append(lang.get("time.days", String.class));
            time -= TimeUnit.DAYS.toMillis(days);
        }
        hours = TimeUnit.MILLISECONDS.toHours(time);
        if(hours > 0){
            builder.append(" ").append(hours).append(lang.get("time.hours", String.class));
            time -= TimeUnit.HOURS.toMillis(hours);
        }
        minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if(minutes > 0){
            builder.append(" ").append(minutes).append(lang.get("time.minutes", String.class));
            time -= TimeUnit.MINUTES.toMillis(minutes);
        }
        seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        if(seconds > 0){
            builder.append(" ").append(seconds).append(lang.get("time.seconds", String.class));
            time -= TimeUnit.SECONDS.toMillis(seconds);
        }
        if(time > 0){
            builder.append(" ").append(time).append(lang.get("time.millis", String.class));
        }

        if(builder.toString().isEmpty()) builder.append("1").append(lang.get("time.millis", String.class));
        return helper.getFirstLetter(builder.toString()).equals(" ") ? builder.toString().replaceFirst(" ", "") : builder.toString();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        days = TimeUnit.MILLISECONDS.toDays(time);
        if(days > 0){
            builder.append(days).append("d");
            time -= TimeUnit.DAYS.toMillis(days);
        }
        hours = TimeUnit.MILLISECONDS.toHours(time);
        if(hours > 0){
            builder.append(" ").append(hours).append("h");
            time -= TimeUnit.HOURS.toMillis(hours);
        }
        minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        if(minutes > 0){
            builder.append(" ").append(minutes).append("min");
            time -= TimeUnit.MINUTES.toMillis(minutes);
        }
        seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        if(seconds > 0){
            builder.append(" ").append(seconds).append("sek");
            time -= TimeUnit.SECONDS.toMillis(seconds);
        }
        if(time > 0){
            builder.append(" ").append(time).append("ms");
        }

        if(builder.toString().isEmpty()) builder.append("1ms");
        return helper.getFirstLetter(builder.toString()).equals(" ") ? builder.toString().replaceFirst(" ", "") : builder.toString();
    }
    public TimeHelper parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;

            while(m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for(int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        if (m.group(1) != null && !m.group(1).isEmpty()) {
                            years = Integer.parseInt(m.group(1));
                        }

                        if (m.group(2) != null && !m.group(2).isEmpty()) {
                            months = Integer.parseInt(m.group(2));
                        }

                        if (m.group(3) != null && !m.group(3).isEmpty()) {
                            weeks = Integer.parseInt(m.group(3));
                        }

                        if (m.group(4) != null && !m.group(4).isEmpty()) {
                            days = Integer.parseInt(m.group(4));
                        }

                        if (m.group(5) != null && !m.group(5).isEmpty()) {
                            hours = Integer.parseInt(m.group(5));
                        }

                        if (m.group(6) != null && !m.group(6).isEmpty()) {
                            minutes = Integer.parseInt(m.group(6));
                        }

                        if (m.group(7) != null && !m.group(7).isEmpty()) {
                            seconds = Integer.parseInt(m.group(7));
                        }
                        break;
                    }
                }
            }

            if (!found) {
                this.time = -1;
            } else {
                Calendar c = new GregorianCalendar();
                if (years > 0) {
                    c.add(Calendar.YEAR, years * (future ? 1 : -1));
                }

                if (months > 0) {
                    c.add(Calendar.MONTH, months * (future ? 1 : -1));
                }

                if (weeks > 0) {
                    c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
                }

                if (days > 0) {
                    c.add(Calendar.DATE, days * (future ? 1 : -1));
                }

                if (hours > 0) {
                    c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
                }

                if (minutes > 0) {
                    c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
                }

                if (seconds > 0) {
                    c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
                }

                Calendar max = new GregorianCalendar();
                max.add(Calendar.YEAR, 10);
                this.time = c.after(max) ? max.getTimeInMillis() : c.getTimeInMillis();
            }
            return this;
        } catch (Exception var14) {
            this.time = -1;
            return this;
        }
    }

    public String secondsToString() {
        this.time -= System.currentTimeMillis();
        return toString();
    }
}
