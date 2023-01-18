import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "List of strings:" + strings.toString();
        } else if (url.getPath().equals("/search")) {
            System.out.println("Path: " + url.getPath());
            if (url.getQuery()!=null) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String substring = parameters[1];
                    ArrayList<String> containingStrings = new ArrayList<String>(); 
                    for (String s : strings) {
                        if (s.contains(substring)) {
                            containingStrings.add(s);
                        }
                    }
                    return containingStrings.toString();
                }
            }
            return String.format("Invalid query!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                if (url.getQuery()!=null) {
                    String[] parameters = url.getQuery().split("=");
                    if (parameters[0].equals("s")) {
                        strings.add(parameters[1]);
                        return String.format("String \"%s\" added!", parameters[1]);
                }
            }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
