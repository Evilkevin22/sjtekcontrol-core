package nl.sjtek.sjtekcontrol.utils;

import nl.sjtek.sjtekcontrol.data.Settings;

import java.io.*;

public class Speech {

//    private static final String TEXT_TO_SPEECH_SERVICE =
//            "https://api.voicerss.org/?key=6ae73efdcce24344acc26e157f526bea?hl=en-gb?src=";
//    private static final String USER_AGENT =
//            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) " +
//                    "Gecko/20100101 Firefox/11.0";

    private Speech() { }

    public static void main(String[] args) throws Exception {
        speek("Hello Tijn. You just lost the game.");
    }

//    private static void download(String text, File output) throws IOException {
//        String strUrl = TEXT_TO_SPEECH_SERVICE + text;
//        URL url = new URL(strUrl);
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.addRequestProperty("User-Agent", USER_AGENT);
//        connection.connect();
//
//        BufferedInputStream bufIn =
//                new BufferedInputStream(connection.getInputStream());
//        byte[] buffer = new byte[1024];
//        int n;
//        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
//        while ((n = bufIn.read(buffer)) > 0) {
//            bufOut.write(buffer, 0, n);
//        }
//
//        BufferedOutputStream out =
//                new BufferedOutputStream(new FileOutputStream(output));
//        out.write(bufOut.toByteArray());
//        out.flush();
//        out.close();
//    }
//
//    public static synchronized void speek(String text) {
//        try {
//            text = URLEncoder.encode(text, "utf-8");
//            File soundFile = new File("/tmp/sjtekcontrol/voice/" + text + ".mp3");
//            if (!soundFile.exists()) {
//                boolean mkdirs = soundFile.getParentFile().mkdirs();
//                download(text, soundFile);
//            }
//            AdvancedPlayer player = new AdvancedPlayer(new FileInputStream(soundFile));
//            player.play();
//            player.close();
//        } catch (IOException | JavaLayerException e) {
//            e.printStackTrace();
//        }
//    }

    public static synchronized void speek(String text) {
        try {
            Executor.execute(new String[]{"/usr/bin/espeak", "-a " + Settings.getInstance().getSpeechVolume(), "\"" + text + "\""});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void speekAsync(String text) {
        new SpeechAsyncThread(text).start();
    }

    private static class SpeechAsyncThread extends Thread {
        private final String text;

        public SpeechAsyncThread(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            speek(text);
        }
    }
}
