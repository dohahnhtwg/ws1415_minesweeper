package de.htwg.se.aview.tui.messages;

import de.htwg.se.model.IStatistic;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class PrintStatisticMessage implements Serializable {

    IStatistic statistic;

    public PrintStatisticMessage(IStatistic statistic)  {
        this.statistic = statistic;
    }

    public IStatistic getStatistic()    {
        return statistic;
    }
}
