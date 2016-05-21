package de.htwg.se.aview.tui;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.htwg.se.minesweeper.MinesweeperModule;

/**
 * Created by GAAB on 21.05.2016.
 * Builder for tui
 */
public class TuiBuilder implements IndirectActorProducer {


    @Override
    public Actor produce() {
        Injector injector = Guice.createInjector(new MinesweeperModule());
        return injector.getInstance(Tui.class);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return Tui.class;
    }
}
