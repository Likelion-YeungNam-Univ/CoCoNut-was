package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.EnumResponseDto;
import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/enums")
public class EnumController implements EnumApi{
    @Override
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() { // 카테고리 조회
        List<EnumResponseDto> categories = Arrays.stream(Category.values())
                .map(category -> new EnumResponseDto(category.name(), category.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
    @Override
    @GetMapping("/businessTypes")
    public ResponseEntity<?> getBusinessType() { // 업종 조회
        List<EnumResponseDto> businessTypes = Arrays.stream(BusinessType.values())
                .map(businessType -> new EnumResponseDto(businessType.name(), businessType.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(businessTypes);
    }
}
