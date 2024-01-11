public class Watcher {
    public static void main(String[] args) {
        while (true) {
            try {
                Process process = new ProcessBuilder(
                        "java", "-cp", System.getProperty("java.class.path"), "DuckSprite").start();

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    break; // Exit the watcher loop
                } else {
                    System.out.println("DuckSprite was closed unexpectedly. Restarting...");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
