package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.entity.Image;
import com.enigma.wmb_api.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path directoryPath;
    private
    @Override
    public Image create(MultipartFile multipartFile) {
        return null;
    }

    @Override
    public Resource getById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
