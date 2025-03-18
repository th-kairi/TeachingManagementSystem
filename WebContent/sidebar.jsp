<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="flex-shrink-0 p-3" style="width: 280px;">
    <h5>出席管理</h5>
    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ms-3">
        <li><a href="/TeachingManagementSystem/AttendanceManagement/show">出席入力</a></li>
        <li><a href="/TeachingManagementSystem/AttendanceManagement/ConfirmationOfAttendance">出席状況確認</a></li>
        <li><a href="/TeachingManagementSystem/AttendanceManagement/FixMonthlyAttendance">出席確定処理</a></li> <!-- 追加 -->
        <li><a href="">発送書類作成</a></li>
        <li><a href="/TeachingManagementSystem/AttendanceManagement/calendarCapture">カレンダー取り込み</a></li>
    </ul>

    <h5>成績管理</h5>
    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small ms-3">
        <li><a href="">成績入力</a></li>
        <li><a href="/TeachingManagementSystem/ScoreManagement/show">成績参照</a></li>
        <li><a href="/TeachingManagementSystem/ScoreManagement/ScoreUpload">得点取り込み</a></li>
    </ul>
</div>
