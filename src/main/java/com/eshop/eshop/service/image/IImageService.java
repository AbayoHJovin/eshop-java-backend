package com.eshop.eshop.service.image;

import com.eshop.eshop.dto.ImageDto;
import com.eshop.eshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
