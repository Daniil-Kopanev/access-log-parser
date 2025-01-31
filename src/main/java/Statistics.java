import java.time.LocalDateTime;
import java.util.*;

public class Statistics {

    private int totalTraffic = 0;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    HashSet<String> allPages = new HashSet<>();
    HashMap<String, Integer> occurrenceOs = new HashMap<>();
    HashMap<String, Double> fractionOs = new HashMap<>();

    public Statistics() {
    }

    public int getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public void addEntry(LogEntry logEntry) {
        this.totalTraffic += logEntry.getResponseSize() / 1000; // переводим байты в Кбайты, т.к. общая сумма не помещается в int
        if (minTime == null) {
            this.minTime = logEntry.getTime();
        } else {
            if (minTime.isAfter(logEntry.getTime())) {
                this.minTime = logEntry.getTime();
            }
        }

        if (maxTime == null) {
            this.maxTime = logEntry.getTime();
        } else {
            if (maxTime.isBefore(logEntry.getTime())) {
                this.maxTime = logEntry.getTime();
            }
        }

        if (logEntry.getResponseCode() == 200) {
            this.allPages.add(logEntry.getPathUrl());
        }

        if (!(logEntry.getUserAgent().getTypeOs().equals("-"))) {
            if (occurrenceOs.containsKey(logEntry.getUserAgent().getTypeOs())) {
                occurrenceOs.put(logEntry.getUserAgent().getTypeOs(), occurrenceOs.get(logEntry.getUserAgent().getTypeOs()) + 1);
            } else occurrenceOs.put(String.valueOf(logEntry.getUserAgent().getTypeOs()), 1);
        }
    }

    public HashMap<String, Double> getStatisticsOs() {
        Integer countRequest = 0;
        for (Map.Entry<String, Integer> entry : occurrenceOs.entrySet()) {
            countRequest += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry : occurrenceOs.entrySet()) {
            fractionOs.put(entry.getKey(), entry.getValue().doubleValue() / countRequest.doubleValue());
        }
        return fractionOs;
    }

    public ArrayList<String> getAllExistPages() {
        List<String> pages = new ArrayList<>();
        for (String allPage : allPages) {
            pages.add(allPage);
        }
        return new ArrayList<>(pages);
    }

    public int getTrafficRate() {

        int hour = 0;
        if (maxTime.getDayOfMonth() > minTime.getDayOfMonth()) {
            int diffDay = maxTime.getDayOfMonth() - minTime.getDayOfMonth();
            if (diffDay == 1) {
                hour = 24 - minTime.getHour() + maxTime.getHour();
            }
            if (diffDay > 1) {
                hour = 24 - minTime.getHour() + maxTime.getHour() + 24 * (diffDay - 1);
            }
        } else {
            hour = maxTime.getHour() - minTime.getHour();
        }
        return totalTraffic / hour;
    }

    public void clear() {
        this.totalTraffic = 0;
        this.maxTime = null;
        this.minTime = null;
        this.allPages.clear();
        this.occurrenceOs.clear();
        this.fractionOs.clear();
    }
}