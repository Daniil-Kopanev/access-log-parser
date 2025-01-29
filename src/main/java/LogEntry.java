import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class LogEntry {

    final String ipAddr;
    final LocalDateTime time;
    final HttpMethod method;
    final String path;
    final int responseSize;
    final String pathUrl;
    final String referer;
    final UserAgent userAgent;
    final int responseCode;

    public LogEntry(String line) {
        List<String> l = List.of(line.split(" "));
        this.ipAddr = l.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss[ZZZ]", Locale.ENGLISH);
        this.time = LocalDateTime.parse(l.get(3).substring(1) + l.get(4).substring(0, l.get(4).indexOf(']')), formatter);
        this.method = HttpMethod.valueOf(l.get(5).substring(1));
        this.path = l.get(6);
        this.referer = l.get(7).replaceAll("\"", "");
        this.responseCode = Integer.parseInt(l.get(8));
        this.responseSize = Integer.parseInt(l.get(9));
        this.pathUrl = l.get(10).replaceAll("\"", "");
        if (!l.get(11).equals("\"-\"")) {
            this.userAgent = new UserAgent(line.substring(line.indexOf(l.get(11))).replaceAll("\"", ""));
        } else this.userAgent = new UserAgent(l.get(11).replaceAll("\"", ""));
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public enum HttpMethod {
        GET, POST, HEAD, PATCH, OPTIONS, DELETE;
    }
}
