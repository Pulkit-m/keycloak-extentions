package org.example.events;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;

public class ResetPasswordEventListenerProvider implements EventListenerProvider {
    private static final Logger logger = Logger.getLogger(ResetPasswordEventListenerProvider.class);

    @Override
    public void onEvent(Event event) {
        if(event.getType() == EventType.RESET_PASSWORD){
            logger.info("This is the cue to sending an email to the user" +
                    "confirming that the password has been successfully reset.");
        }else if(event.getType()==EventType.UPDATE_PASSWORD){
            logger.info("This is the cue to sending an email to the user " +
                    "confirming that the password has been updated successfully"); 
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        logger.info("Do we need to overload this onEvent method in Event Listener.");
    }

    @Override
    public void close() {

    }
}
