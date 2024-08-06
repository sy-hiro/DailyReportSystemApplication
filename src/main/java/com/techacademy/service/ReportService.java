package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 全ての日報を取得
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
    
    // 1件を検索
    public Report findById(Long id) {
    	Optional<Report> option = reportRepository.findById(id);
    	Report report = option.orElse(null);
        return report;
    }
    
    //ログインしているユーザーの情報を取得
    public Report save(Report report, UserDetail userDetail) {
        // ログインしているユーザーを関連付ける
        Employee employee = userDetail.getEmployee();
        report.setEmployee(employee);
        report.setEmployeeCode(employee.getCode());
        
        // 登録日時と更新日時を設定
        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);
        
        // 日報を保存
        return reportRepository.save(report);
    }
}
