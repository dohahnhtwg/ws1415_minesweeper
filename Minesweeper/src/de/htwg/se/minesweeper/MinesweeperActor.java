package de.htwg.se.minesweeper;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import de.htwg.se.aview.tui.Tui;
import de.htwg.se.aview.messages.InputMessage;
import de.htwg.se.minesweeper.messages.TerminateRequest;
import java.util.Scanner;

class MinesweeperActor extends UntypedActor {

    private Scanner scanner;
    private boolean proceed = true;
    private Thread thread;

    @Override
    public void preStart() {
        final ActorRef tui = getContext().actorOf(Props.create(Tui.class), "tui");
        scanner = new Scanner(System.in);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (proceed) {
                    String input = scanner.next();
                    InputMessage msg = new InputMessage(input);
                    tui.tell(msg, getSelf());
                    if(input.equals("q")) {break;}
                }
            }
        });
        thread.start();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof TerminateRequest) {
            proceed = false;
            thread.join();
            scanner.close();
            getContext().system().terminate();
        }
    }

}
