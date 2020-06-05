package codedrinker.blog.controller;

import codedrinker.blog.po.Type;
import codedrinker.blog.service.BlogService;
import codedrinker.blog.service.TypeService;
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
public class TypeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String typeShow(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
            Pageable pageable,@PathVariable Long id,  Model model){

        List<Type> types= typeService.listTypeTop(10000);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types", types);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
