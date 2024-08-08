package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

import jakarta.transaction.Transactional;

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
    
    // 特定の従業員が登録した日報を取得
    public List<Report> findByEmployee(Employee employee) {
        return reportRepository.findByEmployee(employee);
    }
    
    // 新規日報登録
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
    
//  日報を更新
//    public Report update(Report report) {
//        // 更新日時を設定
//        report.setUpdatedAt(LocalDateTime.now());
//        
//        // 日報を更新して保存
//        return reportRepository.save(report);
//    }
    
 // 日報の更新
    public ErrorKinds updateReport(Long id, Report updatedReport) {
    	Report beforeReport = reportRepository.findById(id).orElse(null);
        if (beforeReport == null) {
            return ErrorKinds.NOT_FOUND_ERROR;
        }
        
        // 他の日報と同じ日付で同じ従業員の日報が存在するか確認（現在更新中の日報は除く）
        boolean reportExists = reportRepository.existsByEmployeeAndReportDateAndIdNot(
        		beforeReport.getEmployee(), updatedReport.getReportDate(), id);

        if (reportExists) {
            // すでに同じ日付の日報が存在する場合、特定のエラーを返す
            return ErrorKinds.DATECHECK_ERROR;
        }

        // 更新するフィールドのみを上書きする
        beforeReport.setTitle(updatedReport.getTitle());
        beforeReport.setContent(updatedReport.getContent());
        beforeReport.setReportDate(updatedReport.getReportDate());
        beforeReport.setUpdatedAt(LocalDateTime.now());

        // 更新情報を保存
        reportRepository.save(beforeReport);
        return ErrorKinds.SUCCESS;
    }
    
    // 日報の更新
//    @Transactional
//    public ErrorKinds updateReport(Long id, Report report, UserDetail userDetail) {
//        Report reportToUpdate = findById(id);
//        if (reportToUpdate == null) {
//            // FIXME 指定されたデータが存在しない時、エラーを返す
//            return ErrorKinds.NOT_FOUND_ERROR;
//        }
//
//        // 更新するフィールドのみを上書きする
//        reportToUpdate.setTitle(report.getTitle());
//        reportToUpdate.setContent(report.getContent());
//        reportToUpdate.setReportDate(report.getReportDate());
//        reportToUpdate.setUpdatedAt(LocalDateTime.now());
//
//        // 更新情報を保存
//        reportRepository.save(reportToUpdate);
//        return ErrorKinds.SUCCESS;
//    }

    
    // 日報を削除
    @Transactional
    public void delete(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
