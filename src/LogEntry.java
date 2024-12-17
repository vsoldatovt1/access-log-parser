import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    public final String ipAddr;
    private final LocalDateTime dateRequest;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final String userAgent;

    public LogEntry(String line) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s-\\s-\\s\\[(.*?)\\]\\s\"(\\w+)\\s([^\"]+)\\sHTTP/1.0\"\\s(\\d+)\\s(\\d+)\\s\"([^\"]*)\"\\s\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            ipAddr = matcher.group(1);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/MMM/yyyy:HH:mm:ss Z").toFormatter(Locale.ENGLISH);
            dateRequest = LocalDateTime.parse(matcher.group(2),formatter);
            method = HttpMethod.valueOf(matcher.group(3).toUpperCase());
            path = matcher.group(4);
            responseCode = Integer.parseInt(matcher.group(5));
            responseSize = Integer.parseInt(matcher.group(6));
            referer = matcher.group(7);
            userAgent = matcher.group(8);
        } else throw new IllegalArgumentException("Line in log is incorrect");
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getDateRequest() {
        return dateRequest;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
