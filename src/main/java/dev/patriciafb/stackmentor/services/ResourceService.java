package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource saveResource(String title, String description, String category, String subcategory, String url, MultipartFile file) throws IOException {
        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setCategory(category);
        resource.setSubcategory(subcategory);

        
        if ((url == null || url.isEmpty()) && file != null) {
            url = null; 
        }
        resource.setUrl(url);

      
        if (file != null && !file.isEmpty()) {
            resource.setFileName(file.getOriginalFilename());
            resource.setFileType(file.getContentType());
            resource.setFileData(file.getBytes());
        }

        return resourceRepository.save(resource);
    }

    public List<Resource> getResources(String category, String subcategory) {
        return resourceRepository.findByCategoryAndSubcategory(category, subcategory);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Optional<Resource> getResourceByFileName(String fileName) {
        return resourceRepository.findByFileName(fileName);
    }
}