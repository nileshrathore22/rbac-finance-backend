package com.rbac.finance.service;

import com.rbac.finance.dto.FinancialRecordDto;
import com.rbac.finance.model.FinancialRecord;
import com.rbac.finance.model.TransactionType;
import com.rbac.finance.model.User;
import com.rbac.finance.repository.FinancialRecordRepository;
import com.rbac.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordDto createRecord(FinancialRecordDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = FinancialRecord.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .date(dto.getDate())
                .notes(dto.getNotes())
                .createdBy(user)
                .build();

        record = recordRepository.save(record);
        return mapToDto(record);
    }

    public Page<FinancialRecordDto> getRecords(String category, TransactionType type, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<FinancialRecord> spec = Specification.where((root, query, cb) -> cb.conjunction());

        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }

        if (type != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        }

        if (startDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), startDate));
        }

        if (endDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), endDate));
        }

        return recordRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    public FinancialRecordDto getRecordById(Long id) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return mapToDto(record);
    }

    public FinancialRecordDto updateRecord(Long id, FinancialRecordDto dto) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());

        record = recordRepository.save(record);
        return mapToDto(record);
    }

    public void deleteRecord(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }
        recordRepository.deleteById(id); // Will trigger soft delete due to @SQLDelete
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
