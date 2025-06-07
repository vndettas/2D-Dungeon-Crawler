package Helper;

public class Debugger {

    private static final boolean debug_is_active = false;

    public static void log(String msg) {
        if (debug_is_active) {
            System.err.println(msg);
        }
    }

        public static void log(int msg){
            if(debug_is_active){
                System.err.println(msg);
            }
    }
}
