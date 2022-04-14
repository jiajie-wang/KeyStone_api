package com.keystone.keystone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.keystone.keystone.model.Tag;
import com.keystone.keystone.repo.TagRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    @Autowired 
    private TagRepo tagRepo;

    @Override
    public Tag getTag(int tagId) {
        Optional<Tag> tag = tagRepo.findById(tagId);
        return tag.isPresent() ? tag.get() : null;
    }

    @Override
    public String getTagName(int tagId) {
        return tagRepo.findById(tagId).get().getTagName();
    }

    @Override
    public List<String> getTagsNames(List<Integer> tagsIds) {
        List<String> tagsNames = new ArrayList<String>();
        for(Tag tag: tagRepo.findAllById(tagsIds)){
            tagsNames.add(tag.getTagName());
        }
        return tagsNames;
    }

    @Override
    public int saveTag(Tag tag) {
        return tagRepo.save(tag).getTagId();
    }

    
}
