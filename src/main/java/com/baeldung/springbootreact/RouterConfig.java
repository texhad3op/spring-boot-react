package com.baeldung.springbootreact;

import com.baeldung.springbootreact.domain.Client;
import com.baeldung.springbootreact.domain.Member;
import com.baeldung.springbootreact.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.FluxSink;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

//    @Autowired
//    ClientRepository clientRepository;

    private final MemberRepository memberRepository;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/clients"), request ->
                ServerResponse.ok().body(memberRepository.findAll(), Member.class)
        )
//                .andRoute(GET("/clients/{id}"), serverRequest -> {
//                    Long id = Long.valueOf(serverRequest.pathVariable("id"));
//                    return ServerResponse.ok().bodyValue(clientRepository.findById(id).orElseThrow(RuntimeException::new));
//                })
//                .andRoute(GET("/stream"), serverRequest -> {
//                    Flux<String> fluxGenerator = Flux.create(sink -> {
//                        //Stream.of("aaa", "bbb", "ccc", "ddd", "eee", "fff").forEach(value ->  send(sink, value));
//                        while (true) {
//                            send(sink, LocalDateTime.now().toString());
//                        }
//
//                        //sink.complete();
//                    });
//                    return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(fluxGenerator, String.class);
//                })
//                .andRoute(PUT("/clients/{id}"), serverRequest -> {
//                    Long id = Long.valueOf(serverRequest.pathVariable("id"));
//                    Mono<Client> productMono = serverRequest.bodyToMono(Client.class);
//                    return productMono.flatMap(client -> ServerResponse
//                            .ok()
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .body(Mono.just(updateClient(id, client)), Client.class));
//                })
//                .andRoute(DELETE("/clients/{id}"), serverRequest -> {
//                    Long id = Long.valueOf(serverRequest.pathVariable("id"));
//                    clientRepository.deleteById(id);
//                    return ServerResponse.ok().build();
//                })
//                .andRoute(POST("/clients"), serverRequest -> {
//                    Mono<Client> productMono = serverRequest.bodyToMono(Client.class);
//                    return productMono.flatMap(client ->
//                    {
//                        Client savedClient = clientRepository.save(client);
//                        URI uri = null;
//                        try {
//                            uri = new URI("/clients/" + savedClient.getId());
//                        } catch (URISyntaxException use) {
//                            System.out.println("ktyytctufiyf");
//                        }
//                        return ServerResponse
//                                .created(uri)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(Mono.just(savedClient), Client.class);
//                    });
//                })
                .andRoute(GET("/sample"), sr -> ServerResponse.ok().bodyValue("ok"))
                .andRoute(GET("/public/a"), sr -> ServerResponse.ok().bodyValue("ok1"))
                .andRoute(GET("/public/b"), sr -> ServerResponse.ok().bodyValue("ok2"))
//                .andRoute(GET("/public/prepared"), sr -> {
//
//                    String auth = "user:password";
////                    byte[] encodedAuth = Base64.encodeBase64(
////                            auth.getBytes(Charset.forName("US-ASCII")) );
//                    String authHeader = "Basic " + new String( encodedAuth );
//                    return ServerResponse.ok().bodyValue(authHeader);
//                })
//                .andRoute(GET("/public/{username}/{password}"), sr -> {
//
//                    String auth = sr.pathVariable("username") + ":" + sr.pathVariable("password");
////                    byte[] encodedAuth = Base64.encodeBase64(
////                            auth.getBytes(Charset.forName("US-ASCII")) );
//                    String authHeader = "Basic " + new String( encodedAuth );
//                    return ServerResponse.ok().bodyValue(authHeader);
//                })

                ;
    }

//    private Client updateClient(Long id, Client client) {
//        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
//        currentClient.setName(client.getName());
//        currentClient.setEmail(client.getEmail());
//        return clientRepository.save(client);
//    }


    private void send(FluxSink<String> sink, String value) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sink.next(value);
    }
}