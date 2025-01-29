import java.time.LocalDateTime;

public class Statistics {

    private int totalTraffic = 0;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

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
    }
}