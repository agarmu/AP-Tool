package aptool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Integer;

public class Identifier {
    
    public int unit;
    public int subunit;
    public int video;
    Identifier(int unit, int subunit, int video){
        this.unit = unit;
        this.subunit = subunit;
        this.video = video;
    }
    public static Identifier parse(String match) throws NumberFormatException {
        final String regex = "^(\\d+)\\.(\\d)V(\\d)$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(match);
        boolean matchFound = matcher.find();
            if(matchFound) {
                int unit = Integer.parseInt(matcher.group(1));
                int subunit = Integer.parseInt(matcher.group(2));
                int video = Integer.parseInt(matcher.group(3));
                if (unit <= 0 || subunit <= 0 || video <= 0) {
                    throw new NumberFormatException("The unit, subunit, and video values must be greater than 0.");
                }
                return new Identifier(unit,subunit,video);
            }
            throw new NumberFormatException("Your input tuple " + match + " was not recognizable.");
    }
    public String format() {
        return this.unit + "." + this.subunit + "V" + this.video;
    }
}
