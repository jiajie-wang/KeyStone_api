package com.keystone.keystone.service;

import java.util.List;
import java.util.Optional;

import com.keystone.keystone.model.Path;
import com.keystone.keystone.repo.PathRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//本文档与所有与“Path”相关的文档全部为练习时作为参考的例子，与实际项目无关，请勿改动

@Service
public class PathServiceImpl implements PathService{
    @Autowired
    private PathRepo pathRepo;

    @Override
    public Path getPath(int pathId) {
        Optional<Path> path = pathRepo.findById(pathId);
        return path.isPresent() ? path.get() : null;
    }

    @Override
    public List<Path> getValidPath() {
        return pathRepo.findAllByIsValid(true);
    }

    @Override
    public int savePath(Path path) {
        return pathRepo.save(path).getPathId();
    }
    
}
