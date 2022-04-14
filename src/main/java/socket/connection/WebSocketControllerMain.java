package socket.connection;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketControllerMain {
    @MessageMapping("/message")
    @SendToUser("/evaluate/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        System.out.println("here inside");
        //messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
        return "code recived";
    }

    @MessageExceptionHandler
    @SendToUser("/evaluate/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
