package com.futureim.voicecommerce.product.service;

import com.futureim.voicecommerce.product.api.dto.BrandDto;
import com.futureim.voicecommerce.product.model.Brand;
import com.futureim.voicecommerce.product.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandDto> getAllBrands() {
        return brandRepository.findByActiveTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BrandDto getBrandById(Long id) {
        return brandRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
    }

    public BrandDto getBrandByName(String name) {
        return brandRepository.findByName(name)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with name: " + name));
    }

    public BrandDto createBrand(BrandDto brandDto) {
        if (brandRepository.existsByName(brandDto.getName())) {
            throw new IllegalArgumentException("Brand already exists with name: " + brandDto.getName());
        }
        Brand brand = convertToEntity(brandDto);
        brand = brandRepository.save(brand);
        return convertToDto(brand);
    }

    public BrandDto updateBrand(Long id, BrandDto brandDto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        
        updateBrandFromDto(brand, brandDto);
        brand = brandRepository.save(brand);
        return convertToDto(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        brand.setActive(false);
        brandRepository.save(brand);
    }

    private BrandDto convertToDto(Brand brand) {
        BrandDto dto = new BrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setDescription(brand.getDescription());
        dto.setLogoUrl(brand.getLogoUrl());
        dto.setActive(brand.isActive());
        return dto;
    }

    private Brand convertToEntity(BrandDto dto) {
        Brand brand = new Brand();
        updateBrandFromDto(brand, dto);
        return brand;
    }

    private void updateBrandFromDto(Brand brand, BrandDto dto) {
        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setLogoUrl(dto.getLogoUrl());
        brand.setActive(dto.isActive());
    }
}
