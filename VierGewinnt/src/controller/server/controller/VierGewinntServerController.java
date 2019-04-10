package controller.server.controller;

import controller.server.Server;
import models.Spiel;

public class VierGewinntServerController extends ServerController {
    private Spiel spiel;

    public Spiel getSpiel() {
        return spiel;
    }
    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public VierGewinntServerController(Server server) {
        super(server);
    }
}
