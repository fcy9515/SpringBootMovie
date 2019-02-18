package com.lin.service.impl;

import com.github.pagehelper.PageHelper;
import com.lin.mapper.MovieMapper;
import com.lin.mapper.MovieMapperCustom;
import com.lin.model.Movie;
import com.lin.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 电影服务
 * @author lkmc2
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieMapperCustom movieMapperCustom;

    @Override
    public List<Movie> getHotMovieList(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        // 使用分页插件
        PageHelper.startPage(page, pageSize);

        // 创建查询条件（热度为1，根据发布日期降序排列）
        Example example = new Example(Movie.class);
        example.setOrderByClause("publish_date desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("hot", 1);

        return movieMapper.selectByExample(example);
    }

    @Override
    public List<Movie> newestMovieList(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        // 使用分页插件
        PageHelper.startPage(page, pageSize);

        // 创建查询条件（根据发布日期降序排列）
        Example example = new Example(Movie.class);
        example.setOrderByClause("publish_date desc");

        return movieMapper.selectByExample(example);
    }

    @Override
    public Movie getById(Integer id) {
        return movieMapper.selectByPrimaryKey(id);
    }

    @Override
    public Movie getLast(Integer id) {
        return movieMapper.selectByPrimaryKey(id - 1);
    }

    @Override
    public Movie getNext(Integer id) {
        return movieMapper.selectByPrimaryKey(id + 1);
    }

    @Override
    public List<Movie> randomList(Integer count) {
        if (count == null) {
            count = 10;
        }
        return movieMapperCustom.randomList(count);
    }

}