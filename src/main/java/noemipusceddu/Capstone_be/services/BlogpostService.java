package noemipusceddu.Capstone_be.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemipusceddu.Capstone_be.entities.Blogpost;
import noemipusceddu.Capstone_be.exceptions.NotFoundException;
import noemipusceddu.Capstone_be.payloads.blogpost.BlogpostDTO;
import noemipusceddu.Capstone_be.repositories.BlogpostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BlogpostService {
    @Autowired
    private BlogpostDAO blogpostDAO;
    @Autowired
    private Cloudinary cloudinary;


    public Page<Blogpost> findAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return blogpostDAO.findAll(pageable);
    }

    public Blogpost findById(UUID id){
        return blogpostDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Blogpost saveBlogpost(BlogpostDTO body){
        Blogpost blogpost = new Blogpost();
        blogpost.setTitle(body.title());
        blogpost.setContent(body.content());
        blogpost.setDate(LocalDate.now());
        return blogpostDAO.save(blogpost);
    }

    public Blogpost findByIdAndUpdate(UUID id, BlogpostDTO body){
        Blogpost found = blogpostDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setTitle(body.title());
        found.setContent(body.content());
        return blogpostDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Blogpost found = blogpostDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public String uploadImage(UUID id, MultipartFile file) throws IOException{
        Blogpost blogpost = blogpostDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        blogpost.setImage(url);
        blogpostDAO.save(blogpost);
        return url;
    }
}
