import java.time.LocalDateTime;
import java.util.*;

public class Statistics {

    private int totalTraffic = 0;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> allPages = new HashSet<>();
    private HashSet<String> nonExistPages = new HashSet<>();
    private HashMap<String, Integer> occurrenceOs = new HashMap<>();
    private HashMap<String, Integer> occurrenceBrow = new HashMap<>();
    private HashMap<String, Double> fractionOs = new HashMap<>();
    private HashMap<String, Double> fractionBrow = new HashMap<>();

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
        if (logEntry.getResponseCode() == 404) {
            this.nonExistPages.add(logEntry.getPathUrl());
        }

        if (!(logEntry.getUserAgent().getTypeOs().equals("-"))) {
            if (occurrenceOs.containsKey(logEntry.getUserAgent().getTypeOs())) {
                occurrenceOs.put(logEntry.getUserAgent().getTypeOs(), occurrenceOs.get(logEntry.getUserAgent().getTypeOs()) + 1);
            } else occurrenceOs.put(String.valueOf(logEntry.getUserAgent().getTypeOs()), 1);
        }

        if (!(logEntry.getUserAgent().getTypeBrowser().equals("-"))) {
            if (occurrenceBrow.containsKey(logEntry.getUserAgent().getTypeBrowser())) {
                occurrenceBrow.put(logEntry.getUserAgent().getTypeBrowser(), occurrenceBrow.get(logEntry.getUserAgent().getTypeBrowser()) + 1);
            } else occurrenceBrow.put(String.valueOf(logEntry.getUserAgent().getTypeBrowser()), 1);
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

    public HashMap<String, Double> getStatisticsBrow() {
        Integer countRequest = 0;
        for (Map.Entry<String, Integer> entry : occurrenceBrow.entrySet()) {
            countRequest += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry : occurrenceBrow.entrySet()) {
            fractionBrow.put(entry.getKey(), entry.getValue().doubleValue() / countRequest.doubleValue());

        }
        return fractionBrow;
    }

    public ArrayList<String> getAllExistPages() {
        List<String> pages = new ArrayList<>();
        for (String page : allPages) {
            pages.add(page);
        }
        return new ArrayList<>(pages);
    }

    public ArrayList<String> getNonExistPages() {
        List<String> pages = new ArrayList<>();
        for (String page : nonExistPages) {
            pages.add(page);
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
        this.nonExistPages.clear();
        this.occurrenceBrow.clear();
        this.fractionBrow.clear();
    }
}