package com.videri.core.bus;

import com.squareup.otto.Bus;

/**
 * Created by miguel on 15/04/2016.
 */
public final class BusProvider {

    private static final MainThreadBus BUS = new MainThreadBus();

    private BusProvider() {
        // No instances.
    }

    public static Bus getInstance() {
        return BUS;
    }
}
