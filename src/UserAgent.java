import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    private final String os;
    private final String browser;


    public UserAgent(String userAgent) {
        String regexForOs = "(Windows NT|Linux|Mac OS X)";
        Pattern patternForUserAgent = Pattern.compile(regexForOs);
        Matcher matcherForUserAgent = patternForUserAgent.matcher(userAgent);
        if (matcherForUserAgent.find()) {
            os = matcherForUserAgent.group(1);
        } else os = "Unknown os";

        String regExForFirefox = ".*(?:Firefox).*";
        Pattern patternForFirefox = Pattern.compile(regExForFirefox);
        Matcher matcherForFireFoxBrowser = patternForFirefox.matcher(userAgent);

        String commonRegExForBrowser = ".*(KHTML).*(Chrome).*(Safari).*";
        Pattern patternForCommonBrowser = Pattern.compile(commonRegExForBrowser);
        Matcher matcherForCommonBrowser = patternForCommonBrowser.matcher(userAgent);

        String regExForEdge = ".*(?:Edg).*";
        Pattern patternForEdge = Pattern.compile(regExForEdge);
        Matcher matcherForEdge = patternForEdge.matcher(userAgent);

        String regExForSafari = ".*(?:Mobile).*";
        Pattern patternForSafari = Pattern.compile(regExForSafari);
        Matcher matcherForSafari = patternForSafari.matcher(userAgent);

        String regExForOpera = ".*(?:OPR|Presto).*";
        Pattern patternForOpera = Pattern.compile(regExForOpera);
        Matcher matcherForOpera = patternForOpera.matcher(userAgent);

        if (matcherForFireFoxBrowser.find()) {
            browser = "Firefox";
        } else if (matcherForCommonBrowser.find()) {
            if (matcherForEdge.find()) {
                browser = "Edge";
            } else if (matcherForSafari.find() && Objects.equals(os, "Mac OS")) {
                browser = "Safari";
            } else if (matcherForOpera.find()) {
                browser = "Opera";
            } else {
                browser = "Chrome";
            }
        } else {
            browser = "Unknown browser";
        }
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "os='" + os + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}