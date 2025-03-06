<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">
    <p>${ errorMessage }</p>
    <form action="./show" method="post">
        <div class="d-flex align-items-center">
            <!-- クラスCDラベルと入力枠 -->
            <div class="form-group mr-3 d-flex flex-grow-0">
                <label for="classCd" class="mr-2" style="flex-grow: 1;">クラスCD</label>
                <input type="text" id="classCd" name="classCd" class="form-control" required maxlength="3" value="${classCd}" style="width: 60px;" />
            </div>

            <!-- 科目コードラベルと入力枠 -->
            <div class="form-group mr-3 d-flex flex-grow-0">
                <label for="subjectCd" class="mr-2" style="flex-grow: 1;">科目コード</label>
                <input type="text" id="subjectCd" name="subjectCd" class="form-control" required value="${subjectCd}" style="width: 60px;" />
            </div>

            <!-- 表示ボタン -->
            <button class="btn btn-primary btn-sm" type="submit">表示</button>
        </div>
    </form>

    <c:choose>
        <c:when test="${!empty scoreDetailList}">
            <div class="table-responsive" style="max-height: 400px; overflow-y: auto;">
                <table class="table table-bordered table-striped">
                    <thead class="thead-light" style="position: sticky; top: 0; z-index: 1;">
                        <tr>
                            <th>学籍番号</th>
                            <th>氏名</th>
                            <th>科目コード</th>
                            <th>科目名</th>
                            <th>年</th>
                            <th>月</th>
                            <th>得点</th>
                            <th>回数</th>
                        </tr>
                    </thead>

                    <tbody>
                        <!-- 学籍番号ごとに行を生成 -->
                        <c:forEach var="scoreDetail" items="${scoreDetailList}">
                            <tr>
                                <td>${scoreDetail.studentId}</td>
                                <td>${scoreDetail.name}</td>
                                <td>${scoreDetail.subjectCd}</td>
                                <td>${scoreDetail.subjectName}</td>
                                <td>${scoreDetail.year}</td>
                                <td>${scoreDetail.month}</td>
                                <td>${scoreDetail.score}</td>
                                <td>${scoreDetail.count}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <p>該当データなし</p>
        </c:otherwise>
    </c:choose>

</c:param>

</c:import>
