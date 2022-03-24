package com.keystone.keystone.service;

import java.util.List;

import com.keystone.keystone.model.Path;

//本文档与所有与“Path”相关的文档全部为练习时作为参考的例子，与实际项目无关，请勿改动

public interface PathService {
    public List<Path> getValidPath();
    public Path getPath(int pathId);
    public int savePath(Path path);
}
