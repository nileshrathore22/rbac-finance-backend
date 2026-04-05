package com.rbac.finance.repository;

import com.rbac.finance.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, JpaSpecificationExecutor<FinancialRecord> {

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = 'INCOME'")
    BigDecimal getTotalIncome();

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = 'EXPENSE'")
    BigDecimal getTotalExpenses();

    @Query("SELECT f.category as category, SUM(f.amount) as total FROM FinancialRecord f GROUP BY f.category")
    List<Object[]> getCategoryWiseTotals();

    List<FinancialRecord> findTop5ByOrderByDateDesc();
}
