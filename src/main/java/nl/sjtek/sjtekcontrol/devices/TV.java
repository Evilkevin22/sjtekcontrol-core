package nl.sjtek.sjtekcontrol.devices;

import nl.sjtek.sjtekcontrol.data.Arguments;
import nl.sjtek.sjtekcontrol.utils.Executor;
import org.json.JSONObject;

import java.io.IOException;

@SuppressWarnings("unused")
public class TV {

    // https://github.com/ypid/lgcommander

    private static final String LGCOMMANDER_PATH = "/usr/bin/lgcommander";
    private static final String HOST = "192.168.0.101";
    private static final int PORT = 8080;
    private static final String KEY = "00000";
    private static final String PROTOCOL = "roap";

    private String[] getArgumentsForCommand(String command) {
        return new String[]{LGCOMMANDER_PATH, "-H " + HOST, "-p " + PORT, "-P " + PROTOCOL, "-k " + KEY, "-c " + command};
    }

    public void off(Arguments arguments) {
        new ExecuteThread("1");
    }

    public void volumelower(Arguments arguments) {
        new ExecuteThread("24");
    }

    public void volumeraise(Arguments arguments) {
        new ExecuteThread("25");
    }

    private class ExecuteThread extends Thread {

        private final String[] command;

        public ExecuteThread(String command) {
            this.command = getArgumentsForCommand(command);
        }

        @Override
        public void run() {
            super.run();
            try {
                Executor.execute(command);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject.toString();
    }
}
