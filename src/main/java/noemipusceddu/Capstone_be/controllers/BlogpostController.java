package noemipusceddu.Capstone_be.controllers;

import noemipusceddu.Capstone_be.entities.Blogpost;
import noemipusceddu.Capstone_be.exceptions.BadRequestException;
import noemipusceddu.Capstone_be.payloads.blogpost.BlogpostDTO;
import noemipusceddu.Capstone_be.payloads.blogpost.BlogpostResponseDTO;
import noemipusceddu.Capstone_be.services.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogposts")
public class BlogpostController {
    @Autowired
    private BlogpostService blogpostService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BlogpostResponseDTO createBlogpost(@RequestBody @Validated BlogpostDTO blogpost, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Blogpost newBlogpost = blogpostService.saveBlogpost(blogpost);
            return new BlogpostResponseDTO(newBlogpost.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Blogpost> getBlogposts(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy){
        return blogpostService.findAll(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Blogpost getBlogpostById(@PathVariable UUID uuid){
        return blogpostService.findById(uuid);
    }

    @PutMapping("/{id}")
    public Blogpost getBlogpostByIdAndUpdate(@PathVariable UUID id, @RequestBody BlogpostDTO body){
        return blogpostService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogpostByIdAndDelete(@PathVariable UUID id){
        blogpostService.findByIdAndDelete(id);
    }
}
