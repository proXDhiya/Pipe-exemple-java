import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class app {
    public static void main(String[] argc) {
        try {
            PipedInputStream in = new PipedInputStream();
            PipedOutputStream out = new PipedOutputStream(in);

            // Two threads one write and one read
            // write end when user type "exit"

            Thread write = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();
                    while (!line.equals("exit")) {
                        try {
                            out.write(line.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        line = scanner.nextLine();
                    }

                    try {
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread read = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] buffer = new byte[1024];
                        int len = in.read(buffer);
                        while (len != -1) {
                            System.out.println(new String(buffer, 0, len));
                            len = in.read(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            write.start();
            read.start();
        } catch (Exception e) {;}
    }
}
