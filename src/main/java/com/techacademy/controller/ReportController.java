package com.techacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {

	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	// 日報一覧画面
	@GetMapping
	public String list(Model model, @AuthenticationPrincipal UserDetail userDetail) {
		// 管理者か判断
		if (userDetail.getEmployee().getRole() == Employee.Role.ADMIN) {
			// データベースから全ての日報データを取得し、ビューに渡す
			model.addAttribute("reportList", reportService.findAll());
			// リストサイズ（件数）もビューに渡す
			model.addAttribute("listSize", reportService.findAll().size());
		} else {
			// 一般権限の場合、自分が登録した日報のみを取得
			List<Report> userReports = reportService.findByEmployee(userDetail.getEmployee());
			model.addAttribute("reportList", userReports);
			model.addAttribute("listSize", userReports.size());
		}
		return "reports/list";
	}

	// 日報詳細画面
	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("report", reportService.findById(id));
		return "reports/detail";
	}

	// 日報新規登録画面
	@GetMapping("/add")
	public String create(Model model, @AuthenticationPrincipal UserDetail userDetail) {
		// ログイン中の従業員情報を取得
		Employee employee = userDetail.getEmployee();

		// 新しいReportオブジェクトを作成し、従業員情報を設定
		Report report = new Report();
		report.setEmployee(employee);
		report.setEmployeeCode(employee.getCode());

		// reportオブジェクトをモデルに追加
		model.addAttribute("report", report);
		return "reports/new";
	}

	// 日報新規登録処理
	@PostMapping("/add")
	public String add(@Validated Report report, BindingResult result, Model model, @AuthenticationPrincipal UserDetail userDetail) {
	    if (result.hasErrors()) {
	        // ログインしているユーザー情報を再度設定
	        Employee employee = userDetail.getEmployee();
	        report.setEmployee(employee);
	        report.setEmployeeCode(employee.getCode());

	        return "reports/new";
	    }
	    
	    // タイトルの桁数チェック
	    if (report.getTitle().length() > 100) {
	        model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.TITLE_LENGTH_ERROR),
	                ErrorMessage.getErrorValue(ErrorKinds.TITLE_LENGTH_ERROR));
	        model.addAttribute("report", report);
	        Employee employee = userDetail.getEmployee();
	        report.setEmployee(employee);
	        report.setEmployeeCode(employee.getCode());
	        return "reports/new";
	    }

	    // 内容の桁数チェック
	    if (report.getContent().length() > 600) {
	        model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.CONTENT_LENGTH_ERROR),
	                ErrorMessage.getErrorValue(ErrorKinds.CONTENT_LENGTH_ERROR));
	        model.addAttribute("report", report);
	        Employee employee = userDetail.getEmployee();
	        report.setEmployee(employee);
	        report.setEmployeeCode(employee.getCode());
	        return "reports/new";
	    }

	    // 日報を保存
	    reportService.save(report, userDetail);
	    return "redirect:/reports";
	}

	// 日報更新画面を表示
	@GetMapping("/{id}/update")
	public String edit(@PathVariable Long id, Model model) {
		Report report = reportService.findById(id);
		if (report == null) {
			return "redirect:/reports";
		}
		model.addAttribute("report", report);
		return "reports/update";
	}
	
	// 日報更新処理
	@PostMapping("/{id}/update")
	public String updateReport(@Validated Report report, BindingResult result,@PathVariable Long id, Model model) {
	    if (result.hasErrors()) {
	        // バリデーションエラーがあれば、エラーメッセージと共に再度画面を表示
	        //model.addAttribute("report", report);
	        return "reports/update";
	    }

	    // 既存のレコードを更新する
	    ErrorKinds updateResult = reportService.updateReport(id, report);
	    if (updateResult == ErrorKinds.DATECHECK_ERROR) {
	    	// 同じ日付のレコードが存在する場合、エラーメッセージを表示
	        model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR),
	                          ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
	        model.addAttribute("report", report);
	        return "reports/update";
	    }

	    return "redirect:/reports";
	}
    
	// 日報削除処理
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        reportService.delete(id);
        return "redirect:/reports";
    }
}
