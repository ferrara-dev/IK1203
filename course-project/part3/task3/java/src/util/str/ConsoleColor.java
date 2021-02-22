package src.util.str;

public enum ConsoleColor {
    RED{
        @Override
        public String setColor(String s) {
            return String.format("%s%s%s",TextColor.RED,s,TextColor.RESET);
        }
    },
    RED_BOLD{
        public String setColor(String s){
           return String.format("%s%s%s",TextColor.RED_BOLD,s,TextColor.RESET);
        }
    },
    GREEN{
        @Override
        public String setColor(String s) {
            return String.format("%s%s%s",TextColor.GREEN,s,TextColor.RESET);
        }
    };

    public abstract String setColor(String s);
}
