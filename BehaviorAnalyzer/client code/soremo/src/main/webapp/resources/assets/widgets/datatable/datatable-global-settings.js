/**
 * Datatables 전역 설정
 */

$.extend(true, $.fn.dataTable.defaults, {
	'pageLength': 25,
	'language' : {
		'info' : '_PAGES_ 페이지 중 _PAGE_ 번째 페이지',
		'infoEmpty' : '표시할 데이터가 없습니다.',
		'searchPlaceholder' : '검색어를 입력하세요',
		'search' : '검색',
		'paginate' : {
			'first' : '처음',
			'previous' : '이전',
			'next' : '다음',
			'last' : '마지막'
		},
		'aria' : {
			'sortAscending' : ': 오름차순 정렬',
			'sortDescending' : ': 내림차순 정렬'
		},
		'lengthMenu' : 'Show _MENU_ entries',
		'loadingRecords' : '로드 중...',
		'processing' : '처리 중...',
		'select' : {
			'rows' : '(%d 개의 항목 선택됨)'
		},
		'lengthMenu' : '_MENU_ 개의 항목 표시'
	}
});