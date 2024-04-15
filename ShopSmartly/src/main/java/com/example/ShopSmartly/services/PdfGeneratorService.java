package com.example.ShopSmartly.services;

import com.example.ShopSmartly.dto.OrderDto;

import java.io.IOException;
import java.util.List;

public interface PdfGeneratorService {

    String generatePdf(List<OrderDto> orders) throws IOException;
}
