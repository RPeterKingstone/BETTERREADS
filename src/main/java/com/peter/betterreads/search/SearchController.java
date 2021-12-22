package com.peter.betterreads.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private WebClient webClient;

    public SearchController(WebClient.Builder wBuilder){
        this.webClient = wBuilder.exchangeStrategies(ExchangeStrategies.builder()
        .codecs(configurer -> configurer
                  .defaultCodecs()
                  .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl("http://openlibrary.org/search.json").build();
    }

    @GetMapping(value="/search")
    public String getBookFromSearch(@RequestParam String query, Model model){
        //RequestHeadersUriSpec uriSpec = 
        Mono<SearchResult> monoResults = this.webClient.get()
        .uri("?q={query}",query)
        .retrieve().bodyToMono(SearchResult.class);
        SearchResult result = monoResults.block();
        List<SearchResultBooks> searchResultList = result.getDocs().
        stream().
        limit(10).collect(Collectors.toList());
        model.addAttribute("searchResult", result);
        return "search";
    }
}