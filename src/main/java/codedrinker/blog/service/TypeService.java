package codedrinker.blog.service;


import codedrinker.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageableble);
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);
    List<Type> listType();
}
