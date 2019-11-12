package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import cn.fenrana.blog.service.IArticleService;
import cn.fenrana.blog.service.IArticleTagService;
import cn.fenrana.blog.service.IArticleCategoryService;
import cn.fenrana.blog.entity.ArticleTag;
import cn.fenrana.blog.entity.ArticleCategory;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
public class ArticleController {

    @Autowired
    IArticleService iArticleService;

    @Autowired
    IArticleTagService iArticleTagService;

    @Autowired
    IArticleCategoryService iArticleCategoryService;

    @PostMapping(value = "/admin/addArticle")
    public ResultJson addArticle(@RequestBody Map<String, Object> map){
        try{
            //保存文章
            Article article = BeanUtil.mapToBean(map, Article.class, false);
            article.setAuthor("Fenrana");
            article.setState("0");
            iArticleService.save(article);
            //保存标签
            Long articleId = article.getId();
            ArrayList<Integer> tags = (ArrayList) map.get("tag");
            tags.forEach(
                    item -> {
                        ArticleTag articleTag = new ArticleTag();
                        articleTag.setArticleId(articleId);
                        articleTag.setTagId(Long.valueOf(item));
                        iArticleTagService.save(articleTag);
                    });
            //保存分类
            Integer categoryId = (Integer) map.get("category");
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(Long.valueOf(categoryId));
            return ResultJson.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResultJson.fail();
        }

    }

    @GetMapping()
    public ResultJson<Article> articleList() {
        Page<Article> page = new Page<>();
        page.setCurrent(1);
        iArticleService.page(page);
        return ResultJson.ok();
    }
}
