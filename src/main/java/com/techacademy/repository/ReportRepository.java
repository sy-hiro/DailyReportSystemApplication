package com.techacademy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	//
	List<Report> findByEmployee(Employee employee);
	// 同じ従業員と日付で、現在のレコード以外に重複があるか確認する
    boolean existsByEmployeeAndReportDateAndIdNot(Employee employee, LocalDate reportDate, Long id);
}
