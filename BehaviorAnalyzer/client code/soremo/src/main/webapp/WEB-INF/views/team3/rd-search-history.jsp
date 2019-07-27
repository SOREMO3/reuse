<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../commons/meta.jsp"%>
    <%@ include file="../commons/core-css.jsp"%>
    <%@ include file="../commons/core-js.jsp"%>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tree/jstree.min.js"></script>
    <style type="text/css">
        #external-keyword-table td, #external-keyword-table th {
            text-align: center
        }
    </style>
</head>
<body>
<div id="sb-site">
    <%@ include file="../commons/ui/dashboard-compact.jsp"%>
    <%@ include file="../commons/ui/loader.jsp"%>
    <div id="page-wrapper">
        <div id="page-content-wrapper">
            <div id="page-content">
                <div class="container pad10A">
                    <div class="content-box">

                        <!--
                            Add Form here
                        -->

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="hide" id="extended-search-dialog" title="Relatum Select">
        <div class="form-group pad5T pad10B">
            <label class="col-sm-3 control-label">Base Keyword:</label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" class="form-control" name="base_keyword_popup"
                           placeholder="Base Keyword"> <span class="input-group-btn">
							<button id="external-keyword-pop-btn" class="btn btn-primary"
                                    type="button">Search</button>
						</span>
                </div>
            </div>
        </div>

        <div class="form-group pad5T pad10B">
            <div class="divider"></div>
            <label class="col-sm-3 control-label">Relatum List:</label>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th style="width: 18%;"></th>
                <th style="width: 68; text-align: center;">Relatum</th>
                <th style="width: 19%; text-align: right;">Similarity</th>
                <th style="width: 3%;"></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div style="width: 100%; height: 200px; overflow-y: scroll">
            <table id="external-keyword-table" class="table table-hover">
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="button-pane mrg20T">
            <button class="btn btn-info" id="external-keyword-submit-btn"
                    type="submit">확인</button>
        </div>
    </div>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/resizable.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/draggable.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/sortable.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/selectable.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/dialog/dialog.js"></script>
    <script type="text/javascript">
        var base_keyword_input = $('input[name="base_keyword"]');
        var base_keyword_input_popup = $('[name="base_keyword_popup"]');

        $('#external-keyword-btn').on(
            'click',
            function() {
                searchExtendedKeyword(base_keyword_input.val());
                $('[name="base_keyword_popup"]').val(
                    $('[name="base_keyword"]').val());

                $(function() {
                    $("#extended-search-dialog").dialog({
                        modal : true,
                        minWidth : 500,
                        minHeight : 340,
                        dialogClass : "",
                        show : "fadeIn"
                    });

                    $('.ui-dialog-content').removeClass('hide');
                });
            });

        /* Dialog의 search button */
        $('#external-keyword-pop-btn').on('click', function() {
            searchExtendedKeyword(base_keyword_input_popup.val());
        });

        /* Dialog의 search input */
        $('[name="base_keyword_popup"]').on('keydown', function(e) {
            if (e.keyCode == 13)
                searchExtendedKeyword(base_keyword_input_popup.val());
        });

        /* 체크된 Relatum submit */
        $('#external-keyword-submit-btn').on(
            'click',
            function() {
                var list = '';

                $('[name="extended-keyword-selected"]:checked').each(
                    function(i) {
                        list += $(this).val() + ",";
                    });

                $('[name="ext_rd_relation_list"]').val(list);
                $('#extended-search-dialog').dialog('close');
            });

        $('[name="base_keyword_popup"]').change(function() {
            $('[name="base_keyword"]').val($(this).val());
        });

        /* extended keyword를 비동기 검색하여 table에 출력 */
        function searchExtendedKeyword(keyword) {
            var extended_keyword_table = $('#external-keyword-table');

            $
                .ajax({
                    type : "POST",
                    url : '${pageContext.servletContext.contextPath}/rd/search/extended',
                    data : {
                        base_keyword : keyword
                    },
                    success : function(data) { /////////<-------------'list' variable: data//////////
                        extended_keyword_table.find('tbody').empty();

                        for (var i = 0; i < data.length; i++) {
                            var tmpRelatum = data[i].word;
                            var tmpSimilarity = data[i].similarity;
                            if (parseInt(tmpSimilarity) == -200) {
                                tmpSimilarity = "N/A";
                            }
                            extended_keyword_table
                                .find('tbody')
                                .append(
                                    '<tr><td style="text-algin:center;"><input type="checkbox" name="extended-keyword-selected" class="checkbox" value="'+tmpRelatum+'" /></td><td style="text-algin:center;">'
                                    + data[i].word
                                    + '</td><td style="text-algin:center;">'
                                    + tmpSimilarity
                                    + '</td><td style="text-algin:center;">&nbsp;&nbsp;</td></tr>');
                        }
                    },
                    error : function(e) {
                        console.log('ajax error');
                    }
                });
        }
    </script>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/multi-select/multiselect.js"></script>
    <script type="text/javascript">
        $(function() {
            "use strict";
            $(".multi-select").multiSelect();
            $(".ms-container").append(
                '<i class="glyph-icon icon-exchange"></i>');
        });
        $(document).ready(function() {
            $(".multi-select").multiSelect('deselect_all');
        });
    </script>


    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/datepicker/datepicker.js"></script>
    <script type="text/javascript">
        $(function() {
            "use strict";
            $('.bootstrap-datepicker').bsdatepicker({
                format : 'yyyy-mm-dd'
            });
        });
    </script>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tabs-ui/tabs.js"></script>
    <script type="text/javascript">
        $(function() {
            "use strict";
            $(".nav-tabs").tabs();
        });

        $(function() {
            "use strict";
            $(".tabs-hover").tabs({
                event : "mouseover"
            });
        });
    </script>

    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/assets-minified/admin-all-demo.js"></script>

    <script type="text/javascript">
        $('#collapseMore').on('hidden.bs.collapse', function() {
            $(this).find('select, input').attr('disabled', true);
            $(".multi-select").multiSelect('refresh');
        });

        $('#collapseMore').on('show.bs.collapse', function() {
            $(this).find('select, input').attr('disabled', false);
            $(".multi-select").multiSelect('refresh');
        });
    </script>
</div>
</body>

</html>
