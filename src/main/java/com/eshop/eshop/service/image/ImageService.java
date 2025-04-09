package com.eshop.eshop.service.image;

import com.eshop.eshop.dto.ImageDto;
import com.eshop.eshop.exceptions.ResourceNotFoundException;
import com.eshop.eshop.model.Image;
import com.eshop.eshop.model.Product;
import com.eshop.eshop.repository.ImageRepository;
import com.eshop.eshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(()-> new ResourceNotFoundException("Image not found with id " + imageId));
    }

    @Override
    public void deleteImageById(Long imageId) {
    imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, ()->{
        throw new ResourceNotFoundException("Image not found with id " + imageId);
    });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();
        for(MultipartFile multipartFile : files){
            try{
               Image image = new Image();
               image.setFileName(multipartFile.getName());
               image.setFileType(multipartFile.getContentType());
               image.setImage(new SerialBlob(multipartFile.getBytes()));
               image.setProducts(product);
               String defaultImageUrl = "/api/v1/images/image/download/";
               String downloadUrl=defaultImageUrl+image.getImageId();
               image.setDownloadUrl(downloadUrl);
               imageRepository.save(image);

               Image savedImage = imageRepository.save(image);
               savedImage.setDownloadUrl(defaultImageUrl + savedImage.getImageId());
               imageRepository.save(savedImage);

               ImageDto imageDto = new ImageDto();
               imageDto.setImageId(savedImage.getImageId());
               imageDto.setImageName(savedImage.getFileName());
               imageDto.setDownloadUrl(savedImage.getDownloadUrl());
               savedImageDtos.add(imageDto);
            }catch(IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
    Image image = getImageById(imageId);
    try {
        image.setFileName(file.getOriginalFilename());
        image.setFileName(file.getOriginalFilename());
        image.setImage(new SerialBlob(file.getBytes()));
        imageRepository.save(image);
    }catch (SQLException |IOException e){
         throw new RuntimeException(e.getMessage());
    }
    }
}
