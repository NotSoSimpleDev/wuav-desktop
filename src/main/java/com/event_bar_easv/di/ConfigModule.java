package com.event_bar_easv.di;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.event_bar_easv.gui.controllers.controllerFactory.ControllerFactory;
import com.event_bar_easv.gui.controllers.controllerFactory.IControllerFactory;

public class ConfigModule extends AbstractModule {
    @Override
    public void configure() {

        /* *************************************************************************
         *                                                                         *
         * CONTROLLER                                                                  *
         *                                                                         *
         **************************************************************************

        /*
         * Injection of binding
         */
        bind(IControllerFactory.class).to(ControllerFactory.class);


        /* *************************************************************************
         *                                                                         *
         * SERVICE                                                                 *
         *                                                                         *
         **************************************************************************

        /*
         * Injection of movie service
         */


        /*
         * Injection of movie service
         */

        /*
         * Binds api service
         */


        /* *************************************************************************
        *                                                                         *
        * MODEL                                                                   *
        *                                                                         *
        **************************************************************************



        /* *************************************************************************
        *                                                                         *
        * DAO                                                                     *
        *                                                                         *
        **************************************************************************
        /*
         * Bind the MovieDAO interface to the implementation
         */
        /*
         * Bind the CategoryDAO interface to the implementation
         */

        /* *************************************************************************
        *                                                                         *
        * EVENT                                                                   *
        *                                                                         *
        **************************************************************************

        /*
         * Bind even bus as in singleton scope
         * As eager singleton to ensure instantiation asap Injector is created
         */
        bind(EventBus.class).asEagerSingleton();

        /* *************************************************************************
        *                                                                         *
        * HELPER                                                                   *
        *                                                                         *
        **************************************************************************

        /*
         * Injection of Filter helper
         */
    }
}
