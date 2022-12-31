package ru.practicum.web.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.web.dto.endpointhit.EndpointHit;

@Component
public class EventStatClient extends BaseClient {
    private static final String POST_API = "/hit";

    public EventStatClient(@Value("${explore-with-me-stat.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + POST_API))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> sendHit(EndpointHit endpointHit) {
        return post("", endpointHit);
    }
}