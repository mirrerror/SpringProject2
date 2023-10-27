package md.mirrerror.controllers;

import jakarta.validation.Valid;
import md.mirrerror.models.Book;
import md.mirrerror.models.Person;
import md.mirrerror.services.BooksService;
import md.mirrerror.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "sort_by_year", required = false) Optional<Boolean> sortByYear,
                        @RequestParam(value = "page", required = false) Optional<Integer> page,
                        @RequestParam(value = "books_per_page", required = false) Optional<Integer> booksPerPage,
                        Model model) {

        if(sortByYear.isPresent()) {
            if(page.isPresent() && booksPerPage.isPresent()) model.addAttribute("books", booksService.findAllPaginated(page.get(), booksPerPage.get(), true));
            else model.addAttribute("books", booksService.findAll());
        } else {
            if(page.isPresent() && booksPerPage.isPresent()) model.addAttribute("books", booksService.findAllPaginated(page.get(), booksPerPage.get(), false));
            else model.addAttribute("books", booksService.findAll());
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = booksService.findOne(id);
        Person person = book.getOwner();

        model.addAttribute("book", book);
        model.addAttribute("people", peopleService.findAll());

        if(person == null) {
            model.addAttribute("person", new Person());
        } else {
            model.addAttribute("person", person);
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "redirect:/books";

        Book oldBook = booksService.findOne(id);
        oldBook.setOwner(peopleService.findOne(person.getPersonId()));

        booksService.update(id, oldBook);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "prompt", required = false) Optional<String> prompt,
                         Model model) {
        if(prompt.isPresent()) model.addAttribute("books", booksService.findByNameStartingWith(prompt.get()));
        else model.addAttribute("books", Collections.emptyList());

        model.addAttribute("isPromptPresent", prompt.isPresent());

        return "books/search";
    }

}
