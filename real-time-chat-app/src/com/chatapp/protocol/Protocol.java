package com.chatapp.protocol;

public final class Protocol {
    private Protocol() {}

    // Client -> Server commands
    public static String join(String room, String username) { return "JOIN " + room + "|" + username; }
    public static String leave() { return "LEAVE"; }
    public static String msg(String text) { return "MSG " + text; }
    public static String pm(String target, String text) { return "PM " + target + "|" + text; }
    public static String list() { return "LIST"; }
    public static String history() { return "HISTORY"; }
    public static String quit() { return "QUIT"; }
}