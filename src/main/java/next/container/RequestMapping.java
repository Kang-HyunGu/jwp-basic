package next.container;


import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private Map<String, Controller> controller;

    public RequestMapping() {
        controller = new HashMap<>();
        controller.put("/user/create", new CreateUserController());
        controller.put("/user/list", new ListUserController());
        controller.put("/user/form", new ForwardController("/user/form.jsp"));
        controller.put("/user/login", new ForwardController("/user/login.jsp"));
    }

    public Controller getController(String path) {
        return controller.getOrDefault( path, new HomeController() );
    }
}
