package com.keystone.keystone.service;

import java.util.List;

import com.keystone.keystone.model.Tag;

public interface TagService {
    //使用ID获取Tag信息
    public Tag getTag(int tagId);
    //通过tagId获得名称
    public String getTagName(int tagId);
    //从一系列tag获得所有tag名称
    public List<String> getTagsNames(List<Integer> tagsIds);
    //创建/更改tag，返回标签ID
    public int saveTag(Tag tag);
}
