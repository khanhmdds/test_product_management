//package com.codegym.controller.API;
//
//import com.codegym.exception.DataInputException;
//import com.codegym.exception.EmailExistsException;
//import com.codegym.model.*;
//import com.codegym.model.dto.product.ProductCreateDTO;
//import com.codegym.model.dto.product.ProductDTO;
//import com.codegym.model.dto.product.ProductUpdateDTO;
//
//import com.codegym.service.product.IProductService;
//import com.codegym.utils.AppUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/products")
//public class ProductAPI {
//    @Autowired
//    private IProductService productService;
//
//    @Autowired
//    private AppUtils appUtils;
//
//    @GetMapping
//    public ResponseEntity<?> getAllByDeletedIsFalse() {
//        List<ProductDTO> productDTOS = productService.getAllProductDTO();
//        if (productDTOS.size() == 0) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
//    }
//
//    @GetMapping("/{productId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
//    public ResponseEntity<?> getById(@PathVariable String productId) {
//        long pid;
//        try {
//            pid = Long.parseLong(productId);
//        } catch (NumberFormatException e) {
//            throw new DataInputException("ID sản phẩm không hợp lệ ,vui lòng hãy nhập lại!");
//        }
//
//        Optional<Product> productOptional = productService.findById(pid);
//
//        if (!productOptional.isPresent()) {
//            throw new DataInputException("ID sản phẩm không hợp , vui lòng hãy nhập lại!");
//        }
//
//        return new ResponseEntity<>(productOptional.get().toProductDTO(), HttpStatus.OK);
//    }
//
//
//
//
//    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('ADMIN' ,'USER')")
//    public ResponseEntity<?> create(@Validated ProductCreateDTO productCreateDTO, BindingResult bindingResult) {
//        new ProductCreateDTO().validate(productCreateDTO, bindingResult);
//        MultipartFile imageFile = productCreateDTO.getFile();
//
//        if (imageFile == null) {
//            throw new DataInputException("Vui lòng chọn hình ảnh.");
//        }
//
//        if (bindingResult.hasFieldErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//
//        if (productService.existsByProductName(productCreateDTO.getProductName())) {
//            throw new EmailExistsException("Sản phẩm đã tồn tại trong hệ thống ,vui lòng hãy nhập lại!.");
//        }
//
//        productCreateDTO.setId(null);
//        Product newProduct = productService.createWithAvatar(productCreateDTO);
//
//        return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.CREATED);
//    }
//
//
//
//    @PatchMapping("/update/{productId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> update(@PathVariable Long productId, MultipartFile file, @Validated ProductUpdateDTO productUpdateDTO, BindingResult bindingResult) {
//        Optional<Product> productOptional = productService.findById(productId);
//        if (!productOptional.isPresent()) {
//            throw new DataInputException("ID sản phẩm không tồn tại ,vui lòng hãy nhập lại! ");
//        }
//
//        Product product = productOptional.get();
//
//        if (productService.existsByProductNameAndIdNot(productUpdateDTO.getProductName(), productId)) {
//            throw new DataInputException("Sản phẩm đã tồn tại trong hệ thống ,vui lòng hãy nhập lại!.");
//        }
//
//        if (bindingResult.hasFieldErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//        String productName = productUpdateDTO.getProductName();
//        BigDecimal price = new BigDecimal(Long.parseLong(productUpdateDTO.getPrice()));
//        int quantity = Integer.parseInt(productUpdateDTO.getQuantity());
//        String description = productUpdateDTO.getDescription();
//
//        product.setProductName(productName)
//                .setPrice(price)
//                .setQuantity(quantity)
//                .setDescription(description);
//        product = productService.save(product);
//
//        if(file != null){
//            product = productService.saveWithAvatar(product, file);
//        }
//        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{productId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> delete(@PathVariable Long productId) {
//
//        Optional<Product> productOptional = productService.findById(productId);
//
//        if (!productOptional.isPresent()) {
//            throw new DataInputException("ID sản phẩm không hợp lệ, vui lòng hãy nhập lại!");
//        }
//
//        try {
//            productService.softDelete(productId);
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            throw new DataInputException("Vui lòng liên hệ Administrator.");
//        }
//    }
//}
