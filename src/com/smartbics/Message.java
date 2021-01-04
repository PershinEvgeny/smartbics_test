package com.smartbics;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Comparable<Message> {
    private LocalDateTime dateTime;
    private String level;
    private String desc;

    private String groupHour;
    private String groupMin;

    public Message(LocalDateTime dateTime, String level, String desc) {
        this.dateTime = dateTime;
        this.level = level;
        this.desc = desc;
        grouping();
    }

    private void grouping() {
        groupHour = getGroupDate() + getHourDia();
        groupMin = getGroupDate() + getMinDia();
    }

    @Override //для сортировки по времени
    public int compareTo(Message o) {
        return this.dateTime.compareTo(o.dateTime);
    }

    private String getGroupDate() {
        //2020-01-22T08:32:56.191
        //2019-01-01, 11.00-12.00
        return dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDayOfMonth();
    }

    private String getHourDia() {
        return ", " + dateTime.getHour() + ".00-" + (dateTime.getHour() + 1) + ".00";
    }

    private String getMinDia() {
        return ", " +
                dateTime.getHour() + "." + dateTime.getMinute() +
                "-" + dateTime.getHour() + "." + (dateTime.getMinute() + 1);
    }

    public String getGroupHour() {
        return groupHour;
    }

    public String getGroupMin() {
        return groupMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message that = (Message) o;
        return Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(level, that.level) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(groupHour, that.groupHour) &&
                Objects.equals(groupMin, that.groupMin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, level, desc, groupHour, groupMin);
    }
}
