package nl.fontys.s3;

public class Greeting {
    public String hello(String name) {
        if (name == null || name.isBlank()) {
            return "Hello dear! What's your name?";
        }

        return "Hello " + name + "!";
    }
}
