package nl.sjtek.control.core.network;

import com.google.common.eventbus.Subscribe;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.sjtek.control.core.ampq.AMQP;
import nl.sjtek.control.core.events.Bus;
import nl.sjtek.control.core.modules.*;
import nl.sjtek.control.core.settings.SettingsManager;
import nl.sjtek.control.core.utils.Personalise;
import nl.sjtek.control.core.utils.Speech;
import nl.sjtek.control.data.actions.ActionInterface;
import nl.sjtek.control.data.settings.User;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"UnusedParameters", "unused"})
public class ApiHandler implements HttpHandler {

    public static final String CONTEXT = "/api";
    private static ApiHandler instance = new ApiHandler();
    private Map<String, BaseModule> modules = new HashMap<>();
    private WSServer wsServer;
    private AMQP amqp;

    private ApiHandler() {

        ResponseCache.getInstance();

        System.out.println("Loading modules:");

        System.out.println(" - audio");
        modules.put("audio", new Audio("audio").init());
        System.out.println(" - music");
        BaseModule musicNaspoleon;
        Music musicWouter;

        try {
            musicNaspoleon = new Music("music").init();
        } catch (UnknownHostException | URISyntaxException e) {
            e.printStackTrace();
            musicNaspoleon = null;
        }

//        try {
//            musicWouter = new Music("music-wouter", "10.10.0.4", 6600);
//        } catch (UnknownHostException | URISyntaxException e) {
//            e.printStackTrace();
//            musicWouter = null;
//        }

        modules.put("music", musicNaspoleon);
//        if (musicWouter != null) modules.put("music-wouter", musicWouter);

        System.out.println(" - lights");
        modules.put("lights", new Lights("lights").init());
        System.out.println(" - temperature");
        modules.put("temperature", new Temperature("temperature").init());
        System.out.println(" - tv");
        modules.put("tv", new TV("tv").init());
        System.out.println(" - sonarr");
        modules.put("sonarr", new Sonarr("sonarr").init());
        System.out.println(" - quotes");
        modules.put("quotes", new Quotes("quotes").init());
        System.out.println(" - NFC");
        modules.put("nfc", new NFC("nfc").init());
        System.out.println(" - NightMode");
        modules.put("nightmode", new NightMode("nightmode").init());
        System.out.println(" - Time");
        modules.put("time", new Time("time").init());
        System.out.println(" - Coffee");
        modules.put("coffee", new Coffee("coffee").init());
        System.out.println(" - Screen");
        modules.put("screen", new Screen("screen").init());
        System.out.println(" - art");
        modules.put("art", new Art("lights").init());

        Bus.regsiter(this);

        this.wsServer = new WSServer();
        this.wsServer.start();

        amqp = new AMQP();

        System.out.println();
    }

    public static ApiHandler getInstance() {
        return instance;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Arguments arguments = new Arguments(httpExchange.getRequestURI().getQuery());
        String fullPath = httpExchange.getRequestURI().getPath().toLowerCase();
        System.out.println();
        System.out.println(httpExchange.getRemoteAddress().toString() + " | " +
                httpExchange.getRequestURI().getPath() + " | " +
                httpExchange.getRequestURI().getQuery());

        ExecuteResult executeResult = executePath(fullPath, arguments);
        String response = executeResult.getResponseText();

        httpExchange.sendResponseHeaders(executeResult.getResponseCode(), response.getBytes().length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    @Subscribe
    public ExecuteResult executePath(ActionInterface action) {
        return executePath(CONTEXT + action.getPath());
    }

    public ExecuteResult executePath(String path) {
        return executePath(path, new Arguments());
    }

    public ExecuteResult executePath(String path, Arguments arguments) {
        long start = System.currentTimeMillis();
        int responseCode = 0;

        String splittedPath[] = path.split("/");

        ResponseType responseType = ResponseType.DEFAULT;

        if (splittedPath.length < 3) {
            responseCode = 200;
        } else {

            try {
                String classString = splittedPath[2];
                switch (classString) {
                    case "info":
                        responseCode = 200;
                        if (arguments.useVoice()) Speech.tellAboutModules(modules);
                        break;
                    case "switch":
                        masterToggle(arguments);
                        responseCode = 200;
                        break;
                    case "toggle":
                        masterToggle(arguments);
                        responseCode = 200;
                        break;
                    case "speech":
                        Speech.speak("" + arguments.getText());
                        responseCode = 200;
                        break;
                    case "reload":
                        SettingsManager.getInstance().reload();
                        if (arguments.useVoice()) Speech.speakAsync("Settings reloaded");
                        responseType = ResponseType.SETTINGS;
                        responseCode = 200;
                        break;
                    case "data":
                        responseType = ResponseType.DATA;
                        responseCode = 200;
                        break;
                    case "temp-log":
                        responseType = ResponseType.TEMP_LOG;
                        responseCode = 200;
                        break;
                    default:
                        String methodString = splittedPath[3];

                        BaseModule baseModule = modules.get(classString);
                        if (baseModule != null) {
                            execute(arguments, methodString, baseModule);
                            responseCode = 200;
                        } else {
                            throw new NullPointerException("No such module");
                        }

                        break;
                }
            } catch (ArrayIndexOutOfBoundsException | NoSuchMethodException | IllegalAccessException | NullPointerException e) {
                responseCode = 404;
            } catch (InvocationTargetException e) {
                responseCode = 500;
                e.printStackTrace();
            }
        }

        responseCode = Page.makeValid(responseCode);
        String response;
        if (responseCode == 200) {
            switch (responseType) {
                case DEFAULT:
                    response = ResponseCache.getInstance().toJson();
                    break;
                case CLEAN:
                    response = "{ }";
                    break;
                case SETTINGS:
                    response = SettingsManager.getInstance().toString();
                    break;
                case DATA:
                    response = ResponseBuilder.createData();
                    break;
                case TEMP_LOG:
                    response = getTemperature().getLogData();
                    break;
                default:
                    response = "{ }";
            }
        } else {
            response = Page.getPage(responseCode);
        }

        long stop = System.currentTimeMillis();
        System.out.println("Response " + responseCode + " " + responseType + " " + (stop - start) + "ms");

        return new ExecuteResult(responseCode, response);
    }

    private void execute(Arguments arguments, String methodString, BaseModule executor)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if ("info".equals(methodString)) {
            executor.info(arguments);
            return;
        }
        Method method;
        method = executor.getClass().getDeclaredMethod(methodString, arguments.getClass());


        if (method != null) {
            method.invoke(executor, arguments);
        } else {
            throw new NullPointerException("Method not found");
        }
    }

    public synchronized void masterToggle(Arguments arguments) {
        Lights lights = getLights();
        Music music = getMusic();
        NightMode nightMode = getNightMode();
        TV tv = getTv();

        Arguments dummyArguments = new Arguments();
        User user = arguments.getUser();
        boolean checkExtra = user.isCheckExtraLight();
        if (!isOn(arguments.getUserName())) {
            if (arguments.useVoice()) Speech.speakAsync(Personalise.messageWelcome(user));
            lights.toggle1on(dummyArguments);
            lights.toggle2on(dummyArguments);
            lights.toggle5on(dummyArguments);
            if (checkExtra) {
                lights.toggle3on(dummyArguments);
                lights.toggle4on(dummyArguments);
            }
            if (!nightMode.isEnabled() && user.isAutoStartMusic()) {
                music.start(arguments);
            }
        } else {
            if (arguments.useVoice()) Speech.speakAsync(Personalise.messageLeave(user));
            music.pause(dummyArguments);
            lights.toggle1off(dummyArguments);
            lights.toggle2off(dummyArguments);
            lights.toggle5off(dummyArguments);
            if (checkExtra) {
                lights.toggle3off(dummyArguments);
                lights.toggle4off(dummyArguments);
            }
            tv.off(dummyArguments);
        }
    }

    public Music getMusic() {
        return (Music) modules.get("music");
    }

    public Lights getLights() {
        return (Lights) modules.get("lights");
    }

    public Temperature getTemperature() {
        return (Temperature) modules.get("temperature");
    }

    public TV getTv() {
        return (TV) modules.get("tv");
    }

    public Sonarr getSonarr() {
        return (Sonarr) modules.get("sonarr");
    }

    public Quotes getQuotes() {
        return (Quotes) modules.get("quotes");
    }

    public NFC getNfc() {
        return (NFC) modules.get("nfc");
    }

    public NightMode getNightMode() {
        return (NightMode) modules.get("nightmode");
    }


    public boolean isOn(String user) {
        for (Map.Entry<String, BaseModule> entry : modules.entrySet()) {
            if (entry.getValue().isEnabled(user)) return true;
        }

        return false;
    }

    private enum ResponseType {
        DEFAULT,
        CLEAN,
        SETTINGS,
        DATA,
        TEMP_LOG,
    }

    private class ExecuteResult {
        private final int responseCode;
        private final String responseText;

        public ExecuteResult(int responseCode, String responseText) {
            this.responseCode = responseCode;
            this.responseText = responseText;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getResponseText() {
            return responseText;
        }
    }
}