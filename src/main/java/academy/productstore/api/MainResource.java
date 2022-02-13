package academy.productstore.api;

import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MainResource {

    @GetMapping
    public ResponseEntity<Links> showLinks() {
        Links links = Links.of(
                linkTo(CategoryResource.class).slash("categories").withRel("categories"),
                linkTo(ProductResource.class).slash("/products/search").withRel("search")
        );
        return ResponseEntity.ok(links);
    }
}