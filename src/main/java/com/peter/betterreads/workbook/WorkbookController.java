package com.peter.betterreads.workbook;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WorkbookController {

    @Autowired
    WorksRepository bookRepository;

    private final String cover_url = "http://covers.openlibrary.org/b/id/";

    @GetMapping(value="book/{bookId}")
    public String getBook(@PathVariable("bookId") String bookid, Model model){
        Optional<Works> optionalWorks = bookRepository.findById(bookid);
        if(optionalWorks.isPresent()){
            Works book = optionalWorks.get();
            String image_url = "/images/no-image.jpg";
            if(book.getCoverIds() != null && book.getCoverIds().size() > 0){
                image_url = cover_url + book.getCoverIds().get(0)+"-L.jpg";
                System.out.println("Cover url formed : -----"+ image_url);
                model.addAttribute("coverImage", image_url);
            }
            else{
                model.addAttribute("coverImage", image_url);
            }
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-present";
    }
}
