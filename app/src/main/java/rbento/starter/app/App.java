
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app;

import lombok.extern.slf4j.Slf4j;
import rbento.starter.app.server.EchoServer;

@Slf4j
public class App {

    private static final int PORT = 3000;

    public static void main(String[] args) {

        log.info("Running...");

        new EchoServer(PORT).start();
    }
}
