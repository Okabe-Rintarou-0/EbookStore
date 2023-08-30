package com.catstore.feign;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "book-service", configuration = FeignAutoConfiguration.class)
public interface BookServiceClient {
    @GetMapping("/book/author/{title}")
    String getAuthorByTitle(@PathVariable(name = "title") String title);
}
