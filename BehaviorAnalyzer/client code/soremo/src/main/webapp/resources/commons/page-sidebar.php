<div id="page-sidebar">
	<div class="scroll-sidebar tabs ui-tabs ui-widget ui-widget-content" style="border-color:#dfe8f1;border-right-style:solid;border-right-width:1px">
		<h3 class="content-box-header bg-default">
			<span>&nbsp;</span>
			<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" role="tablist">
				<li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active" role="tab" tabindex="0" aria-controls="tabs-example-1" aria-labelledby="ui-id-7" aria-selected="true" aria-expanded="true">
					<a href="#tabs-example-1" title="Tab 1" class="ui-tabs-anchor" role="presentation" tabindex="-1" id="ui-id-7">
							Menu
						</a>
				</li>
				<li class="ui-state-default ui-corner-top" role="tab" tabindex="-1" aria-controls="tabs-example-2" aria-labelledby="ui-id-8" aria-selected="false" aria-expanded="false">
					<a href="#tabs-example-2" title="Tab 2" class="ui-tabs-anchor" role="presentation" tabindex="-1" id="ui-id-8">
							RD 
						</a>
				</li>
				<li class="ui-state-default ui-corner-top" role="tab" tabindex="-1" aria-controls="tabs-example-3" aria-labelledby="ui-id-9" aria-selected="false" aria-expanded="false">
					<a href="#tabs-example-3" title="Tab 3" class="ui-tabs-anchor" role="presentation" tabindex="-1" id="ui-id-9">
							Prop
						</a>
				</li>
			</ul>
		</h3>
		<div id="tabs-example-1" aria-labelledby="ui-id-7" class="ui-tabs-panel ui-widget-content ui-corner-bottom" role="tabpanel" aria-hidden="false">
			<ul id="sidebar-menu">
				<li class="header"><span>Research Descriptor</span></li>
				<li>
					<a href="index.html" title="Admin Dashboard"> <i class="glyph-icon icon-linecons-tv"></i> <span>RD 검색</span>
					</a>
				</li>
				<li>
					<a href="../frontend-template/index.html" title="Frontend template"> <i class="glyph-icon icon-linecons-beaker"></i> <span>RD 저작</span>
					</a>
				</li>
				<li>
					<a href="#" title="Frontend template"> <i class="glyph-icon icon-linecons-beaker"></i> <span>Relation
						관리</span>
					</a>
				</li>
				<li class="divider"></li>
				<li class="header"><span>RD 테스트</span></li>
				<li>
					<a href="#" title="Elements"> <i class="glyph-icon icon-linecons-diamond"></i> <span>테스트
						계획</span>
					</a>
				</li>
				<li>
					<a href="#" title="Frontend template"> <i class="glyph-icon icon-linecons-beaker"></i> <span>테스트 명세
						및 수행</span>
					</a>
				</li>
				<li>
					<a href="#" title="Frontend template"> <i class="glyph-icon icon-linecons-beaker"></i> <span>테스트 결과
						확인</span>
					</a>
				</li>
				<li class="divider"></li>
				<li class="header"><span>프로젝트 모니터링</span></li>
				<li>
					<a href="#" title="Pages"> <i class="glyph-icon icon-linecons-fire"></i> <span>프로젝트
						기본정보</span>
					</a>
				</li>
				<li>
					<a href="#" title="Other Pages"> <i class="glyph-icon icon-linecons-cup"></i> <span>진척도 모니터링</span>
					</a>
				</li>
				<li>
					<a href="#" title="Mailbox"> <i class="glyph-icon icon-linecons-mail"></i> <span>주요산출물
						테스트 진행</span>
					</a>
				</li>
			</ul>
		</div>
		<div id="tabs-example-2" aria-labelledby="ui-id-8" class="ui-tabs-panel ui-widget-content ui-corner-bottom" role="tabpanel" aria-hidden="true" style="display: none;">
			<div id="rd_tree" style="overflow:hidden">
				<ul>
					<li>RD 추적성 지원을 위한 관계유형 재정의
						<ul>
							<li id="child_node_1">Child node 1</li>
							<li>Child node 2</li>
						</ul>
					</li>
					<li>Root node 2</li>
				</ul>
			</div>
		</div>
		<div id="tabs-example-3" aria-labelledby="ui-id-9" class="ui-tabs-panel ui-widget-content ui-corner-bottom" role="tabpanel" aria-hidden="true" style="display: none;">
			<table id="datatable-fixedcolumns" class="table table-hover">
				<thead>
					<tr>
						<th style="width: 110px">속성</th>
						<th>값</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>RD Name</td>
						<td><a href="#" id="username" data-type="text" data-pk="1" data-title="Enter RD Name">RD 구성요소</a></td>
					</tr>
					<tr>
						<td>RD Type</td>
						<td>
							<a href="#" id="rd-type" data-type="select" data-pk="1" data-value="" data-title="Select RD Type"></a>
						</td>
					</tr>
					<tr>
						<td>Process</td>
						<td>
							<a href="#" id="rd-process" data-type="select" data-pk="1" data-value="" data-title="Select Process"></a>
						</td>
					</tr>
					<tr>
						<td>Relation</td>
						<td>
							<a href="#" id="rd-relation" data-type="select" data-pk="1" data-value="" data-title="Select Relation"></a>
						</td>
					</tr>
					<tr>
						<td>Target RD</td>
						<td><a href="#" class="editable-click" data-toggle="modal" data-target="#myModal"><span id="target-rd-name">Select RD</span></a></td>
					</tr>
					<tr>
						<td>Description</td>
						<td>
							<a href="#" id="username" data-type="text" data-pk="1" data-value="" data-title="Description"></a>
						</td>
					</tr>
					<tr>
					<td>Due Date</td>
						<td>
							<a href="#" id="username" data-type="text" data-pk="1" data-value="" data-title="Description"></a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>