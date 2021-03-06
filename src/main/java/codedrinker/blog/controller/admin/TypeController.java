package codedrinker.blog.controller.admin;

import codedrinker.blog.po.Type;
import codedrinker.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/types-input";

    }

    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type dbType = typeService.getTypeByName(type.getName());
        if(dbType != null){
            attributes.addFlashAttribute("message","添加的分类已存在！");
            return "redirect:/admin/types/input";
        }
        Type t = typeService.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","抱歉，操作失败！");
        }else{
            attributes.addFlashAttribute("message","恭喜，操作成功！");
        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        //先要解决外键关系，然后再做删除
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","恭喜，操作成功！");
        return "redirect:/admin/types";
    }
}
