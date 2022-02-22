package com.louie.authentication.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.louie.authentication.models.Book;
import com.louie.authentication.models.LoginUser;
import com.louie.authentication.models.User;
import com.louie.authentication.services.BookService;
import com.louie.authentication.services.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userServ;
	@Autowired
	private BookService bookServ;

	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		// retrieve from the DB using the session ID
		Long userID = (Long) session.getAttribute("user_id");
		// check if userID is null
		if (userID != null) {
			return "redirect:/home";
		} else {
			// Bind empty User and LoginUser objects to the JSP
			// to capture the form input
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		}
	}

	// REGISTER##############################################################
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {

		// TO-DO Later -- call a register method in the service
		// to do some extra validations and create a new user!

		// execute the service to register first!
		userServ.register(newUser, result);
		// then check errors
		if (result.hasErrors()) {
			// Be sure to send in the empty LoginUser before
			// re-rendering the page.

			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		} else {
			// No errors!
			// in other words, log them in.

			session.setAttribute("user_id", newUser.getId());
			return "redirect:/home";
		}
	}

	// LOGIN##############################################################
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
			HttpSession session) {
		User user = userServ.login(newLogin, result);

		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		} else {
			session.setAttribute("user_id", user.getId());
			return "redirect:/home";
		}

	}

	// HOME##############################################################
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		// retrieve from the DB using the session ID
		Long userID = (Long) session.getAttribute("user_id");
		// check if userID is null
		if (userID == null) {
			return "redirect:/";
		} else {
			// go to the DB to retrieve the user using the id
			User thisUser = userServ.findOne(userID);
			List<Book> allBooks = bookServ.allBooks();
			model.addAttribute("thisUser", thisUser);
			model.addAttribute("allBooks", allBooks);
			return "home.jsp";
		}
	}

	// CREATE###################################################################
	@GetMapping("/new")
	public String newBook(HttpSession session, Model model) {
		Long userID = (Long) session.getAttribute("user_id");
		if (userID == null) {
			return "redirect:/";
		} else {
			User thisLoggedInUser = userServ.findOne(userID);
			model.addAttribute("thisUser", thisLoggedInUser);
			model.addAttribute("newBook", new Book());
			return "new.jsp";
		}
	}

	@PostMapping("/create/book")
	public String processCreateBook(@Valid @ModelAttribute("newBook") Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "new.jsp";
		} else {
			bookServ.createBook(book);
			return "redirect:/home";
		}
	}

	// SHOW#################################################################
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userID = (Long) session.getAttribute("user_id");
		if (userID == null) {
			return "redirect:/";
		} else {
			User thisUser = userServ.findOne(userID);
			model.addAttribute("thisUser", thisUser);
			Book book = bookServ.findBook(id);
			model.addAttribute("book", book);
			return "show.jsp";
		}
	}

	// EDIT#################################################################
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userID = (Long) session.getAttribute("user_id");
		Book book = bookServ.findBook(id);
		if (userID == null) {
			return "redirect:/";
//		}else if(book.getPoster().getId() != userID){
//			return "redirect:/home";
		}else {
			User thisUser = userServ.findOne(userID);
			model.addAttribute("thisUser", thisUser);
			model.addAttribute("book", book);
			return "edit.jsp";
		}
	}
	
	@PutMapping("/proccessedit/{id}")
	public String processEdit(@Valid @ModelAttribute("book") Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "edit.jsp";
		}else {
			bookServ.updateBook(book);
			return "redirect:/show/{id}";
		}
	}
	
	//DELETE#############################################################################
	
	@DeleteMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		bookServ.deleteBook(id);
		return "redirect:/home";
	}

	// logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
