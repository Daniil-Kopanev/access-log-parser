public class UserAgent {

    final String typeBrowser;
    final String typeOs;

    public UserAgent(String userAgent) {
        String s;
        String s2 = "";

        if (userAgent.equals("-")) {
            this.typeBrowser = "-";
        } else {
            if (userAgent.indexOf('/') == -1) {
                s = userAgent.trim();
            } else {
                if (!(userAgent.indexOf('/') == -1)) {
                    s = userAgent.substring(0, userAgent.indexOf('/')).trim();
                } else s = "";
            }
            this.typeBrowser = String.valueOf(TypeBrowse.forValue(s));
        }
        if (getTypeBrowser().equals("-")) {
            this.typeOs = "-";
        } else {
            for (TypeOs typeOs1 : TypeOs.values()) {
                int i = userAgent.indexOf(typeOs1.name);
                if (!(i == -1)) {
                    int i2 = i + typeOs1.name.length();
                    s2 = userAgent.substring(i, i2);
                    break;
                } else s2 = "";
            }
            this.typeOs = String.valueOf(TypeOs.forValue(s2));
        }
    }

    public String getTypeOs() {
        return typeOs;
    }

    public String getTypeBrowser() {
        return typeBrowser;
    }

    @Override
    public String toString() {
        return typeBrowser + "/" + typeOs;
    }

    public enum TypeBrowse {
        EDGE("Edge"), MOZILLA("Mozilla"), OPERA("Opera"), CHROME("Chrome"), OTHER("Other");

        final String name;

        TypeBrowse(String name) {
            this.name = name;
        }

        public static String forValue(String nameBrowser) {
            for (TypeBrowse typeBrowse : TypeBrowse.values()) {
                if (typeBrowse.name.equals(nameBrowser)) {
                    return typeBrowse.name;
                }
            }
            return TypeBrowse.OTHER.name;
        }
    }

    public enum TypeOs {
        WINDOWS("Windows"), MACOS("Mac OS"), LINUX("Linux");

        final String name;

        TypeOs(String name) {
            this.name = name;
        }

        public static String forValue(String nameOs) {
            for (TypeOs typeOs1 : TypeOs.values()) {
                if (typeOs1.name.equals(nameOs)) {
                    return typeOs1.name;
                }
            }
            return "-";
        }
    }

}


