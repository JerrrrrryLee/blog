package codedrinker.blog.controller;

import codedrinker.blog.po.Tag;
import codedrinker.blog.service.BlogService;
import codedrinker.blog.service.TagService;
import codedrinker.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String tagShow(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
            Pageable pageable,@PathVariable Long id,  Model model){

        List<Tag> tags= tagService.listTagTop(10000);
        if(id == -1){
            id = tags.get(0).getId();
        }

        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("tags", tags);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
