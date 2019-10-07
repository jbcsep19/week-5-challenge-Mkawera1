package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    PostRepository postRepository;

    @RequestMapping("/")
    public String listPosts(Model model){
        model.addAttribute("posts", postRepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String postForm(Model model){
        model.addAttribute("post", new Post());
        return "postform";
    }
    @PostMapping("/process")
    public String processForm(@Valid Post post,
                              BindingResult result){
        if(result.hasErrors()){
            return "postform";
        }
        postRepository.save(post);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showPost(@PathVariable("id") long id, Model model)
    {model.addAttribute("post", postRepository.findById(id).get());
    return "show";
    }

    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("post", postRepository.findById(id).get());
        return "postform";
    }
    @RequestMapping("/delete/{id}")
    public String delPost(@PathVariable("id") long id){
        postRepository.deleteById(id);
        return "redirect:/";
    }
}
