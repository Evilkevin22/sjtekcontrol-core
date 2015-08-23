package nl.sjtek.sjtekcontrol.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {

    /**
     * Execute a command.
     * @param command
     * @return Output of the command
     * @throws IOException
     * @throws InterruptedException
     */
    public static String execute(String command) throws IOException, InterruptedException {
        StringBuffer output = new StringBuffer();

        Process process;
        process = Runtime.getRuntime().exec(command);
        process.waitFor();
        int exitValue = process.exitValue();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = "";
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        return output.toString();
    }
}
