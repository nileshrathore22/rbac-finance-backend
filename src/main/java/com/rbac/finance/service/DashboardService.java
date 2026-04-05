package com.rbac.finance.service;

import com.rbac.finance.dto.DashboardSummaryDto;
import com.rbac.finance.dto.FinancialRecordDto;
import com.rbac.finance.model.FinancialRecord;
import com.rbac.finance.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public DashboardSummaryDto getSummary() {
        BigDecimal totalIncome = recordRepository.getTotalIncome();
        BigDecimal totalExpenses = recordRepository.getTotalExpenses();
        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        List<Object[]> categoryTotalsRaw = recordRepository.getCategoryWiseTotals();
        Map<String, BigDecimal> categoryTotals = categoryTotalsRaw.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (BigDecimal) row[1]
                ));

        List<FinancialRecordDto> recentActivity = recordRepository.findTop5ByOrderByDateDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return DashboardSummaryDto.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryWiseTotals(categoryTotals)
                .recentActivity(recentActivity)
                .build();
    }

    private FinancialRecordDto mapToDto(FinancialRecord record) {
        return FinancialRecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .type(record.getType())
                .category(record.getCategory())
                .date(record.getDate())
                .notes(record.getNotes())
                .createdById(record.getCreatedBy().getId())
                .createdByUsername(record.getCreatedBy().getUsername())
                .build();
    }
}
