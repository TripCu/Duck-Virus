public class Watcher {
    public static void main(String[] args) {
        while (true) {
            try {
                Process process = new ProcessBuilder(

                        "java", "-cp", System.getProperty("java.class.path"), "DuckSprite").start();

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    break; // Correct password entered, do not restart
                } else {
                    System.out.println("DuckSprite was closed unexpectedly. Restarting...");
                    Thread.sleep(1000); // Brief delay before restarting
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
