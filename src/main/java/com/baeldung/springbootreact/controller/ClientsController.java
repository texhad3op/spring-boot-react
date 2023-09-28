//package com.baeldung.springbootreact.controller;
//
//import com.baeldung.springbootreact.domain.Client;
//import com.baeldung.springbootreact.repository.ClientRepository;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.FluxSink;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("/clients")
//public class ClientsController {
//
//    private final ClientRepository clientRepository;
//
//    public ClientsController(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }
//
//    @GetMapping
//    public List<Client> getClients() {
//        return clientRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Client getClient(@PathVariable Long id) {
//        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
//    }
//
//    @PostMapping
//    public ResponseEntity createClient(@RequestBody Client client) throws URISyntaxException {
//        Client savedClient = clientRepository.save(client);
//        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Client client) {
//        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
//        currentClient.setName(client.getName());
//        currentClient.setEmail(client.getEmail());
//        currentClient = clientRepository.save(client);
//
//        return ResponseEntity.ok(currentClient);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteClient(@PathVariable Long id) {
//        clientRepository.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/stream")
//    public ResponseEntity<Flux<String>> getNames() {
//        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.fromStream(Stream.of("aaa", "bbb","ccc")));
//    }
//
//    @GetMapping("/stream2")
//    public ResponseEntity<Flux<String>> getNames2() {
//
//    Flux<String> fluxGenerator = Flux.create(sink -> {
//        Stream.of("aaa", "bbb", "ccc", "ddd", "eee", "fff").forEach(value ->  send(sink, value));
//        sink.complete();
//    });
//
//        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM)
//                .body(fluxGenerator);
//    }
//
//
//
//    @RequestMapping(value = "/stream3", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> getNames3() {
//
//        Flux<String> fluxGenerator = Flux.create(sink -> {
//            Stream.of("aaa", "bbb", "ccc", "ddd", "eee", "fff").forEach(value ->  send(sink, value));
//            sink.complete();
//        });
//
//        return fluxGenerator;
//    }
//
//    private void send(FluxSink<String> sink, String value){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        sink.next(value);
//    }
//
//
//
//
//}
