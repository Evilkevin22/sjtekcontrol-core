package nl.sjtek.control.core.modules;

import com.google.common.eventbus.Subscribe;
import nl.sjtek.control.core.events.Bus;
import nl.sjtek.control.core.network.Arguments;
import nl.sjtek.control.core.settings.SettingsManager;
import nl.sjtek.control.data.ampq.events.LightStateEvent;
import nl.sjtek.control.data.responses.LightsResponse;
import nl.sjtek.control.data.responses.Response;
import nl.sjtek.control.data.settings.User;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnusedParameters", "unused", "WeakerAccess"})
public class Lights extends BaseModule {

    private static final long UPDATE_DELAY = 100;
    private final ScheduledThreadPoolExecutor executor;
    private ScheduledFuture future;

    private boolean states[] = new boolean[10];

    public Lights(String key) {
        super(key);
        executor = new ScheduledThreadPoolExecutor(2);
    }

    @Subscribe
    public void onLightStateUpdate(LightStateEvent event) {
        states[event.getId()] = event.isEnabled();
        delayedUpdate();
    }

    @Override
    public void onStateChanged(boolean enabled, User user) {
        Arguments arguments = new Arguments();
        if (enabled) {
            toggle1on(arguments);
            toggle2on(arguments);
            toggle5on(arguments);
            if (user.isCheckExtraLight()) {
                toggle3on(arguments);
                toggle4on(arguments);
            }
        } else {
            toggle1off(arguments);
            toggle2off(arguments);
            toggle5off(arguments);
            toggle7off(arguments);
            if (user.isCheckExtraLight()) {
                toggle3off(arguments);
                toggle4off(arguments);
            }
        }
    }

    private void delayedUpdate() {
        if (future == null || future.isCancelled() || future.isDone()) {
            future = executor.schedule(new Runnable() {
                @Override
                public void run() {
                    dataChanged();
                }
            }, UPDATE_DELAY, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isOn() {
        return states[1] || states[2];
    }

    public void toggle(Arguments arguments) {
        if (states[1] || states[2] || states[5]) {
            toggle1off(arguments);
            toggle2off(arguments);
            toggle5off(arguments);
        } else {
            toggle1on(arguments);
            toggle2on(arguments);
            toggle5on(arguments);
        }
    }

    public void toggle1(Arguments arguments) {
        if (states[1]) {
            toggle1off(arguments);
        } else {
            toggle1on(arguments);
        }
    }

    public void toggle1off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(1, false));
        states[1] = false;
        dataChanged();
    }

    public void toggle1on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(1, true));
        states[1] = true;
        dataChanged();
    }

    public boolean getToggle1() {
        return states[1];
    }

    public void toggle2(Arguments arguments) {
        if (states[2]) {
            toggle2off(arguments);
        } else {
            toggle2on(arguments);
        }
    }

    public void toggle2off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(2, false));
        states[2] = false;
        dataChanged();
    }

    public void toggle2on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(2, true));
        states[2] = true;
        dataChanged();
    }

    public boolean getToggle2() {
        return states[2];
    }

    public void toggle3(Arguments arguments) {
        if (states[3]) {
            toggle3off(arguments);
        } else {
            toggle3on(arguments);
        }
    }

    public void toggle3off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(3, false));
        states[3] = false;
        dataChanged();
    }

    public void toggle3on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(3, true));
        states[3] = true;
        dataChanged();
    }

    public boolean getToggle3() {
        return states[3];
    }

    public void toggle4(Arguments arguments) {
        if (states[4]) {
            toggle4off(arguments);
        } else {
            toggle4on(arguments);
        }
    }

    public void toggle4off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(4, false));
    }

    public void toggle4on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(4, true));
    }

    public void toggle5(Arguments arguments) {
        if (states[5]) {
            toggle5off(arguments);
        } else {
            toggle5on(arguments);
        }
    }

    public void toggle5on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(5, true));
    }

    public void toggle5off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(5, false));
    }

    public void toggle6(Arguments arguments) {
        if (states[6]) {
            toggle6off(arguments);
        } else {
            toggle6on(arguments);
        }
    }

    public void toggle6on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(6, true));
    }

    public void toggle6off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(6, false));
    }

    public void toggle7(Arguments arguments) {
        if (states[7]) {
            toggle7off(arguments);
        } else {
            toggle7on(arguments);
        }
    }

    public void toggle7on(Arguments arguments) {
        Bus.post(arguments.getLightEvent(7, true));
    }

    public void toggle7off(Arguments arguments) {
        Bus.post(arguments.getLightEvent(7, false));
    }

    @Override
    public Response getResponse() {
        return new LightsResponse(states[1], states[2], states[3], states[4], states[5], states[6], states[7]);
    }

    @Override
    public String getSummaryText() {
        String result =
                (states[1] ? "1, " : "") +
                        (states[2] ? "2, " : "") +
                        (states[3] ? "3, " : "") +
                        (states[4] ? "4, " : "");
        if (result.isEmpty())
            return "The lights are turned off.";
        return "The following lights are turned on: " + result + ".";
    }

    @Override
    public boolean isEnabled(String user) {
        boolean extra = SettingsManager.getInstance().getUser(user).isCheckExtraLight();
        return states[1] || states[2] || (extra && states[3]) || (extra && states[4]) || states[5];
    }
}
